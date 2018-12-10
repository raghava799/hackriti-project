package com.alacriti.hackriti.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import com.alacriti.hackriti.api.handlers.BaseApiHandler;
import com.alacriti.hackriti.calendar.api.CalendarApiHandler;
import com.alacriti.hackriti.context.RequestContext;
import com.alacriti.hackriti.dao.SlotDAO;
import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.vo.EventVO;
import com.alacriti.hackriti.vo.Slot;

public class BookSlotApiHandler implements BaseApiHandler {

	@Override
	public void handleRequest(RequestContext context) throws BOException, Exception {

		if (context.getContextContainer() != null && context.getContextContainer().getSlot() != null) {

			Slot slot = context.getContextContainer().getSlot();

			slot = bookSlot(slot);

			pushEventToCalendar(context);

			// context.setSlot(slot);
		}

	}

	private void pushEventToCalendar(RequestContext context) {

		EventVO event = new EventVO();

		context.getSlot().getDate();
		context.getSlot().getSlotNumber();
		context.getSlot().getDate();
		context.getSlot().getDate();
		context.getSlot().getDate();

		event.setDate("");
		event.setOwnerMailId("");
		event.setUserMailId("");
		event.setSlotMailId("");
		event.setSlotNumber("");
		event.setSlotName("");

		CalendarApiHandler handler = new CalendarApiHandler();
		handler.createCalendarEvent(context, event);

	}

	public Slot bookSlot(Slot slot) throws BOException, ParseException {

		SlotDAO dao = new SlotDAO();
		// Connection conn = BaseDAO.getConnection();
		// dao.setCon(conn);

		try {

			return dao.bookSlot(slot);

		} catch (SQLException e) {

			e.printStackTrace();
			throw new BOException("SQLException occured in BookSlotApiHandler layer");
		}
		// finally{
		// close(conn);
		// }

	}

	public void close(Connection conn) {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Exception occured while closing connection");
			}
	}

}
