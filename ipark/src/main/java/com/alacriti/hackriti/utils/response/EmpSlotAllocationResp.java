package com.alacriti.hackriti.utils.response;

import java.util.Map;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.employee.forms.EmployeeResponseForm;
import com.alacriti.hackriti.employee.forms.ParkingInfo;
import com.alacriti.hackriti.response.forms.BaseResponseForm;
import com.alacriti.hackriti.vo.EmpParkingRespVO;

public class EmpSlotAllocationResp implements BaseResponseFormGenerator {

	@Override
	public BaseResponseForm generateResponse(RequestContext context) {

		EmployeeResponseForm form = new EmployeeResponseForm();

		if (context.getContextContainer() != null && context.getContextContainer().getEmpDetail() != null) {

			ParkingInfo info = new ParkingInfo();

			Map<String, EmpParkingRespVO> empDetailResp= context.getContextContainer().getEmpDetail();
			for (Map.Entry<String, EmpParkingRespVO> response : empDetailResp.entrySet())
			{
				form.setEmployee_id(response.getKey());
				form.setEmployee_number(response.getValue().getEmpId());
				form.setEmployee_mail_id(response.getValue().getEmailId());
				form.setEmployee_name(response.getValue().getEmpName());
				info.setParking_level(response.getValue().getParkingLevel());
				info.setParking_slot_number(response.getValue().getParkingNO());
			}
			
			form.setParking_info(info);
		}

		return form;
	}

}
