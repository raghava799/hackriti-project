package com.alacriti.hackriti.vo;

import java.sql.Date;
import java.sql.Timestamp;

public class EmployeeVO {
	private String employeeName;
	private String employeeId;
	private String employeeRollId;
	private String emailId;
	private Date dateOfJoining;
	private String vechileType;
	private Timestamp creatationTime;
	private String preference;
	
	public EmployeeVO() {
	}
	public EmployeeVO(String employeeName, String employeeId, String emailId,
			Date dateOfJoining, String vechileType, Timestamp creatationTime,String preference) {
		this.employeeName = employeeName;
		this.employeeId = employeeId;
		this.emailId = emailId;
		this.dateOfJoining = dateOfJoining;
		this.vechileType = vechileType;
		this.creatationTime = creatationTime;
		this.preference = preference;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Date getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(Date date) {
		this.dateOfJoining = date;
	}
	public String getVechileType() {
		return vechileType;
	}
	public void setVechileType(String vechileType) {
		this.vechileType = vechileType;
	}
	public Timestamp getCreatationTime() {
		return creatationTime;
	}
	public void setCreatationTime(Timestamp creatationTime) {
		this.creatationTime = creatationTime;
	}
	public String getPreference() {
		return preference;
	}
	public void setPreference(String preference) {
		this.preference = preference;
	}
	public String getEmployeeRollId() {
		return employeeRollId;
	}
	public void setEmployeeRollId(String employeeRollId) {
		this.employeeRollId = employeeRollId;
	}
	

}
