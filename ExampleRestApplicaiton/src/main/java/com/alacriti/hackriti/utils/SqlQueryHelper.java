package com.alacriti.hackriti.utils;

public class SqlQueryHelper {

	public static String getEmployeeDetailsQuery() {

		return "select emp_id, emp_no,emp_name,emp_email,emp_role,date_of_joining \n "
				+ "from r_employee_tbl ";

	}

	public static String getParkingSlotDetailsQuery() {

		// from master table

		return "select pst.parking_slot_id,pst.parking_slot_no,pst.parking_type,pst.parking_level from r_parking_slot_tbl pst "
				+ "join employee_parking_tbl ept on pst.parking_slot_id=ept.parking_slot_id where ept.emp_id=?";
	}

	public static String getOwnerSlotDetailsQuery() {

		// get all the records with date

		return "select psmt.parking_slot_no,psmt.owner_id,psmt.parker_id,psmt.date_of_availability,pst.parking_type,pst.parking_level "
				+ "from parking_slot_mgmt_tbl psmt join r_parking_slot_tbl pst "
				+ "on pst.parking_slot_no=psmt.parking_slot_no where psmt.date_of_availability=? and psmt.owner_id=?";
	}
	
	
	public static String getUserSlotDetailsQuery() {

		// get all the records with date

		return "select psmt.parking_slot_no,psmt.owner_id,psmt.parker_id,psmt.date_of_availability,pst.parking_type,pst.parking_level "
				+ "from parking_slot_mgmt_tbl psmt join r_parking_slot_tbl pst "
				+ "on pst.parking_slot_no=psmt.parking_slot_no where psmt.date_of_availability=? and psmt.parker_id=?";
	}

	public static String searchAvailableSlotsQuery() {

		// get all the records with date

		return "select psmt.parking_slot_no,psmt.owner_id,pst.parking_slot_id,pst.parking_type,pst.parking_level,et.emp_no,et.emp_name,et.emp_email,et.date_of_joining "
				+ "from parking_slot_mgmt_tbl psmt "
				+ "join r_parking_slot_tbl pst join r_employee_tbl et on (psmt.parking_slot_no=pst.parking_slot_no and et.emp_id=psmt.owner_id) "
				+ "where psmt.parker_id is NULL and psmt.date_of_availability=?";
	}

	public static String getEmployeeParkingDetailsQuery() {

		// from master table

		return "select emp_id,parking_slot_id from employee_parking_tbl where emp_id = ?";
	}

	public static String getUpdateParkingDetailsQuery() {

		return "update parking_slot_mgmt_tbl set parker_id=? where date_of_availability = ? and owner_id=? and parking_slot_no=?";

	}
	
	public static String getCancelUserSlotQuery() {

		return "update parking_slot_mgmt_tbl set parker_id=NULL where parking_slot_no=? and owner_id=? and  date_of_availability = ?";

	}

	public static String getInsertParkingDetailsQuery() {

		return "insert into parking_slot_mgmt_tbl (parking_slot_no,owner_id,date_of_availability,date_created)"
				+ " values(?,?,?,now());";

	}

}
