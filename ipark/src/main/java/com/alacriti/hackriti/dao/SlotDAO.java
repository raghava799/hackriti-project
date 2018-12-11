package com.alacriti.hackriti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alacriti.hackriti.calendar.api.CreateCalendarEventApiHandler;
import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.utils.SqlQueryHelper;
import com.alacriti.hackriti.utils.constants.StringConstants;
import com.alacriti.hackriti.vo.Employee;
import com.alacriti.hackriti.vo.EventVO;
import com.alacriti.hackriti.vo.ParkingInfo;
import com.alacriti.hackriti.vo.Slot;

public class SlotDAO extends BaseDAO {

	// protected static Connection conn;
	//
	// public void setConn(Connection con) {
	// con = super.con;
	// }

	public Slot getSlotDetails(Slot slot, String api) throws SQLException, BOException, ParseException {

		Connection conn = getConnection();

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date parsed = format.parse(slot.getDate());
		java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());

		System.out.println("sqlDate : " + sqlDate.toString());
		System.out.println("slot.getEmpId() : " + slot.getEmpId());

		PreparedStatement preparedStmt = null;
		String sqlQuery = null;

		try {

			if (api.equals(StringConstants.ApiConstants.GET_OWNER_SLOT)) {
				sqlQuery = SqlQueryHelper.getOwnerSlotDetailsQuery();
			}

			if (api.equals(StringConstants.ApiConstants.GET_USER_SLOT)) {
				sqlQuery = SqlQueryHelper.getUserSlotDetailsQuery();
			}

			preparedStmt = conn.prepareStatement(sqlQuery);

			preparedStmt.setDate(1, sqlDate);
			preparedStmt.setString(2, slot.getEmpId());

			System.out.println("preparedStmt.toString()" + preparedStmt.toString());

			System.out.println("query" + sqlQuery);

			ResultSet rs = preparedStmt.executeQuery();

			if (rs != null && rs.next()) {

				System.out.println("rs is not null ...");

				slot.setSlotNumber(rs.getString("parking_slot_no"));
				slot.setEmpId(rs.getString("owner_id"));
				slot.setParkerId(rs.getString("parker_id"));
				// slot.setDate(rs.getString("date_of_availability"));
				slot.setParkingType(rs.getString("parking_type"));
				slot.setParkingLevel(rs.getString("parking_level"));

			}
			System.out.println("slot.getSlotNumber()" + slot.getSlotNumber());
			System.out.println("slot.getEmpId()" + slot.getEmpId());

		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {

			try {
				if (conn != null) {
					preparedStmt.close();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}

		return slot;
	}

	public List<Slot> searchAvailavleSlots(Slot slot) throws SQLException, BOException, ParseException {

		Connection conn = getConnection();

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date parsed = format.parse(slot.getDate());
		java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());

		System.out.println("sqlDate : " + sqlDate.toString());

		PreparedStatement preparedStmt = null;

		try {
			String sqlQuery = SqlQueryHelper.searchAvailableSlotsQuery();

			preparedStmt = conn.prepareStatement(sqlQuery);

			preparedStmt.setDate(1, sqlDate);

			ResultSet rs = preparedStmt.executeQuery();
			List<Slot> slots = new ArrayList<Slot>();

			if (rs != null) {

				while (rs.next()) {

					Slot slotResponse = new Slot();

					slotResponse.setSlotNumber(rs.getString("parking_slot_no"));
					slotResponse.setEmpId(rs.getString("owner_id"));
					// slotResponse.setParkerId(rs.getString("parker_id"));
					slotResponse.setDate(slot.getDate()); // taking from request
					slotResponse.setParkingType(rs.getString("parking_type"));
					slotResponse.setParkingLevel(rs.getString("parking_level"));
					slotResponse.setParkingSlotId(rs.getString("parking_slot_id"));

					Employee employee = new Employee();

					employee.setEmployeeNumber(rs.getString("emp_no"));
					employee.setEmployeeName(rs.getString(StringConstants.EMP_NAME));
					employee.setDateOfJoining(rs.getString("date_of_joining"));
					employee.setEmployeeMail(rs.getString("emp_email"));
					employee.setEmployeeId(rs.getString("owner_id"));
					employee.setEmployeeRole(rs.getString("emp_role"));
					// ParkingInfo parkingInfo = new ParkingInfo();
					//
					// parkingInfo.setParkingSlotId(rs.getString("parking_slot_id"));
					// parkingInfo.setParkingType(rs.getString("parking_type"));
					// parkingInfo.setParkingLevel(rs.getString("parking_level"));
					// parkingInfo.setParkingSlotNumber(rs.getString("parking_slot_no"));
					//
					// employee.setParkingInfo(parkingInfo);

					slotResponse.setEmployee(employee);
					slots.add(slotResponse);

				}
			}
			return slots;

		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {

			try {
				if (conn != null) {
					preparedStmt.close();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}

	}

	public Slot bookSlot(Slot slot, RequestContext context) throws SQLException, BOException, ParseException {

		Connection conn = getConnection();

		conn.setAutoCommit(false);

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date parsed = format.parse(slot.getDate());
		java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());

		System.out.println("sqlDate : " + sqlDate.toString());
		System.out.println("slot.getEmpId() : " + slot.getEmpId());

		PreparedStatement preparedStmt = null;

		try {
			String sqlQuery = SqlQueryHelper.getUpdateParkingDetailsQuery();

			preparedStmt = conn.prepareStatement(sqlQuery);

			preparedStmt.setDate(2, sqlDate);
			preparedStmt.setString(3, slot.getEmpId());
			preparedStmt.setString(1, slot.getParkerId());
			preparedStmt.setString(4, slot.getSlotNumber());

			System.out.println("preparedStmt.toString()" + preparedStmt.toString());

			System.out.println("query" + sqlQuery);

			int recordsUpdated = preparedStmt.executeUpdate();

			pushEventToCalendar(context);

			if (context.isError()) {

				System.out.println("Got errors in creating calendar event, so rollback the connection...!");
				conn.rollback();
			}
			
			else if (recordsUpdated == 1 && !context.isError()) {
				if (!conn.getAutoCommit()) {
					System.out.println("commiting ....");
					conn.commit();
				}
			}

			System.out.println("number of records updated " + recordsUpdated);

		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {

			try {
				if (conn != null) {
					preparedStmt.close();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}

		return slot;
	}

	public Slot cancelSlot(Slot slot, String api) throws SQLException, BOException, ParseException {

		Connection conn = getConnection();

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date parsed = format.parse(slot.getDate());
		java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());

		System.out.println("sqlDate : " + sqlDate.toString());
		System.out.println("slot.getEmpId() : " + slot.getEmpId());

		PreparedStatement preparedStmt = null;

		try {
			String sqlQuery = "";

			if (api.equals(StringConstants.ApiConstants.CANCEL_OWNER_SLOT)) {
				sqlQuery = SqlQueryHelper.getInsertParkingDetailsQuery();
			} else if (api.equals(StringConstants.ApiConstants.CANCEL_USER_SLOT)) {
				sqlQuery = SqlQueryHelper.getCancelUserSlotQuery();
			}

			preparedStmt = conn.prepareStatement(sqlQuery);

			preparedStmt.setDate(3, sqlDate);
			preparedStmt.setString(2, slot.getEmpId());
			preparedStmt.setString(1, slot.getSlotNumber());

			System.out.println("preparedStmt.toString()" + preparedStmt.toString());

			System.out.println("query" + sqlQuery);

			int recordsUpdated = preparedStmt.executeUpdate();

			if (recordsUpdated == 1) {
				if (!conn.getAutoCommit()) {
					System.out.println("commiting ....");
					conn.commit();
				}
			}

			System.out.println("number of records inserted " + recordsUpdated);

		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {

			try {
				if (conn != null) {
					preparedStmt.close();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}

		return slot;
	}

	private void pushEventToCalendar(RequestContext context) {

		CreateCalendarEventApiHandler handler = new CreateCalendarEventApiHandler();
		try {
			handler.handleRequest(context);
		} catch (BOException e) {
			context.setError(true);
			e.printStackTrace();
		} catch (Exception e) {
			context.setError(true);
			e.printStackTrace();
		}

	}
}
