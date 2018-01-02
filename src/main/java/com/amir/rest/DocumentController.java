package com.amir.rest;

import java.io.IOException;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody ResponseMetadata handleFileUpload(@RequestParam(value="file") MultipartFile file) throws IOException, SAXException, TikaException {
        return documentService.save(file);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<String> getDocument(@PathVariable Long id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<String>(documentService.getDocumentFile(id), httpHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Document> getDocument() {
        return documentService.findAll();
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseMetadata deleteDocument(@PathVariable Long id){
    	 documentService.deleteById(id);
         ResponseMetadata metadata = new ResponseMetadata();
         metadata.setMessage("success");
         metadata.setStatus(200);
         return metadata;
    }
    @RequestMapping(value = "/search/{searchQuery}/", method = RequestMethod.GET)
    public @ResponseBody List<Document> fulltextSearch(@PathVariable String searchQuery){
    	return documentService.fulltextSearch(searchQuery);
    }
}
