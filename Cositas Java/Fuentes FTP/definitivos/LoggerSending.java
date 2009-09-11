

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
public class LoggerSending {
	
	private File file;
	private FileWriter fw;
	private PrintWriter writer;
	/**
	 * Constructor de la clase [especificando ....]
	 * @throws IOException 
	 */
	public LoggerSending(String fileName) throws IOException {		
		file = new File(fileName);
		fw = new FileWriter(file);
		writer = new PrintWriter(fw);
		writer.println();
	}
	
	
	public void append(String segment){
		writer.print(" "+segment);
	}
	
	public void appendInNewLine(String segment){
		writer.println();
		writer.print(segment);
	}
	
	public void finalize(){
		writer.close();
	}
	
	public String getPath(){
		return file.getAbsolutePath();
	}
	
	
	

}
