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
import com.amir.repository.DocumentRepository;
import com.amir.service.DocumentService;
import com.amir.service.ResponseMetadata;
import com.amir.tika.ContentExtraction;

@Service
public class DocumentServiceImpl implements DocumentService {
	
	@Autowired
    private DocumentRepository documentRepository;
	
	@Override
    public ResponseMetadata save(MultipartFile file) throws IOException, SAXException, TikaException {
    	Document doc = new Document();
    	ElasticDocument elasticDocument = new ElasticDocument();
    	ResponseMetadata metadata = new ResponseMetadata();
    	ContentExtraction contentExtractor = new ContentExtraction();
		String c = contentExtractor.getContent(file);
		if(c != "Invalid content"){
			doc.setDocName(file.getOriginalFilename());
			elasticDocument.setDocName(file.getOriginalFilename());
			doc.setFile(c);
			elasticDocument.setFile(c);
			documentRepository.save(doc);
	        metadata.setMessage("success");
	        metadata.setStatus(200);
		}else{
			metadata.setMessage("File type not allowed.please select another file");
	        metadata.setStatus(200);
		}
		return metadata;
    }

    @Override
    public String getDocumentFile(Long id) {
      return documentRepository.findOne(id).getFile();
    }

    @Override
    public List<Document> findAll() {
        return (List<Document>) documentRepository.findAll();
    }
    @Override
    public ResponseMetadata deleteById(Long id){
    	documentRepository.delete(id);
    	ResponseMetadata metadata = new ResponseMetadata();
        metadata.setMessage("success");
        metadata.setStatus(200);
        return metadata;
    }

	@Override
	public List<Document> search(String searchQuery) {
		
		return documentRepository.findByFileContainsAllIgnoreCase(searchQuery);
	}
}
