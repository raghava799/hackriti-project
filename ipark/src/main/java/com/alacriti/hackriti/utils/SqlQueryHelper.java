package com.alacriti.hackriti.utils;

public class SqlQueryHelper {

	public static String getEmployeeDetailsQuery() {

		return "select emp_id, emp_no,emp_name,emp_email,emp_role,date_of_joining \n " + "from r_employee_tbl ";

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

		return "select psmt.parking_slot_no,psmt.owner_id,pst.parking_slot_id,pst.parking_type,pst.parking_level,et.emp_no,et.emp_name,et.emp_email,et.date_of_joining,et.emp_role "
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

	public static String getEventDetailsQuery() {

		return "select et.emp_email as owner_mail,pt.parking_slot_no,pt.parking_type,pt.parking_level,pt.slot_mail_id,ut.emp_email as parker_mail from r_employee_tbl ut, employee_parking_tbl ept,r_parking_slot_tbl pt,r_employee_tbl et "
				+ "where ept.parking_slot_id=pt.parking_slot_id and ept.emp_id=? and et.emp_id = ept.emp_id and ut.emp_id=?";

	}

	public static String getEventDetailsQueryToCancelOwnerEvent() {

		return "select pt.parking_slot_no,pt.parking_type,pt.parking_level,pt.slot_mail_id,ut.emp_email "
				+ "from r_employee_tbl ut,r_parking_slot_tbl pt,employee_parking_tbl ept where ept.parking_slot_id=pt.parking_slot_id "
				+ "and ept.emp_id=ut.emp_id and ept.emp_id = ?";

	}
	
	public static String getEmployeeParkingSlotDetails() {

		return " SELECT ep.emp_parking_id, ep.emp_id, ep.parking_slot_id, p.parking_slot_no, "
				+ "p.parking_type, p.parking_level, p.slot_mail_id FROM employee_parking_tbl ep, "
				+ "r_employee_tbl e, r_parking_slot_tbl p WHERE  "
				+ "e.emp_id = ep.emp_id AND e.emp_email = ? AND "
				+ "ep.parking_slot_id = p.parking_slot_id";

	}

	public static String insertEmployeeLeaveDetails() {
		return " INSERT INTO employee_leave_tbl(emp_id, from_date, to_date, leave_desc, date_created, last_updated) "
				+ "VALUES(?, ?, ?, ?, now(), now()) ";
	}

	public static String insertLeaveDataForParkingFromMail() {
		return "INSERT INTO parking_slot_mgmt_tbl(parking_slot_no, owner_id,date_of_availability, date_created, last_updated) "
				+ "VALUES(?, ?, ?, now(), now()) ";
	}

	public static String getParkingLeaveData() 
	{
		return "SELECT COUNT(*) FROM parking_slot_mgmt_tbl WHERE parking_slot_no = ? AND owner_id = ? AND date_of_availability = ?";
	}


}
