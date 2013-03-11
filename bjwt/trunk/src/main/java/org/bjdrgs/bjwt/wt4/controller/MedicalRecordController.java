package org.bjdrgs.bjwt.wt4.controller;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.bjdrgs.bjwt.core.util.SpringContextUtils;
import org.bjdrgs.bjwt.core.web.AjaxResult;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.wt4.model.MedicalRecord;
import org.bjdrgs.bjwt.wt4.parameter.MedicalRecordParam;
import org.bjdrgs.bjwt.wt4.service.IMedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wt4/medicalRecord")
public class MedicalRecordController {
	public static final String ENCODING = "UTF-8";

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="medicalRecordService")
	private IMedicalRecordService medicalRecordService;
	
	@RequestMapping("/page")
	@ResponseBody
	public GridPage<MedicalRecord> page(MedicalRecordParam param){
		if(param.getLe_AAC01()!=null){
			//将晚于的时间设为当天的23:59:59 999
			Date date = param.getLe_AAC01();
			date.setTime(date.getTime()+24*3600*1000-1);
			param.setLe_AAC01(date);
		}
		GridPage<MedicalRecord> page =GridPage.valueOf(medicalRecordService.query(param));
		return page;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(@RequestBody MedicalRecord[] entities) {
		AjaxResult result = new AjaxResult();
		medicalRecordService.save(entities);
		result.setSuccess(true);
		result.setMessage(SpringContextUtils.getMessage("sys.save.success"));
		return result;
	}
	
//	@RequestMapping("/export")
//	public ResponseEntity<byte[]> export(MedicalRecordParam param){		
//		String xml = null;
//		try {
//			List<MedicalRecord> list = medicalRecordService.queryAll(param);
//			xml = medicalRecordService.exportToXML(list);
//			HttpHeaders headers = new HttpHeaders();  
//			headers.setContentType(MediaType.APPLICATION_XML);  
//			headers.setContentDispositionFormData("attachment", "export.xml");
//			return new ResponseEntity<byte[]>(xml.getBytes(), headers, HttpStatus.CREATED);
//		} catch (Exception e) {
//			logger.warn(e.toString());
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	@RequestMapping("/export")
	public void export(MedicalRecordParam param, HttpServletResponse response){		
		String xml = null;
		OutputStream output = null;
		try {
			List<MedicalRecord> list = medicalRecordService.queryAll(param);
			xml = medicalRecordService.exportToXML(list);
			
			response.setContentType("text/xml;charset="+ENCODING);
			response.setCharacterEncoding(ENCODING);
			response.setHeader("Content-disposition", "attachment;filename=untitled.xml");
			long fileLength = xml.length();
			String length = String.valueOf(fileLength);
			response.setHeader("Content_Length", length+1024*10);
			
			output = response.getOutputStream();
			
			IOUtils.write(xml, output, ENCODING);
			output.flush();
		} catch (Exception e) {
			logger.warn(e.toString());
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(output);
		}
	}	
	
	@RequestMapping("/download")
	public void download(@RequestParam("mrXMl")String xml, HttpServletResponse response){
		OutputStream output = null;
		try {
			response.setContentType("text/xml;charset="+ENCODING);
			response.setCharacterEncoding(ENCODING);
			response.setHeader("Content-disposition", "attachment;filename=untitled.xml");
			long fileLength = xml.length();
			String length = String.valueOf(fileLength);
			response.setHeader("Content_Length", length+1024*10);
			
			output = response.getOutputStream();
			
			IOUtils.write(xml, output, ENCODING);
			output.flush();
		} catch (Exception e) {
			logger.warn(e.toString());
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(output);
		}
		
	}
}
