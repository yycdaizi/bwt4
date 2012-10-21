package org.beiyi.wt4.service;

public interface ICaseService {
	/**
	 * 保存xml字符串内容到数据库
	 * @param xmlStr
	 * @throws Exception
	 */
	long[] save(String xmlStr) throws Exception;
	
	/**
	 * 校验是否存在
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	boolean checkExist(long pk) throws Exception;
}
