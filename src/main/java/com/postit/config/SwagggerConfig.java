package com.postit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/*
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
*/

@Configuration
public class SwagggerConfig {
	
	

	/*
	private ApiKey apiKeys() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private List<SecurityContext> securityContexts() {
		return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
	}

	private List<SecurityReference> securityReferences() {

		AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");

		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] { scope }));
	}

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();

	}

	private ApiInfo getInfo() {

		return new ApiInfo("Blogging Application : Post It",
				"This project is developed with Java, React and love", "1.0", "Terms of Service",
				new Contact("Kartikeya","https://www.linkedin.com/in/kartikeyadubey/","kartikeyadubey1997@gmail.com"),
				"License of APIS", "API license URL", Collections.emptyList());
	};
	*/
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String SCHEME_NAME = "bearerAuth";
	public static final String BEARER_FORMAT = "JWT";
	public static final String SCHEME = "Bearer";
	
	
	//View API Documentation in web browser: http://localhost:9090/swagger-ui/index.html
	
	
	Contact contact = new Contact();
	
	@Bean
	public OpenAPI caseOpenAPI() {
		
		contact.setName("Kartikeya");
		contact.setUrl("https://www.linkedin.com/in/kartikeyadubey/");
		contact.setEmail("kartikeyadubey1997@gmail.com");
		
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
				.components(new Components()
						.addSecuritySchemes(SCHEME_NAME, new SecurityScheme()
								.name(SCHEME_NAME)
								.type(SecurityScheme.Type.HTTP)
								.bearerFormat(BEARER_FORMAT)
								.in(SecurityScheme.In.HEADER)
								.scheme(SCHEME)
								
								)
						)
				.info(new Info()
						.title("Blogging Application : Post It")
						.description("This project is developed with Java, React and love")
						.version("1.0")
						.contact(contact)
					);
	}
}
