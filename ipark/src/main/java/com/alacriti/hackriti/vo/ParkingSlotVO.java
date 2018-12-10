package com.alacriti.hackriti.vo;

public class ParkingSlotVO
{
	public ParkingSlotVO() {
	}
	private String parkingSlotId;
	private String parkingLevel;
	private String parkingNO;
	private String vehicleType;
	
	public String getParkingSlotId() {
		return parkingSlotId;
	}
	public void setParkingSlotId(String parkingSlotId) {
		this.parkingSlotId = parkingSlotId;
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
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public ParkingSlotVO(String parkingSlotId, String parkingLevel,
			String parkingNO, String vehicleType) {
		
		this.parkingSlotId = parkingSlotId;
		this.parkingLevel = parkingLevel;
		this.parkingNO = parkingNO;
		this.vehicleType = vehicleType;
	}
	

}
