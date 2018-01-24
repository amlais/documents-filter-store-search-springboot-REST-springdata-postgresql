package com.amir.dao;

import static org.junit.Assert.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.amir.domain.Document;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DocumentDaoTest {
	 //@Autowired (removed because Springboot provides autoconfiguredTestEntityManager
	 //private TestEntityManager entityManager;
	 
	 @Autowired
	 private DocumentDao DocumentDao;
	 
	 @Test
	 public void whenFindByFileContains_thenReturnListDocsContainingInputText() {
	     Document doc = new Document(1L, "doc1.txt", "document test Content");
	     DocumentDao.save(doc);
	     
	     List<Document> found = DocumentDao.findByFileContainsAllIgnoreCase("test Content");
	  
	     assertEquals(found.get(0).getDocName(), doc.getDocName());
	 }
}
