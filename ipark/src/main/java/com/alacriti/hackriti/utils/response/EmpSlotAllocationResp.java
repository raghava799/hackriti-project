package com.alacriti.hackriti.utils.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.employee.forms.EmployeeResponseForm;
import com.alacriti.hackriti.employee.forms.GetOwnerAndSlotMapForm;
import com.alacriti.hackriti.employee.forms.ParkingInfo;
import com.alacriti.hackriti.response.forms.BaseResponseForm;
import com.alacriti.hackriti.vo.EmpParkingRespVO;

public class EmpSlotAllocationResp implements BaseResponseFormGenerator {

	@Override
	public BaseResponseForm generateResponse(RequestContext context) {
		GetOwnerAndSlotMapForm form = new GetOwnerAndSlotMapForm();

		List<EmployeeResponseForm> empSlotResp = new ArrayList<EmployeeResponseForm>();

		if (context.getContextContainer() != null && context.getContextContainer().getEmpDetail() != null) {

			ParkingInfo info = new ParkingInfo();

			Map<String, EmpParkingRespVO> empDetailResp= context.getContextContainer().getEmpDetail();
			for (Map.Entry<String, EmpParkingRespVO> response : empDetailResp.entrySet())
			{
				EmployeeResponseForm resp = new EmployeeResponseForm();
				
				resp.setEmployee_id(response.getKey());
				resp.setEmployee_number(response.getValue().getEmpId());
				resp.setEmployee_mail_id(response.getValue().getEmailId());
				resp.setEmployee_name(response.getValue().getEmpName());
				info.setParking_level(response.getValue().getParkingLevel());
				info.setParking_slot_number(response.getValue().getParkingNO());
				resp.setParking_info(info);
				empSlotResp.add(resp);
			}
			form.setOwnerSlotsMap(empSlotResp);
		}

		return form;
	}

}
