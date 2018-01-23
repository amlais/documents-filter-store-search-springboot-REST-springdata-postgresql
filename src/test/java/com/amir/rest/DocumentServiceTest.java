package com.amir.rest;

import com.amir.service.DocumentService;
import com.amir.service.ResponseMetadata;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.tika.exception.TikaException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.xml.sax.SAXException;
import org.junit.runner.RunWith;
import com.amir.dao.DocumentDao;
import com.amir.domain.Document;

public class DocumentServiceTest {
	
	private DocumentService DocumentService;
	private DocumentDao DocumentDaoMock;
	private List<Document> mockdocumentsList1 = 
			of(new Document(1L, "mockTitle.docx", "mockText big data"))
			.collect(toList());
	@Before
	public void setUp(){
		DocumentDaoMock = Mockito.mock(DocumentDao.class);
	}
	
	@Test
	public void fulltextSearchTest(){
		Mockito.when(DocumentDaoMock.findBySearchQuery("big data")).thenReturn(mockdocumentsList1);
		List<Document> listDocs = DocumentService.fulltextSearch("big data");
		assertTrue(listDocs.contains(mockdocumentsList1));
	}
	
	@Test
	public void saveTest() throws IOException, SAXException, TikaException{
		ResponseMetadata resp = new ResponseMetadata(200, "success", null);
		ResponseMetadata respSave = new ResponseMetadata();
		MockMultipartFile file = new MockMultipartFile("doc", "filename.pdf", "application/pdf", "some text to be loaded".getBytes());
		Document doc= new Document(1L, "mochTitle.docx", "mockText big data");
		Mockito.when(
				DocumentDaoMock.save(doc)).thenReturn(doc);
		respSave = DocumentService.save(file);
		assertTrue(resp.equals(respSave));
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
