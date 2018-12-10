package com.alacriti.hackriti.bo;

import java.sql.SQLException;

import com.alacriti.hackriti.api.handlers.BaseApiHandler;
import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.dao.EmployeeDAO;
import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.vo.Employee;

public class EmployeeApiHandler implements BaseApiHandler {

	@Override
	public void handleRequest(RequestContext context) throws BOException {

		if (context.getContextContainer() != null && context.getContextContainer().getEmployee() != null) {

			Employee employee = context.getContextContainer().getEmployee();

			employee = getEmployeeDetails(employee);
		}

	}

	public Employee getEmployeeDetails(Employee employee) throws BOException {

		EmployeeDAO dao = new EmployeeDAO();

		try {

			employee = dao.getEmployeeDetails(employee);

		} catch (SQLException e) {

			e.printStackTrace();
			throw new BOException("SQLException occured in DAO layer");
		}

		return employee;

	}

}
