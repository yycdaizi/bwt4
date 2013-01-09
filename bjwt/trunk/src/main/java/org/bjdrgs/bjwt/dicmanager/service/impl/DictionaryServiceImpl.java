package org.bjdrgs.bjwt.dicmanager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.dicmanager.model.DicItem;
import org.bjdrgs.bjwt.dicmanager.service.IDicItemService;
import org.bjdrgs.bjwt.dicmanager.service.IDictionaryService;
import org.springframework.stereotype.Service;

@Service("dictionaryService")
public class DictionaryServiceImpl implements IDictionaryService {

	@Resource(name = "dicItemService")
	private IDicItemService dicItemService;
	
	@Override
	public List<DicItem> listDicItemsByType(String type) {
		return dicItemService.findByType(type);
	}

	@Override
	public DicItem getDicItem(String type, String code) {
		return dicItemService.get(type, code);
	}

}
