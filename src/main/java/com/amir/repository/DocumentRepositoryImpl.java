package com.amir.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.apache.lucene.util.QueryBuilder;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.amir.domain.Document;

@Transactional(readOnly = true)
public class DocumentRepositoryImpl implements DocumentRepositoryCustom{
	
	@PersistenceContext
    private EntityManager em;
	
	 /** Hibernate Full Text Entity Manager. */
    private FullTextEntityManager ftem;
    
    @SuppressWarnings("unchecked")
	@Transactional
    @Override
    public List<Document> fulltextSearch(String searchString) {
		
		if (ftem == null) {
            ftem = Search.getFullTextEntityManager(em);
        }
		// Create a Query Builder
        QueryBuilder qb = (QueryBuilder) ftem.getSearchFactory().buildQueryBuilder().forEntity(Document.class).get();
        // Create a Lucene Full Text Query
        org.apache.lucene.search.Query luceneQuery = ((org.hibernate.search.query.dsl.QueryBuilder) qb).bool()
                .must((Query) ((org.hibernate.search.query.dsl.QueryBuilder) qb).keyword()
                		.onFields("file").matching(searchString)).createQuery();
        Query fullTextQuery = (Query) ftem.createFullTextQuery(luceneQuery, Document.class);
        // Run Query and print out results to console
        return (List<Document>) ( (javax.persistence.Query) fullTextQuery).getResultList();
	}

}
