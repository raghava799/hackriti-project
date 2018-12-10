package com.alacriti.hackriti.logging.utils;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import com.alacriti.hackriti.factory.ResourceFactory;

public class ResourceInitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {

		// initLog4j(config);
		initResources();

		super.init(config);
	}

	private void initResources() {

		System.out.println("************** Initializing ResourceFactory from ResourceInitServlet ***************");

		new ResourceFactory();
	}

	private void initLog4j(ServletConfig config) {
		System.out.println("Log4JInitServlet is initializing log4j");
		String log4jLocation = config.getInitParameter("log4j-properties-location");

		ServletContext sc = config.getServletContext();
		// TODO Auto-generated method stub
		if (log4jLocation == null) {
			System.err.println(
					"*** No log4j-properties-location init param, so initializing log4j with BasicConfigurator");
			BasicConfigurator.configure();
		} else {
			String webAppPath = sc.getRealPath(File.separator);
			System.out.println("webAppPath:--->" + webAppPath);
			String log4jProp = webAppPath + log4jLocation;
			File log4jConfigFile = new File(log4jProp);
			if (log4jConfigFile.exists()) {
				System.out.println("Initializing log4j with: " + log4jProp);
				PropertyConfigurator.configure(log4jProp);
			} else {
				System.err
						.println("*** " + log4jProp + " file not found, so initializing log4j with BasicConfigurator");
				BasicConfigurator.configure();
			}
		}
	}
}
