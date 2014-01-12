package org.bjdrgs.bjwt.core.util;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component
public class ServletContextUtils implements ServletContextAware {
	private static ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {
		ServletContextUtils.servletContext = servletContext;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static String getRealPath(String path) {
		return getServletContext().getRealPath(path);
	}
}
