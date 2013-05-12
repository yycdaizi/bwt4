package org.bjdrgs.bjwt.authority.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bjdrgs.bjwt.authority.utils.ContextUtils;

public class PermissionFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest)
				|| !(response instanceof HttpServletResponse)) {
			throw new ServletException(
					"PermissionFilter just supports HTTP requests");
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String path = httpRequest.getContextPath();
		String basePath = httpRequest.getScheme() + "://"
				+ request.getServerName() + ":" + httpRequest.getServerPort()
				+ path + "/";
		String urlPath = basePath+path;
		String url = httpRequest.getRequestURI().toString();
		if(!ContextUtils.isPermitted(url)){
			httpResponse.sendRedirect(urlPath+"/login.jsp");
			return;
		}
		chain.doFilter(httpRequest, httpResponse);
	}

	@Override
	public void destroy() {

	}

}
