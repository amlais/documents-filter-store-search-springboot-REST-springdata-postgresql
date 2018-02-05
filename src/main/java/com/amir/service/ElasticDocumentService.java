package com.amir.service;

import java.util.List;

import com.amir.domain.ElasticDocument;

public interface ElasticDocumentService {
	
	List<ElasticDocument> fulltextSearch(String searchQuery);
}
