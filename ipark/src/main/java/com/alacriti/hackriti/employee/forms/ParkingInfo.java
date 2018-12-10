package com.alacriti.hackriti.employee.forms;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParkingInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1830845294673569623L;

	@JsonProperty("parking_slot_id")
	private String parking_slot_id;
	
	@JsonProperty("parking_slot_number")
	private String parking_slot_number;

	@JsonProperty("parking_type")
	private String parking_type;

	@JsonProperty("parking_level")
	private String parking_level;

	public String getParking_slot_id() {
		return parking_slot_id;
	}

	public void setParking_slot_id(String parking_slot_id) {
		this.parking_slot_id = parking_slot_id;
	}

	public String getParking_slot_number() {
		return parking_slot_number;
	}

	public void setParking_slot_number(String parking_slot_number) {
		this.parking_slot_number = parking_slot_number;
	}

	public String getParking_type() {
		return parking_type;
	}

	public void setParking_type(String parking_type) {
		this.parking_type = parking_type;
	}

	public String getParking_level() {
		return parking_level;
	}

	public void setParking_level(String parking_level) {
		this.parking_level = parking_level;
	}

}
