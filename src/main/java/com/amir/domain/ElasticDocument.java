package com.amir.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "amirindex", type = "docs")
public class ElasticDocument {
	@Id
    private Long id;
    private String docName;
    private String file;
    public ElasticDocument() {
	}
    public ElasticDocument(Long id, String name, String file) {
    	super();
		this.id = id;
		this.docName = name;
		this.file = file;
	}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }
}
