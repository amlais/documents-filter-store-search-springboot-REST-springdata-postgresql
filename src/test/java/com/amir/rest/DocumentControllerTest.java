package com.amir.rest;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.amir.domain.Document;
import com.amir.service.DocumentService;

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

	Document mockDocument = new Document(1L, "mockTitle.docx", "mockTextFileContent");
	@Test
	public void retrieveFileContent() throws Exception {

		Mockito.when(
				documentService.getDocumentFile(Mockito.anyLong())).thenReturn(mockDocument.getFile());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/doc/1").accept(
				MediaType.TEXT_PLAIN_VALUE);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		String expected = "mockTextFileContent";
		System.out.println(result.getResponse().getContentAsString());
		assertEquals("The expected String doesn't matchwith WS getDocumentFile return", expected, (result.getResponse().getContentAsString().replaceAll(System.getProperty("line.separator"),"").trim()));
	}

}
