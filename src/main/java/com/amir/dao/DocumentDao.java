package com.amir.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.amir.domain.Document;

@Repository
@Transactional
public interface DocumentDao extends CrudRepository<Document, Long>{

	public List<Document>findByFileContainsAllIgnoreCase(String filePart);
	public void delete(Long id);

}
