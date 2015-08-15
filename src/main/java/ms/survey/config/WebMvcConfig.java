package ms.survey.config;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.cache.StandardCacheManager;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.conditionalcomments.dialect.ConditionalCommentsDialect;
import org.thymeleaf.extras.tiles2.dialect.TilesDialect;
import org.thymeleaf.extras.tiles2.spring4.web.configurer.ThymeleafTilesConfigurer;
import org.thymeleaf.extras.tiles2.spring4.web.view.FlowAjaxThymeleafTilesView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.AjaxThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


@Configuration
@EnableWebMvc	
@ComponentScan(basePackages = "ms.survey")
@Profile("production")
public class WebMvcConfig extends WebMvcConfigurerAdapter  {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/", "classpath:/META-INF/web-resources/");
		
		Integer period = 1;//31556926;
		registry.addResourceHandler("/images/**").addResourceLocations("/images/").setCachePeriod(period);
		registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(period);
		registry.addResourceHandler("/styles/**").addResourceLocations("/styles/").setCachePeriod(period);
		registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/").setCachePeriod(period);
		registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(period);
		registry.addResourceHandler("/scripts/**").addResourceLocations("/scripts/").setCachePeriod(period);
		registry.addResourceHandler("/plugins/**").addResourceLocations("/plugins/").setCachePeriod(period);
		registry.addResourceHandler("/template/**").addResourceLocations("/template/").setCachePeriod(period);
	}
	
	@Bean
	public MessageSource messageSource() {
	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    messageSource.setBasename("messages");
	    return messageSource;
	}
	
	@Bean
	@Profile("production")
	public AjaxThymeleafViewResolver tilesViewResolver() {
		AjaxThymeleafViewResolver viewResolver = new AjaxThymeleafViewResolver();
		viewResolver.setViewClass(FlowAjaxThymeleafTilesView.class);
		viewResolver.setTemplateEngine(templateEngine());
		return viewResolver;
	}

	@Bean
	@Profile("production")
	public SpringTemplateEngine templateEngine(){

		Set<IDialect> dialects = new LinkedHashSet<IDialect>();
		dialects.add(new TilesDialect());
		//dialects.add(new SpringSecurityDialect());
		dialects.add(new ConditionalCommentsDialect());

		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setAdditionalDialects(dialects);
		
		// Default is 50
		StandardCacheManager cacheManager = new StandardCacheManager();
		cacheManager.setTemplateCacheMaxSize(100);	
		templateEngine.setCacheManager(cacheManager);
		
		return templateEngine;
	}

	@Bean
	@Profile("production")
	public ServletContextTemplateResolver templateResolver() {
		
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheable(false);
		return templateResolver;
	}
	
	@Bean
	@Profile("production")
	public ThymeleafTilesConfigurer tilesConfigurer() {
		ThymeleafTilesConfigurer configurer = new ThymeleafTilesConfigurer();
		configurer.setDefinitions("/WEB-INF/**/views.xml");
		return configurer;
	}
	
	@Bean
	@Profile("production")
	public LocalValidatorFactoryBean validator(){
		LocalValidatorFactoryBean b = new LocalValidatorFactoryBean();
		b.setValidationMessageSource(messageSource());
		return b;
	}
	
	@Bean
	@Profile("production")
	public CommonsMultipartResolver multipartResolver(){
		CommonsMultipartResolver r = new CommonsMultipartResolver();
		r.setMaxUploadSize(10000000);
		
		return r;
	}
}
