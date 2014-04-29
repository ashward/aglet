package com.github.ashward.aglet.services;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.springframework.web.WebApplicationInitializer;

@ApplicationPath("/rest")
public class AgletApplication extends Application implements
		WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
}