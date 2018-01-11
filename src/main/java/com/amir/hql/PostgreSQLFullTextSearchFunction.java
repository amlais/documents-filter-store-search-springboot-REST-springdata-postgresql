package com.amir.hql;

import java.util.List;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;

public class PostgreSQLFullTextSearchFunction implements SQLFunction {
	/*public BooleanType getReturnType(Type columnType, Mapping mapping)
			throws QueryException {
		return new BooleanType();
	}*/
 
	@Override
	public boolean hasArguments() {
		return true;
	}
 
	@Override
	public boolean hasParenthesesIfNoArguments() {
		return false;
	}
	
	@Override
	public org.hibernate.type.Type getReturnType(org.hibernate.type.Type arg0, Mapping arg1) throws QueryException {
		return new BooleanType();
	}

	@Override
	public String render(org.hibernate.type.Type arg0, @SuppressWarnings("rawtypes") List args, SessionFactoryImplementor factory)
			throws QueryException {
		if (args!= null && args.size() < 2) {
			throw new IllegalArgumentException(
					"The function must be passed 2 arguments");
		}
 
		String fragment = null;
		String ftsConfig = null;
		//field is a tsvector of text content
		String field = null;
		String value = null;
		if(args.size() == 3) {
			ftsConfig = (String) args.get(0);
			field = (String) args.get(1);
			value = (String) args.get(2);
			System.out.println("This is value: "+value);
			fragment = "to_tsvector(" + ftsConfig + ", " + field + ") @@ plainto_tsquery("+ftsConfig+", "+value+")";
		} else {
			field = (String) args.get(0);
			value = (String) args.get(1);
			fragment = "to_tsvector("+ field + ") @@ plainto_tsquery("+value+")";
		}
		return fragment;
	}
}