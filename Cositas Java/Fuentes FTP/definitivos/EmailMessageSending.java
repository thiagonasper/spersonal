

/**
 * 
 * [Descripcion de la clase]
 * 
 * @empresa INTAP S.A.
 * @author Esteban Vásquez González
 * @fechaCreacion 09/07/2009
 * @version 1.0
 * @historial <table border="1">
 *            <tr>
 *            <td><b>Quien</b></td>
 *            <td><b>Cuando</b></td>
 *            <td><b>Que</b></td>
 *            </tr>
 *            <tr>
 *            <td>Esteban Vásquez González</td>
 *            <td>09/07/2009</td>
 *            <td>Creación</td>
 *            </tr>
 *            </table>
 */
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailMessageSending {


	final static String CONFIG_FILE= "mail.props";
	static String sServidorCorreo = "mail.intap.com.co";
	static String sCorreoOrigen="drojas@intap.com.co";
	static String[] asCorreoDestino={"drojas@intap.com.co"};
	
	public static boolean sendMail(String subject, String texto){
		Properties prop = new Properties();
		prop.put("mail.host", sServidorCorreo);
		Session mailSession = Session.getInstance(prop, null);
		Message msg = new MimeMessage(mailSession);
		try {
			msg.setFrom(new InternetAddress(sCorreoOrigen));
		
		msg.setSubject(subject);
		msg.setSentDate(new java.util.Date());
		msg.setText(texto);
		InternetAddress addresses[] = new InternetAddress[asCorreoDestino.length];
		
		for(int i=0; i != asCorreoDestino.length;i++){
			
			addresses[i] = new InternetAddress(asCorreoDestino[i]);
		}
		
		msg.setRecipients(Message.RecipientType.TO, addresses);
		Transport.send(msg);
		
		return true;
		
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static void sendMailWithFIle(String subject,String text,String mailServer, String directionFrom, ArrayList directionsTo, String pathFileToSend, Sending rec){
		if(rec != null)
			rec.getLogger().appendInNewLine("Enviando correo con alertas...");
		
		Properties prop = new Properties();
		prop.put("mail.host", mailServer);
		Session mailSession = Session.getInstance(prop, null);
		Message msg = new MimeMessage(mailSession);
		try{
				msg.setFrom(new InternetAddress(directionFrom));
				msg.setSubject(subject);
				msg.setSentDate(new java.util.Date());
				
				
				InternetAddress addresses[] = new InternetAddress[directionsTo.size()];
				
				for(int i=0; i <directionsTo.size();i++){
					String dir = String.valueOf(directionsTo.get(i));
					addresses[i] = new InternetAddress(dir);
				}
				
				msg.setRecipients(Message.RecipientType.BCC, addresses);
				
				MimeBodyPart part1 = new MimeBodyPart();
				part1.setText(text);
				
				MimeBodyPart part2 = new MimeBodyPart();
					FileDataSource fds = new FileDataSource(pathFileToSend);
					part2.setDataHandler(new DataHandler(fds));
					part2.setFileName(fds.getName());
					
				Multipart mpp = new MimeMultipart();
				mpp.addBodyPart(part1);
				mpp.addBodyPart(part2);
				
				msg.setContent(mpp);
				Transport.send(msg);
				if(rec != null)
					rec.getLogger().append("Envio satisfactorio.");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if(rec != null)
				rec.getLogger().append("Problema enviando email de alerta.  Detalle: "+e.getMessage());
		}
				
	}
	

}
