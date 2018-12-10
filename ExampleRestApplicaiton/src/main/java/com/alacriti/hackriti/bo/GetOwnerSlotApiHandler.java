package com.alacriti.hackriti.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import com.alacriti.hackriti.api.handlers.BaseApiHandler;
import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.dao.SlotDAO;
import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.vo.Slot;

public class GetOwnerSlotApiHandler implements BaseApiHandler{

	@Override
	public void handleRequest(RequestContext context) throws BOException, Exception {
		
		if (context.getContextContainer() != null && context.getContextContainer().getSlot() != null) {

			Slot slot = context.getContextContainer().getSlot();

			slot = getSlotDetails(slot,context);
		}

	}
	
	public Slot getSlotDetails(Slot slot,RequestContext context) throws BOException, ParseException {

		SlotDAO dao = new SlotDAO();
		//Connection conn = BaseDAO.getConnection();
		//dao.setCon(conn);

		try {

			slot = dao.getSlotDetails(slot,context.getApiName());

		} catch (SQLException e) {

			e.printStackTrace();
			throw new BOException("SQLException occured in OwnerSlotHandler layer");
		}
//		finally{
//			close(conn);
//		}

		return slot;

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
