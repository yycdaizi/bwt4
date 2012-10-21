package org.beiyi.wt4.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beiyi.SysInfo;
import org.beiyi.wt4.MappingXMLOperator;
import org.beiyi.wt4.dao.SqlExcuteHelper;
import org.beiyi.wt4.service.ICaseService;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

public class CaseServiceImpl implements ICaseService {

	@Override
	public long[] save(String xmlStr) throws Exception {
		Document document = DocumentHelper.parseText(xmlStr);
		List<Node> caseNodes = document.selectNodes("//CASE");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		for (Node node : caseNodes) {
			CaseXMLVisitor visitor = new CaseXMLVisitor(SysInfo.getMappingXMLOperator("medicalRecord"));
			node.accept(visitor);			
			list.add(visitor.getData());
		}
		long[] pks=new long[list.size()];
		int index=0;
		for (Map<String, Object> map : list) {
			pks[index++]=save(map, "medicalRecord");			
		}
		return pks;
	}
	@Override
	public boolean checkExist(long pk) throws Exception{
		SqlExcuteHelper sqlHelper=null;
		try{
			String sql="select count(b_wt4_id) from b_wt4 where b_wt4_id=?";
			sqlHelper=new SqlExcuteHelper();
			Object obj = sqlHelper.queryFisrt(sql, new Object[]{pk});
			if(obj!=null){
				long count=(Long)obj;
				return (count>0);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			sqlHelper.releaseQuitely();
		}
		return false;
	}
	
	private long save(Map<String, Object> map, String entityId){
		MappingXMLOperator operator = SysInfo.getMappingXMLOperator(entityId);
		String table = operator.getTable();
		
		Set<String> keySet = map.keySet();
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(table).append(" (");
		List<Object> params = new ArrayList<Object>();
		
		for (String key : keySet) {
			Object value = map.get(key);
			if(value instanceof List){
				continue;
			}
			sql.append(key).append(",");
			params.add(value);			
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(") values(");
		for(int i=0;i<params.size();i++){
			sql.append("?,");
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(")");
		
		System.out.println(sql.toString());
		SqlExcuteHelper sqlHelper=null;
		long pk_medical = 0L;
		//执行插入
		try{
			sqlHelper=new SqlExcuteHelper();
			sqlHelper.startTransaction();
			pk_medical = sqlHelper.insertSingle(sql.toString(), params.toArray());
			sqlHelper.commit();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			sqlHelper.releaseQuitely();
		}
		List<Node> listNode = operator.getDocument().selectNodes("//list");
		for (Node node : listNode) {
			String id = node.valueOf("@id");
			String joinColumn = node.valueOf("@join-column");
			String objectRef = operator.getObjectRef(node);
			List<Map<String, Object>> childList = (List<Map<String, Object>>) map.get(id);
			for (Map<String, Object> child : childList) {
				child.put(joinColumn, pk_medical);  //设置外键的值
				save(child, objectRef);
			}
		}
		return pk_medical;
	}
}
