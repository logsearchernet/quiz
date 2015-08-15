package ms.survey.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ms.survey.model.FormEntity;
import ms.survey.model.ItemEntity;
import ms.survey.model.PageEntity;
import ms.survey.model.PartEntity;
import ms.survey.service.FormService;



@Controller
public class WebController extends WebMvcConfigurerAdapter {
	
	static String email = "log.searcher@yahoo.com";
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private FormService formService;
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request){
		
		System.out.println("INDEX");
    	
    	return "index";
    }
	
	@RequestMapping(value="/form/{formid}", method = RequestMethod.GET)
    public String form(@PathVariable String formid, HttpServletRequest request){
		
		System.out.println("FORM");
		
		FormEntity form = formService.load(formid);
		
		FileOutputStream output;
		try {
			output = new FileOutputStream(new File("D:\\upload\\form.ser"));
			byte[] b = SerializationUtils.serialize(form);
			IOUtils.write(b, output);
			IOUtils.closeQuietly(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		request.setAttribute("form", form);
    	
    	return "form";
    }
	
	@RequestMapping(value="/loadSandbox", method=RequestMethod.POST)
	public ResponseEntity<FormEntity> load(HttpEntity<String> requestEntity, HttpSession session) {
		
		System.out.println("LOAD SANDBOX");
		
		String formid = requestEntity.getBody();
		FormEntity form = new FormEntity();
		if (StringUtils.isNotBlank(formid) && !StringUtils.equalsIgnoreCase(formid, "new")){
			form = formService.load(formid);
			/*List<PageEntity> pages = form.getPages();
			for (PageEntity page : pages) {
				String pagesn = page.getPagesn();
				System.out.println("pagesn==>"+pagesn);
			}*/
		}
		
		return new ResponseEntity<FormEntity>(form, HttpStatus.OK);
	}
	
	@RequestMapping(value="/sandbox/{formid}", method = RequestMethod.GET)
    public String sandbox(@PathVariable String formid, HttpServletRequest request){
		
		System.out.println("SANDBOX");
		request.setAttribute("formid", formid);
    	
    	return "sandbox";
    }
	
	@RequestMapping(value="/sandboxPost", method=RequestMethod.POST)
	public ResponseEntity<Set<String>> formSubmit(HttpEntity<FormEntity> requestEntity, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("SANDBOX POST");
	
		FormEntity form = requestEntity.getBody();
		form.setEmail(email);
		form.setDateModified(new Date());
		
		formService.save(form);
		
		Set<String> set = new HashSet<String>();
		set.add(form.getFormid());
		
		return new ResponseEntity<Set<String>>(set, HttpStatus.OK);
	}
	
	@RequestMapping(value="/dashboard", method = RequestMethod.GET)
    public String dashboard(HttpServletRequest request){
		
		System.out.println("DASHBOARD");
		
		
		Long count = formService.count(email);
		System.out.println("count="+count);
		
		int firstRow = 0;
		int numRows = 20;
		List<FormEntity> formList = formService.loadPagination(firstRow, numRows, email);
		
		request.setAttribute("formList", formList);
    	
    	return "dashboard";
    }
	
	@RequestMapping(value="/remove/{formid}", method = RequestMethod.GET)
    public String dashboard(@PathVariable String formid, HttpServletRequest request){
		
		formService.remove(formid);
		
		return "redirect:../dashboard";
	}
}


