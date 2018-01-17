package com.amir.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.amir.dao.DocumentDao;
import com.amir.domain.Document;
import com.amir.service.DocumentService;
import com.amir.service.ResponseMetadata;
import com.amir.tika.ContentExtraction;

@Service
public class DocumentServiceImpl implements DocumentService {
    
	@Autowired
    private DocumentDao documentDao;
	
	@Override
    public ResponseMetadata save(MultipartFile file) throws IOException, SAXException, TikaException {
    	Document doc = new Document();
    	ResponseMetadata metadata = new ResponseMetadata();
    	ContentExtraction contentExtractor = new ContentExtraction();
		String c = contentExtractor.getContent(file);
		if(c != "Invalid content"){
			doc.setDocName(file.getOriginalFilename());
			doc.setFile(c);
			documentDao.save(doc);
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
      return documentDao.findOne(id).getFile();
    }

    @Override
    public List<Document> findAll() {
        return (List<Document>) documentDao.findAll();
    }
    @Override
    public ResponseMetadata deleteById(Long id){
    	documentDao.delete(id);
    	ResponseMetadata metadata = new ResponseMetadata();
        metadata.setMessage("success");
        metadata.setStatus(200);
        return metadata;
    }

	@Override
	public List<Document> fulltextSearch(String searchQuery) {
		
		return documentDao.findBySearchQuery(searchQuery);
	}

}
