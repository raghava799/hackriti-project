package com.alacriti.hackriti.bo;

import java.sql.SQLException;

import com.alacriti.hackriti.api.handlers.BaseApiHandler;
import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.dao.EmployeeDAO;
import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.utils.constants.StringConstants;
import com.alacriti.hackriti.vo.Employee;

public class ParkingHandler implements BaseApiHandler {

	@Override
	public void handleRequest(RequestContext context) throws BOException {

		if (context.getContextContainer() != null && context.getContextContainer().getEmployee() != null) {

			Employee employee = context.getContextContainer().getEmployee();

			if (StringConstants.ReqeustConstants.EMP_ROLE_OWNER.equals(employee.getEmployeeRole())) {
				System.out.print("calling getParking details...");
				employee = getParkingDetails(employee);
			}
			else{
				
				System.out.println("not an owner so skipping api call, getParkingSlotDetails");
				return;
			}
		}

	}

	public Employee getParkingDetails(Employee employee) throws BOException {

		EmployeeDAO dao = new EmployeeDAO();

		try {

			employee = dao.getParkingDetails(employee);

		} catch (SQLException e) {

			e.printStackTrace();
			throw new BOException("SQLException occured in DAO layer");
		}

		return employee;

	}

}
