package com.amir.rest;

import java.io.IOException;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.amir.domain.Document;
import com.amir.domain.ElasticDocument;
import com.amir.service.DocumentService;
import com.amir.service.ElasticDocumentService;
import com.amir.service.ResponseMetadata;

@RestController
@RequestMapping(value = "/doc")
public class DocumentController {
    
    @Autowired
    DocumentService documentService;
    @Autowired
    ElasticDocumentService elasticDocumentService;
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseMetadata handleFileUpload(@RequestAttribute(value="file") MultipartFile file) throws IOException, SAXException, TikaException {
    	elasticDocumentService.save(file);
        return documentService.save(file);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getDocument(@PathVariable Long id) {
        return documentService.getDocumentFile(id);
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody List<Document> getDocument() {
        return documentService.findAll();
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseMetadata deleteDocument(@PathVariable Long id){
    	 return documentService.deleteById(id);
    }
    
    @RequestMapping(value = "/search/{searchQuery}/", method = RequestMethod.GET)
    public @ResponseBody List<Document> search(@PathVariable String searchQuery){
    	return documentService.search(searchQuery);
    }
    
    @RequestMapping(value = "/fulltextsearch/{searchQuery}/", method = RequestMethod.GET)
    public @ResponseBody List<ElasticDocument> fulltextsearch(@PathVariable String searchQuery){
    	return elasticDocumentService.fulltextSearch(searchQuery);
    }
}
