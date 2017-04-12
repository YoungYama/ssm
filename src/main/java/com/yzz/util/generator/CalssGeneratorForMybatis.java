package com.yzz.util.generator;

import java.util.List;
import java.util.Map;

import com.yzz.util.TimeUtil;

/**
 * 
 * @description:自动生成复用性高的通用crud操作实体类、DAO接口、DAO实现类，适用于mybatis框架与MySQL数据库
 *
 * @author 杨志钊
 * @date 2017年4月3日 下午6:40:07
 */
@SuppressWarnings("unchecked")
public class CalssGeneratorForMybatis {

	public static void main(String[] args) throws Exception {
		String classGeneratorConfigXML = projectPath + "\\src\\main\\resources\\generator\\yzz-class-generator.xml";

		CalssGeneratorConfig.setClassGeneratorConfigXML(classGeneratorConfigXML);
		Map<String, Object> map = CalssGeneratorConfig.getTableAndClassDatas();

		CalssGeneratorForMybatis.setTableAndClassDatas(map);
		
//		CalssGeneratorForMybatis.generateEntityClass();
//		
//		CalssGeneratorForMybatis.generateDaoClass();
		
		CalssGeneratorForMybatis.generateMapperXml();
	}

	private static String projectPath = System.getProperty("user.dir");

	public static void setProjectPath(String projectPath) {
		CalssGeneratorForMybatis.projectPath = projectPath;
	}

	private static List<String> tableNames;
	private static List<List<String>> tableColumnNames;
	private static List<List<String>> tableColumnJdbcTypes;
	private static List<List<String>> tableColumnRemarks;
	private static List<String> classNames;
	private static List<List<String>> classPropertyTypes;
	private static List<List<String>> propertyTypeFullNames;
	private static List<List<String>> classPropertyNames;
	private static String entityTargetDir;
	private static String entityTargetPackage;
	private static String daoTargetDir;
	private static String daoTargetPackage;
	private static String daoImplTargetDir;
	private static String daoImplTargetPackage;
	private static boolean stringTrim;

	public static void setTableAndClassDatas(Map<String, Object> map) {
		tableNames = (List<String>) map.get("tableNames");
		tableColumnNames = (List<List<String>>) map.get("tableColumnNames");
		tableColumnJdbcTypes = (List<List<String>>) map.get("tableColumnJdbcTypes");
		tableColumnRemarks = (List<List<String>>) map.get("tableColumnRemarks");
		classNames = (List<String>) map.get("classNames");
		classPropertyTypes = (List<List<String>>) map.get("classPropertyTypes");
		propertyTypeFullNames = (List<List<String>>) map.get("propertyTypeFullNames");
		classPropertyNames = (List<List<String>>) map.get("classPropertyNames");
		entityTargetDir = projectPath + map.get("entityTargetDir");
		entityTargetPackage = map.get("entityTargetPackage").toString();
		daoTargetDir = projectPath + map.get("daoTargetDir");
		daoTargetPackage = map.get("daoTargetPackage").toString();
		daoImplTargetDir = projectPath + map.get("daoImplTargetDir");
		daoImplTargetPackage = map.get("daoImplTargetPackage").toString();
		stringTrim = (boolean) map.get("stringTrim");
	}

	public static String geTauthorInfo(String description, String author) {
		String authorInfo = "/** \n"+
				 "* \n"+
				 "* @description: " + description + " \n"+
				 "* \n"+
				 "* @author " + author + " \n"+
				 "* @date " + TimeUtil.getCurrentTimeString() + " \n"+
				 "*/";
		return authorInfo;
	}

	public static void generateEntityClass() throws Exception {

		for (int i = 0; i < tableNames.size(); i++) {
			String description = tableNames.get(i) + "表的实体类" + classNames.get(i);
			String author = "杨志钊";

			String classStr = "package " + entityTargetPackage
					+ "; \n\n"
					+ geTauthorInfo(description, author)
					+ " \n\npublic class " + classNames.get(i) + " {\n\n";
			String propertyStr = "";
			String methodStr = "";
			for (int j = 0; j < classPropertyNames.get(i).size(); j++) {
				String classPropertyType = classPropertyTypes.get(i).get(j);
				String classPropertyName = classPropertyNames.get(i).get(j);
				String classPropertyRemark = tableColumnRemarks.get(i).get(j);
				String classPropertyNameUpper = classPropertyName.substring(0, 1).toUpperCase()
						+ classPropertyName.substring(1, classPropertyName.length());
				String tempStr = "";
				tempStr = "	private " + classPropertyType + " " + classPropertyName + ";//" + classPropertyRemark
						+ "\n\n";
				propertyStr += tempStr;
				tempStr = "	public " + classPropertyType + " get" + classPropertyNameUpper + "() { \n		return "
						+ classPropertyName + "; \n	} \n\n";
				methodStr += tempStr;

				if (classPropertyType.equals("String") & stringTrim) {
					tempStr = "	public void set" + classPropertyNameUpper + "(" + classPropertyType + " "
							+ classPropertyName + ") { \n " + "		this." + classPropertyName + " = "
							+ classPropertyName + " == null ? null : " + classPropertyName + ".trim(); \n	} \n\n";
					methodStr += tempStr;
				} else {
					tempStr = "	public void set" + classPropertyNameUpper + "(" + classPropertyType + " "
							+ classPropertyName + ") { \n " + "		this." + classPropertyName + " = "
							+ classPropertyName + "; \n	} \n";
					methodStr += tempStr;
				}
			}

			classStr += propertyStr;
			classStr += methodStr + " \n } \n";
			
			CalssGeneratorConfig.outputClassFile(entityTargetDir, classNames.get(i) + ".java", classStr);
			System.out.println("实体类" + classNames.get(i) + ".java自动生成完成...");
		}

	}

	public static void generateDaoClass() throws Exception {
		for (int i = 0; i < classNames.size(); i++) {
			String description = "实体类" + classNames.get(i) + "的DAO接口";
			String author = "杨志钊";
			String info = geTauthorInfo(description, author);
			String className = classNames.get(i);
			String entityClassName =className;
			String entityIdName = entityClassName.substring(0, 1).toUpperCase()
					+ entityClassName.substring(1, entityClassName.length()) + "Id";
			className += "Dao";
			
			String daoTemplate = "package " + daoTargetPackage + ";\n"+
					"\n"+
					"import java.util.List;\n"+
					"\n"+
					"import org.apache.ibatis.annotations.Param;\n"+
					"import org.springframework.stereotype.Repository;\n"+
					"\n"+
					"import com.yzz.dto.Page;\n"+
					"import com.yzz.entity." + entityClassName + ";\n"+
					"\n" + info + " \n"+
					"@Repository\n"+
					"public interface " + className + " {\n"+
					"\n"+
					"	int deleteByPrimaryKey(String " + entityIdName + ");\n"+
					"\n"+
					"	int insert(" + entityClassName + " entity);\n"+
					"\n"+
					"	int insertSelective(" + entityClassName + " entity);\n"+
					"\n"+
					"	" + entityClassName + " selectByPrimaryKey(String " + entityIdName + ");\n"+
					"\n"+
					"	List<" + entityClassName + "> selectByEntityAndPage(@Param(\"entity\") " + entityClassName + " entity, @Param(\"page\") Page page);\n"+
					"\n"+
					"	int countByEntity(@Param(\"entity\") " + entityClassName + " entity);\n"+
					"\n"+
					"	int updateByPrimaryKeySelective(" + entityClassName + " entity);\n"+
					"\n"+
					"	int updateByPrimaryKey(" + entityClassName + " entity);\n"+
					"\n"+
					"}\n";
			
			CalssGeneratorConfig.outputClassFile(daoTargetDir, className + ".java", daoTemplate);
			System.out.println("DAO接口" + className + ".java自动生成完成...");
		}
		
	}

	public static void generateMapperXml() throws Exception {
		for (int i = 0; i < classNames.size(); i++) {
			String className = classNames.get(i);
			String entityClassName =className;
			String entityIdName = entityClassName.substring(0, 1).toUpperCase()
					+ entityClassName.substring(1, entityClassName.length()) + "Id";
			String daoClassName = className + "Dao";
			className += "Mapper";
			String tableName = tableNames.get(i);
			
			String mapperXmlTemplate = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n";
			
			mapperXmlTemplate += "<mapper namespace=\"" + daoTargetPackage + "." + daoClassName + "\">\n<resultMap id=\"BaseResultMap\" type=\"" + entityTargetPackage + "." + entityClassName + "\">\n";
			String baseColumnList = "";
			String classIdTypeFullName = propertyTypeFullNames.get(i).get(0);
			String tableIdName = tableColumnNames.get(i).get(0);
			String tableIdType = tableColumnJdbcTypes.get(i).get(0);
			
			String selectWheres = "";
			String insertAll = "";
			String insertSelectiveBefore = "";
			String insertSelectiveAfter = "";
			for (int j = 0; j < classPropertyNames.get(i).size(); j++) {
				String tableColumnName = tableColumnNames.get(i).get(j);
				String classPropertyName = classPropertyNames.get(i).get(j);
				String tableColumnJdbcType = tableColumnJdbcTypes.get(i).get(j);
				
				if (j != 0) {
					mapperXmlTemplate += "<result column=\"" + tableColumnName + "\" property=\"" + classPropertyName + "\" jdbcType=\"" + tableColumnJdbcType + "\" />\n";
				} else {
					mapperXmlTemplate += "<id column=\"" + tableColumnName + "\" property=\"" + classPropertyName + "\" jdbcType=\"" + tableColumnJdbcType + "\" />\n";
				}
				
				insertAll += "#{" + classPropertyName + ",jdbcType=" + tableColumnJdbcType + "},\n";
				insertSelectiveBefore += "<if test=\"" + classPropertyName + " != null\">" + tableColumnName + ",\n</if>\n";
				insertSelectiveAfter += "<if test=\"" + classPropertyName + " != null\">\n " + tableColumnName + " = #{" + classPropertyName + ",jdbcType=" + tableColumnJdbcType + "}\n</if>\n";
				
				selectWheres += "<if test=\"entity." + classPropertyName + " != null\">\nand " + tableColumnName + " = #{entity." + classPropertyName + ",jdbcType=" + tableColumnJdbcType + "}\n</if>\n";
				
				baseColumnList += tableColumnName + ",";
			}
			baseColumnList = baseColumnList.substring(0, baseColumnList.lastIndexOf(","));
			insertAll = insertAll.substring(0, insertAll.lastIndexOf(","));
			
			mapperXmlTemplate += "</resultMap>\n<sql id=\"Base_Column_List\">\n" + baseColumnList + "\n</sql>\n";
			
			mapperXmlTemplate += "<select id=\"selectByPrimaryKey\" resultMap=\"BaseResultMap\" parameterType=\"" + classIdTypeFullName + "\">\nselect\n<include refid=\"Base_Column_List\" />\nfrom " + tableName + " \nwhere " + tableIdName + " = #{" + entityIdName + ",jdbcType=" + tableIdType + "}\n</select>\n";
			
			mapperXmlTemplate += "<select id=\"selectByEntityAndPage\" resultMap=\"BaseResultMap\" >\nselect\n<include refid=\"Base_Column_List\" />\nfrom " + tableName + " \n<where>\n " + selectWheres + "</where>\n";
			mapperXmlTemplate += "<if test=\"page.orderField != null\">\norder by #{page.orderField,jdbcType=VARCHAR} #{page.sort,jdbcType=VARCHAR}\n</if>\n<if test=\"page.start != null\">\nlimit #{page.start,jdbcType=INTEGER}, #{page.pageSize,jdbcType=INTEGER}\n</if>\n</select>\n";
				
			mapperXmlTemplate += "<select id=\"countByEntity\" resultType=\"java.lang.Integer\" >\nselect count(*) from " + tableName + " \n<where>\n " + selectWheres + "</where>\n</select>\n";
			
			mapperXmlTemplate += "<delete id=\"deleteByPrimaryKey\" parameterType=\"" + classIdTypeFullName + "\">\ndelete\nfrom " + tableName + " \nwhere " + tableIdName + " = #{" + entityIdName + ",jdbcType=" + tableIdType + "}\n</delete>\n";
			
			mapperXmlTemplate += "<insert id=\"insert\" parameterType=\""  + entityTargetPackage + "." + entityClassName + "\">\ninsert into " + tableName + " \n( " + baseColumnList + ")\nvalues (" + insertAll + ") \n</insert>\n";
			
			mapperXmlTemplate += "<insert id=\"insertSelective\" parameterType=\""  + entityTargetPackage + "." + entityClassName + "\">\ninsert into " + tableName + "\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n" + insertSelectiveBefore + "\n</trim>" + "\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n" + insertSelectiveAfter + "\n</trim>\n";
			
			System.out.println(mapperXmlTemplate);
		}
	}

}
