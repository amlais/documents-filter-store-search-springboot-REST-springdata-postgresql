package com.amir.service;

import java.io.IOException;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.amir.dao.DocumentDao;
import com.amir.domain.Document;
import com.amir.tika.ContentExtraction;

@Service
public class DocumentServiceImpl implements DocumentService {
    
	@Autowired
    private DocumentDao documentDao;
    @SuppressWarnings("null")
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
    public void deleteById(Long id){
    	documentDao.delete(id);
    }

	@Override
	public List<Document> fulltextSearch(String searchQuery) {
		
		return (List<Document>)documentDao.findBySearchQuery(searchQuery);
	}

}
