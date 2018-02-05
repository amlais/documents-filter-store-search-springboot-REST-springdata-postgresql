package com.amir.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.amir.domain.ElasticDocument;

import java.util.List;

@Repository
public interface ElasticDocumentRepository extends ElasticsearchRepository<ElasticDocument, String> {

    //will be replaced by search(org.elasticsearch.index.query.QueryBuilder q) elastic java query API
    List<ElasticDocument> findByFileContainingAllIgnoreCase(String searchQuery);

}