package com.amir.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.amir.domain.Document;

@Repository
@Transactional
public interface DocumentDao extends CrudRepository<Document, Long>{

	@Query("select d from Document d where fts('pg_catalog.french', d.file, :searchQuery) = true")
	public List<Document>findBySearchQuery(@Param("searchQuery")String searchQuery);
	public void delete(Long id);

}
