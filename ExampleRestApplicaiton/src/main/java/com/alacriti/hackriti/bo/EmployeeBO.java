package com.alacriti.hackriti.bo;

import java.sql.SQLException;

import com.alacriti.hackriti.dao.EmployeeDAO;
import com.alacriti.hackriti.employee.forms.GetEmployeeRequestForm;
import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.vo.Employee;

public class EmployeeBO {

	public Employee getEmployeeDetails(GetEmployeeRequestForm form) throws BOException {

		Employee employee = new Employee();

		employee.setEmployeeMail(form.getEmployee_mail());

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
