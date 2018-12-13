package com.alacriti.hackriti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mortbay.log.Log;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.gmail.api.service.account.ReadMails;
import com.alacriti.hackriti.utils.SqlQueryHelper;
import com.alacriti.hackriti.utils.Validations;
import com.alacriti.hackriti.utils.constants.Excelconstants;
import com.alacriti.hackriti.utils.constants.StringConstants;
import com.alacriti.hackriti.vo.Employee;
import com.alacriti.hackriti.vo.EmployeeParkingVO;
import com.alacriti.hackriti.vo.EmployeeVO;
import com.alacriti.hackriti.vo.ParkingInfo;
import com.alacriti.hackriti.vo.ParkingSlotVO;

public class EmployeeDAO extends BaseDAO 
{
	final static Logger logger = Logger.getLogger(EmployeeDAO.class);
	
    public EmployeeDAO()
    {
    }

    protected static Connection connection;
    
    public void setConnection(Connection conn)
    {
        connection = conn;
    }

	
	/**
     * Closes PreparedStatment
     *
     * @param ps
     */
    protected void close(PreparedStatement ps)
    {
        try
        {
            if (ps != null)
                ps.close();
        }
        catch (SQLException sqe)
        {
        	System.out.println("Exception caught while closing prepared statement");
        }
    }

    /**
     * Closes ResultSet
     *
     * @param rs
     */
    protected void close(ResultSet rs)
    {
        try
        {
            if (rs != null)
                rs.close();
        }
        catch (SQLException sqe)
        {	
        	System.out.println("Exception caught while closing result set");
        }
    }


	public Employee getEmployeeDetails(Employee employee,RequestContext context) throws SQLException, BOException {

		Connection conn = getConnection();

		PreparedStatement preparedStmt = null;

		try {
			String sqlQuery = SqlQueryHelper.getEmployeeDetailsQuery();
			if (employee.getEmployeeMail() != null) {
				sqlQuery = sqlQuery + "where emp_email=? \n";
				preparedStmt = conn.prepareStatement(sqlQuery);
				preparedStmt.setString(1, employee.getEmployeeMail());
			} else if (employee.getEmployeeId() != null) {
				sqlQuery = sqlQuery + "where emp_id=? \n";
				preparedStmt = conn.prepareStatement(sqlQuery);
				preparedStmt.setString(1, employee.getEmployeeId());
			}

			ResultSet rs = preparedStmt.executeQuery();

			if (rs != null && rs.next()) {
				employee.setEmployeeNumber(rs.getString("emp_no"));
				employee.setEmployeeName(rs.getString(StringConstants.EMP_NAME));
				employee.setDateOfJoining(rs.getString("date_of_joining"));
				employee.setEmployeeId(rs.getString("emp_id"));
				employee.setEmployeeRole(rs.getString("emp_role"));
				employee.setEmployeeMail(rs.getString("emp_email"));

			}
			else{
				context.setError(true);
				Validations.addErrorToContext("employee","Employee Not Found with the details given, Please provide valid details and try again.", context);
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

		return employee;
	}

	public Employee getParkingDetails(Employee employee) throws SQLException, BOException {

		Connection conn = getConnection();

		PreparedStatement preparedStmt = null;

		try {
			String sqlQuery = SqlQueryHelper.getParkingSlotDetailsQuery();

			preparedStmt = conn.prepareStatement(sqlQuery);
			preparedStmt.setString(1, employee.getEmployeeId());

			System.out.print("query in getParking deatils : " + preparedStmt.toString());

			ResultSet rs = preparedStmt.executeQuery();

			if (rs != null && rs.next()) {

				ParkingInfo parkingInfo = new ParkingInfo();

				parkingInfo.setParkingSlotId(rs.getString("parking_slot_id"));
				parkingInfo.setParkingSlotNumber(rs.getString("parking_slot_no"));
				parkingInfo.setParkingType(rs.getString("parking_type"));
				parkingInfo.setParkingLevel(rs.getString("parking_level"));

				employee.setParkingInfo(parkingInfo);

			}
			else{
				System.out.println("Parking Details Not Found");
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

		return employee;
	}

	public List<EmployeeVO> getEmployeeDetails(EmployeeVO emp) throws BOException {
		logger.info("getEmployeeDetails start");
		Connection con = getConnection();
		List<EmployeeVO> empDetailsList = new ArrayList<EmployeeVO>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT emp_id,emp_no,emp_name,emp_email,date_of_joining FROM r_employee_tbl order by date_of_joining";

			ps = con.prepareStatement(sql);
			logger.info("sql :" + sql);

			rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				int i = 0;
				EmployeeVO empVO = new EmployeeVO();
				empVO.setEmployeeId(rs.getString(++i));
				empVO.setEmployeeRollId(rs.getString(++i));
				empVO.setEmployeeName(rs.getString(++i));
				empVO.setEmailId(rs.getString(++i));
				empVO.setDateOfJoining(rs.getDate(++i));
				empDetailsList.add(empVO);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					ps.close();
					con.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("getEmployeeDetails end");
		return empDetailsList;
	}

	public Map<Integer, ArrayList<ParkingSlotVO>> getParkingSlotDetails() throws BOException {
		logger.info("getParkingSlotDetails start");
		Connection con = getConnection();
		ArrayList<ParkingSlotVO> twoWheelerSlotDetailsList = new ArrayList<ParkingSlotVO>();
		ArrayList<ParkingSlotVO> fourWheelerSlotDetailsList = new ArrayList<ParkingSlotVO>();
		Map<Integer, ArrayList<ParkingSlotVO>> slotDetailsList = new HashMap<Integer, ArrayList<ParkingSlotVO>>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT parking_slot_id,parking_slot_no,parking_type,parking_level FROM r_parking_slot_tbl order by parking_slot_no";

			ps = con.prepareStatement(sql);
			logger.info("sql :" + sql);
			rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				int i = 0;
				ParkingSlotVO slotVO = new ParkingSlotVO();
				slotVO.setParkingSlotId(rs.getString(++i));
				slotVO.setParkingNO(rs.getString(++i));
				slotVO.setVehicleType(rs.getString(++i));
				slotVO.setParkingLevel(rs.getString(++i));
				if (Excelconstants.TWO_WHEELER.equalsIgnoreCase(slotVO.getVehicleType())) {
					twoWheelerSlotDetailsList.add(slotVO);
				} else {
					fourWheelerSlotDetailsList.add(slotVO);
				}

			}
			slotDetailsList.put(Excelconstants.TWO_WHEELER_ARG, twoWheelerSlotDetailsList);
			slotDetailsList.put(Excelconstants.FOUR_WHEELER_ARG, fourWheelerSlotDetailsList);

		} catch (SQLException e) {

		} finally {
			try {
				logger.info("con :" + con);
				if (con != null) {
					con.close();
					ps.close();
				}

			} catch (SQLException e) {
			}
		}
		logger.info("getParkingSlotDetails end");
		return slotDetailsList;
	}

	public int postEmpSlotMapDetails(Map<String, String> empSlotMapping) throws BOException {
		logger.info("getEmployeeDetails start");
		Connection con = getConnection();
		PreparedStatement ps = null;
		int count=0;
		try {
			StringBuilder sql = new StringBuilder(
					"INSERT INTO employee_parking_tbl(emp_id,parking_slot_id,date_created) VALUES(");

			int size = empSlotMapping.size();

			for (Map.Entry<String, String> entry : empSlotMapping.entrySet()) {
				sql.append("?, ?, ?)");
				if (size != 1) {
					sql.append(",(");
				}
				size--;
			}
			logger.info("sql :" + sql);
			ps = con.prepareStatement(sql.toString());
			int i = 0;
			for (Map.Entry<String, String> entry : empSlotMapping.entrySet()) {
				ps.setString(++i, entry.getKey());
				ps.setString(++i, entry.getValue());
				ps.setDate(++i, getCurrentDate());
			}
			count = ps.executeUpdate();
			logger.info(count + "values inserted");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					ps.close();
					con.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("getEmployeeDetails end");
		return count;
	}

	private static java.sql.Date getCurrentDate() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Date(today.getTime());
	}
	
	
	public int insertLeaveDates(EmployeeParkingVO vo, ArrayList<Date> leaveList, String messageSubject) {
        
		String command = SqlQueryHelper.insertEmployeeLeaveDetails();
		int updatedCount = 0;
        PreparedStatement ps = null;
        try
        {

//        	Date date = leaveList.get(0).toLocaleString();
            ps = connection.prepareStatement(command);
            ps.setInt(1, vo.getEmpId());
            ps.setDate(2, new java.sql.Date(leaveList.get(0).getTime()));
            ps.setDate(3, new java.sql.Date(leaveList.get(1).getTime()));
            ps.setString(4, messageSubject);
            
            updatedCount = ps.executeUpdate();
            System.out.println("Updated count: "+updatedCount);
            return updatedCount;
            
        }
        catch (Exception e)
        {
        	System.out.println("Exception Caught while inserting the leave details : "+e);
        	return updatedCount;
        }
        finally
        {
            close(ps);
        }
    }
	
	public boolean isNewMail(EmployeeParkingVO vo, ArrayList<Date> leaveList, String messageSubject) {
        
		System.out.println("isNewMail");
		String command = SqlQueryHelper.getEmployeeLeaveData();
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean isNewMail = true;
        try
        {
            ps = connection.prepareStatement(command);
            ps.setInt(1, vo.getEmpId());
            ps.setDate(2, new java.sql.Date(leaveList.get(0).getTime()));
            ps.setDate(3, new java.sql.Date(leaveList.get(1).getTime()));
            ps.setString(4, messageSubject);
            
            rs=ps.executeQuery();
            if (rs!=null && rs.next())
            {
            	int count = rs.getInt(1);
            	System.out.println("employee leave mail count: "+count);
            	if (count > 0)
            	{
            		logger.info("Existing Mail :" );
            		isNewMail = false;
            	}
            }

            
            return isNewMail;
            
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	System.out.println("Exception Caught while checking if the existing leave mail : "+e.getMessage());
        	return isNewMail;
        }
        finally
        {
            close(ps);
            close(rs);
        }
    }

	public void insertLeaveDataForParking(EmployeeParkingVO vo, ArrayList<Date> leaveList, int leaveCount) {
		String command = SqlQueryHelper.insertLeaveDataForParkingFromMail();
		java.util.Date date = leaveList.get(0);
        PreparedStatement ps = null;
        PreparedStatement parkCheckingPreparedStatement = null;
        ResultSet rs = null;
        try
        {
            ps = connection.prepareStatement(command);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            for (int i=0; i<leaveCount; i++)
            {
            	String checkIfAlreadyInserted = SqlQueryHelper.getParkingLeaveData();
            	parkCheckingPreparedStatement = connection.prepareStatement(checkIfAlreadyInserted);
            	
            	parkCheckingPreparedStatement.setInt(1, vo.getParkingSlotNo());
            	parkCheckingPreparedStatement.setInt(2, vo.getEmpId());
            	parkCheckingPreparedStatement.setDate(3, new java.sql.Date(cal.getTime().getTime()));
            	
            	rs= parkCheckingPreparedStatement.executeQuery();
            	int count = 0;
            	if (rs.next())
            	{
            		count = rs.getInt(1);
            		System.out.println("@@@@@@@@ COUNT ****: "+count);
            	}
            	if (count == 0)
            	{
					ReadMails mail =  new ReadMails();
					
            		ps.setInt(1, vo.getParkingSlotNo());
            		ps.setInt(2, vo.getEmpId());
            		ps.setDate(3, new java.sql.Date(cal.getTime().getTime()));
            	
            		int updatedCount = ps.executeUpdate();
            		cal.add(Calendar.DATE, 1);
            		System.out.println("dates inserted count: "+updatedCount);
            		try
            		{
            			mail.cancelCalanderEvent(vo, cal.getTime());
            		}
            		catch(Exception e)
            		{
            			logger.debug("Exception caught while cancelling event: "+e.getMessage());
            		}
            		
            	}
            	else
            	{
            		System.out.println("Not Updating As it is already inserted");
            	}
            }
            
        }
        catch (Exception e)
        {
        	System.out.println("Exception Caught while inserting leave details for Parking Slot "+e);
        }
        finally
        {
            close(ps);
            close(parkCheckingPreparedStatement);
            close(rs);
        }	
	}
	
	public EmployeeParkingVO getEmployeeParkingSlotDetails(String emailId)
    {
        
		String command = SqlQueryHelper.getEmployeeParkingSlotDetails();
		System.out.println("command: "+command);
        PreparedStatement ps = null;
        ResultSet rs = null;
        EmployeeParkingVO empParkingVo = null;
        try
        {
            ps = connection.prepareStatement(command);
            ps.setString(1, emailId);
            rs = ps.executeQuery();
            System.out.println("No results found");
            if (rs.next())
            {
            	System.out.println("query executed");
            	int i=0;
            	empParkingVo =  new EmployeeParkingVO();
            	empParkingVo.setEmpParkingId(rs.getInt(++i));
            	empParkingVo.setEmpId(rs.getInt(++i));
            	empParkingVo.setEmpParkingSlotId(rs.getInt(++i));
            	empParkingVo.setParkingSlotNo(rs.getInt(++i));
            	empParkingVo.setParkingType(rs.getString(++i));
            	empParkingVo.setParkingLevel(rs.getString(++i));
            	empParkingVo.setSlotMailID(rs.getString(++i));
            }
        }
        catch (Exception e)
        {
        	System.out.println("Exception Caught while getting the Parking Slot Details");
        	return empParkingVo;
        }
        finally
        {
            close(rs);
            close(ps);
        }
        return empParkingVo;
    }

}
