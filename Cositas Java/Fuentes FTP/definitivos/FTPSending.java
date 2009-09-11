
import java.io.IOException;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;
//import com.enterprisedt.net.ftp.FileTransferClient;


/**
 * 
 * [Descripcion de la clase]
 * 
 * @empresa INTAP S.A.
 * @author David Steven Rojas
 * @fechaCreacion 10/07/2009
 * @version 1.0
 * @historial <table border="1">
 *            <tr>
 *            <td><b>Quien</b></td>
 *            <td><b>Cuando</b></td>
 *            <td><b>Que</b></td>
 *            </tr>
 *            <tr>
 *            <td>Esteban Vásquez González</td>
 *            <td>10/07/2009</td>
 *            <td>Creación</td>
 *            </tr>
 *            </table>
 */
public class FTPSending {
	/*FTP Client to establish a ftp connection with remote host*/
	//private FileTransferClient ftc;
	
	private FTPClient ftp;
	/*Current location into remote server*/
	private Sending context;
	/**
	 * Constructor de la clase [especificando ....]
	 * @throws FTPException 
	 * @throws IOException 
	 */
	public FTPSending(Sending context) throws FTPException, IOException {
		super();
		this.context = context;
		System.out.println("Iniciando creacion de ftp object");
		System.out.println("Intentando crear FTPTransferClient");
		//ftc = new FileTransferClient();
		ftp = new FTPClient();
		System.out.println("FTPTransferClient creado");
	
		ftp.setRemoteHost(context.getRemoteHost());
			//ftc.setRemoteHost(context.getRemoteHost());
			//ftc.setUserName(context.getUserName());
			//ftc.setPassword(context.getPassword());				
	}
	
	
	public boolean connect() throws FTPException, IOException{
		System.out.println("Intentando conectar");
		//ftc.connect();
		ftp.connect();
		ftp.login(context.getUserName(), context.getPassword());
		
		return true;
	}
	
	public boolean changeDirectory(String path) throws FTPException, IOException{
		//ftc.changeDirectory(path);
		ftp.chdir(path);
		
		return true;
	}
	
	public boolean checkConnection(){
		return ftp.connected();//ftc.isConnected();
	}
	
	public void updateDirectoryList() throws FTPException, IOException{
		String [] directories ;//= ftc.directoryNameList();
		directories = ftp.dir("",false);
		context.setDirectories(directories);
	}
	
	public boolean uploadFile(String localFileName,String remoteFileName) throws FTPException, IOException{
		context.getLogger().append("Renombrando archivo anteponiendo letra e");
		String tmp ="";
			remoteFileName = "/e"+remoteFileName.substring(1);
			String remote = context.getRemotePath()+remoteFileName;
		//ftc.uploadFile(localFileName, remote);
		ftp.put(localFileName, remote, false);
		context.getLogger().appendInNewLine("Removiendo letra e del archivo");
		tmp = new String(remoteFileName);
		remoteFileName = "/"+remoteFileName.substring(2);
		ftp.rename(context.getRemotePath()+tmp, context.getRemotePath()+remoteFileName);
		
		
		return true;
	}
	
	
	public boolean disconnect() throws FTPException, IOException{
		//ftc.disconnect();
		ftp.quit();
		return true;
	}
	

}
