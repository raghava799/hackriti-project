package com.alacriti.hackriti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.utils.SqlQueryHelper;
import com.alacriti.hackriti.utils.constants.StringConstants;
import com.alacriti.hackriti.vo.Employee;

public class EmployeeDAO extends BaseDAO {

	public Employee getEmployeeDetails(Employee employee) throws SQLException, BOException {

		Connection conn = getConnection();

		PreparedStatement preparedStmt;

		try {
			String sqlQuery = SqlQueryHelper.getEmployeeDetailsQuery();

			preparedStmt = conn.prepareStatement(sqlQuery);
			preparedStmt.setString(1, employee.getEmployeeMail());

			ResultSet rs = preparedStmt.executeQuery(sqlQuery);

			if (rs != null && rs.next()) {

				employee.setEmployeeName(rs.getString(StringConstants.EMP_NAME));
				employee.setDateOfJoining(rs.getString("date_of_joining"));
				employee.setEmployeeRole(rs.getString("emp_role"));
			}
		}

		catch (SQLException e) {
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
