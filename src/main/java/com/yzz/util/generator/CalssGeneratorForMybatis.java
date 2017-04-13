package com.yzz.util.generator;

import java.util.List;
import java.util.Map;

import com.yzz.util.TimeUtil;

/**
 * 使用该插件时必须注意：表ID必须是表名加_id；如sys_user表的ID一定要是sys_user_id
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
		
		CalssGeneratorForMybatis.generateEntityClass();
		
		CalssGeneratorForMybatis.generateDaoClass();
		
		CalssGeneratorForMybatis.generateMapperXml();
		
		CalssGeneratorForMybatis.generateServiceClass();
		
		CalssGeneratorForMybatis.generateServiceImplClass();
		
		CalssGeneratorForMybatis.generateCtrlClass();
	}

	private static String projectPath = System.getProperty("user.dir");
	
	private static String AUTHOR = "杨志钊";

	public static void setProjectPath(String projectPath) {
		CalssGeneratorForMybatis.projectPath = projectPath;
	}

	public static void setAUTHOR(String AUTHOR) {
		CalssGeneratorForMybatis.AUTHOR = AUTHOR;
	}

	private static List<String> tableNames;
	private static List<List<String>> tableColumnNames;
	private static List<List<String>> tableColumnJdbcTypes;
	private static List<List<String>> tableColumnRemarks;
	private static List<String> classNames;
	private static List<List<String>> classPropertyTypes;
	private static List<List<String>> propertyTypeFullNames;
	private static List<List<String>> classPropertyNames;
	private static String dtoTargetPackage;
	private static String utilTargetPackage;
	private static String entityTargetDir;
	private static String entityTargetPackage;
	private static String daoTargetDir;
	private static String daoTargetPackage;
	private static String daoImplTargetDir;
//	private static String daoImplTargetPackage;
	private static String serviceTargetDir;
	private static String serviceTargetPackage;
	private static String serviceImplTargetDir;
	private static String serviceImplTargetPackage;
	private static String ctrlTargetDir;
	private static String ctrlTargetPackage;
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
		dtoTargetPackage = map.get("dtoTargetPackage").toString();
		utilTargetPackage = map.get("utilTargetPackage").toString();
		entityTargetDir = projectPath + map.get("entityTargetDir");
		entityTargetPackage = map.get("entityTargetPackage").toString();
		daoTargetDir = projectPath + map.get("daoTargetDir");
		daoTargetPackage = map.get("daoTargetPackage").toString();
		daoImplTargetDir = projectPath + map.get("daoImplTargetDir");
//		daoImplTargetPackage = map.get("daoImplTargetPackage").toString();
		serviceTargetDir = projectPath + map.get("serviceTargetDir").toString();
		serviceTargetPackage = map.get("serviceTargetPackage").toString();
		serviceImplTargetDir = projectPath + map.get("serviceImplTargetDir").toString();
		serviceImplTargetPackage = map.get("serviceImplTargetPackage").toString();
		ctrlTargetDir = projectPath + map.get("ctrlTargetDir").toString();
		ctrlTargetPackage = map.get("ctrlTargetPackage").toString();
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
			String author = AUTHOR;

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
							+ classPropertyName + "; \n	} \n\n";
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
			String author = AUTHOR;
			String info = geTauthorInfo(description, author);
			String className = classNames.get(i);
			String entityClassName =className;
			String entityIdName = entityClassName.substring(0, 1).toLowerCase()
					+ entityClassName.substring(1, entityClassName.length()) + "Id";
			className += "Dao";
			
			String classIdPropertyType = classPropertyTypes.get(i).get(0);
			
			String daoTemplate = "package " + daoTargetPackage + ";\n"+
					"\n"+
					"import java.util.List;\n"+
					"\n"+
					"import org.apache.ibatis.annotations.Param;\n"+
					"import org.springframework.stereotype.Repository;\n"+
					"\n"+
					"import " + dtoTargetPackage + ".Page;\n"+
					"import " + entityTargetPackage + "." + entityClassName + ";\n"+
					"\n" + info + " \n"+
					"@Repository\n"+
					"public interface " + className + " {\n"+
					"\n	//单个实体全部字段添加\n"+
					"	int insert(" + entityClassName + " entity);\n"+
					"\n	//根据实体ID单个实体删除\n"+
					"	int deleteByPrimaryKey(" + classIdPropertyType + " " + entityIdName + ");\n"+
					"\n	//根据实体ID数组批量删除实体\n"+
					"	int deleteBatch(List<" + classIdPropertyType + "> " + entityIdName + "s);\n"+
					"\n	//单个实体全部字段更新\n"+
					"	int updateByPrimaryKey(" + entityClassName + " entity);\n"+
					"\n	//单个实体选择性字段更新\n"+
					"	int updateByPrimaryKeySelective(" + entityClassName + " entity);\n"+
					"\n	//根据实体ID查询单个实体\n"+
					"	" + entityClassName + " selectByPrimaryKey(" + classIdPropertyType + " " + entityIdName + ");\n"+
					"\n	//根据选择性实体字段分页查询实体数组\n"+
					"	List<" + entityClassName + "> selectByEntityAndPage(@Param(\"entity\") " + entityClassName + " entity, @Param(\"page\") Page page);\n"+
					"\n	//根据选择性实体字段查询实体数量\n"+
					"	int countByEntity(@Param(\"entity\") " + entityClassName + " entity);\n"+
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
			String entityIdName = entityClassName.substring(0, 1).toLowerCase()
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
//			String insertSelectiveBefore = "";
//			String insertSelectiveAfter = "";
			
			String updateAll = "";
			String updateSelective = "";
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
//				insertSelectiveBefore += "<if test=\"" + classPropertyName + " != null\">" + tableColumnName + ",\n</if>\n";
//				insertSelectiveAfter += "<if test=\"" + classPropertyName + " != null\">\n #{" + classPropertyName + ",jdbcType=" + tableColumnJdbcType + "},\n</if>\n";
				
				if (j != 0) {
					updateAll += tableColumnName + " = #{" + classPropertyName + ",jdbcType=" + tableColumnJdbcType + "},\n";
					updateSelective += "<if test=\"" + classPropertyName + " != null\">\n " + tableColumnName + " = #{" + classPropertyName + ",jdbcType=" + tableColumnJdbcType + "},\n</if>\n";
				}
				
				selectWheres += "<if test=\"entity." + classPropertyName + " != null\">\nand " + tableColumnName + " = #{entity." + classPropertyName + ",jdbcType=" + tableColumnJdbcType + "}\n</if>\n";
				
				baseColumnList += tableColumnName + ",";
			}
			baseColumnList = baseColumnList.substring(0, baseColumnList.lastIndexOf(","));
			insertAll = insertAll.substring(0, insertAll.lastIndexOf(","));
			updateAll = updateAll.substring(0, updateAll.lastIndexOf(","));
			
			mapperXmlTemplate += "</resultMap>\n<sql id=\"Base_Column_List\">\n" + baseColumnList + "\n</sql>\n";
			
			mapperXmlTemplate += "<insert id=\"insert\" parameterType=\""  + entityTargetPackage + "." + entityClassName + "\">\ninsert into " + tableName + " \n( " + baseColumnList + ")\nvalues (" + insertAll + ") \n</insert>\n\n";
			
			mapperXmlTemplate += "<delete id=\"deleteBatch\" parameterType=\"java.util.List\">\ndelete\nfrom " + tableName + " \nwhere " + tableIdName + " in \n<foreach collection=\"list\" item=\"item\" open=\"(\" separator=\",\" close=\")\"> \n #{item} \n </foreach>\n</delete>\n\n";
			
			mapperXmlTemplate += "<delete id=\"deleteByPrimaryKey\" parameterType=\"" + classIdTypeFullName + "\">\ndelete\nfrom " + tableName + " \nwhere " + tableIdName + " = #{" + entityIdName + ",jdbcType=" + tableIdType + "}\n</delete>\n\n";
			
			mapperXmlTemplate += "<update id=\"updateByPrimaryKey\" parameterType=\""  + entityTargetPackage + "." + entityClassName + "\">\nupdate " + tableName + " set \n" + updateAll + "\n" + " \nwhere " + tableIdName + " = #{" + entityIdName + ",jdbcType=" + tableIdType + "}\n</update>\n\n";
			
			mapperXmlTemplate += "<update id=\"updateByPrimaryKeySelective\" parameterType=\""  + entityTargetPackage + "." + entityClassName + "\">\nupdate " + tableName + " <set> \n" + updateSelective + "</set>\n" + " \nwhere " + tableIdName + " = #{" + entityIdName + ",jdbcType=" + tableIdType + "}\n</update>\n\n";
			
			mapperXmlTemplate += "<select id=\"selectByPrimaryKey\" resultMap=\"BaseResultMap\" parameterType=\"" + classIdTypeFullName + "\">\nselect\n<include refid=\"Base_Column_List\" />\nfrom " + tableName + " \nwhere " + tableIdName + " = #{" + entityIdName + ",jdbcType=" + tableIdType + "}\n</select>\n\n";
			
			mapperXmlTemplate += "<select id=\"selectByEntityAndPage\" resultMap=\"BaseResultMap\" >\nselect\n<include refid=\"Base_Column_List\" />\nfrom " + tableName + " \n<if test=\"entity != null\">\n<where>\n " + selectWheres + "</where>\n</if>\n";
			mapperXmlTemplate += "<if test=\"page != null\">\n<if test=\"page.orderField != null\">\norder by #{page.orderField,jdbcType=VARCHAR} #{page.sort,jdbcType=VARCHAR}\n</if>\n<if test=\"page.start != null\">\nlimit #{page.start,jdbcType=INTEGER}, #{page.pageSize,jdbcType=INTEGER}\n</if>\n</if>\n</select>\n\n";
				
			mapperXmlTemplate += "<select id=\"countByEntity\" resultType=\"java.lang.Integer\" >\nselect count(*) from " + tableName + " \n<if test=\"entity != null\">\n<where>\n " + selectWheres + "</where>\n</if>\n</select>\n\n";
			
			mapperXmlTemplate += "</mapper>";
			
			CalssGeneratorConfig.outputClassFile(daoImplTargetDir, className + ".xml", mapperXmlTemplate);
			System.out.println("映射文件" + className + ".xml自动生成完成...");
		}
	}
	
	public static void generateServiceClass() throws Exception {
		for (int i = 0; i < classNames.size(); i++) {
			String description = "实体类" + classNames.get(i) + "的service接口";
			String author = AUTHOR;
			String info = geTauthorInfo(description, author);
			String className = classNames.get(i);
			String entityClassName =className;
			String entityIdName = entityClassName.substring(0, 1).toLowerCase()
					+ entityClassName.substring(1, entityClassName.length()) + "Id";
			className += "Service";
			
			String classIdPropertyType = classPropertyTypes.get(i).get(0);
			
			String serviceTemplate = "package " + serviceTargetPackage + ";\n"+
					"\n"+
					"import java.util.List;\n"+
					"\n"+
					"import " + dtoTargetPackage + ".Page;\n"+
					"import " + dtoTargetPackage + ".ResultData;\n"+
					"import " + entityTargetPackage + "." + entityClassName + ";\n"+
					"\n" + info + " \n"+
					"public interface " + className + " {\n"+
					"\n	//单个实体全部字段添加\n"+
					"	ResultData<Void> insertOne(" + entityClassName + " entity);\n"+
					"\n	//根据实体ID单个实体删除\n"+
					"	ResultData<Void> deleteOne(" + classIdPropertyType + " " + entityIdName + ");\n"+
					"\n	//根据实体ID数组批量删除实体\n"+
					"	ResultData<Void> deleteBatch(" + classIdPropertyType + "[] " + entityIdName + "s);\n"+
					"\n	//单个实体全部字段更新\n"+
					"	ResultData<Void> updateOne(" + entityClassName + " entity);\n"+
					"\n	//单个实体选择性字段更新\n"+
					"	ResultData<Void> updateOneSelective(" + entityClassName + " entity);\n"+
					"\n	//根据实体ID查询单个实体\n"+
					"	ResultData<" + entityClassName + "> selectOne(" + classIdPropertyType + " " + entityIdName + ");\n"+
					"\n	//根据选择性实体字段分页查询实体数组\n"+
					"	ResultData<List<" + entityClassName + ">> selectList(" + entityClassName + " entity, Page page);\n"+
					"\n	//查询全部实体\n"+
					"	ResultData<List<" + entityClassName + ">> selectAll();\n"+
					"\n"+
					"}\n";
			
			CalssGeneratorConfig.outputClassFile(serviceTargetDir, className + ".java", serviceTemplate);
			System.out.println("service接口" + className + ".java自动生成完成...");
		}
		
	}
	
	public static void generateServiceImplClass() throws Exception {
		for (int i = 0; i < classNames.size(); i++) {
			String className = classNames.get(i);
			String description = className + "Service接口的实现类" + className + "ServiceImpl";
			String author = AUTHOR;
			String info = geTauthorInfo(description, author);
			String entityClassName =className;
			String entityClassVarName =entityClassName.substring(0,1).toLowerCase() + entityClassName.substring(1,entityClassName.length());
			String daoClassName =className + "Dao";
			String daoClassVarName =daoClassName.substring(0,1).toLowerCase() + daoClassName.substring(1,daoClassName.length());
			String serviceClassName =className + "Service";
			String entityIdName = entityClassName.substring(0, 1).toLowerCase()
					+ entityClassName.substring(1, entityClassName.length()) + "Id";
			className += "ServiceImpl";
			
			String classIdPropertyType = classPropertyTypes.get(i).get(0);
			
			String serviceImplTemplate = "package " + serviceImplTargetPackage + ";\n"+
					"\n"+
					"import java.util.ArrayList;\n"+
					"import java.util.Arrays;\n"+
					"import java.util.List;\n"+
					"\n"+
					"import javax.annotation.Resource;\n"+
					"\n"+
					"import org.springframework.stereotype.Service;\n"+
					"\n"+
					"import " + daoTargetPackage + "." + daoClassName + ";\n"+
					"import " + dtoTargetPackage + ".Page;\n"+
					"import " + dtoTargetPackage + ".ResultData;\n"+
					"import " + entityTargetPackage + "." + entityClassName + ";\n"+
					"import " + serviceTargetPackage + "." + serviceClassName + ";\n"+
					"import " + utilTargetPackage + ".DBOperatedState;\n"+
					"\n" + info + " \n"+
					"@Service\n"+
					"public class " + className + " implements " + serviceClassName + " {\n"+
					"\n"+
					"	@Resource\n"+
					"	" + daoClassName + " " + daoClassVarName + ";\n"+
					"\n	//单个实体全部字段添加\n"+
					"	@Override\n"+
					"	public ResultData<Void> insertOne(" + entityClassName + " entity) {\n"+
					"		ResultData<Void> resultData = new ResultData<>();\n"+
					"		try {\n"+
					"			int rows = " + daoClassVarName + ".insert(entity);\n"+
					"			if (rows < 0) {\n"+
					"				resultData.setCode(400);\n"+
					"				resultData.setMsg(DBOperatedState.INSERT_FAILURE);\n"+
					"			} else {\n"+
					"				resultData.setMsg(DBOperatedState.INSERT_SUCCESS);\n"+
					"			}\n"+
					"		} catch (RuntimeException e) {\n"+
					"			resultData.setCode(400);\n"+
					"			resultData.setMsg(DBOperatedState.INNER_ERROR);\n"+
					"		}\n"+
					"		return resultData;\n"+
					"	}\n"+
					"\n	//根据实体ID单个实体删除\n"+
					"	@Override\n"+
					"	public ResultData<Void> deleteOne(" + classIdPropertyType + " " + entityIdName + ") {\n"+
					"		ResultData<Void> resultData = new ResultData<>();\n"+
					"		try {\n"+
					"			int rows = " + daoClassVarName + ".deleteByPrimaryKey(" + entityIdName + ");\n"+
					"			if (rows < 0) {\n"+
					"				resultData.setCode(400);\n"+
					"				resultData.setMsg(DBOperatedState.DELETE_FAILURE);\n"+
					"			} else {\n"+
					"				resultData.setMsg(DBOperatedState.DELETE_SUCCESS);\n"+
					"			}\n"+
					"		} catch (RuntimeException e) {\n"+
					"			resultData.setCode(400);\n"+
					"			resultData.setMsg(DBOperatedState.INNER_ERROR);\n"+
					"		}\n"+
					"		return resultData;\n"+
					"	}\n"+
					"\n	//根据实体ID数组批量删除实体\n"+
					"	@Override\n"+
					"	public ResultData<Void> deleteBatch(" + classIdPropertyType + "[] " + entityIdName + "s) {\n"+
					"		ResultData<Void> resultData = new ResultData<>();\n"+
					"		try {\n"+
					"			int rows = " + daoClassVarName + ".deleteBatch(Arrays.asList(" + entityIdName + "s));\n"+
					"			if (rows < 0) {\n"+
					"				resultData.setCode(400);\n"+
					"				resultData.setMsg(DBOperatedState.DELETE_FAILURE);\n"+
					"			} else {\n"+
					"				resultData.setMsg(DBOperatedState.DELETE_SUCCESS);\n"+
					"			}\n"+
					"		} catch (RuntimeException e) {\n"+
					"			resultData.setCode(400);\n"+
					"			resultData.setMsg(DBOperatedState.INNER_ERROR);\n"+
					"		}\n"+
					"		return resultData;\n"+
					"	}\n"+
					"\n	//单个实体全部字段更新\n"+
					"	@Override\n"+
					"	public ResultData<Void> updateOne(" + entityClassName + " entity) {\n"+
					"		ResultData<Void> resultData = new ResultData<>();\n"+
					"		try {\n"+
					"			int rows = " + daoClassVarName + ".updateByPrimaryKey(entity);\n"+
					"			if (rows < 0) {\n"+
					"				resultData.setCode(400);\n"+
					"				resultData.setMsg(DBOperatedState.UPDATE_FAILURE);\n"+
					"			} else {\n"+
					"				resultData.setMsg(DBOperatedState.UPDATE_SUCCESS);\n"+
					"			}\n"+
					"		} catch (RuntimeException e) {\n"+
					"			resultData.setCode(400);\n"+
					"			resultData.setMsg(DBOperatedState.INNER_ERROR);\n"+
					"		}\n"+
					"		return resultData;\n"+
					"	}\n"+
					"\n	//单个实体选择性字段更新\n"+
					"	@Override\n"+
					"	public ResultData<Void> updateOneSelective(" + entityClassName + " entity) {\n"+
					"		ResultData<Void> resultData = new ResultData<>();\n"+
					"		try {\n"+
					"			int rows = " + daoClassVarName + ".updateByPrimaryKeySelective(entity);\n"+
					"			if (rows < 0) {\n"+
					"				resultData.setCode(400);\n"+
					"				resultData.setMsg(DBOperatedState.UPDATE_FAILURE);\n"+
					"			} else {\n"+
					"				resultData.setMsg(DBOperatedState.UPDATE_SUCCESS);\n"+
					"			}\n"+
					"		} catch (RuntimeException e) {\n"+
					"			resultData.setCode(400);\n"+
					"			resultData.setMsg(DBOperatedState.INNER_ERROR);\n"+
					"		}\n"+
					"		return resultData;\n"+
					"	}\n"+
					"\n	//根据实体ID查询单个实体\n"+
					"	@Override\n"+
					"	public ResultData<" + entityClassName + "> selectOne(" + classIdPropertyType + " " + entityIdName + ") {\n"+
					"		ResultData<" + entityClassName + "> resultData = new ResultData<>();\n"+
					"		try {\n"+
					"			" + entityClassName + " " + entityClassVarName + " = " + daoClassVarName + ".selectByPrimaryKey(" + entityIdName + ");\n"+
					"			if (" + entityClassVarName + " == null) {\n"+
					"				resultData.setMsg(DBOperatedState.NO_DATA);\n"+
					"			} else {\n"+
					"				resultData.setMsg(DBOperatedState.SELECT_SUCCESS);\n"+
					"				resultData.setData(" + entityClassVarName + ");\n"+
					"			}\n"+
					"		} catch (RuntimeException e) {\n"+
					"			resultData.setCode(400);\n"+
					"			resultData.setMsg(DBOperatedState.INNER_ERROR);\n"+
					"		}\n"+
					"\n"+
					"		return resultData;\n"+
					"	}\n"+
					"\n	//根据选择性实体字段分页查询实体数组\n"+
					"	@Override\n"+
					"	public ResultData<List<" + entityClassName + ">> selectList(" + entityClassName + " entity, Page page) {\n"+
					"		ResultData<List<" + entityClassName + ">> resultData = new ResultData<>();\n"+
					"		try {\n"+
					"			List<" + entityClassName + "> " + entityClassVarName + "s = new ArrayList<>();\n"+
					"			int count = " + daoClassVarName + ".countByEntity(entity);\n"+
					"			if (count > 0) {// 总记录大于则有数据，可以进一步分页查询\n"+
					"				page.setTotalRecord(count);\n"+
					"				" + entityClassVarName + "s = " + daoClassVarName + ".selectByEntityAndPage(entity, page);\n"+
					"\n"+
					"				if (" + entityClassVarName + "s.size() > 0) {\n"+
					"					resultData.setMsg(DBOperatedState.SELECT_SUCCESS);\n"+
					"				} else {\n"+
					"					resultData.setMsg(DBOperatedState.NO_DATA);\n"+
					"				}\n"+
					"				\n"+
					"				resultData.setData(" + entityClassVarName + "s, page);\n"+
					"			} else {\n"+
					"				resultData.setMsg(DBOperatedState.NO_DATA);\n"+
					"			}\n"+
					"\n"+
					"		} catch (RuntimeException e) {\n"+
					"			resultData.setCode(400);\n"+
					"			resultData.setMsg(DBOperatedState.INNER_ERROR);\n"+
					"		}\n"+
					"\n"+
					"		return resultData;\n"+
					"	}\n"+
					"\n	//查询全部实体\n"+
					"	@Override\n"+
					"	public ResultData<List<" + entityClassName + ">> selectAll() {\n"+
					"		ResultData<List<" + entityClassName + ">> resultData = new ResultData<>();\n"+
					"		try {\n"+
					"			List<" + entityClassName + "> " + entityClassVarName + "s = " + daoClassVarName + ".selectByEntityAndPage(null, null);\n"+
					"\n"+
					"			if (" + entityClassVarName + "s.size() > 0) {\n"+
					"				resultData.setMsg(DBOperatedState.SELECT_SUCCESS);\n"+
					"				resultData.setData(" + entityClassVarName + "s);\n"+
					"			} else {\n"+
					"				resultData.setMsg(DBOperatedState.NO_DATA);\n"+
					"			}\n"+
					"\n"+
					"		} catch (RuntimeException e) {\n"+
					"			resultData.setCode(400);\n"+
					"			resultData.setMsg(DBOperatedState.INNER_ERROR);\n"+
					"		}\n"+
					"\n"+
					"		return resultData;\n"+
					"	}\n"+
					"\n"+
					"}\n";
			
			CalssGeneratorConfig.outputClassFile(serviceImplTargetDir, className + ".java", serviceImplTemplate);
			System.out.println("service接口" + serviceClassName + "的实现类" + className + ".java自动生成完成...");
		}
		
	}
	
	public static void generateCtrlClass() throws Exception {
		for (int i = 0; i < classNames.size(); i++) {
			String className = classNames.get(i);
			String description = "实体类" + className + "的控制器" + className + "Ctrl";
			String author = AUTHOR;
			String info = geTauthorInfo(description, author);
			String entityClassName =className;
			String entityClassVarName =className.substring(0,1).toLowerCase() + className.substring(1,className.length());;
			String serviceClassName =entityClassName + "Service";
			String serviceClassVarName =serviceClassName.substring(0,1).toLowerCase() + serviceClassName.substring(1,serviceClassName.length());
			String entityIdName = entityClassName.substring(0, 1).toLowerCase()
					+ entityClassName.substring(1, entityClassName.length()) + "Id";
			className += "Ctrl";
			
			String classIdPropertyType = classPropertyTypes.get(i).get(0);
			
			String ctrlTemplate = "package " + ctrlTargetPackage + ";\n"+
					"\n"+
					"import java.util.List;\n"+
					"\n"+
					"import javax.annotation.Resource;\n"+
					"\n"+
					"import org.springframework.stereotype.Controller;\n"+
					"import org.springframework.web.bind.annotation.RequestMapping;\n"+
					"import org.springframework.web.bind.annotation.RequestMethod;\n"+
					"import org.springframework.web.bind.annotation.ResponseBody;\n"+
					"\n"+
					"import " + dtoTargetPackage + ".Page;\n"+
					"import " + dtoTargetPackage + ".ResultData;\n"+
					"import " + entityTargetPackage + "." + entityClassName + ";\n"+
					"import " + serviceTargetPackage + "." + serviceClassName + ";\n"+
					"\n" + info + " \n"+
					"@Controller\n"+
					"@RequestMapping(\"/" + entityClassVarName + "\")\n"+
					"public class " + className + " {\n"+
					"\n"+
					"	@Resource\n"+
					"	" + serviceClassName + " " + serviceClassVarName + ";\n"+
					"\n	//单个实体全部字段添加\n"+
					"	@RequestMapping(value = \"/insert\", method = RequestMethod.POST)\n"+
					"	@ResponseBody\n"+
					"	public ResultData<Void> insertOne(" + entityClassName + " entity) {\n"+
					"		ResultData<Void> resultData = " + serviceClassVarName + ".insertOne(entity);\n"+
					"\n"+
					"		return resultData;\n"+
					"	}\n"+
					"\n	//根据实体ID单个实体删除\n"+
					"	@RequestMapping(value = \"/delete\", method = RequestMethod.POST)\n"+
					"	@ResponseBody\n"+
					"	public ResultData<Void> deleteOne(" + classIdPropertyType + " " + entityIdName + ") {\n"+
					"		ResultData<Void> resultData = " + serviceClassVarName + ".deleteOne(" + entityIdName + ");\n"+
					"		\n"+
					"		return resultData;\n"+
					"	}\n"+
					"\n	//根据实体ID数组批量删除实体\n"+
					"	@RequestMapping(value = \"/deleteBatch\", method = RequestMethod.POST)\n"+
					"	@ResponseBody\n"+
					"	public ResultData<Void> deleteBatch(" + classIdPropertyType + "[] " + entityIdName + "s) {\n"+
					"		ResultData<Void> resultData = " + serviceClassVarName + ".deleteBatch(" + entityIdName + "s);\n"+
					"\n"+
					"		return resultData;\n"+
					"	}\n"+
					"\n	//单个实体全部字段更新\n"+
					"	@RequestMapping(value = \"/update\", method = RequestMethod.POST)\n"+
					"	@ResponseBody\n"+
					"	public ResultData<Void> updateOne(" + entityClassName + " entity) {\n"+
					"		ResultData<Void> resultData = " + serviceClassVarName + ".updateOne(entity);\n"+
					"\n"+
					"		return resultData;\n"+
					"	}\n"+
					"\n	//单个实体选择性字段更新\n"+
					"	@RequestMapping(value = \"/updateSelective\", method = RequestMethod.POST)\n"+
					"	@ResponseBody\n"+
					"	public ResultData<Void> updateOneSelective(" + entityClassName + " entity) {\n"+
					"		ResultData<Void> resultData = " + serviceClassVarName + ".updateOneSelective(entity);\n"+
					"\n"+
					"		return resultData;\n"+
					"	}\n"+
					"\n	//根据实体ID查询单个实体\n"+
					"	@RequestMapping(value = \"/select\", method = RequestMethod.GET)\n"+
					"	@ResponseBody\n"+
					"	public ResultData<" + entityClassName + "> selectOne(" + classIdPropertyType + " " + entityIdName + ") {\n"+
					"		ResultData<" + entityClassName + "> resultData = " + serviceClassVarName + ".selectOne(" + entityIdName + ");\n"+
					"\n"+
					"		return resultData;\n"+
					"	}\n"+
					"\n	//根据选择性实体字段分页查询实体数组\n"+
					"	@RequestMapping(value = \"/list\", method = RequestMethod.GET)\n"+
					"	@ResponseBody\n"+
					"	public ResultData<List<" + entityClassName + ">> selectList(" + entityClassName + " entity, Page page) {\n"+
					"		ResultData<List<" + entityClassName + ">> resultData = " + serviceClassVarName + ".selectList(entity, page);\n"+
					"\n"+
					"		return resultData;\n"+
					"	}\n"+
					"\n	//查询全部实体\n"+
					"	@RequestMapping(value = \"/all\", method = RequestMethod.GET)\n"+
					"	@ResponseBody\n"+
					"	public ResultData<List<" + entityClassName + ">> selectAll() {\n"+
					"		ResultData<List<" + entityClassName + ">> resultData = " + serviceClassVarName + ".selectAll();\n"+
					"\n"+
					"		return resultData;\n"+
					"	}\n"+
					"\n"+
					"}\n";
			
			CalssGeneratorConfig.outputClassFile(ctrlTargetDir, className + ".java", ctrlTemplate);
			System.out.println("控制器" + className + ".java自动生成完成...");
		}
		
	}

}
