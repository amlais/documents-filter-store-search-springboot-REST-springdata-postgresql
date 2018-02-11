package com.amir.service;

import java.io.IOException;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.amir.domain.ElasticDocument;

public interface ElasticDocumentService {
	ResponseMetadata save(MultipartFile multipartFile) throws IOException, SAXException, TikaException;

	List<ElasticDocument> fulltextSearch(String searchQuery);
}
