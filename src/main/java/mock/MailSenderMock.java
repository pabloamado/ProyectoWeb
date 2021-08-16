package mock;

import entity.Account;
import entity.MailSender;
import entity.MailToSend;

public class MailSenderMock extends MailSender {
	
	@Override
	public MailToSend prepareBlockMail(String mail) {
		
		return new MailToSend();
	}
	
	@Override
	public MailToSend prepareChangePassMail(String mail) {
		
		return new MailToSend();
	}
	
	@Override
	public MailToSend prepareForgotMail(String email, String code) {

		return new MailToSend();
	}
	@Override
	public MailToSend prepareReactivateMail(Account account) {
	
		return new MailToSend();
	}
	
	@Override
	public MailToSend prepareRegisterMail(Account account) {
		
		return new MailToSend();
	}
	
	@Override
	public boolean sendEmail(MailToSend mailToSend) {
		
		return true;
	}
	
}
