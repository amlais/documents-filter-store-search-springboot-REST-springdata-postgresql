package com.amir.rest;

import com.amir.service.DocumentService;
import com.amir.service.ResponseMetadata;
import com.amir.service.impl.DocumentServiceImpl;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.tika.exception.TikaException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;
import org.junit.runner.RunWith;
import com.amir.dao.DocumentDao;
import com.amir.domain.Document;

@RunWith(SpringRunner.class)
public class DocumentServiceImplTest {
	
	@TestConfiguration
    static class DocumentServiceImplTestContextConfiguration {
  
        @Bean
        public DocumentService DocumentService() {
            return new DocumentServiceImpl();
        }
    }
	@Autowired
    private DocumentService DocumentService;
 
    @MockBean
    private DocumentDao DocumentDaoMock;
    
	
	@Before
	public void setUp(){
		Document doc = new Document(1L, "mockTitle.docx", "mockText big data");
		List<Document> docs = new ArrayList<>(
			    Arrays.asList(doc));
		Mockito.when(DocumentDaoMock.save(doc))
	      .thenReturn(doc);
		Mockito.when(DocumentDaoMock.findOne(doc.getId()))
	      .thenReturn(doc);
		Mockito.when(DocumentDaoMock.findAll())
	      .thenReturn(docs);
		Mockito.when( DocumentDaoMock.findBySearchQuery("big data"))
				.thenReturn(docs);
	}
	
	@Test
	public void fulltextSearchTest(){
		Document doc = new Document(1L, "mockTitle.docx", "mockText big data");
		List<Document> listDocs = DocumentService.fulltextSearch("big data");
		assertEquals(listDocs.get(0).getDocName(), doc.getDocName());
	}
	
	@Test
	public void saveTest() throws IOException, SAXException, TikaException{
		ResponseMetadata respSave = null;
		MockMultipartFile file = new MockMultipartFile("doc", "filename.pdf", "application/pdf", "some text to be loaded".getBytes());
		Document doc= new Document(1L, "mochTitle.docx", "mockText big data");
		respSave = DocumentService.save(file);
		assertTrue(respSave.getStatus()==200 && respSave.getMessage()=="success");
	}
	
	@Test
	public void getDocumentFileTest(){
		
	}
	
	@Test
	public void findAllTest(){
		
	}
	@Test
	public void deleteByIdTest(){
		
	}
}
