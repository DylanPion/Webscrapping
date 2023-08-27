package com.WebCrawling.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.WebCrawling.collection.Url;

@Repository
public interface UrlRepository extends MongoRepository<Url, String> {

}
