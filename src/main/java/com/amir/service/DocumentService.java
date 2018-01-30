package com.amir.service;

import java.io.IOException;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.amir.domain.Document;
import com.amir.domain.ElasticDocument;

public interface DocumentService {
	List<Document> search(String searchQuery);

    ResponseMetadata save(MultipartFile multipartFile) throws IOException, SAXException, TikaException;

    String getDocumentFile(Long id);

    List<Document> findAll();

	ResponseMetadata deleteById(Long id);

	List<ElasticDocument> fulltextSearch(String searchQuery);
}
