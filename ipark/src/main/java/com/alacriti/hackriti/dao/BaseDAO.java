package com.alacriti.hackriti.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.alacriti.hackriti.exceptions.BOException;

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
	
	private static final String RDS_INSTANCE_HOSTNAME = "hacriti.cb8bmzqnvpfv.us-east-2.rds.amazonaws.com";
    private static final int RDS_INSTANCE_PORT = 3306;
    private static final String DB_NAME = "user";
    private static final String DB_USER = "AWSRDS";
    private static final String DB_PWD = "Hacriti123";
    private static final String JDBC_URL = "jdbc:mysql://" + RDS_INSTANCE_HOSTNAME + ":" + RDS_INSTANCE_PORT+"/"+DB_NAME;
    
    /** The connection used by this DAO */
    protected static Connection connection;
    
    public void setConnection(Connection conn)
    {
        connection = conn;
    }
    
	public static Connection getConnection() throws BOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection(JDBC_URL, DB_USER, DB_PWD);
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
