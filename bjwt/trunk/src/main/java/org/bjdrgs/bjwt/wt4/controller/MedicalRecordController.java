package org.bjdrgs.bjwt.wt4.controller;

import static org.bjdrgs.bjwt.wt4.Wt4Constants.ENCODING_GBK;
import static org.bjdrgs.bjwt.wt4.Wt4Constants.ENCODING_UTF8;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.service.IUserService;
import org.bjdrgs.bjwt.authority.utils.SecurityUtils;
import org.bjdrgs.bjwt.core.exception.BusinessLogicException;
import org.bjdrgs.bjwt.core.util.SpringContextUtils;
import org.bjdrgs.bjwt.core.util.TempFileUtils;
import org.bjdrgs.bjwt.core.util.ZipUtils;
import org.bjdrgs.bjwt.core.web.AjaxResult;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.wt4.Wt4Constants;
import org.bjdrgs.bjwt.wt4.model.MedicalRecord;
import org.bjdrgs.bjwt.wt4.parameter.MedicalRecordParam;
import org.bjdrgs.bjwt.wt4.service.IMedicalRecordService;
import org.bjdrgs.bjwt.wt4.viewmodel.ImportResult;
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

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "medicalRecordService")
	private IMedicalRecordService medicalRecordService;

	@Resource(name = "UserService")
	private IUserService userService;

	protected void transPram(MedicalRecordParam param) {
		if (param.getLe_AAC01() != null) {
			// 将晚于的时间设为当天的23:59:59 999
			Date date = param.getLe_AAC01();
			date.setTime(date.getTime() + 24 * 3600 * 1000 - 1);
			param.setLe_AAC01(date);
		}
	}

	@RequestMapping("/page")
	@ResponseBody
	public GridPage<MedicalRecord> page(MedicalRecordParam param) {
		transPram(param);

		GridPage<MedicalRecord> page = GridPage.valueOf(medicalRecordService
				.query(param));

		// 控制编辑权限
		User user = SecurityUtils.getCurrentUser();
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

	@RequestMapping("/exportToXML")
	public void exportToXML(MedicalRecordParam param,
			HttpServletResponse response) {

		transPram(param);

		File tempDir = null;
		OutputStream output = null;
		InputStream input = null;
		try {
			tempDir = TempFileUtils.createTempDir();
			List<File> xmlFiles = medicalRecordService.exportToXML(param,
					tempDir);

			String fileName = "export@"
					+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			File zipFile = new File(tempDir, fileName + ".zip");
			ZipUtils.zip(xmlFiles, zipFile);// 压缩

			response.setContentType("application/zip;charset=" + ENCODING_UTF8);
			response.setCharacterEncoding(ENCODING_UTF8);
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName + ".zip");
			long fileLength = FileUtils.sizeOf(zipFile);
			String length = String.valueOf(fileLength);
			response.setHeader("Content_Length", length + 1024 * 10);

			output = response.getOutputStream();
			input = new FileInputStream(zipFile);

			IOUtils.copy(input, output);
			output.flush();
		} catch (Exception e) {
			logger.warn(e.toString(), e);
		} finally {
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(input);
			// 删除临时文件
			FileUtils.deleteQuietly(tempDir);
		}
	}

	@RequestMapping("/exportToCSV")
	public void exportToCSV(MedicalRecordParam param,
			HttpServletResponse response) {
		transPram(param);

		File tempDir = null;
		OutputStream output = null;
		Reader reader = null;
		try {
			tempDir = TempFileUtils.createTempDir();
			String fileName = "export@"
					+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			File csvFile = new File(tempDir, fileName + ".csv");
			medicalRecordService.exportToCSV(param, csvFile);

			response.setContentType("application/csv;charset=" + ENCODING_GBK);
			response.setCharacterEncoding(ENCODING_GBK);
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName + ".csv");
			long fileLength = FileUtils.sizeOf(csvFile);
			String length = String.valueOf(fileLength);
			response.setHeader("Content_Length", length + 1024 * 10);

			output = response.getOutputStream();
			reader = new FileReader(csvFile);

			IOUtils.copy(reader, output, ENCODING_GBK);
			output.flush();
		} catch (Exception e) {
			logger.warn(e.toString(), e);
		} finally {
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(reader);
			// 删除临时文件
			FileUtils.deleteQuietly(tempDir);
		}
	}

	@RequestMapping("/download")
	public void download(@RequestParam("mrXMl") String xml,
			HttpServletResponse response) {
		OutputStream output = null;
		try {
			response.setContentType("text/xml;charset=" + ENCODING_UTF8);
			response.setCharacterEncoding(ENCODING_UTF8);
			response.setHeader("Content-disposition",
					"attachment;filename=untitled.xml");
			long fileLength = xml.length();
			String length = String.valueOf(fileLength);
			response.setHeader("Content_Length", length + 1024 * 10);

			output = response.getOutputStream();

			IOUtils.write(xml, output, ENCODING_UTF8);
			output.flush();
		} catch (Exception e) {
			logger.warn(e.toString(), e);
		} finally {
			IOUtils.closeQuietly(output);
		}

	}

	@RequestMapping("/importFile")
	@ResponseBody
	public AjaxResult importFile(MultipartFile importfile) {
		AjaxResult result = new AjaxResult();
		File tempDir = null;
		try {
			String fileName = importfile.getOriginalFilename();
			tempDir = TempFileUtils.createTempDir();
			File file = new File(tempDir, fileName);
			importfile.transferTo(file);

			Collection<File> xmlFileList = null;
			if (fileName != null && fileName.matches("^.+\\.(?i)xml$")) {
				xmlFileList = new ArrayList<File>();
				xmlFileList.add(file);
			} else if (fileName != null && fileName.matches("^.+\\.(?i)zip$")) {
				File unzipDir = new File(tempDir, fileName.substring(0,
						fileName.lastIndexOf(".")));
				ZipUtils.unZip(file, unzipDir);
				xmlFileList = FileUtils.listFiles(unzipDir,
						new String[] { "xml" }, true);
			} else {
				result.setSuccess(false);
				result.setMessage("导入失败！导入文件格式有误，只能倒入xml或zip文件！");
				return result;
			}
			ImportResult importResult = medicalRecordService
					.importXmlFile(xmlFileList);
			result.setSuccess(true);
			result.setData(importResult);
			result.setMessage("导入成功！");
		} catch (BusinessLogicException e) {
			result.setSuccess(false);
			result.setMessage("导入失败: " + e.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("对不起，系统出错，导入失败！");
			logger.error(e.getMessage(), e);
		} finally {
			// 删除临时文件
			FileUtils.deleteQuietly(tempDir);
		}
		return result;
	}
}
