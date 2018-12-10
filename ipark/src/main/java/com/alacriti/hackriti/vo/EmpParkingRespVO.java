package com.alacriti.hackriti.vo;

public class EmpParkingRespVO {
	
	private String empId;
	private String empName;
	private String parkingLevel;
	private String parkingNO;
	private String emailId;
	
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getParkingLevel() {
		return parkingLevel;
	}
	public void setParkingLevel(String parkingLevel) {
		this.parkingLevel = parkingLevel;
	}
	public String getParkingNO() {
		return parkingNO;
	}
	public void setParkingNO(String parkingNO) {
		this.parkingNO = parkingNO;
	}
	public EmpParkingRespVO(String empId, String empName, String parkingLevel,
			String parkingNO) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.parkingLevel = parkingLevel;
		this.parkingNO = parkingNO;
	}
	public EmpParkingRespVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	
	

}
