package com.amir.rest;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.amir.domain.Document;
import com.amir.service.DocumentService;
import com.amir.service.ElasticDocumentService;
import com.amir.service.ResponseMetadata;

/**
 * 
 * @author Amir
 * 
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = DocumentController.class, secure = false)
public class DocumentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DocumentService documentService;
	
	@MockBean
	private ElasticDocumentService elasticDocumentService;
	
	Document mockDocument = new Document(1L, "mockTitle.docx", "\n\nmockTextFileContent\n\n\n");
	Document mockDocument1 = new Document(2L, "mockTitle1.docx", "mockTextFileContent1");
	
	List<Document> mockdocumentsList1 = 
			of(new Document(1L, "mockTitle.docx", "mockTextFileContent"), mockDocument1)
			.collect(toList());
	
	List<Document> mockdocumentsList2 = 
			of(new Document(1L, "mockTitle.docx", "mockTextFileContent"))
			.collect(toList());
	
	ResponseMetadata mockResp = new ResponseMetadata(200, "success", null);
	
	@Test
	public void retrieveFileContent() throws Exception {

		Mockito.when(
				documentService.getDocumentFile(Mockito.anyLong())).thenReturn(mockDocument.getFile());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/doc/2").accept(
				MediaType.TEXT_PLAIN_VALUE);
		//Get MockMvcRequest
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = "mockTextFileContent";
		assertEquals("The expected String doesn't matchwith WS getDocumentFile return", expected, (result.getResponse().getContentAsString().replaceAll(System.getProperty("line.separator"),"").trim()));
	}
	
	@Test
	public void retrieveListDocs() throws Exception {

		Mockito.when(
				documentService.findAll()).thenReturn(mockdocumentsList1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/doc/").accept(
				MediaType.APPLICATION_JSON_UTF8);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{\"id\":1,\"docName\":\"mockTitle.docx\",\"file\":\"mockTextFileContent\"},{\"id\":2,\"docName\":\"mockTitle1.docx\",\"file\":\"mockTextFileContent1\"}]";
		assertEquals("The expected String doesn't matchwith WS findAll return", expected, (result.getResponse().getContentAsString()));
	}
	
	@Test
	public void deleteDoc() throws Exception {
		
		Mockito.when(
				documentService.deleteById(1L)).thenReturn(mockResp);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
				"/doc/delete/1").accept(
				MediaType.APPLICATION_JSON_UTF8);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"status\":200,\"message\":\"success\",\"data\":null}";
		assertEquals("The expected String doesn't matchwith WS deleteById return", expected, (result.getResponse().getContentAsString()));
	}
	@Test
	public void saveDoc() throws Exception {
		MockMultipartFile file = new MockMultipartFile("doc", "filename.pdf", "application/pdf", "some text to be loaded".getBytes());
		Mockito.when(
				documentService.save(file)).thenReturn(mockResp);

		RequestBuilder requestBuilder =  MockMvcRequestBuilders
				.post("/doc/upload")
				.requestAttr("file", file)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.APPLICATION_JSON_UTF8);
//				.fileUpload("/doc/upload")
//				.file(file)
//				.accept(MediaType.APPLICATION_JSON_UTF8);
		
		ResultActions result = mockMvc.perform(requestBuilder).andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isCreated());
		
	}
	@Test
	public void search() throws Exception {
		
		Mockito.when(
				documentService.search("mockTextFileContent")).thenReturn(mockdocumentsList2);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/doc/search/mockTextFileContent/").accept(
				MediaType.APPLICATION_JSON_UTF8);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{\"id\":1,\"docName\":\"mockTitle.docx\",\"file\":\"mockTextFileContent\"}]";
		assertEquals("The expected String doesn't matchwith WS fulltextSearch return", expected, (result.getResponse().getContentAsString()));
	}
}
