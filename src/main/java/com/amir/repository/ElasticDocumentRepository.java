package com.amir.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.amir.domain.ElasticDocument;

import java.util.List;

@Repository
@Transactional(readOnly=true)
public interface ElasticDocumentRepository extends ElasticsearchRepository<ElasticDocument, String> {

    //will be replaced by search(org.elasticsearch.index.query.QueryBuilder q) elastic java query API
    List<ElasticDocument> findByFileContainingAllIgnoreCase(String searchQuery);
    ElasticDocument findOne(String id);
    void delete(ElasticDocument ed);
}