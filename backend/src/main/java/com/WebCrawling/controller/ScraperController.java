package com.WebCrawling.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.WebCrawling.collection.Url;
import com.WebCrawling.repositories.UrlRepository;

@RestController
public class ScraperController {

    private final UrlRepository urlRepository; // Inject the repository

    public ScraperController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @PostMapping("/scraped-data")
    public String scrapeData(@RequestBody java.util.Map<String, String> request) {
        String urlValue = request.get("url");

        // Save the URL in the database
        Url url = new Url();
        url.setUrl(urlValue);
        urlRepository.save(url);

        System.out.println("L'URL reçue est la suivante : " + urlValue);
        String scrapedData = "L'URL entrée est la suivante. " + urlValue;
        return scrapedData;
    }
}
