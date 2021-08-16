package entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Account {

	private int id;
	private String name;
	private String lastName;
	private LocalDate birthday;
	private String mail;
	private String password;
	private String nickName;
	private int attempts;
    private boolean blocked;
    private boolean deleted;
    private String profPicture;
    
    private ArrayList<Post> postList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attemps) {
		this.attempts = attemps;
	}

	public void addAttempt() {
		attempts++;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public ArrayList<Post> getPostList() {
		return postList;
	}

	public void setPostList(ArrayList<Post> postList) {
		this.postList = postList;
	}

	public String getProfPicture() {
		return profPicture;
	}

	public void setProfPicture(String profPicture) {
		this.profPicture = profPicture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

}
