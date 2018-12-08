package com.alacriti.hackriti.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.alacriti.hackriti.api.handlers.BaseApiHandler;
import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.dao.SlotDAO;
import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.vo.Slot;

public class SearchAvailableSlotsApiHandler implements BaseApiHandler {

	@Override
	public void handleRequest(RequestContext context) throws BOException, Exception {
		
		if (context.getContextContainer() != null && context.getContextContainer().getSlot() != null) {

			Slot slot = context.getContextContainer().getSlot();

			List<Slot> slots = searchAvailableSlots(slot);
			
			context.setSlots(slots);
		}

	}
	
	public List<Slot> searchAvailableSlots(Slot slot) throws BOException, ParseException {

		SlotDAO dao = new SlotDAO();
		//Connection conn = BaseDAO.getConnection();
		//dao.setCon(conn);

		try {

			return dao.searchAvailavleSlots(slot);

		} catch (SQLException e) {

			e.printStackTrace();
			throw new BOException("SQLException occured in OwnerSlotHandler layer");
		}
//		finally{
//			close(conn);
//		}

	}
	
	public void close(Connection conn){
		if (conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Exception occured while closing connection");
			}
	}

}
