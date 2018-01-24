package com.amir.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.amir.domain.Document;

@DataJpaTest
public class DocumentDaoTest {
	 @Autowired
	 private TestEntityManager entityManager;
	 
	 @Autowired
	 private DocumentDao DocumentDao;
	 
	 @Test
	 public void whenFindByFileContains_thenReturnListDocsContainingInputText() {
	     Document doc = new Document(1L, "doc1.txt", "document test Content");
	     entityManager.persist(doc);
	     entityManager.flush();
	     
	     List<Document> found = DocumentDao.findByFileContainsAllIgnoreCase("test Content");
	  
	     assertEquals(found.get(0).getDocName(), doc.getDocName());
	 }
}
