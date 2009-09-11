
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

/**
 * 
 */

/**
 * 
 * [Descripcion de la clase]
 * 
 * @empresa INTAP S.A.
 * @author David Steven Rojas
 * @fechaCreacion 28/07/2009
 * @version 1.0
 * @historial <table border="1">
 *            <tr>
 *            <td><b>Quien</b></td>
 *            <td><b>Cuando</b></td>
 *            <td><b>Que</b></td>
 *            </tr>
 *            <tr>
 *            <td>david Steven Rojas</td>
 *            <td>28/07/2009</td>
 *            <td>Creación</td>
 *            </tr>
 *            </table>
 */
public class DB {
	
	private ResultSet rs;
	private Connection con;
	private Statement stm;
	private LoggerSending logger;
	private String pathEmergencyLog;

	public DB(){
		
	}
	
	public void setLogEmergencyPath(String path){
		pathEmergencyLog = path;
	}
	
	/**
	 * [descripcion del método]
	 *@fechaCreacion 28/07/2009
	 *@author David Steven Rojas
	 *@param args
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DB db = new DB();
		try {
			
			File file = new File("/Java FTP/properties.properties");
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String line="";
			Properties prop = new Properties();
			line = bf.readLine().trim();
			prop.setProperty("user", line);
			line = bf.readLine().trim();
			prop.setProperty("password", line);
			line = bf.readLine().trim();
			prop.setProperty("url", line);
			line = bf.readLine().trim();
			prop.setProperty("driver", line);
			line = bf.readLine().trim();
			prop.setProperty("pathLog", line);
			line = bf.readLine().trim();
			prop.setProperty("libreria", line);
			line = bf.readLine().trim();
			prop.setProperty("tabla", line);
			bf.close();
			
			db.setLogEmergencyPath(prop.getProperty("pathLog"));
			
			db.getData(prop);
			db.fillParameters();
			db.closeAll();
		
		
		} catch (FileNotFoundException e) {
			db.generarLog(e);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			db.generarLog(e);
		}

	}
	
	private void getData(Properties prop){
		try {
			Class.forName(prop.getProperty("driver"));
			Connection con = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));
			
			 stm = con.createStatement();
			 String libreria = prop.getProperty("libreria").trim();
			 String tabla = prop.getProperty("tabla").trim();
			 String path ="";
			 if(libreria.equals("")){
				 path= tabla;
				 
			 }else{
				 path = libreria + "."+tabla;
			 }
			 
			String query = "select * from "+path+" where RPAACT = 'A'";
			 rs = stm.executeQuery(query);
			 
	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			generarLog(e);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			generarLog(e);
			
		}
		
	}
	
	private void fillParameters(){
		try {
			while(rs.next()){
				String tipoOperacion = rs.getString("RPAPRO").trim();
				String remoteServer = rs.getString("RPASER").trim();
				String user = rs.getString("RPANUS").trim();
				String pass = rs.getString("RPACUS").trim();
				String localPath = rs.getString("RPARUL").trim();
				String remotePath = rs.getString("RPARUR").trim();
				String prefix = rs.getString("RPATBA").trim();
				String logPath = rs.getString("RPARLO").trim();
				String backUpPath = rs.getString("RPARRE").trim();
				String sendTo = rs.getString("REMAIL").trim();
				String sendFrom = rs.getString("ORMAIL").trim();
				String mailServer = rs.getString("RPSERV").trim();
				
				ArrayList listToSend = new ArrayList();
				StringTokenizer st = new StringTokenizer(sendTo,",");
				String tmp="";
				while(st.hasMoreTokens()){
					tmp = st.nextToken();
					listToSend.add(tmp);
				}
				
				if(prefix.endsWith("*"))
					prefix = prefix.substring(0, prefix.length()-1);
				
				if(tipoOperacion.equals("E")){
					
					Thread s = new Thread(new Sending(user,pass,remoteServer,remotePath,localPath,mailServer,sendFrom,listToSend,logPath,prefix,backUpPath));
					s.run();
						
				}else if(tipoOperacion.equals("R")){
					
					Thread r = new Thread(new Receiving(user,pass,remoteServer,remotePath,localPath,mailServer,sendFrom,listToSend,logPath,prefix));
					r.run();
					
				}else{
					
					
				}		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			generarLog(e);
		}
	}
	
	private void closeAll(){
		
		try {
			if(rs != null && stm != null && con != null){
			rs.close();
			stm.close();
			con.close();
			}
		} catch (SQLException e) {
		   generarLog(e);
		}
		
		
	}
	
	private void generarLog(Exception e){
		if(!pathEmergencyLog.trim().equals("")){
			Date dateInvocation = new Date();
			String logFileName = "logErrorConexionDB"+" " +dateInvocation.getHours()+";"+dateInvocation.getMinutes()+";"+dateInvocation.getSeconds()+" Fecha "+dateInvocation.getDate()+" "+dateInvocation.getMonth()+" "+dateInvocation.getYear()+".txt";
			if(!pathEmergencyLog.endsWith("/")){
				pathEmergencyLog = pathEmergencyLog + "/";
			}
			try {
				logger = new LoggerSending(pathEmergencyLog+logFileName);
				logger.appendInNewLine("Error fatal al tratar de obtener datos desde DB");
				logger.appendInNewLine("Detalle técnico:");
				logger.appendInNewLine(e.getMessage());
				logger.finalize();
				
			} catch (IOException ee) {
				// TODO Auto-generated catch block
				
			}
		}
		
	}

}
