package com.alacriti.hackriti.delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alacriti.hackriti.bo.EmployeeBO;
import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.vo.EmployeeVO;
import com.alacriti.hackriti.vo.ParkingSlotVO;


public class EmployeeDelegate 
{
	EmployeeBO empBO=new EmployeeBO();
	
	public List<EmployeeVO> getEmployeeDetails() throws BOException 
	{
		List<EmployeeVO> empDetailsList=empBO.getEmployeeDetails();
		
		/*for(EmployeeVO employeeVO : empDetailsList)
		{
			log.info("emp :"+employeeVO.getEmployeeId()+" "+employeeVO.getEmployeeRollId()+" "+employeeVO.getEmployeeName()+" "+employeeVO.getDateOfJoining());
		}*/
		return empDetailsList;
	}
	
	public Map<Integer, ArrayList<ParkingSlotVO>> getParkingSlotDetails() throws BOException 
	{
		Map<Integer, ArrayList<ParkingSlotVO>> parkingSlotList = empBO.getParkingSlotDetails();
		/*for(ParkingSlotVO parkingSlotVO : parkingSlotList){
			log.info("parking :"+parkingSlotVO.getParkingSlotId()+" "+parkingSlotVO.getParkingLevel()+" "+parkingSlotVO.getParkingNO()+" "+parkingSlotVO.getVehicleType());
		}*/
		return parkingSlotList;
	}
	
	public int postEmpSlotMapDetails(Map<String, String> empSlotMapping) throws BOException
	{
		int count = empBO.postEmpSlotMapDetails(empSlotMapping);
		return count;
	}
	

}
