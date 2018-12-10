package com.alacriti.hackriti.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alacriti.hackriti.dao.EmployeeDAO;
import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.vo.EmployeeVO;
import com.alacriti.hackriti.vo.ParkingSlotVO;


public class EmployeeBO {
	EmployeeVO emp=new EmployeeVO();
	EmployeeDAO employeeDAO=new EmployeeDAO();

	public List<EmployeeVO> getEmployeeDetails() throws BOException
	{		
		List<EmployeeVO> empDetails = new ArrayList<EmployeeVO>();
		empDetails =employeeDAO.getEmployeeDetails(emp);
		return empDetails;
	}

	public Map<Integer, ArrayList<ParkingSlotVO>> getParkingSlotDetails() throws BOException 
	{	
		Map<Integer, ArrayList<ParkingSlotVO>> parkingSlotDetails =employeeDAO.getParkingSlotDetails();
		return parkingSlotDetails;
	}

	public void postEmpSlotMapDetails(Map<String, String> empSlotMapping) throws BOException
	{	
		employeeDAO.postEmpSlotMapDetails(empSlotMapping);
	}

}
