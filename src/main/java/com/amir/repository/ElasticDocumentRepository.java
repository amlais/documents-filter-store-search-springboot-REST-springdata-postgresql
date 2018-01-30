package com.amir.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.amir.domain.ElasticDocument;

import java.util.List;

public interface ElasticDocumentRepository extends ElasticsearchRepository<ElasticDocument, String> {

    //Page<ElasticDocument> findByFile(String searchQuery, Pageable pageable);

    List<ElasticDocument> findByFile(String searchQuery);

}