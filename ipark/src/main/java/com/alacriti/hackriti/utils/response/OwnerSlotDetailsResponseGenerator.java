package com.alacriti.hackriti.utils.response;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.employee.forms.SlotResponseForm;
import com.alacriti.hackriti.response.forms.BaseResponseForm;
import com.alacriti.hackriti.vo.Slot;

public class OwnerSlotDetailsResponseGenerator implements BaseResponseFormGenerator{
	
	@Override
	public BaseResponseForm generateResponse(RequestContext context) {

		SlotResponseForm form = new SlotResponseForm();

		if (context.getContextContainer() != null && context.getContextContainer().getSlot() != null) {

			
			Slot slot = context.getContextContainer().getSlot();

			form.setDate(slot.getDate());
			form.setEmployee_id(slot.getEmpId());
			form.setParker_id(slot.getParkerId());
			form.setParking_level(slot.getParkingLevel());
			form.setParking_type(slot.getParkingType());
			form.setSlot_number(slot.getSlotNumber());
		}

		return form;
	}
}
