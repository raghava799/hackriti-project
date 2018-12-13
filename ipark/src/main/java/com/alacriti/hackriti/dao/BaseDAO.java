package com.alacriti.hackriti.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.logging.utils.ResourceInitServlet;

public class BaseDAO {
	
//	protected static Connection con;
//
//	public void setCon(Connection con) {
//		this.con = con;
//	}
//
//	public static Connection getConnection() throws BOException {
//
//		try {
//
//			Class.forName("com.mysql.jdbc.Driver");
//			con = DriverManager.getConnection("jdbc:mysql://hacriti.cb8bmzqnvpfv.us-east-2.rds.amazonaws.com:3306/user", "AWSRDS", "Hacriti123");
//			con.setAutoCommit(false);
//
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if (con == null) {
//			throw new BOException("connection not established");
//		}
//		return con;
//	}
	

    private static final String JDBC_URL = "jdbc:mysql://" + ResourceInitServlet.HOST_NAME + ":" + ResourceInitServlet.PORT+"/"+ResourceInitServlet.DB_NAME;
    
    /** The connection used by this DAO */
    protected static Connection connection;
    
    public void setConnection(Connection conn)
    {
        connection = conn;
    }
    
	public static Connection getConnection() throws BOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection(JDBC_URL, ResourceInitServlet.LOGIN_USER, ResourceInitServlet.LOGIN_PWD);
			connection.setAutoCommit(false);
			System.out.println("Connection established");
		} catch (ClassNotFoundException e) {
			System.out.println("Exception 1 ");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Exception 2 ");
		}
		if (connection == null) {
			System.out.println("Connection not established");
			throw new BOException("connection not established");
		}
		return connection;
	}
}
