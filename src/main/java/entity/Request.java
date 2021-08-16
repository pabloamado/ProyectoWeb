package entity;

public class Request {
	   
	private int requestId;
    private int idUserSender;
    private String nicknameUserSender;
    private String profPic;
    
    public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	
    public int getIdUserSender() {
        return idUserSender;
    }

    public void setIdUserSender(int idUserSender) {
        this.idUserSender = idUserSender;
    }

    public String getNicknameUserSender() {
        return nicknameUserSender;
    }

    public void setNicknameUserSender(String nicknameUserSender) {
        this.nicknameUserSender = nicknameUserSender;
    }

	public String getProfPic() {
		return profPic;
	}

	public void setProfPic(String profPic) {
		this.profPic = profPic;
	}
 
}