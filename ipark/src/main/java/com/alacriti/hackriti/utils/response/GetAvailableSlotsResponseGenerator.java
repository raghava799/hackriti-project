package com.alacriti.hackriti.utils.response;

import java.util.ArrayList;
import java.util.List;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.employee.forms.EmployeeResponseForm;
import com.alacriti.hackriti.employee.forms.GetAailableSlotsResponseForm;
import com.alacriti.hackriti.employee.forms.ParkingInfo;
import com.alacriti.hackriti.employee.forms.SlotsResponseForm;
import com.alacriti.hackriti.response.forms.BaseResponseForm;
import com.alacriti.hackriti.vo.Slot;

public class GetAvailableSlotsResponseGenerator implements BaseResponseFormGenerator {

	@Override
	public BaseResponseForm generateResponse(RequestContext context) {

		GetAailableSlotsResponseForm form = new GetAailableSlotsResponseForm();

		if (context.getContextContainer() != null && context.getContextContainer().getSlots() != null
				&& context.getContextContainer().getSlots().size() > 0) {

			List<SlotsResponseForm> slotsResponse = new ArrayList<SlotsResponseForm>();
			
			for (Slot slot : context.getContextContainer().getSlots()) {

				SlotsResponseForm slotResponse = new SlotsResponseForm();
				EmployeeResponseForm employee = new EmployeeResponseForm();
				ParkingInfo parkingInfo = new ParkingInfo();
				
				slotResponse.setDate(slot.getDate());
				slotResponse.setOwner_id(slot.getEmpId());
				slotResponse.setParker_id(slot.getParkerId());
				slotResponse.setParking_level(slot.getParkingLevel());
				slotResponse.setParking_type(slot.getParkingType());
				slotResponse.setSlot_number(slot.getSlotNumber());

				if (slot.getEmployee() != null) {

					employee.setDate_of_joining(slot.getEmployee().getDateOfJoining());
					employee.setEmployee_id(slot.getEmployee().getEmployeeId());
					employee.setEmployee_mail_id(slot.getEmployee().getEmployeeMail());
					employee.setEmployee_name(slot.getEmployee().getEmployeeName());
					employee.setEmployee_number(slot.getEmployee().getEmployeeNumber());
					employee.setEmployee_role(slot.getEmployee().getEmployeeRole());
					
					if(slot.getEmployee().getParkingInfo()!=null){
						
						parkingInfo.setParking_level(slot.getEmployee().getParkingInfo().getParkingLevel());
						parkingInfo.setParking_slot_id(slot.getEmployee().getParkingInfo().getParkingSlotId());
						parkingInfo.setParking_slot_number(slot.getEmployee().getParkingInfo().getParkingSlotNumber());
						parkingInfo.setParking_type(slot.getEmployee().getParkingInfo().getParkingType());
						employee.setParking_info(parkingInfo);
						
					}
				}
				
				
				slotResponse.setOwner(employee);
				slotsResponse.add(slotResponse);
			}
			form.setSlots(slotsResponse);

		}

		return form;
	}
}
