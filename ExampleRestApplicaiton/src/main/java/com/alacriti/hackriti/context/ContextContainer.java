package com.alacriti.hackriti.context;

import com.alacriti.hackriti.vo.Employee;

public class ContextContainer {

	private Employee employee;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
