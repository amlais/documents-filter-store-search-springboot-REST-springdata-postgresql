package com.amir.repository;

import static org.junit.Assert.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;
import com.amir.domain.Document;
import com.amir.repository.DocumentRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DocumentRepositoryTest {
	 //@Autowired (removed because Springboot provides autoconfiguredTestEntityManager
	 //private TestEntityManager entityManager;
	 
	 @Autowired
	 private DocumentRepository DocumentRepository;
	 @Test
	public void should_store_a_document() {
		Document doc = DocumentRepository.save(new Document(1L, "doc1.txt", "document test Content"));
 
		assertThat(doc).hasFieldOrPropertyWithValue("docName", "doc1.txt");
		assertThat(doc).hasFieldOrPropertyWithValue("file", "document test Content");
	}
	 
	 @Test
	 public void whenFindByFileContains_thenReturnListDocsContainingInputText() {
	     Document doc = new Document(1L, "doc1.txt", "document test Content");
	     DocumentRepository.save(doc);
	     
	     List<Document> found = DocumentRepository.findByFileContainsAllIgnoreCase("test Content");
	  
	     assertEquals(found.get(0).getDocName(), doc.getDocName());
	 }
	 
	 @Test
	 public void should_delete_all_documents(){
		 DocumentRepository.save(new Document(1L, "doc1.txt", "document test Content1"));
		 DocumentRepository.save(new Document(1L, "doc2.txt", "document test Content2"));
		 
		 DocumentRepository.deleteAll();
		 
		 assertTrue(((List<Document>) DocumentRepository.findAll()).isEmpty());
	 }
	 
	 @Test
	 public void should_find_all_documents(){
		 Document doc1 = DocumentRepository.save(new Document(1L, "doc1.txt", "document test Content1"));
		 Document doc2 = DocumentRepository.save(new Document(1L, "doc2.txt", "document test Content2"));
		 
		 Iterable<Document> docs = DocumentRepository.findAll();
		 
		 assertThat(docs).hasSize(2).contains(doc1, doc2);
	 }
}
