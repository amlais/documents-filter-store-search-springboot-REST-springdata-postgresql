package com.amir.hql;

import org.hibernate.dialect.PostgreSQL9Dialect;

public class CustomPostgresDialect extends PostgreSQL9Dialect {
	 
	 public CustomPostgresDialect() {
		 super();
		 registerFunction("fts", new PostgreSQLFullTextSearchFunction());
	 }
}
