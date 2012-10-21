package org.beiyi.wt4.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beiyi.wt4.service.ICaseService;
import org.beiyi.wt4.service.impl.CaseServiceImpl;

/**
 * Servlet implementation class InsertServlet
 */
public class SaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String xmlStr = (String) request.getParameter("xmlStr");
		String pk_case = (String) request.getParameter("pk_case");
		if(xmlStr==null || xmlStr.length()==0){
			showResultMsg(response, "xmlStr为空!");
			return;
		}
		System.out.println(pk_case);
		xmlStr=new String(xmlStr.getBytes("ISO-8859-1"),"UTF-8");
		try {
			ICaseService caseService=new CaseServiceImpl();
			if(pk_case!=null && pk_case.length()>0){
				long originPk = Long.parseLong(pk_case);
				boolean isExist = caseService.checkExist(originPk);
				if(isExist){
					showResultMsg(response, toJsonStr(originPk, "数据已保存，请勿重复提交！"));
					return;
				}
			}
			long pk = caseService.save(xmlStr)[0];
			showResultMsg(response, toJsonStr(pk, "保存成功！"));
		} catch (Exception e) {
			e.printStackTrace();
			showResultMsg(response, toJsonStr(0, "保存数据时失败,错误信息："+e.getMessage()));
			return;
		}
	}
	private String toJsonStr(long pk,String msg){
		return "{pk:\""+pk+"\",msg:\""+msg+"\"}";
	}
	private void showResultMsg(HttpServletResponse response,String resultMsg)throws ServletException, IOException{
		 response.setContentType("text/html;charset=utf-8");
		 response.setCharacterEncoding("utf-8");
	     PrintWriter out = response.getWriter();
	     out.print(resultMsg);
	     out.close();
	}

}
