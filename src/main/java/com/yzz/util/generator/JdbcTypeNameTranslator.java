package com.yzz.util.generator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JdbcTypeNameTranslator {

	public static Map<String, String> typeToName = new HashMap<String, String>();

	static {
		typeToName.put("ARRAY", Object.class.getName());

		typeToName.put("BIGINT", Long.class.getName());

		typeToName.put("BINARY", "byte[]");

		typeToName.put("BIT", Boolean.class.getName());

		typeToName.put("BLOB", "byte[]");

		typeToName.put("BOOLEAN", Boolean.class.getName());

		typeToName.put("CHAR", String.class.getName());

		typeToName.put("CLOB", String.class.getName());

		typeToName.put("DATALINK", Object.class.getName());

		typeToName.put("DATE", Date.class.getName());

		typeToName.put("DISTINCT", Object.class.getName());

		typeToName.put("DOUBLE", Double.class.getName());

		typeToName.put("FLOAT", Double.class.getName());

		typeToName.put("INTEGER", Integer.class.getName());
		
		typeToName.put("INT", Integer.class.getName());

		typeToName.put("JAVA_OBJECT", Object.class.getName());

		typeToName.put("LONGNVARCHAR", String.class.getName());

		typeToName.put("LONGVARBINARY", "byte[]");

		typeToName.put("LONGVARCHAR", String.class.getName());

		typeToName.put("NCHAR", String.class.getName());

		typeToName.put("NCLOB", String.class.getName());

		typeToName.put("NVARCHAR", String.class.getName());

		typeToName.put("NULL", Object.class.getName());

		typeToName.put("OTHER", Object.class.getName());

		typeToName.put("REAL", Float.class.getName());

		typeToName.put("REF", Object.class.getName());

		typeToName.put("SMALLINT", Short.class.getName());

		typeToName.put("STRUCT", Object.class.getName());

		typeToName.put("TIME", Date.class.getName());

		typeToName.put("TIMESTAMP", Date.class.getName());
		
		typeToName.put("DATETIME", Date.class.getName());

		typeToName.put("TINYINT", Byte.class.getName());

		typeToName.put("VARBINARY", "byte[]");

		typeToName.put("VARCHAR", String.class.getName());
		
		typeToName.put("TEXT", String.class.getName());
		
		typeToName.put("MEDIUMTEXT", String.class.getName());
		
		typeToName.put("NUMERIC", BigDecimal.class.getName());
	}

	public static String getSimpleName(String sqlType) {
		String fullName = typeToName.get(sqlType);
		
		if (fullName != null && fullName.indexOf(".") > 0) {
			fullName = fullName.substring(fullName.lastIndexOf(".") + 1);
		}else{
			fullName = sqlType;
		}

		return fullName;
	}

}
