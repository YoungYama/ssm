package com.yzz.util.generator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.yzz.util.HumpUtil;

public class CalssGeneratorConfig {

	public static String classGeneratorConfigXML;

	public static void setClassGeneratorConfigXML(String classGeneratorConfigXML) {
		CalssGeneratorConfig.classGeneratorConfigXML = classGeneratorConfigXML;
	}

	public static void outputClassFile(String distDir, String className, String classStr) throws Exception {
		File file = new File(distDir + "\\" + className);

		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(classStr.getBytes("UTF-8"));
		fos.close();
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getTableAndClassDatas() throws Exception {
		Map<String, Object> map = parseClassGeneratorXML();
		Connection conn = (Connection) map.get("conn");
		if (conn == null) {
			conn = getConnection(map);
		}

		List<String> tableNames = (List<String>) map.get("tableNames");

		List<List<String>> tableColumnNames = new ArrayList<List<String>>();
		List<List<String>> tableColumnJdbcTypes = new ArrayList<List<String>>();
		List<List<String>> tableColumnRemarks = new ArrayList<List<String>>();
		List<String> classNames = new ArrayList<String>();
		List<List<String>> classPropertyTypes = new ArrayList<List<String>>();
		List<List<String>> classPropertyFullNames = new ArrayList<List<String>>();
		List<List<String>> classPropertyNames = new ArrayList<List<String>>();
		for (String tableName : tableNames) {
			String className = HumpUtil.lineToHump(tableName);
			className = className.substring(0, 1).toUpperCase() + className.substring(1, className.length());
			classNames.add(className);

			List<String> columnNames = new ArrayList<String>();
			List<String> columnJdbcTypes = new ArrayList<String>();
			List<String> columnRemarks = new ArrayList<String>();
			List<String> propertyTypes = new ArrayList<String>();
			List<String> propertyTypeFullNames = new ArrayList<String>();
			List<String> propertyNames = new ArrayList<String>();

			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet resultSet = dbmd.getTables(null, "%", "%", new String[] { "TABLE" });
			while (resultSet.next()) {
				String tempTableName = resultSet.getString("TABLE_NAME");
				if (tableName.equals(tempTableName)) {
					// 其他数据库不需要这个方法的，直接传null，这个是oracle和db2这么用
					// ResultSet rs = dbmd.getColumns(null,
					// getSchema(conn),tableName.toUpperCase(), "%");
					ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
					while (rs.next()) {
						// 获得指定列的列名
						String columnName = rs.getString("COLUMN_NAME");
						String remark = rs.getString("REMARKS");
						String columnJdbcType = rs.getString("TYPE_NAME");
						if (columnJdbcType.equals("INT")) {
							columnJdbcType = "INTEGER";
						}
						
						if (remark.equals(null) | remark.equals("")) {
							remark = columnName;
						}
						String propertyName = HumpUtil.lineToHump(columnName);
						columnNames.add(columnName);
						columnJdbcTypes.add(columnJdbcType);
						columnRemarks.add(remark);
						propertyTypes.add(JdbcTypeNameTranslator.getSimpleName(columnJdbcType));
						propertyTypeFullNames.add(JdbcTypeNameTranslator.typeToName.get(columnJdbcType));
						propertyNames.add(propertyName);
					}

					break;
				}
			}

			tableColumnNames.add(columnNames);
			tableColumnJdbcTypes.add(columnJdbcTypes);
			tableColumnRemarks.add(columnRemarks);
			classPropertyTypes.add(propertyTypes);
			classPropertyFullNames.add(propertyTypeFullNames);
			classPropertyNames.add(propertyNames);

		}

		map.put("tableColumnNames", tableColumnNames);
		map.put("tableColumnJdbcTypes", tableColumnJdbcTypes);
		map.put("tableColumnRemarks", tableColumnRemarks);
		map.put("classNames", classNames);
		map.put("classPropertyTypes", classPropertyTypes);
		map.put("propertyTypeFullNames", classPropertyFullNames);
		map.put("classPropertyNames", classPropertyNames);

		return map;
	}

	// 其他数据库不需要这个方法 oracle和db2需要
	public static String getSchema(Connection conn) throws Exception {
		String schema;
		schema = conn.getMetaData().getUserName();
		if ((schema == null) || (schema.length() == 0)) {
			throw new Exception("ORACLE数据库模式不允许为空");
		}
		return schema.toUpperCase().toString();

	}

	@SuppressWarnings("unused")
	public static Map<String, Object> parseClassGeneratorXML() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String xml = "";
		FileInputStream fis = new FileInputStream(new File(classGeneratorConfigXML));

		byte[] b = new byte[1024];

		int len;
		while ((len = fis.read(b)) != -1) {
			xml += new String(b, "UTF-8");
		}
		fis.close();

		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docbuilder = dbfactory.newDocumentBuilder();
		Document doc = docbuilder.parse(new ByteArrayInputStream(xml.trim().getBytes(Charset.defaultCharset())));

		Node jdbcConnection = doc.getElementsByTagName("jdbcConnection").item(0);
		NamedNodeMap map = jdbcConnection.getAttributes();
		String driverClass = map.getNamedItem("driverClass").getNodeValue();
		String url = map.getNamedItem("url").getNodeValue();
		String user = map.getNamedItem("user").getNodeValue();
		String password = map.getNamedItem("password").getNodeValue();

		Node javaEntityGenerator = doc.getElementsByTagName("javaEntityGenerator").item(0);
		NamedNodeMap javaEntityGeneratorAttrs = javaEntityGenerator.getAttributes();
		String entityTargetPackage = javaEntityGeneratorAttrs.getNamedItem("targetPackage").getNodeValue();
		String entityTargetShortDir = javaEntityGeneratorAttrs.getNamedItem("targetDir").getNodeValue();

		String entityTargetDir = "\\" + entityTargetShortDir + entityTargetPackage.replace(".", "\\");

		boolean stringTrim = Boolean.valueOf(javaEntityGeneratorAttrs.getNamedItem("stringTrim").getNodeValue());

		Node javaDaoGenerator = doc.getElementsByTagName("javaDaoGenerator").item(0);
		NamedNodeMap javaDaoGeneratorAttrs = javaDaoGenerator.getAttributes();
		String daoTargetPackage = javaDaoGeneratorAttrs.getNamedItem("targetPackage").getNodeValue();
		String daoTargetShortDir = javaDaoGeneratorAttrs.getNamedItem("targetDir").getNodeValue();

		String daoTargetDir = "\\" + daoTargetShortDir + daoTargetPackage.replace(".", "\\");
		
		Node javaDaoImplGenerator = doc.getElementsByTagName("javaDaoImplGenerator").item(0);
		NamedNodeMap javaDaoImplGeneratorAttrs = javaDaoImplGenerator.getAttributes();
		String daoImplTargetPackage = javaDaoImplGeneratorAttrs.getNamedItem("targetPackage").getNodeValue();
		String daoImplTargetShortDir = javaDaoImplGeneratorAttrs.getNamedItem("targetDir").getNodeValue();

		String daoImplTargetDir = "\\" + daoImplTargetShortDir + daoImplTargetPackage.replace(".", "\\");

		NodeList tables = doc.getElementsByTagName("table");
		List<String> tableNames = new ArrayList<String>();
		for (int i = 0; i < tables.getLength(); i++) {
			String tableName = tables.item(i).getAttributes().getNamedItem("tableName").getNodeValue();
			tableNames.add(tableName);
		}

		result.put("driverClass", driverClass);
		result.put("url", url);
		result.put("user", user);
		result.put("password", password);
		result.put("entityTargetDir", entityTargetDir);
		result.put("entityTargetPackage", entityTargetPackage);
		result.put("stringTrim", stringTrim);
		result.put("daoTargetDir", daoTargetDir);
		result.put("daoTargetPackage", daoTargetPackage);
		result.put("daoImplTargetDir", daoImplTargetDir);
		result.put("daoImplTargetPackage", daoImplTargetPackage);

		if (tables.getLength() <= 0) {
			Connection conn = getConnection(result);
			tableNames = getTableNames(conn);
			result.put("conn", conn);
		}

		result.put("tableNames", tableNames);

		return result;
	}

	public static Connection getConnection(Map<String, Object> map) throws Exception {
		Connection conn = null;
		Class.forName(map.get("driverClass").toString());
		String url = map.get("url").toString();
		String user = map.get("user").toString();
		String password = map.get("password").toString();
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	public static List<String> getTableNames(Connection conn) throws Exception {
		List<String> tableNames = new ArrayList<String>();
		DatabaseMetaData metaData = conn.getMetaData();
		ResultSet rs = metaData.getTables(conn.getCatalog(), "root", null, new String[] { "TABLE" });
		while (rs.next()) {
			tableNames.add(rs.getString("TABLE_NAME"));
		}
		return tableNames;
	}

}
