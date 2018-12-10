package com.alacriti.hackriti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.utils.SqlQueryHelper;
import com.alacriti.hackriti.utils.constants.StringConstants;
import com.alacriti.hackriti.vo.Employee;
import com.alacriti.hackriti.vo.ParkingInfo;

public class EmployeeDAO extends BaseDAO {

    public Employee getEmployeeDetails(Employee employee) throws SQLException, BOException {

        Connection conn = getConnection();

        PreparedStatement preparedStmt;

        try {
            String sqlQuery = SqlQueryHelper.getEmployeeDetailsQuery();

            preparedStmt = conn.prepareStatement(sqlQuery);
            preparedStmt.setString(1, employee.getEmployeeMail());

            ResultSet rs = preparedStmt.executeQuery();

            if (rs != null && rs.next()) {


                ParkingInfo parkingInfo = new ParkingInfo();

                employee.setEmployeeNumber(rs.getString("emp_no"));
                employee.setEmployeeName(rs.getString(StringConstants.EMP_NAME));
                employee.setDateOfJoining(rs.getString("date_of_joining"));
                employee.setEmployeeId(rs.getString("emp_id"));
                employee.setEmployeeRole(rs.getString("emp_role"));

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


    public Employee getParkingDetails(Employee employee) throws SQLException, BOException {

        Connection conn = getConnection();

        PreparedStatement preparedStmt;

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
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }

        return employee;
    }

}
