package com.amir.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.amir.domain.Document;
import com.amir.domain.ElasticDocument;
import com.amir.repository.ElasticDocumentRepository;
import com.amir.service.DocumentService;
import com.amir.service.ElasticDocumentService;
import com.amir.service.ResponseMetadata;
import com.amir.tika.ContentExtraction;

@Service
public class ElasticDocumentServiceImpl implements ElasticDocumentService{
	@Autowired
	ElasticDocumentRepository elasticDocumentRepository;
	
	@Override
    public ResponseMetadata save(MultipartFile file) throws IOException, SAXException, TikaException {
		
    	ElasticDocument elasticDocument = new ElasticDocument();
    	ResponseMetadata metadata = new ResponseMetadata();
    	ContentExtraction contentExtractor = new ContentExtraction();
		String c = contentExtractor.getContent(file);
		if(c != "Invalid content"){
			elasticDocument.setDocName(file.getOriginalFilename());
			elasticDocument.setFile(c);
			elasticDocumentRepository.save(elasticDocument);
	        metadata.setMessage("success");
	        metadata.setStatus(200);
		}else{
			metadata.setMessage("File type not allowed.please select another file");
	        metadata.setStatus(200);
		}
		return metadata;
    }
	
	@Override
	public List<ElasticDocument> fulltextSearch(String searchQuery) {
		return elasticDocumentRepository.findByFileContainingAllIgnoreCase(searchQuery);
	}
}
