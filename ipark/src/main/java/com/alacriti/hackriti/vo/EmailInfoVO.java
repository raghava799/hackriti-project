package com.alacriti.hackriti.vo;

public class EmailInfoVO 
{
	private String content;
	private String subject;
	private String sender;
	private String sentOn;
	private boolean isLeaveMail;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSentOn() {
		return sentOn;
	}
	public void setSentOn(String sentOn) {
		this.sentOn = sentOn;
	}
	public boolean isLeaveMail() {
		return isLeaveMail;
	}
	public void setLeaveMail(boolean isLeaveMail) {
		this.isLeaveMail = isLeaveMail;
	}
	
	
	
}
