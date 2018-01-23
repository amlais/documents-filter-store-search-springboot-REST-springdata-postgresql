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
		Mockito.doNothing().when( DocumentDaoMock).delete(1L);
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
		respSave = DocumentService.save(file);
		assertTrue(respSave.getStatus()==200 && respSave.getMessage()=="success");
	}
	
	@Test
	public void getDocumentFileTest(){
		String expectedContent = "mockText big data";
		String fileContent = DocumentService.getDocumentFile(1L);
		assertEquals(expectedContent, fileContent);
	}
	
	@Test
	public void findAllTest(){
		List<Document> expectedDocs = new ArrayList<>(
			    Arrays.asList(new Document(1L, "mockTitle.docx", "mockText big data")));
		List<Document> returnedDocs = DocumentService.findAll();
		//System.out.println("expectedDocs has:" + expectedDocs);
	    //System.out.println("returnedDocs has:" + returnedDocs);
		//assertTrue(expectedDocs.equals(returnedDocs));
		assertEquals(expectedDocs.get(0).getId(), returnedDocs.get(0).getId());
		assertEquals(expectedDocs.get(0).getDocName(), returnedDocs.get(0).getDocName());
		assertEquals(expectedDocs.get(0).getFile(), returnedDocs.get(0).getFile());
		assertEquals(expectedDocs.size(), returnedDocs.size());
	}
	@Test
	public void deleteByIdTest(){
		ResponseMetadata respSave = null;
		respSave = DocumentService.deleteById(1L);
		assertTrue(respSave.getStatus()==200 && respSave.getMessage()=="success");
	}
}
