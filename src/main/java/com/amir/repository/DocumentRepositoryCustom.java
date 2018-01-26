package com.amir.repository;

import java.util.List;

import com.amir.domain.Document;
public interface DocumentRepositoryCustom {
	
	List<Document> fulltextSearch(String name);
}
