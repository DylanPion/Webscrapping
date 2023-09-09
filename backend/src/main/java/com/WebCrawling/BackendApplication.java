package com.WebCrawling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan(basePackages = { "com.WebCrawling.controller", "com.WebCrawling.repositories" })

public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/scraped-data") // URL à laquelle vous souhaitez autoriser les requêtes
						.allowedOrigins("http://localhost:3000") // L'origine autorisée
						.allowedMethods("POST") // Méthodes HTTP autorisées
						.allowCredentials(true); // Autoriser les informations d'identification (si nécessaires)
			}
		};
	}
}

// Quesion : .allowedOrigins("http://localhost:3000") // L'origine autorisée
// Cela autorise tout les chemins depuis sur site ou uniquement la racine
// localhost:3000 ?
