package com.alacriti.hackriti.utils;

public class SqlQueryHelper {

	public static String getEmployeeDetailsQuery() {

		return "select * from r_employee_tbl where emp_email=?;";

	}

	public static String getEmployeeParkingMgmtDetailsQuery() {

		// get all the records with date

		return "select * from parking_slot_mgmt_tbl where date_of_availability=?";
	}

	public static String getEmployeeParkingDetailsQuery() {

		// from master table

		return "select * from employee_parking_tbl where emp_id = ?;";
	}

	public static String getUpdateParkingDetailsQuery() {

		return "update parking_slot_mgmt_tbl set parker_id=? where date_of_availability = ? and owner_id=?;";

	}

	public static String getInsertParkingDetailsQuery() {

		return "insert into parking_slot_mgmt_tbl (parking_slot_mgmt_id,parking_slot_no,owner_id,date_of_availability,date_created)"
				+ " values(?,?,?,?,?);";

	}

}
