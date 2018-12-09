package com.alacriti.hackriti.utils.response;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.employee.forms.EmployeeResponseForm;
import com.alacriti.hackriti.employee.forms.ParkingInfo;
import com.alacriti.hackriti.employee.forms.SlotsResponseForm;
import com.alacriti.hackriti.response.forms.BaseResponseForm;
import com.alacriti.hackriti.vo.Slot;

public class SlotResponseGenerator implements BaseResponseFormGenerator {

	@Override
	public BaseResponseForm generateResponse(RequestContext context) {

		SlotsResponseForm slotResponse = new SlotsResponseForm();

		if (context.getContextContainer() != null && context.getContextContainer().getSlot() != null) {

				Slot slot = context.getContextContainer().getSlot();
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
					
					if (slot.getEmployee().getParkingInfo() != null) {

						parkingInfo.setParking_level(slot.getEmployee().getParkingInfo().getParkingLevel());
						parkingInfo.setParking_slot_id(slot.getEmployee().getParkingInfo().getParkingSlotId());
						parkingInfo.setParking_slot_number(slot.getEmployee().getParkingInfo().getParkingSlotNumber());
						parkingInfo.setParking_type(slot.getEmployee().getParkingInfo().getParkingType());
						employee.setParking_info(parkingInfo);

					}
				}

				
				slotResponse.setOwner(employee);
		}

		return slotResponse;
	}

}
