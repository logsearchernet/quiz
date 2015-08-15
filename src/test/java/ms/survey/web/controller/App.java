package ms.survey.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.AbstractEnvironment;

import ms.survey.config.DataAccessConfig;
import ms.survey.config.PropertyPlaceholderConfig;
import ms.survey.model.FormEntity;
import ms.survey.model.ItemEntity;
import ms.survey.model.PageEntity;
import ms.survey.model.PartEntity;
import ms.survey.model.SetupEntity;
import ms.survey.service.FormService;

public class App {

	//private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {

		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "production");
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class, DataAccessConfig.class, PropertyPlaceholderConfig.class);
		
		/*AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.getEnvironment().setActiveProfiles("live");
		context.register(AppConfig.class);
		context.refresh();*/
		
		/*FormDao formDao =  context.getBean(FormDao.class);
		System.out.println("formDao="+formDao);*/
		
		
		String email = "a@a.com";
		FormService service = context.getBean(FormService.class);
		String formid = save(service, email);
		
		FormEntity form = service.load(formid);
		printForm(form);
		
		/*int firstRow = 0;
		int numRows = 3;
		loadPagination(service, firstRow, numRows, email);
		
		Long count = service.count(email);
		System.out.println("count="+count);*/
		
		/*service.remove(formid);
		
		Long count2 = service.count(email);
		System.out.println("count2="+count2);*/

		((ConfigurableApplicationContext) context).close();

	}
	
	public static void loadPagination(FormService service, int firstRow, int numRows, String email){
		
		List<FormEntity> list = service.loadPagination(firstRow, numRows, email);
		print(list);
	}
	
	public static void print(List<FormEntity> list){
		System.out.println("============ START =====================");
		for (FormEntity form : list) {
			printForm(form);
		}
		System.out.println("============= END =====================");
	}
	
	public static void printForm(FormEntity form){
		System.out.println("---------------- FORM START -------------------");
		System.out.println("form="+ form.toString());
		
		List<PageEntity> pages = form.getPages();
		for (PageEntity page : pages) {
			System.out.println("page="+page.toString());
			
			List<ItemEntity> items = page.getItems();
			for (ItemEntity item : items) {
				System.out.println(item.toString());
				
				List<PartEntity> parts = item.getParts();
				for (PartEntity part : parts) {
					System.out.println("part="+part.toString());
				}
			}
		}
		System.out.println("---------------- FORM END -------------------");
	}
	
	public static String save(FormService service, String email){
		
		String formid = UUID.randomUUID().toString();
		FormEntity form = new FormEntity();
		form.setFormid(formid);
		form.setEmail(email);
		
		PageEntity page = new PageEntity();
		page.setForm(form);
		page.setPagesn(UUID.randomUUID().toString());
		
		List<PageEntity> pages = new ArrayList<>();
		pages.add(page);
		form.setPages(pages);
		
		SetupEntity setup = new SetupEntity();
		setup.setTitle("Title here");
		setup.setRandomizedAnswer(false);
		setup.setRandomizedQuestion(false);
		setup.setShowAnswer(false);
		setup.setFormid(formid);
		form.setSetup(setup);
		
		ItemEntity item = new ItemEntity();
		item.setCol(1);
		item.setImg("");
		item.setItemsn(UUID.randomUUID().toString());
		item.setPage(page);
		item.setQuestion("Your Question");
		item.setRow(1);
		item.setType("radio");
		
		List<ItemEntity> items = new ArrayList<>();
		items.add(item);
		page.setItems(items);
		

		List<PartEntity> parts = new ArrayList<PartEntity>();
		for (int i = 0; i < 3; i++) {
			PartEntity part = new PartEntity();
			part.setAns(true);
			part.setAnsText("");
			part.setImg("");
			part.setPartsn(UUID.randomUUID().toString());
			part.setValue("Your Option");
			part.setItem(item);
			
			parts.add(part);
		}
		item.setParts(parts);
		
		service.save(form);
		
		return formid;
	}
}