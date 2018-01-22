package com.amir.rest;

import com.amir.service.DocumentService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.amir.dao.DocumentDao;

public class DocumentServiceTest {
	
	private DocumentService DocumentService;
	private DocumentDao DocumentDaoMock;
	
	@Before
	public void setUp(){
		DocumentDaoMock = Mockito.mock(DocumentDao.class);
	}
	
	@Test
	public void fulltextSearchTest(){
		
	}
	
	@Test
	public void saveTest(){
		
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
