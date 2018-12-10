package com.alacriti.hackriti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.utils.SqlQueryHelper;
import com.alacriti.hackriti.utils.constants.Excelconstants;
import com.alacriti.hackriti.utils.constants.StringConstants;
import com.alacriti.hackriti.vo.Employee;
import com.alacriti.hackriti.vo.EmployeeVO;
import com.alacriti.hackriti.vo.ParkingInfo;
import com.alacriti.hackriti.vo.ParkingSlotVO;

public class EmployeeDAO extends BaseDAO {
	final static Logger logger = Logger.getLogger(EmployeeDAO.class);

	public Employee getEmployeeDetails(Employee employee) throws SQLException, BOException {

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

			ResultSet rs = preparedStmt.executeQuery();

			if (rs != null && rs.next()) {

				ParkingInfo parkingInfo = new ParkingInfo();

				parkingInfo.setParkingSlotId(rs.getString("parking_slot_id"));
				parkingInfo.setParkingSlotNumber(rs.getString("parking_slot_no"));
				parkingInfo.setParkingType(rs.getString("parking_type"));
				parkingInfo.setParkingLevel(rs.getString("parking_level"));

				employee.setParkingInfo(parkingInfo);

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
            ResultSet rs = preparedStmt.executeQuery();

            if (rs != null && rs.next()) {


                ParkingInfo parkingInfo = new ParkingInfo();

                parkingInfo.setParkingSlotId(rs.getString("parking_slot_id"));
                parkingInfo.setParkingSlotNumber(rs.getString("parking_slot_no"));
                parkingInfo.setParkingType(rs.getString("parking_type"));
                parkingInfo.setParkingLevel(rs.getString("parking_level"));

                employee.setParkingInfo(parkingInfo);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } finally {

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }

        return employee;
    }
    public List<EmployeeVO> getEmployeeDetails(EmployeeVO emp) throws BOException 
	{	
    	logger.info("getEmployeeDetails start");
    	Connection con = getConnection();
		List<EmployeeVO> empDetailsList = new ArrayList<EmployeeVO>();
		PreparedStatement ps;
		ResultSet rs = null;
		try 
		{
			String sql= "SELECT emp_id,emp_no,emp_name,emp_email,date_of_joining FROM r_employee_tbl order by date_of_joining";
				
				ps=con.prepareStatement(sql);
				logger.info("sql :"+sql);		
				
				rs=ps.executeQuery();
				while(rs != null && rs.next())
	            {
	                    int i = 0;
	                    EmployeeVO empVO = new EmployeeVO();
	                    empVO.setEmployeeId(rs.getString(++i));
	                    empVO.setEmployeeRollId(rs.getString(++i));
	                    empVO.setEmployeeName(rs.getString(++i));
	                    empVO.setEmailId(rs.getString(++i));
	                    empVO.setDateOfJoining(rs.getDate(++i));
	                    empDetailsList.add(empVO);
	             }
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}finally{
			try
			{
				if(con!=null){
					con.close();
				}
				 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("getEmployeeDetails end");
	return empDetailsList;
	}

	public Map<Integer, ArrayList<ParkingSlotVO>> getParkingSlotDetails() throws BOException 
	{	
		logger.info("getParkingSlotDetails start");
		Connection con = getConnection();
		ArrayList<ParkingSlotVO> twoWheelerSlotDetailsList = new ArrayList<ParkingSlotVO>();
		ArrayList<ParkingSlotVO> fourWheelerSlotDetailsList = new ArrayList<ParkingSlotVO>();
		Map<Integer,ArrayList<ParkingSlotVO>> slotDetailsList = new HashMap<Integer,ArrayList<ParkingSlotVO>>();
		PreparedStatement ps;
		ResultSet rs = null;
		try {
			String sql= "SELECT parking_slot_id,parking_slot_no,parking_type,parking_level FROM r_parking_slot_tbl order by parking_slot_no";
			
				ps=con.prepareStatement(sql);
				logger.info("sql :"+sql);					
				rs=ps.executeQuery();
				
				while(rs != null && rs.next())
	            {
	                    int i = 0;
	                    ParkingSlotVO slotVO = new ParkingSlotVO();
	                    slotVO.setParkingSlotId(rs.getString(++i));
	                    slotVO.setParkingNO(rs.getString(++i));
	                    slotVO.setVehicleType(rs.getString(++i));
	                    slotVO.setParkingLevel(rs.getString(++i));
	                    if(Excelconstants.TWO_WHEELER.equalsIgnoreCase(slotVO.getVehicleType()))
	                    {
	                    	twoWheelerSlotDetailsList.add(slotVO);
	                    }else{
	                    	fourWheelerSlotDetailsList.add(slotVO);
	                    }
	                    
	             }
				slotDetailsList.put(Excelconstants.TWO_WHEELER_ARG, twoWheelerSlotDetailsList);
				slotDetailsList.put(Excelconstants.FOUR_WHEELER_ARG, fourWheelerSlotDetailsList);
		
		}
		catch(SQLException e)
		{
			
		}finally{
			try
			{
				logger.info("con :"+con);
				if(con!=null){
					con.close();
				}
				 
			} catch (SQLException e) {
			}
		}
		logger.info("getParkingSlotDetails end");
	return slotDetailsList;
	}

	public void postEmpSlotMapDetails(Map<String, String> empSlotMapping) throws BOException 
	{	
		logger.info("getEmployeeDetails start");
		Connection con = getConnection();
		PreparedStatement ps;
		try 
		{
				StringBuilder sql= new StringBuilder("INSERT INTO employee_parking_tbl(emp_id,parking_slot_id,date_created) VALUES(");
				
				int size = empSlotMapping.size();
				
				for (Map.Entry<String,String> entry : empSlotMapping.entrySet()) 
				{
				    sql.append("?, ?, ?)");
				    if(size !=1){
				    	sql.append(",(");
				    }
				    size--;
				}
				logger.info("sql :"+sql);
				ps=con.prepareStatement(sql.toString());
				int i=0;
				for (Map.Entry<String,String> entry : empSlotMapping.entrySet()) 
				{
				   ps.setString(++i,entry.getKey());
				   ps.setString(++i,entry.getValue());
				   ps.setDate(++i, getCurrentDate());
				}
				int count=ps.executeUpdate();
				logger.info(count+"values inserted");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}finally{
			try
			{
				if(con!=null){
					con.close();
				}
				 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("getEmployeeDetails end");
	}
	private static java.sql.Date getCurrentDate() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}
}
