package com.alacriti.hackriti.vo;

public class InputVO {
    
    private String toAddress;
    private String fromAddress;
    private String bccAddress;
    private String subject;
    private String htmlBody;
    private String textBody;
    
    public InputVO() {
	}
	public InputVO(String toAddress, String fromAddress, String bccAddress, String subject, String htmlBody,
			String textBody) {
		this.toAddress = toAddress;
		this.fromAddress = fromAddress;
		this.bccAddress = bccAddress;
		this.subject = subject;
		this.htmlBody = htmlBody;
		this.textBody = textBody;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getHtmlBody() {
		return htmlBody;
	}
	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}
	public String getTextBody() {
		return textBody;
	}
	public void setTextBody(String textBody) {
		this.textBody = textBody;
	}
	public String getToAddress() {
        return toAddress;
    }
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }
    public String getFromAddress() {
        return fromAddress;
    }
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
    public String getBccAddress() {
        return bccAddress;
    }
    public void setBccAddress(String bccAddress) {
        this.bccAddress = bccAddress;
    }

}


