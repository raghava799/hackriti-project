package com.alacriti.hackriti.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.alacriti.hackriti.exceptions.BOException;

public class BaseDAO {

	public static Connection getConnection() throws BOException {

		Connection con = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "user_name", "password");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (con == null) {
			throw new BOException("connection not established");
		}
		return con;
	}
}
