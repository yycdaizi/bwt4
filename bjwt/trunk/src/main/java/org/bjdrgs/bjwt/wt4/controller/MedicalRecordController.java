package org.bjdrgs.bjwt.wt4.controller;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.service.IUserService;
import org.bjdrgs.bjwt.authority.utils.SecurityUtils;
import org.bjdrgs.bjwt.core.util.SpringContextUtils;
import org.bjdrgs.bjwt.core.web.AjaxResult;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.wt4.Wt4Constants;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/wt4/medicalRecord")
public class MedicalRecordController {
	public static final String ENCODING = "UTF-8";

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "medicalRecordService")
	private IMedicalRecordService medicalRecordService;

	@Resource(name = "UserService")
	private IUserService userService;

	@RequestMapping("/page")
	@ResponseBody
	public GridPage<MedicalRecord> page(MedicalRecordParam param) {
		if (param.getLe_AAC01() != null) {
			// 将晚于的时间设为当天的23:59:59 999
			Date date = param.getLe_AAC01();
			date.setTime(date.getTime() + 24 * 3600 * 1000 - 1);
			param.setLe_AAC01(date);
		}
		// 只能查询本机构下面的病案
		User user = SecurityUtils.getCurrentUser();
		param.setOrgId(user.getOrg().getOrgid());

		GridPage<MedicalRecord> page = GridPage.valueOf(medicalRecordService
				.query(param));

		// 控制编辑权限
		boolean isDirector = userService.hasRole(user,
				Wt4Constants.ROLE_DIRECTOR);
		for (MedicalRecord obj : page.getRows()) {
			boolean editable = false;
			if (user.getUserid().equals(obj.getCreatedBy().getUserid())) {
				editable = true;
			} else if (isDirector) {
				editable = true;
			}
			obj.setEditable(editable);
		}
		return page;
	}

	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(@RequestBody MedicalRecord[] entities) {
		AjaxResult result = new AjaxResult();
		for (MedicalRecord medicalRecord : entities) {
			// 设置状态为完成
			medicalRecord.setState(MedicalRecord.STATE_COMPLETE);
		}
		medicalRecordService.save(entities);
		result.setSuccess(true);
		result.setMessage(SpringContextUtils.getMessage("sys.save.success"));
		return result;
	}

	@RequestMapping("/saveDraft")
	@ResponseBody
	public AjaxResult saveDraft(@RequestBody MedicalRecord entity) {
		AjaxResult result = new AjaxResult();
		// 设置状态为草稿
		entity.setState(MedicalRecord.STATE_DRAFT);
		medicalRecordService.save(entity);
		result.setSuccess(true);
		result.setMessage(SpringContextUtils.getMessage("sys.save.success"));
		result.setData(entity.getId());
		return result;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public AjaxResult delete(Long id) {
		AjaxResult result = new AjaxResult();
		MedicalRecord entity = medicalRecordService.get(id);
		// 只有草稿能删除，其他的不能删
		if (MedicalRecord.STATE_DRAFT.equals(entity.getState())) {
			medicalRecordService.delete(entity);
			result.setSuccess(true);
			result.setMessage(SpringContextUtils
					.getMessage("sys.delete.success"));
		} else {
			result.setSuccess(false);
			result.setMessage("此病案不是草稿，不能删除！");
		}
		return result;
	}

	// @RequestMapping("/export")
	// public ResponseEntity<byte[]> export(MedicalRecordParam param){
	// String xml = null;
	// try {
	// List<MedicalRecord> list = medicalRecordService.queryAll(param);
	// xml = medicalRecordService.exportToXML(list);
	// HttpHeaders headers = new HttpHeaders();
	// headers.setContentType(MediaType.APPLICATION_XML);
	// headers.setContentDispositionFormData("attachment", "export.xml");
	// return new ResponseEntity<byte[]>(xml.getBytes(), headers,
	// HttpStatus.CREATED);
	// } catch (Exception e) {
	// logger.warn(e.toString());
	// e.printStackTrace();
	// return null;
	// }
	// }

	@RequestMapping("/export")
	public void export(MedicalRecordParam param, HttpServletResponse response) {
		String xml = null;
		OutputStream output = null;
		try {
			// 只能查询本机构下面的病案
			User user = SecurityUtils.getCurrentUser();
			param.setOrgId(user.getOrg().getOrgid());

			List<MedicalRecord> list = medicalRecordService.queryAll(param);
			xml = medicalRecordService.exportToXML(list);

			response.setContentType("text/xml;charset=" + ENCODING);
			response.setCharacterEncoding(ENCODING);
			response.setHeader("Content-disposition",
					"attachment;filename=untitled.xml");
			long fileLength = xml.length();
			String length = String.valueOf(fileLength);
			response.setHeader("Content_Length", length + 1024 * 10);

			output = response.getOutputStream();

			IOUtils.write(xml, output, ENCODING);
			output.flush();
		} catch (Exception e) {
			logger.warn(e.toString());
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(output);
		}
	}

	@RequestMapping("/download")
	public void download(@RequestParam("mrXMl") String xml,
			HttpServletResponse response) {
		OutputStream output = null;
		try {
			response.setContentType("text/xml;charset=" + ENCODING);
			response.setCharacterEncoding(ENCODING);
			response.setHeader("Content-disposition",
					"attachment;filename=untitled.xml");
			long fileLength = xml.length();
			String length = String.valueOf(fileLength);
			response.setHeader("Content_Length", length + 1024 * 10);

			output = response.getOutputStream();

			IOUtils.write(xml, output, ENCODING);
			output.flush();
		} catch (Exception e) {
			logger.warn(e.toString());
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(output);
		}

	}

	@RequestMapping("/importFile")
	@ResponseBody
	public AjaxResult importFile(MultipartFile importfile,
			HttpServletRequest request) {
		AjaxResult result = new AjaxResult();
		InputStream input = null;
		File zipFile = null;
		try {
			String fileName = importfile.getOriginalFilename();
			if (fileName != null && fileName.matches("^.+\\.(?i)xml$")) {
				input = importfile.getInputStream();
				List<MedicalRecord> list = medicalRecordService
						.importXmlFile(input);
				result.setSuccess(true);
				result.setMessage("导入成功！共导入了" + list.size() + "条记录。");
			} else if (fileName != null && fileName.matches("^.+\\.(?i)zip$")) {
				String tempDirPath = request.getSession().getServletContext().getRealPath("/temp/mrimport");
				File tempDir = new File(tempDirPath);
				FileUtils.forceMkdir(tempDir);
				zipFile = File.createTempFile(fileName.substring(0, fileName.lastIndexOf(".")), ".zip", tempDir);
				importfile.transferTo(zipFile);
				
				List<MedicalRecord> list = medicalRecordService
						.importZipFile(zipFile);
				result.setSuccess(true);
				result.setMessage("导入成功！共导入了" + list.size() + "条记录。");
			} else {
				result.setSuccess(false);
				result.setMessage("导入失败！导入文件格式有误，只能倒入xml或zip文件！");
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("对不起，系统出错，导入失败！");
			logger.error(e.toString());
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(input);
			//删除临时文件
			FileUtils.deleteQuietly(zipFile);
		}
		return result;
	}
}
