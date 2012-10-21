package org.beiyi.wt4.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadServlet
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String xmlStr = (String) request.getParameter("xmlStr");
		if (xmlStr == null || xmlStr.length() == 0) {
			showResultMsg(response, "xmlStr为空!");
			return;
		}
		xmlStr=new String(xmlStr.getBytes("ISO-8859-1"),"UTF-8");
		OutputStream o = response.getOutputStream();
		byte b[] = new byte[1024];
		// the dialogbox of download file.
		response.setHeader("Content-disposition", "attachment;filename="
				+ "untitled.xml");
		// set the MIME type.
		response.setContentType("text/xml");
		// get the file length.
		long fileLength = xmlStr.length();
		String length = String.valueOf(fileLength);
		response.setHeader("Content_Length", length+1024*10);
		// download the file.
		o.write(xmlStr.getBytes());
		o.flush();
		o.close();

	}

	private void showResultMsg(HttpServletResponse response, String resultMsg)
			throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(resultMsg);
		out.close();
	}

}
