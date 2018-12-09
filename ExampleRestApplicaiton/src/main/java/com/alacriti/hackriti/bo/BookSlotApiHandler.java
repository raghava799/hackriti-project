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

public class BookSlotApiHandler implements BaseApiHandler {

	@Override
	public void handleRequest(RequestContext context) throws BOException, Exception {
		
		if (context.getContextContainer() != null && context.getContextContainer().getSlot() != null) {

			Slot slot = context.getContextContainer().getSlot();

			 slot = bookSlot(slot);
			
			//context.setSlot(slot);
		}

	}
	
	public Slot bookSlot(Slot slot) throws BOException, ParseException {

		SlotDAO dao = new SlotDAO();
		//Connection conn = BaseDAO.getConnection();
		//dao.setCon(conn);

		try {

			return dao.bookSlot(slot);

		} catch (SQLException e) {

			e.printStackTrace();
			throw new BOException("SQLException occured in BookSlotApiHandler layer");
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
