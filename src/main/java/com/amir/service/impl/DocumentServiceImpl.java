package com.amir.service.impl;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.apache.lucene.util.QueryBuilder;
import org.apache.tika.exception.TikaException;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	
	@PersistenceContext
    private EntityManager em;
	
	 /** Hibernate Full Text Entity Manager. */
    private FullTextEntityManager ftem;
    
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
	public List<Document> search(String searchQuery) {
		
		return documentDao.findByFileContainsAllIgnoreCase(searchQuery);
	}

	@Override
	@Transactional
	public List<Document> fulltextSearch(String searchString) {
		
		if (ftem == null) {
            ftem = Search.getFullTextEntityManager(em);
        }
		// Create a Query Builder
        QueryBuilder qb = (QueryBuilder) ftem.getSearchFactory().buildQueryBuilder().forEntity(Document.class).get();
        // Create a Lucene Full Text Query
        org.apache.lucene.search.Query luceneQuery = ((org.hibernate.search.query.dsl.QueryBuilder) qb).bool()
                .must((Query) ((org.hibernate.search.query.dsl.QueryBuilder) qb).keyword()
                		.onFields("file").matching(searchString)).createQuery();
        Query fullTextQuery = (Query) ftem.createFullTextQuery(luceneQuery, Document.class);
        // Run Query and print out results to console
        return (List<Document>) ( (javax.persistence.Query) fullTextQuery).getResultList();
	}

}
