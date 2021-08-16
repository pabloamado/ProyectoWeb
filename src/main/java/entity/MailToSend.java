package entity;

public class MailToSend{
    
    private String emailRecipient;
    private String subject;
    private String message;
     
    public void  setEmailRecipient(String emailRecipient){
       this.emailRecipient = emailRecipient;
    }
   
    public String getEmailRecipient(){
       return emailRecipient;
    }
   
    public String getSubject(){
       return subject;
    }
   
    public void setSubject(String subject){
       this.subject = subject;
    }
 
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

       
}
