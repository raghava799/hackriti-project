package com.alacriti.hackriti.vo;

public class Slot {
	
	private String empId;
	private String date;
	private String slotNumber;
	private String parkerId;
	private String parkingLevel;
	private String parkingType;
	private String parkingSlotId;
	private String toDate;

	
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	private Employee employee; // owner
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSlotNumber() {
		return slotNumber;
	}
	public void setSlotNumber(String slotNumber) {
		this.slotNumber = slotNumber;
	}
	public String getParkerId() {
		return parkerId;
	}
	public void setParkerId(String parkerId) {
		this.parkerId = parkerId;
	}
	public String getParkingLevel() {
		return parkingLevel;
	}
	public void setParkingLevel(String parkingLevel) {
		this.parkingLevel = parkingLevel;
	}
	public String getParkingType() {
		return parkingType;
	}
	public void setParkingType(String parkingType) {
		this.parkingType = parkingType;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public String getParkingSlotId() {
		return parkingSlotId;
	}
	public void setParkingSlotId(String parkingSlotId) {
		this.parkingSlotId = parkingSlotId;
	}
	
	

}
