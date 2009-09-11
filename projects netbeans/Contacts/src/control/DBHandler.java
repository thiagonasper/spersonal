package control;

import java.io.IOException;
import java.sql.Connection;

import com.javaexchange.dbConnectionBroker.DbConnectionBroker;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @fechaCreacion 07/07/2009
 * @version 1.0
 */
public class DBHandler {
	
	public static DbConnectionBroker pool;
	
	public static DbConnectionBroker getPool(){
		if(pool != null){
			return pool;
		}
		return null;	
	}
	
	private static boolean createPool(){
		try {
                    pool = new DbConnectionBroker("com.mysql.jdbc.Driver", "jdbc:mysql://192.168.0.1/contactos_intap", "contactos", "contactos", 10, 15, "C:\\log.txt", 20);
                    //pool = new DbConnectionBroker("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/contactos_intap", "contactos", "contactos", 10, 15, "D:\\log.txt", 20);
            //pool = new DbConnectionBroker("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/contactos_intap", "contactos", "contactos", 10, 15, "C:\\log.txt", 20);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo establecer conexión con la base de datos.  Razón: "+e.getMessage()+"\n"
                    +e.getLocalizedMessage());
			return false;
		}
		
	}
	
	public static boolean initialize(){
		if(pool == null){
			return createPool();
		}else{
			return false;
		}
	}
	
	public Connection getConnection(){
		if(pool!=null){
			pool.getConnection();
		}
		return null;
	}
	
	public boolean freeConnection(Connection connection){
		if(pool!=null){
			pool.freeConnection(connection);
			return true;
		}
		return false;
	}	

}
