package com.alacriti.hackriti.context;

import java.util.List;
import java.util.Map;

import com.alacriti.hackriti.vo.EmpParkingRespVO;
import com.alacriti.hackriti.vo.Employee;
import com.alacriti.hackriti.vo.EventVO;
import com.alacriti.hackriti.vo.Slot;

public class ContextContainer {

	private Employee employee;
	
	private Slot slot;
	
	private List<Slot> slots;
	
	private Map<String, EmpParkingRespVO> empDetail;
	
	private EventVO calendarEvent;
	
	

	public Map<String, EmpParkingRespVO> getEmpDetail() {
		return empDetail;
	}

	public void setEmpDetail(Map<String, EmpParkingRespVO> empDetailResp) {
		this.empDetail = empDetailResp;
	}

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

	public EventVO getCalendarEvent() {
		return calendarEvent;
	}

	public void setCalendarEvent(EventVO calendarEvent) {
		this.calendarEvent = calendarEvent;
	}
	

}
