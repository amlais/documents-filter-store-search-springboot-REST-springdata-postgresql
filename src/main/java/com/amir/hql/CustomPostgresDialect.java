package com.amir.hql;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL9Dialect;

public class CustomPostgresDialect extends PostgreSQL9Dialect {
	 
	 public CustomPostgresDialect() {
		 super();
		 registerFunction("fts", new PostgreSQLFullTextSearchFunction());
	 }
}
