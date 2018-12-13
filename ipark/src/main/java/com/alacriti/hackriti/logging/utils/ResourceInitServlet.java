package com.alacriti.hackriti.logging.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.alacriti.hackriti.exceptions.BOException;
import com.alacriti.hackriti.factory.ResourceFactory;
import com.alacriti.hackriti.gmail.api.service.account.ReadMails;

public class ResourceInitServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(ResourceInitServlet.class);

	private static final long serialVersionUID = 1L;
	public static String HOST_NAME;
	public static String PORT;
	public static String DB_NAME;
	public static String LOGIN_USER;
	public static String LOGIN_PWD;
	
	public static String CREDENTIALS_FILE_PATH;
	private Properties dbConfProp = new Properties();
	
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Inside INIT");
		initLog4j(config);
		initResources();
		initDBResources(config);
		initSecureKey(config);
		invokeReadMails();
		
		super.init(config);
	}

	private void initSecureKey(ServletConfig config) {
		System.out.println("init secure key");
		CREDENTIALS_FILE_PATH = config.getInitParameter("secure_key");
	}

	private void invokeReadMails() {
		
		/*Thread t = new Thread() {
		    @Override
		    public void run() {
		        while(true) {
		            try {
		                log.debug("Invoking read mails...");
		               
		        		Thread.sleep(1000*60*3);
		            } catch (InterruptedException ie) {
		            }
		        }
		    }
		};
		t.start();*/
		
		System.out.println("invoking read mails");
		ReadMails mails = new ReadMails();
         
 		try {
 			mails.freeParkingSlot();
 		} catch (IOException | GeneralSecurityException | ParseException  e) {
 			System.out.println("Exception caught while reading mail");
 			log.error("Exception caught while reading mails: "+e.getMessage());
 		}
	}

	private void initDBResources(ServletConfig config) {
		System.out.println("Init DB resources");
		String dbConfigPath = config.getInitParameter("db-conf");
		try {
			dbConfProp.load(new FileInputStream(dbConfigPath));
			HOST_NAME = dbConfProp.getProperty("HOST");
			PORT = dbConfProp.getProperty("PORT");
			DB_NAME = dbConfProp.getProperty("DB_NAME");
			LOGIN_USER = dbConfProp.getProperty("LOGIN_USER");
			LOGIN_PWD = dbConfProp.getProperty("DB_PWD");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void initResources() {

		System.out.println("************** Initializing ResourceFactory from ResourceInitServlet ***************");

		new ResourceFactory();
	}

	

	private void initLog4j(ServletConfig config) {
		System.out.println("Log4JInitServlet is initializing log4j");
		String log4jLocation = config.getInitParameter("log4j-properties-location");

//		ServletContext sc = config.getServletContext();
		// TODO Auto-generated method stub
		if (log4jLocation == null) {
			System.err.println(
					"*** No log4j-properties-location init param");
		} 
		else 
		{
//			String webAppPath = sc.getRealPath(File.separator);
//			System.out.println("webAppPath:--->" + webAppPath);
//			String log4jProp = webAppPath + log4jLocation;
//			File log4jConfigFile = new File(log4jLocation);
				System.out.println("Initializing log4j with: " + log4jLocation);
				log.info("Hi");
				try
		        {
					System.out.println("Before init");
					log.info("before init");
		            DOMConfigurator.configure(log4jLocation);
		            log.info("initialized");
		            log.debug("Hi This is success");
		        }
		        catch (Exception exp)
		        {
		            log.error("Error ", exp);
		        }
			
		}
	}
}
