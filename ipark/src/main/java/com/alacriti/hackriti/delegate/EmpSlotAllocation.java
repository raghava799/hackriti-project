package com.alacriti.hackriti.delegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;

import com.alacriti.hackriti.api.handlers.BaseApiHandler;
import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.samples.AmazonSESSample;
import com.alacriti.hackriti.samples.S3Sample;
import com.alacriti.hackriti.utils.constants.Excelconstants;
import com.alacriti.hackriti.vo.EmpParkingRespVO;
import com.alacriti.hackriti.vo.Employee;
import com.alacriti.hackriti.vo.EmployeeVO;
import com.alacriti.hackriti.vo.InputVO;
import com.alacriti.hackriti.vo.ParkingSlotVO;


public class EmpSlotAllocation implements BaseApiHandler{
	
	@Override
	public void handleRequest(RequestContext context) throws BOException, Exception {
		
		if (context.getContextContainer() != null && context.getContextContainer().getSlot() != null) {

			Map<String, EmpParkingRespVO> empdetail = context.getContextContainer().getEmpDetail();
			//need to pass excel
			empdetail = EmpParkingMap();
		}

	}

	public Map<String, EmpParkingRespVO> EmpParkingMap()
	{
		Map<String, EmpParkingRespVO> empDetailResp = new HashMap<String, EmpParkingRespVO>();
		EmployeeDelegate employeeDelegate=new EmployeeDelegate();
		try 
		{
			/*File file =null;
			Map<Integer, ArrayList<EmployeeVO>> empPrefFromExcel = excelReader.getEmpValues(file);*/
			Map<Integer, ArrayList<EmployeeVO>> empPrefFromExcel = S3Sample.s3exp();
			ArrayList<EmployeeVO> twoWheelerEmpPrefDetails = empPrefFromExcel.get(Excelconstants.TWO_WHEELER_ARG);
			ArrayList<EmployeeVO> fourWheelerEmpPrefDetails = empPrefFromExcel.get(Excelconstants.FOUR_WHEELER_ARG);
			List<String> twoWheelrEmpIds=new ArrayList<String>();
			List<String> fourWheelrEmpIds=new ArrayList<String>();
			List<String> twoWheelParkingNos=new ArrayList<String>();
			List<String> fourWheelParkingNos=new ArrayList<String>();
			int i=0;
			for(EmployeeVO employeeVO : twoWheelerEmpPrefDetails)
			{
				System.out.println("twoWheelerEmpPrefDetails :"+employeeVO.getEmployeeRollId());
				twoWheelrEmpIds.add(i, employeeVO.getEmployeeRollId());
				i++;
			}
			i=0;
			for(EmployeeVO employeeVO : fourWheelerEmpPrefDetails)
			{
				System.out.println("fourWheelerEmpPrefDetails :"+employeeVO.getEmployeeRollId());
				fourWheelrEmpIds.add(i, employeeVO.getEmployeeRollId());
				i++;
			}
			Map<Integer, ArrayList<ParkingSlotVO>> parkingslotDetails = employeeDelegate.getParkingSlotDetails();	
			ArrayList<ParkingSlotVO> twoWheelerParkingslotDetails = parkingslotDetails.get(Excelconstants.TWO_WHEELER_ARG);
			ArrayList<ParkingSlotVO> fourWheelerParkingslotDetails = parkingslotDetails.get(Excelconstants.FOUR_WHEELER_ARG);
			Map<String, String> parkingMap  = new HashMap<String, String>();
			i=0;
			for(ParkingSlotVO parkingSlotVO : twoWheelerParkingslotDetails)
			{
				twoWheelParkingNos.add(i, parkingSlotVO.getParkingSlotId());
				parkingMap.put(parkingSlotVO.getParkingSlotId(), parkingSlotVO.getParkingLevel()+"-"+parkingSlotVO.getParkingNO());
				System.out.println("twoWheelParkingNos :"+twoWheelParkingNos.get(i));
				i++;
			}
			i=0;
			for(ParkingSlotVO parkingSlotVO : fourWheelerParkingslotDetails)
			{
				fourWheelParkingNos.add(i, parkingSlotVO.getParkingSlotId());
				parkingMap.put(parkingSlotVO.getParkingSlotId(), parkingSlotVO.getParkingLevel()+"-"+parkingSlotVO.getParkingNO());
				System.out.println("fourWheelParkingNos :"+fourWheelParkingNos.get(i));
				i++;
			}
			List<EmployeeVO> empDetailsFromDB = employeeDelegate.getEmployeeDetails();
			
			Map<String, String> empSlotMapping = null;
			empSlotMapping = new HashMap<String, String>();
			
			EmpParkingRespVO empParkingRespVO= null;
			int a=0;
			for(EmployeeVO empDetail : empDetailsFromDB)
			{
				empParkingRespVO = new EmpParkingRespVO();
				if(twoWheelrEmpIds.contains(empDetail.getEmployeeRollId()))
				{
					if(twoWheelParkingNos.size() > 0)
					{
						System.out.println("a :"+a+" size :"+twoWheelParkingNos.size()+" twoWheelParkingNos.get(a) :"+twoWheelParkingNos.get(a));
						empSlotMapping.put(empDetail.getEmployeeId(),twoWheelParkingNos.get(a));
						empParkingRespVO.setEmpId(empDetail.getEmployeeRollId());
						empParkingRespVO.setEmpName(empDetail.getEmployeeName());
						empParkingRespVO.setEmailId(empDetail.getEmailId());
						String parkingNo = parkingMap.get(twoWheelParkingNos.get(a));
						if(parkingNo!=null)
						{
							String arr[]=parkingNo.split("-");
							empParkingRespVO.setParkingLevel(arr[0]);
							empParkingRespVO.setParkingNO(arr[1]);
						}
						twoWheelParkingNos.remove(a);
						
					}
				}else if(fourWheelrEmpIds.contains(empDetail.getEmployeeRollId()))
				{
					if(fourWheelParkingNos.size()>0) 
					{
						System.out.println("a :"+a+" size :"+fourWheelParkingNos.size()+" fourWheelParkingNos.get(a) :"+fourWheelParkingNos.get(a));
						empSlotMapping.put(empDetail.getEmployeeId(),fourWheelParkingNos.get(a));
						empParkingRespVO.setEmpId(empDetail.getEmployeeRollId());
						empParkingRespVO.setEmpName(empDetail.getEmployeeName());
						empParkingRespVO.setEmailId(empDetail.getEmailId());
						String parkingNo = parkingMap.get(twoWheelParkingNos.get(a));
						if(parkingNo!=null)
						{
							String arr[]=parkingNo.split("-");
							empParkingRespVO.setParkingLevel(arr[0]);
							empParkingRespVO.setParkingNO(arr[1]);
						}
						fourWheelParkingNos.remove(a);
						
					}
				}
				empDetailResp.put(empDetail.getEmployeeId(), empParkingRespVO);
			}			
			employeeDelegate.postEmpSlotMapDetails(empSlotMapping);
			for (Map.Entry<String,String> entry : empSlotMapping.entrySet())
			{
				System.out.println("entry :"+entry);
				
			}
			for (Map.Entry<String, EmpParkingRespVO> entry : empDetailResp.entrySet())
			{
				System.out.println("response :"+entry.getKey()+" "+entry.getValue().getEmpId()+" "+entry.getValue().getEmpName()+" "+entry.getValue().getParkingNO()+" "+entry.getValue().getEmailId());
				InputVO inputVO=new InputVO();
				inputVO.setFromAddress("kusumavanicool@gmail.com");
				inputVO.setToAddress(entry.getValue().getEmailId());
				inputVO.setSubject("Parking Slot Allocation");
				//inputVO.setHtmlBody("Hi "+entry.getValue().getEmpName()+", \n\n You have been assigned to parking slot no "+entry.getValue().getParkingNO());
				inputVO.setHtmlBody("<h3>Hi "+entry.getValue().getEmpName()+",</h3>"+
						"<h3>You have been assigned to parking slot no "+entry.getValue().getParkingNO()+".</h3>");
				inputVO.setTextBody("Thanks,\n Alacriti Management.");
				AmazonSESSample.sendEmail(inputVO);
			}	
			
						
		} catch (EncryptedDocumentException e) 
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return empDetailResp;
	}

}
