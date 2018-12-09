package com.alacriti.hackriti.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.alacriti.hackriti.exceptions.BOException;

public class BaseDAO {
	
	protected static Connection con;

	public void setCon(Connection con) {
		this.con = con;
	}

	public static Connection getConnection() throws BOException {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://hacriti.cb8bmzqnvpfv.us-east-2.rds.amazonaws.com:3306/user", "AWSRDS", "Hacriti123");
			con.setAutoCommit(false);

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
