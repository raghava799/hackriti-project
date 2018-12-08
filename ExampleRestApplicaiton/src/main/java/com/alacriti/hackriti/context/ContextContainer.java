package com.alacriti.hackriti.context;

import java.util.List;

import com.alacriti.hackriti.vo.Employee;
import com.alacriti.hackriti.vo.Slot;

public class ContextContainer {

	private Employee employee;
	
	private Slot slot;
	
	private List<Slot> slots;
	
	

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

	public List<Slot> getSlots() {
		return slots;
	}

	public void setSlots(List<Slot> slots) {
		this.slots = slots;
	}
	
	

}
