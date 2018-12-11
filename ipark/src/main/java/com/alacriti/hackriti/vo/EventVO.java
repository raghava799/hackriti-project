package com.alacriti.hackriti.vo;

public class EventVO {

	private String fromDate;
	private String toDate;
	private String ownerMailId;
	private String userMailId;
	private String slotMailId;
	private String slotNumber;
	private String floor; 
	private String parkingType;


	
	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String date) {
		this.toDate = date;
	}

	public String getOwnerMailId() {
		return ownerMailId;
	}

	public void setOwnerMailId(String ownerMailId) {
		this.ownerMailId = ownerMailId;
	}

	public String getUserMailId() {
		return userMailId;
	}

	public void setUserMailId(String userMailId) {
		this.userMailId = userMailId;
	}

	public String getSlotMailId() {
		return slotMailId;
	}

	public void setSlotMailId(String slotMailId) {
		this.slotMailId = slotMailId;
	}

	public String getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(String slotNumber) {
		this.slotNumber = slotNumber;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getParkingType() {
		return parkingType;
	}

	public void setParkingType(String parkingType) {
		this.parkingType = parkingType;
	}


}
