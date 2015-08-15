package ms.survey.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ms.survey.dao.FormDao;
import ms.survey.dao.ItemDao;
import ms.survey.dao.PageDao;
import ms.survey.dao.PartDao;
import ms.survey.model.FormEntity;
import ms.survey.model.ItemEntity;
import ms.survey.model.PageEntity;
import ms.survey.model.PartEntity;

@Service("formService")
public class FormService {

	@Autowired
	private FormDao formDao;
	
	@Autowired
	private PageDao pageDao;
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private PartDao partDao;
	
	public FormEntity load(String formid){
		FormEntity fe = formDao.find(formid);
		if(fe == null) {
	        throw new EntityNotFoundException("Form with id: " + formid + " was not found.");
	    }
		return fe;
	}
	
	@Transactional
	public void save(FormEntity form){

		form = assignValue4Form(form);
		
		String formid = form.getFormid();
		FormEntity fe = formDao.find(formid);
		
		if (fe==null){
			formDao.create(form);
		} else {
			formDao.update(form);
			List<PageEntity> pages = form.getPages();
			for (int i = 0; i < pages.size(); i++) {
				PageEntity page = pages.get(i);
				pageDao.update(page);
				List<ItemEntity> items = page.getItems();
				for (int j = 0; items!=null && j < items.size(); j++) {
					ItemEntity item = items.get(j);
					itemDao.update(item);
					List<PartEntity> parts = item.getParts();
					for (int k = 0; parts!=null && k < parts.size(); k++) {
						PartEntity part = parts.get(k);
						partDao.update(part);
					}
				}
			}
		}
	}
	
	private FormEntity assignValue4Form(FormEntity form){
		List<PageEntity> pages = form.getPages();
		for (int i = 0; i < pages.size(); i++) {
			
			PageEntity page = pages.get(i);
			page.setRank(i+1);
			page.setForm(form);
			List<ItemEntity> items = page.getItems();
			for (int j = 0; items!=null && j < items.size(); j++) {
				ItemEntity item = items.get(j);
				item.setRank(j+1);
				item.setPage(page);
				List<PartEntity> parts = item.getParts();
				for (int k = 0; parts!=null && k < parts.size(); k++) {
					PartEntity part = parts.get(k);
					part.setRank(k+1);
					part.setItem(item);
				}
			}
		}
		return form;
	}
	
	public Long count(String email){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		Long count = formDao.countWithNamedQuery("count", params);
		return count;
	}
	
	public List<FormEntity> loadPagination(int firstRow, int numRows, String email){
		
		Map<String, Boolean> sort = null;
		Map<String, Object> filters = new HashMap<String, Object>();
		Map<String, String> hints = new HashMap<String, String>();
		hints.put("email", email);
		return formDao.findLazy(firstRow, numRows, sort, filters, hints);
	}

	@Transactional
	public void remove(String formid) {
		FormEntity fe = formDao.find(formid);
		if(fe == null) {
	        throw new EntityNotFoundException("Form with id: " + formid + " was not found.");
	    }
		formDao.delete(formid);
	}
}
