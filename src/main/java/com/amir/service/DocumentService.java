package com.amir.service;

import java.io.IOException;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.amir.domain.Document;

@Transactional
public interface DocumentService {
	public List<Document> fulltextSearch(String searchQuery);

    ResponseMetadata save(MultipartFile multipartFile) throws IOException, SAXException, TikaException;

    String getDocumentFile(Long id);

    List<Document> findAll();

	void deleteById(Long id);
}
