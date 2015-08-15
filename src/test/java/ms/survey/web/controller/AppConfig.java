package ms.survey.web.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"ms.survey.dao", "ms.survey.service"})
public class AppConfig {

}
