package com.WebCrawling.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "urls")
public class Url {

    @Id
    private String id;

    private String url;

    public Url() {
        // Constructeur par défaut requis par Spring Data MongoDB
    }

    public Url(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Url{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
