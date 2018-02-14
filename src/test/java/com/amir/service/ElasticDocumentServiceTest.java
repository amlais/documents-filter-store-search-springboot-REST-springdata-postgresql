package com.amir.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;

import com.amir.domain.ElasticDocument;
import com.amir.repository.ElasticDocumentRepository;
import com.amir.service.impl.ElasticDocumentServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ElasticDocumentServiceTest{
	
	@TestConfiguration
    static class ElasticDocumentServiceImplTestContextConfiguration {
  
        @Bean
        public ElasticDocumentService ElasticDocumentService() {
            return new ElasticDocumentServiceImpl();
        }
    }
	@MockBean
	private ElasticDocumentRepository elasticDocumentRepositoryMock;
	@Autowired
	private ElasticDocumentService elasticDocumentService;
	
	@Before
	public void setUp(){
		ElasticDocument doc = new ElasticDocument(1L, "mockTitle", "Elastic Document Abstract Informatique");
		List<ElasticDocument> docs = new ArrayList<>(
			    Arrays.asList(doc));
		Mockito.when(elasticDocumentRepositoryMock.save(doc))
	      .thenReturn(doc);
		Mockito.when(elasticDocumentRepositoryMock.findOne(doc.getId().toString()))
	      .thenReturn(doc);
		Mockito.when(elasticDocumentRepositoryMock.findAll())
	      .thenReturn(docs);
		Mockito.when( elasticDocumentRepositoryMock.findByFileContainingAllIgnoreCase("Informatique"))
				.thenReturn(docs);
		Mockito.doNothing().when( elasticDocumentRepositoryMock).delete(doc);
	}
	
    @Test
    public void testSave() throws IOException, SAXException, TikaException {

    	MockMultipartFile file = new MockMultipartFile("doc", "filename.pdf", "application/pdf", "Elastic Document Abstract Informatique".getBytes());
    	ResponseMetadata respSave = null;
    	respSave = elasticDocumentService.save(file);
		assertTrue(respSave.getStatus()==200 && respSave.getMessage()=="success");

    }

    @Test
    public void testFindOne() throws IOException, SAXException, TikaException {
    	
    	MockMultipartFile file = new MockMultipartFile("doc", "mockTitle.pdf", "application/pdf", "Elastic Document Abstract Informatique".getBytes());
    	ElasticDocument expectedDoc = new ElasticDocument(1L, "mockTitle", "Elastic Document Abstract Informatique");
    	elasticDocumentService.save(file);

        ElasticDocument testElasticDocument = elasticDocumentService.findOne(expectedDoc.getId().toString());

        assertNotNull(testElasticDocument.getId());
        assertTrue(testElasticDocument.getDocName().equals( expectedDoc.getDocName()));
        assertTrue(testElasticDocument.getFile().equals( expectedDoc.getFile()));

    }

    @Test
    public void testFindByKeyword() throws IOException, SAXException, TikaException {
    	
    	MockMultipartFile file = new MockMultipartFile("doc", "filename.pdf", "application/pdf", "Elastic Document Abstract Informatique".getBytes());
    	elasticDocumentService.save(file);

        List<ElasticDocument> byTitle = elasticDocumentService.fulltextSearch("Informatique");
        assertTrue(byTitle.size()==1);
    }
    
    @Test
    public void testDelete() throws IOException, SAXException, TikaException {

    	ElasticDocument elasticDocument = new ElasticDocument(10L, "mockTitle", "Elastic Document Abstract Informatique");
    	MockMultipartFile file = new MockMultipartFile("doc", "filename.pdf", "application/pdf", "Elastic Document Abstract Informatique".getBytes());
    	
    	elasticDocumentService.save(file);
        elasticDocumentService.delete(elasticDocument);
        ElasticDocument testElasticDocument = elasticDocumentService.findOne(elasticDocument.getId().toString());
        assertNull(testElasticDocument);
    }

}
