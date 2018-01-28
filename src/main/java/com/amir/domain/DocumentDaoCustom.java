package com.amir.domain;

import java.util.List;

import com.amir.domain.Document;
public interface DocumentDaoCustom {
	
	List<Document> fulltextSearch(String name);
}
