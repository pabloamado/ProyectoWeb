package entity;


import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

	private Properties properties;

	public MailSender() {

		properties = new Properties();

	}

	private Session getSession() {

		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", 587);
		properties.put("mail.smtp.mail.sender", MailConfig.getEMAIL_USER());
		properties.put("mail.smtp.user", MailConfig.getUSER());
		properties.put("mail.smtp.auth", "true");

		return Session.getDefaultInstance(properties);

	}

	public boolean sendEmail(MailToSend mailToSend) {

		Session session = getSession();
		boolean sent = false;

		try {

			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailToSend.getEmailRecipient()));
			message.setSubject(mailToSend.getSubject());
			message.setText(mailToSend.getMessage());

			Transport transport = session.getTransport("smtp");
			transport.connect((String) properties.get("mail.smtp.user"), MailConfig.getPASSWORD());
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

			sent = true;

		} catch (MessagingException e) {

			System.out.println("Falló el envio del mail: " + e.getStackTrace());

		}

		return sent;

	}
	
		public MailToSend prepareForgotMail(String email, String code) {
		
			return getMailToSend(email,AutomaticMsg.subjectPassCode(),AutomaticMsg.msgPassCode(code));
		}

		public MailToSend prepareRegisterMail(Account account) {

			return getMailToSend(account.getMail(),AutomaticMsg.subjectUserRegistered(),
					AutomaticMsg.msgUserRegistered(account.getName(), account.getLastName()));
		}

		public MailToSend prepareReactivateMail(Account account) {
			
			return getMailToSend(account.getMail(),AutomaticMsg.subjectReactivateAccount(),
					 AutomaticMsg.msgReactivateAccount(account.getName(), account.getLastName()));
		}

		public MailToSend prepareChangePassMail(String mail) {
			
			return getMailToSend(mail,AutomaticMsg.subjectPassUpdated(),AutomaticMsg.msgPassUpdated());
		}

		public MailToSend prepareBlockMail(String mail) {
			
			return getMailToSend(mail,AutomaticMsg.subjectBlockAccount(),AutomaticMsg.msgBlockAccount());
		}
		
		private MailToSend getMailToSend(String mail, String subject,String msg) {
			
			MailToSend preparedMail = new MailToSend();
			preparedMail.setEmailRecipient(mail);
			preparedMail.setSubject(subject);
			preparedMail.setMessage(msg); 
			
			return preparedMail;
		}


}
