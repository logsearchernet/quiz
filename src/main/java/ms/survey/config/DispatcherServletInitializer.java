package ms.survey.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		//servletContext.setInitParameter("spring.profiles.active", "production");

		//Set multiple active profile
		//servletContext.setInitParameter("spring.profiles.active", "dev, testdb");
	}
	
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		
		WebApplicationContext context = (WebApplicationContext)super.createRootApplicationContext();
	    ((ConfigurableEnvironment)context.getEnvironment()).setActiveProfiles("production");
	    return context;
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {
			WebMvcConfig.class,
			DataAccessConfig.class,
			PropertyPlaceholderConfig.class
		};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	/*@Override
	protected Filter[] getServletFilters() {
		return new Filter[] { new HiddenHttpMethodFilter(), new GzipFilter()};
	}

	@Override
	protected void registerDispatcherServlet(ServletContext servletContext) {
        super.registerDispatcherServlet(servletContext);

        servletContext.addListener(new SessionExpireListener());

    }*/
}
