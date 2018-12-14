package com.alacriti.hackriti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.utils.SqlQueryHelper;
import com.alacriti.hackriti.utils.Validations;
import com.alacriti.hackriti.vo.EventVO;

public class CalendarDAO extends BaseDAO {

	public EventVO getEventDetails(RequestContext context) throws BOException, SQLException {

		Connection conn = getConnection();

		PreparedStatement preparedStmt = null;
		EventVO event=null;

		try {
			
			String sqlQuery;

			if(context.getSlot().getParkerId()==null){
				
				System.out.println("***** OWNER REBOOKING CASE *****");
				System.out.println(" context.getSlot().getParkerId()==null in getEmployee Details query ......");
				
				sqlQuery = SqlQueryHelper.getEventDetails();
				
				preparedStmt = conn.prepareStatement(sqlQuery);
				preparedStmt.setString(1, context.getSlot().getEmpId());

				ResultSet rs = preparedStmt.executeQuery();

				if (rs != null && rs.next()) {

					event = new EventVO();

					event.setFloor(rs.getString("parking_level"));
					event.setFromDate(context.getSlot().getDate()); // setting request value
					event.setOwnerMailId(rs.getString("owner_mail"));
					event.setParkingType(rs.getString("parking_type"));
					event.setSlotMailId(rs.getString("slot_mail_id"));
					event.setSlotNumber(rs.getString("parking_slot_no"));
					//event.setToDate(""); // need to get from request
					//event.setUserMailId(rs.getString("parker_mail"));

				} else {
					context.setError(true);
					Validations.addErrorToContext("employee",
							"Employee Not Found with the details given, Please provide valid details and try again.",
							context);
				}
			}
			else{
				sqlQuery = SqlQueryHelper.getEventDetailsQuery();
				preparedStmt = conn.prepareStatement(sqlQuery);
				preparedStmt.setString(1, context.getSlot().getEmpId());
				preparedStmt.setString(2, context.getSlot().getParkerId());

				ResultSet rs = preparedStmt.executeQuery();

				if (rs != null && rs.next()) {

					event = new EventVO();

					event.setFloor(rs.getString("parking_level"));
					event.setFromDate(context.getSlot().getDate()); // setting request value
					event.setOwnerMailId(rs.getString("owner_mail"));
					event.setParkingType(rs.getString("parking_type"));
					event.setSlotMailId(rs.getString("slot_mail_id"));
					event.setSlotNumber(rs.getString("parking_slot_no"));
					//event.setToDate(""); // need to get from request
					event.setUserMailId(rs.getString("parker_mail"));

				} else {
					context.setError(true);
					Validations.addErrorToContext("employee",
							"Employee Not Found with the details given, Please provide valid details and try again.",
							context);
				}
			}


		} catch (SQLException e) {
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

		return event;
	}
	
	public EventVO getEventDetailsToCancelOwnerEvent(RequestContext context) throws BOException, SQLException {

		Connection conn = getConnection();

		PreparedStatement preparedStmt = null;
		EventVO event=null;

		try {
			
			String sqlQuery = SqlQueryHelper.getEventDetailsQueryToCancelOwnerEvent();

			preparedStmt = conn.prepareStatement(sqlQuery);
			preparedStmt.setString(1, context.getSlot().getEmpId());

			ResultSet rs = preparedStmt.executeQuery();

			if (rs != null && rs.next()) {
				
				 event = new EventVO();
				
				event.setFloor(rs.getString("parking_level"));
				event.setFromDate(context.getSlot().getDate()); // setting request value
				event.setOwnerMailId(rs.getString("emp_email"));
				event.setParkingType(rs.getString("parking_type"));
				event.setSlotMailId(rs.getString("slot_mail_id"));
				event.setSlotNumber(rs.getString("parking_slot_no"));
				event.setToDate(context.getSlot().getToDate()); 

			} else {
				context.setError(true);
				Validations.addErrorToContext("employee",
						"Employee Not Found with the details given, Please provide valid details and try again.",
						context);
			}
		} catch (SQLException e) {
			System.out.println("Exception caught while getting employee details : "+e.getMessage());
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

		return event;
	}


}
