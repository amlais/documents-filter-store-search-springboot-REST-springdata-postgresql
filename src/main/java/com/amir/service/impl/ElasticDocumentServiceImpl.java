package com.amir.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amir.domain.ElasticDocument;
import com.amir.repository.ElasticDocumentRepository;
import com.amir.service.DocumentService;
import com.amir.service.ElasticDocumentService;

@Service
public class ElasticDocumentServiceImpl implements ElasticDocumentService{
	@Autowired
	ElasticDocumentRepository elasticDocumentRepository;
	
	@Override
	public List<ElasticDocument> fulltextSearch(String searchQuery) {
		return elasticDocumentRepository.findByFileContainingAllIgnoreCase(searchQuery);
	}
}
