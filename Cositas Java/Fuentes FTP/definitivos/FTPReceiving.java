
import java.io.IOException;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPException;
//import com.enterprisedt.net.ftp.FileTransferClient;


/**
 * 
 * [Descripcion de la clase]
 * 
 * @empresa INTAP S.A.
 * @author Esteban Vásquez González
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
public class FTPReceiving {
	/*FTP Client to establish a ftp connection with remote host*/
	//private FileTransferClient ftc;
	private FTPClient ftp;
	/*Current location into remote server*/
	
	/*Work Context this is Sending or Receiving*/
	private Receiving context;
	/**
	 * Constructor de la clase [especificando ....]
	 * @throws FTPException 
	 * @throws IOException 
	 */
	public FTPReceiving(Receiving context) throws FTPException, IOException {
		super();
		System.out.println("Iniciando la creacion de objeto FTP");
		this.context = context;
		//ftc = new FileTransferClient();
		ftp = new FTPClient();
		ftp.setRemoteHost(context.getRemoteHost());
		
		
		System.out.println("Creacion de FileTransferClient satisfactoria");
			//ftc.setRemoteHost(context.getRemoteHost());
			//ftc.setUserName(context.getUserName());
			//ftc.setPassword(context.getPassword());				
	}
	
	
	public boolean connect() throws FTPException, IOException{
		System.out.println("Intentando conectar");
		//ftc.connect();
		ftp.connect();
		ftp.login(context.getUserName(), context.getPassword());
		System.out.println("Conectado");
		
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
	
	public boolean getFile(String localPath,String remotePath) throws FTPException, IOException{
		//ftc.downloadFile(localPath, remotePath);
		ftp.get(localPath, remotePath);
		
		return true;
	}
	
	public boolean deleteFile(String remoteFileName) throws FTPException, IOException{
		//ftc.deleteFile(remoteFileName);
		ftp.delete(remoteFileName);
		
		return true;
	}
	
	public boolean disconnect() throws FTPException, IOException{
		//ftc.disconnect();
		ftp.quit();
		return true;
	}
	

}
