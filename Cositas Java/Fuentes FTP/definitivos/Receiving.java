


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import com.enterprisedt.net.ftp.FTPException;

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
 *            <td>David Steven Rojas</td>
 *            <td>10/07/2009</td>
 *            <td>Creación</td>
 *            </tr>
 *            </table>
 */
public class Receiving implements Runnable{
	/*Username to log on remote server*/
	private String userName;
	/*Password to log on remote server*/
	private String password;
	/*Remote host*/
	private String remoteHost;
	/*List of directories which belong to*/
	private String[] directories;
	/*Remote path from which files will be downloaded*/
	private String remotePath;
	/*Local path where files will be saved*/
	private String localPath;
	/*Port number to establish a connection*/
	private int port;
	/*Date in which this program is invoqued*/
	private Date dateInvocation;
	/*Date for convenience, for example to save some date in which an alarm mail was generated*/
	private Date dateConvenience;
	/*Prefix to match*/
	private String prefix;
	/*Logger to hand messages*/
	private LoggerReceiving logger;
	/*Log file name*/
	private String logFileName;
	/*Log file name aux*/
	private String logFileNameAux;
	/**/
	
	/*Ftp client*/
	private FTPReceiving ftp;
	/*Array that contains path of PRAV files*/
	private ArrayList targetFiles;
	/*Amount of found files*/
	private int countFoundFiles;
	/*Amount of downloades files*/
	private int countDownloadedFiles;
	/*Amount of deleted files*/
	private int countDeletedFiles;
	/*List of PRAV files downloaded*/
	private ArrayList targetDownloaded;
	/*Contains messages to send to mail*/
	private ArrayList messagesToMail;
	/*Mail subject*/

	/**/
	private ArrayList listToSend;
	/**/
	private String mailServer;
	/**/
	private String directionFrom;
	/**/
	private String pathLogFile;
	/**/
	private static final String EMPTY = "";
	private boolean wasError;
	
	

	/**
	 * Constructor de la clase [especificando ....]
	 * @param userName
	 * @param password
	 * @param remoteHost
	 * @param directories
	 * @param remotePath
	 * @param localPath
	 */
	public Receiving(String userName, String password, String remoteHost,
			 String remotePath, String localPath, String mailServer, String directionFrom, ArrayList listToSend,String pathLogFile,
			 String prefix) {
		super();
		wasError = false;
		this.userName = userName;
		this.password = password;
		this.remoteHost = remoteHost;
		this.remotePath = remotePath;
		this.localPath = localPath;
		this.mailServer = mailServer;
		this.directionFrom = directionFrom;
		this.listToSend = listToSend;
		this.pathLogFile = pathLogFile;
		this.prefix = prefix;
		//usingLogAux = false;
		countDownloadedFiles =0;
		countFoundFiles =0;
		countDeletedFiles =0;
		dateInvocation = new Date();
		logFileName = "logFTP Prebel Avon"+" " +dateInvocation.getHours()+";"+dateInvocation.getMinutes()+";"+dateInvocation.getSeconds()+" Fecha "+dateInvocation.getDate()+" "+dateInvocation.getMonth()+" "+dateInvocation.getYear()+".txt";
		dateInvocation = new Date();
		port = 25;//Port by default
		targetFiles = new ArrayList();
		targetDownloaded = new ArrayList();
		messagesToMail = new ArrayList();
		//usingLogAux = false;
		try {
			if(!this.pathLogFile.endsWith("/"))
				logFileName = "/"+logFileName;
				
			logger = new LoggerReceiving(pathLogFile+logFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				if(pathLogFile.endsWith("/")){
					logFileNameAux = "logAux"+new Date().getTime()+".txt";
				}else{
					logFileNameAux = "/logAux"+new Date().getTime()+".txt";
				}
				
				logger = new LoggerReceiving(logFileNameAux);
				//usingLogAux = true;
				String message = "Este archivo de log auxiliar fue creado debido al problema: "+e.getMessage();
				logger.appendInNewLine(message);
				messagesToMail.add(message);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		initialize();
	}
	
	




	/**
	 * Constructor de la clase [especificando ....]
	 * @param userName
	 * @param password
	 * @param remoteHost
	 * @param directories
	 * @param remotePath
	 * @param localPath
	 * @param port
	 */
	public Receiving(String userName, String password, String remoteHost,
			String remotePath, String localPath, int port, String mailServer, String directionFrom, ArrayList listToSend,String pathLogFile,
			String prefix) {
			this.userName = userName;
			this.password = password;
			this.remoteHost = remoteHost;
			this.remotePath = remotePath;
			this.localPath = localPath;
			this.port = port;
			this.mailServer = mailServer;
			this.directionFrom = directionFrom;
			this.listToSend = listToSend;
			this.pathLogFile = pathLogFile;
			this.prefix = prefix;
			targetFiles = new ArrayList();
			logFileName = "logFTP Prebel Avon"+" " +dateInvocation.getHours()+";"+dateInvocation.getMinutes()+";"+dateInvocation.getSeconds()+" Fecha "+dateInvocation.getDate()+" "+dateInvocation.getMonth()+" "+dateInvocation.getYear()+".txt";
			dateInvocation = new Date();
			targetFiles = new ArrayList();
			targetDownloaded = new ArrayList();
			messagesToMail = new ArrayList();
			//usingLogAux = false;
			try {
				if(!this.pathLogFile.endsWith("/"))
					logFileName = "/"+logFileName;
				
				logger = new LoggerReceiving(logFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				try {
					if(pathLogFile.endsWith("/")){
						logFileNameAux = "logAux"+new Date().getTime()+".txt";
					}else{
						logFileNameAux = "/logAux"+new Date().getTime()+".txt";
					}
					
					logger = new LoggerReceiving(logFileNameAux);
					//usingLogAux = true;
					String message = "Este archivo de log auxiliar fue creado debido al problema: "+e.getMessage();
					logger.appendInNewLine(message);
					messagesToMail.add(message);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			prefix = "PRAV";
			initialize();
	}
	
	public void initProcess(){
		logger.appendInNewLine(EMPTY);
		logger.appendInNewLine("Inicio de proceso transferencia Prebel-Avon -- " + new Date());
		logger.appendInNewLine("Obteniendo lista de archivos desde "+remoteHost+" ...");
		try {
			ftp.updateDirectoryList();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			wasError = true;
			logger.appendInNewLine(EMPTY);
			String message = "Problema al obtener lista de archivos desde "+remoteHost + "Detalle: "+e.getMessage() + " "+ new Date();
			logger.appendInNewLine(message);
			messagesToMail.add(message);
			checkConnection();
			finalizeProcessWarning();
			System.exit(0);
		} catch (IOException e) {
			wasError = true;
			// TODO Auto-generated catch block
			logger.appendInNewLine(EMPTY);
			String message = "Problema al obtener lista de archivos desde "+remoteHost + "Detalle: "+e.getMessage() + " "+ new Date();
			logger.appendInNewLine(message);
			messagesToMail.add(message);
			checkConnection();
			finalizeProcessWarning();
			System.exit(0);
		}
		
		logger.appendInNewLine(EMPTY);
		logger.appendInNewLine("Lista de archivos obtenida, escaneando archivos cuyo nombre comience con "+prefix);
		getTargetFiles();
		int size =targetFiles.size();
		countFoundFiles = size;
		if(size>0){
			logger.appendInNewLine(EMPTY);
			logger.appendInNewLine("Se han encontrado "+countFoundFiles+" archivos para descargar en "+ localPath +".  Se listarán a continuación:");
			String fileName ="";
			for(int i=0;i<size;i++){
				fileName = String.valueOf(targetFiles.get(i));
				logger.appendInNewLine(fileName);
			}
			
		}else{
			logger.appendInNewLine(EMPTY);
			logger.appendInNewLine("No se han encontrado archivos para descargar.");
			finalizeProcessOK();
			System.exit(0);
		}
		
		downloadFiles();
		deleteFiles();
		if(!wasError){
			finalizeProcessOK();
		}else{
			finalizeProcessWarning();
		}
	}
	
	private void finalizeProcessOK(){
		try {
			ftp.disconnect();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			String message = "Problema al cerrar conexión FTP.  Detalle: "+e.getMessage();
			messagesToMail.add(message);
			logger.appendInNewLine(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// TODO Auto-generated catch block
			String message = "Problema al cerrar conexión FTP.  Detalle: "+e.getMessage();
			messagesToMail.add(message);
			logger.appendInNewLine(message);
		}
		logger.appendInNewLine("Proceso finalizado correctamente -- "+new Date());
		logger.appendInNewLine(EMPTY);
		logger.appendInNewLine("Resumen");
		logger.appendInNewLine("Archivos encontrados en "+ remoteHost+":");
		logger.append(""+countFoundFiles);
		logger.appendInNewLine("Archivos descargados a "+localPath+":");
		logger.append(""+countDownloadedFiles);
		logger.appendInNewLine("Archivos eliminados en "+remoteHost+":");
		logger.append(""+countDeletedFiles);
		logger.appendInNewLine("----------------------------------------------------------------------------------------------");
		logger.finalize();
	}
	
	private void finalizeProcessWarning(){
		try {
			ftp.disconnect();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			wasError = true;
			String message = "Problema al cerrar conexión FTP.  Detalle: "+e.getMessage();
			messagesToMail.add(message);
			logger.appendInNewLine(message);
		} catch (IOException e) {
			wasError = true;
			// TODO Auto-generated catch block
			String message = "Problema al cerrar conexión FTP.  Detalle: "+e.getMessage();
			messagesToMail.add(message);
			logger.appendInNewLine(message);
		}
		logger.appendInNewLine("Proceso finalizado con alertas -- "+new Date());
		logger.appendInNewLine(EMPTY);
		logger.appendInNewLine("Resumen");
		logger.appendInNewLine("Archivos encontrados en "+ remoteHost+":");
		logger.append(""+countFoundFiles);
		logger.appendInNewLine("Archivos descargados a "+localPath+":");
		logger.append(""+countDownloadedFiles);
		logger.appendInNewLine("Archivos eliminados en "+remoteHost+":");
		logger.append(""+countDeletedFiles);
		logger.appendInNewLine("----------------------------------------------------------------------------------------------");
		logger.finalize();
		String text = "Ocurrieron ciertos inconvenientes durante el proceso de transmision de archivos desde "+remoteHost+":\n ";
		String tmp="";
		for(int i=0;i<messagesToMail.size();i++){
			tmp = String.valueOf(messagesToMail.get(i))+"\n";
			text = text + tmp;
		}
		EmailMessageReceiving.sendMailWithFIle("Alerta Transmisión de archivos", text, mailServer, directionFrom, listToSend, logger.getPath(), this);
	}
	
	private void downloadFiles(){
		logger.appendInNewLine(EMPTY);
		logger.appendInNewLine("Iniciando descarga de archivos...");
		String fileName="";
		for(int i=0; i<countFoundFiles;i++){
			fileName = String.valueOf(targetFiles.get(i));
			logger.appendInNewLine(fileName);
			try {
				ftp.getFile(localPath, fileName);
				logger.append("		Descargado "+new Date() );
				countDownloadedFiles++;
				targetDownloaded.add(fileName);
			} catch (FTPException e) {
				// TODO Auto-generated catch block
				String message = "		Problema al descargar.  Detalle: "+e.getMessage()+" "+new Date();
				logger.append(message);
				messagesToMail.add(message);
				checkConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				String message = "		Problema al descargar.  Detalle: "+e.getMessage()+" "+new Date();
				logger.append(message);
				messagesToMail.add(message);
				checkConnection();
			}
			
		}
	}
	
	private void deleteFiles(){
		logger.appendInNewLine(EMPTY);
		logger.appendInNewLine("Iniciando eliminación de archivos de "+remotePath+" en "+ remoteHost +" " +new Date());
		String fileName = "";
		for(int i=0;i< countDownloadedFiles;i++){
			fileName = String.valueOf(targetDownloaded.get(i));
			logger.appendInNewLine(fileName);
			try {
				ftp.deleteFile(fileName);
				logger.append("Eliminado "+new Date());
				countDeletedFiles++;
			} catch (FTPException e) {
				// TODO Auto-generated catch block
				
				String message = "Problema al eliminar.  Detalle: "+e.getMessage() +" "+new Date();
				logger.append(message);
				messagesToMail.add(message);
				checkConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				String message = "Problema al eliminar.  Detalle: "+e.getMessage() +" "+new Date();
				logger.append(message);
				messagesToMail.add(message);
				checkConnection();
			}
			
			
		}
	}
	
	private void checkConnection(){
		if(!ftp.checkConnection()){
			logger.appendInNewLine("Se perdió conexión con "+remoteHost +" -- "+ new Date());
			finalizeProcessWarning();
			System.exit(0);
		}
	}
	
	
	
	/*This method try to establish a FTP connection*/
	private String connectFTPServer() throws FTPException, IOException {
		System.out.println("Intentando crear FTP Object");
		ftp = new FTPReceiving(this);
		System.out.println("FTP Object creado");
		ftp.connect();
		ftp.changeDirectory(remotePath);
		return "Conexión establecida";
		
	}
	/*This method try to establish a FTP connection with remote host in 3 chances and notifying whether this process was not possible*/
	private void initialize(){
		logger.appendInNewLine("----------------------------------------------------------------------------------------------");
		logger.appendInNewLine("Estableciendo conexion FTP con "+remoteHost+" ...");
		
		
		boolean ok = true;
		int tries = 1;
		while(ok){
			try {
				
				String answer = connectFTPServer();
				ok = false;
				logger.appendInNewLine(answer);
			} catch (FTPException e) {
				// TODO Auto-generated catch block
				tries++;
				logger.appendInNewLine("Imposible establecer conexion FTP. Razón: "+e.getMessage()+". Intento "+tries);
				if(tries ==3){
					String message = "Imposible establecer conexion FTP. Razón: "+e.getMessage()+". Intento "+tries;
					messagesToMail.add(message);
					logger.appendInNewLine("La aplicación cerrará ");
					finalizeProcessWarning();
					System.exit(0);
				}
			}catch (IOException e) {
				// TODO: handle exception
				tries++;
				logger.appendInNewLine("Imposible establecer conexion FTP. Razón: "+e.getMessage()+". Intento "+tries);
				if(tries ==3){
					String message = "Imposible establecer conexion FTP. Razón: "+e.getMessage()+". Intento "+tries;
					messagesToMail.add(message);
					logger.appendInNewLine("La aplicación cerrará ");
					finalizeProcessWarning();
					System.exit(0);
				}
				
			}
		}
			
	}
	/*This method selects files whose ame start with PRAV*/
	private void getTargetFiles(){
		String tmp;
		for(int i=0;i<directories.length;i++){
			tmp = directories[i];
			if(tmp.startsWith(prefix)){
				targetFiles.add(tmp);
			}
		}
	}
	
	




	/**
	 * [descripcion del método]
	 *@fechaCreacion 10/07/2009
	 *@author David Steven Rojas
	 *@param args
	 * 
	 */
	






	public String getUserName() {
		return userName;
	}






	public String getPassword() {
		return password;
	}






	public String getRemoteHost() {
		return remoteHost;
	}






	public String[] getDirectories() {
		return directories;
	}






	public String getRemotePath() {
		return remotePath;
	}






	public String getLocalPath() {
		return localPath;
	}






	public int getPort() {
		return port;
	}






	public Date getDateInvocation() {
		return dateInvocation;
	}






	public Date getDateConvenience() {
		return dateConvenience;
	}






	public String getPrefix() {
		return prefix;
	}






	public LoggerReceiving getLogger() {
		return logger;
	}






	public String getLogFileName() {
		return logFileName;
	}






	public void setDirectories(String[] directories) {
		this.directories = directories;
	}






	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		initProcess();
	}

}
