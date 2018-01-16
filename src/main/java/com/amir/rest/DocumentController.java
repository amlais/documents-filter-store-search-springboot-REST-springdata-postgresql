package com.amir.rest;

import java.io.IOException;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.amir.domain.Document;
import com.amir.service.DocumentService;
import com.amir.service.ResponseMetadata;

@CrossOrigin(origins="*")
@RestController
@RequestMapping(value = "/doc")
public class DocumentController {
    
    @Autowired
    DocumentService documentService;
    
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseMetadata handleFileUpload(@RequestAttribute(value="file") MultipartFile file) throws IOException, SAXException, TikaException {
        return documentService.save(file);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getDocument(@PathVariable Long id) {
        return documentService.getDocumentFile(id);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Document> getDocument() {
        return documentService.findAll();
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseMetadata deleteDocument(@PathVariable Long id){
    	 return documentService.deleteById(id);
    }
    
    @RequestMapping(value = "/search/{searchQuery}/", method = RequestMethod.GET)
    public @ResponseBody List<Document> fulltextSearch(@PathVariable String searchQuery){
    	return documentService.fulltextSearch(searchQuery);
    }
}
