package com.alacriti.hackriti.rest.handlers;

import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.employee.forms.BaseRequestForm;
import com.alacriti.hackriti.employee.forms.GetAvailableSlotsRequestForm;
import com.alacriti.hackriti.employee.forms.GetEmployeeRequestForm;
import com.alacriti.hackriti.employee.forms.GetSlotRequestForm;
import com.alacriti.hackriti.employee.forms.ManageSlotRequestForm;
import com.alacriti.hackriti.vo.Employee;
import com.alacriti.hackriti.vo.Slot;

public class RequestPreparer {

	public void prepareRequest(RequestContext context, BaseRequestForm form) {

		if (form instanceof GetEmployeeRequestForm) {

			Employee employee;

			if (context.getEmployee() == null) {

				employee = new Employee();

			} else {

				employee = context.getEmployee();
			}

			employee.setEmployeeMail(((GetEmployeeRequestForm) form).getEmployee_mail_id());

			context.setEmployee(employee);

		}
		
		if (form instanceof GetSlotRequestForm) {

			Slot slot;

			if (context.getSlot() == null) {

				slot = new Slot();

			} else {

				slot = context.getSlot();
			}

			slot.setEmpId(((GetSlotRequestForm) form).getEmployee_id());
			slot.setDate(((GetSlotRequestForm) form).getDate());
			slot.setSlotNumber(((GetSlotRequestForm) form).getSlot_number());

			context.setSlot(slot);
		}
		
		if (form instanceof GetAvailableSlotsRequestForm) {

			Slot slot;

			if (context.getSlot() == null) {

				slot = new Slot();

			} else {

				slot = context.getSlot();
			}

			slot.setDate(((GetAvailableSlotsRequestForm) form).getDate());

			context.setSlot(slot);
		}
		
		if (form instanceof ManageSlotRequestForm) {

			Slot slot;

			if (context.getSlot() == null) {

				slot = new Slot();

			} else {

				slot = context.getSlot();
			}

			slot.setDate(((ManageSlotRequestForm) form).getDate());
			slot.setEmpId(((ManageSlotRequestForm) form).getEmployee_id());
			slot.setParkerId(((ManageSlotRequestForm) form).getParker_id());
			slot.setSlotNumber(((ManageSlotRequestForm) form).getSlot_number());

			context.setSlot(slot);
		}
	}

}
