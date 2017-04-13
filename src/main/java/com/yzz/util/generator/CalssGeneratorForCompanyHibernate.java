package com.yzz.util.generator;

import java.util.List;
import java.util.Map;

import com.yzz.util.TimeUtil;

/**
 * 
 * @description:自动生成复用性高的通用crud操作实体类、DAO接口、DAO实现类，适用于hibernate框架与MySQL数据库
 *
 * @author 杨志钊
 * @date 2017年4月13日 上午9:40:07
 */
@SuppressWarnings("unchecked")
public class CalssGeneratorForCompanyHibernate {

	public static void main(String[] args) throws Exception {
		String classGeneratorConfigXML = projectPath + "\\src\\main\\resources\\generator\\yzz-class-generator2.xml";
		
		CalssGeneratorConfig.setClassGeneratorConfigXML(classGeneratorConfigXML);
		Map<String, Object> map = CalssGeneratorConfig.getTableAndClassDatas();

		CalssGeneratorForCompanyHibernate.setTableAndClassDatas(map);
		
		CalssGeneratorForCompanyHibernate.generateEntityClass();
		
		CalssGeneratorForCompanyHibernate.generateDaoClass();
		
		CalssGeneratorForCompanyHibernate.generateDaoImplClass();
	}

	private static String projectPath = System.getProperty("user.dir");
	
	private static String AUTHOR = "杨志钊";

	public static void setProjectPath(String projectPath) {
		CalssGeneratorForCompanyHibernate.projectPath = projectPath;
	}
	
	public static void setAUTHOR(String AUTHOR) {
		CalssGeneratorForCompanyHibernate.AUTHOR = AUTHOR;
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
				 "* 程序名称：易外销云CRM系统\n"+
				 "* 程序版本：V2.0\n"+
				 "* 版权所有：深圳市科飞时速网络科技有限公司\n"+
				 "* 技术支持：Tech@21gmail.com\n"+
				 "* 单元名称：" + description + " \n"+
				 "* 开始时间：" + TimeUtil.getCurrentTimeString() + " \n"+
				 "* 开发人员：" + author + " \n"+
				 "* 最后修改：" + TimeUtil.getCurrentTimeString() + " \n"+
				 "* 备注说明：如需修改请联系开发人员\n " +
				 "*/";
		return authorInfo;
	}

	public static void generateEntityClass() throws Exception {

		for (int i = 0; i < tableNames.size(); i++) {
			String description = tableNames.get(i) + "表的实体类" + classNames.get(i);
			String author = AUTHOR;

			String classStr = "package " + entityTargetPackage
					+ "; \n\nimport java.io.Serializable; \nimport javax.persistence.Entity; \nimport javax.persistence.GeneratedValue; \nimport javax.persistence.GenerationType; \nimport javax.persistence.Id; \nimport javax.persistence.Table; \n\n"
					+ geTauthorInfo(description, author) + "\n\n@Entity \n@Table(name = \"" + tableNames.get(i)
					+ "\") \npublic class " + classNames.get(i) + " implements Serializable {\n\n";
			String propertyStr = "";
			String methodStr = "";
			for (int j = 0; j < tableColumnNames.get(i).size(); j++) {
				String classPropertyType = classPropertyTypes.get(i).get(j);
				String classPropertyName = tableColumnNames.get(i).get(j);
				String classPropertyRemark = tableColumnRemarks.get(i).get(j);
				String classPropertyNameUpper = classPropertyName.substring(0, 1).toUpperCase()
						+ classPropertyName.substring(1, classPropertyName.length());
				String tempStr = "";
				tempStr = "	private " + classPropertyType + " " + classPropertyName + ";//" + classPropertyRemark
						+ "\n\n";
				propertyStr += tempStr;
				if (j != 0) {
					tempStr = "	public " + classPropertyType + " get" + classPropertyNameUpper + "() { \n		return "
							+ classPropertyName + "; \n	} \n\n";
				}else{
					tempStr = "	@Id \n 	@GeneratedValue(strategy=GenerationType.AUTO)\n 	public " + classPropertyType + " get" + classPropertyNameUpper + "() { \n		return "
							+ classPropertyName + "; \n	} \n\n";
				}
				
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
			String className = classNames.get(i);
			String entityClassName =className;
			className += "Dao";
			
			String description = "实体类" + classNames.get(i) + "的DAO接口" + className;
			String author = AUTHOR;
			
			String daoTemplate = "package " + daoTargetPackage + ";\n"+
					"\n"+
					"import java.util.List;\n"+
					"import com.espeed.command.PageBean;\n"+
					"import " + entityTargetPackage + "." + entityClassName + ";\n"+
					"\n"+ geTauthorInfo(description, author)+
					"\n"+
					"public interface " + className + " {\n"+
					"	/**实体插入*/\n"+
					"	public int addPojo(" + entityClassName + " obj)throws Exception;\n"+
					"	/**实体删除*/\n"+
					"	public void deletePojo(" + entityClassName + " obj)throws Exception;\n"+
					"	/**实体编辑*/\n"+
					"	public void updatePojo(" + entityClassName + " obj)throws Exception;\n"+
					"	/**HQL查询*/\n"+
					"	public List<" + entityClassName + "> findByHql(String hql)throws Exception;\n"+
					"	/**SQL查询*/\n"+
					"	public List<Object> findBySql(String sql)throws Exception;\n"+
					"	/**HQL查询分页*/\n"+
					"	public List<" + entityClassName + "> findByHqlPage(String hql,String hqlcount,PageBean pb)throws Exception;\n"+
					"	/**HQL查询数量*/\n"+
					"	public int findByHqlCount(String hql)throws Exception;\n"+
					"}\n";
			
			CalssGeneratorConfig.outputClassFile(daoTargetDir, className + ".java", daoTemplate);
			System.out.println("DAO接口" + className + ".java自动生成完成...");
		}
		
	}

	public static void generateDaoImplClass() throws Exception {
		for (int i = 0; i < classNames.size(); i++) {
			String className = classNames.get(i);
			String entityClassName =className;
			String daoClassName = className + "Dao";
			className = daoClassName + "Impl";
			
			String description = "DAO接口" + daoClassName + "的实现类" + className;
			String author = AUTHOR;
			
			String classIdPropertyType = classPropertyTypes.get(i).get(0);
			
			String daoImplTemplate = "package " + daoImplTargetPackage + ";\n"+
					"\n"+
					"import java.util.List;\n"+
					"import com.espeed.command.PageBean;\n"+
					"import " + daoTargetPackage + "." + daoClassName + ";\n"+
					"import " + entityTargetPackage + "." + entityClassName + ";\n"+
					"\n"+ geTauthorInfo(description, author)+
					"\n"+
					"public class " + className + " extends HibernateBaseDAOImpl<" + entityClassName + ", " + classIdPropertyType + "> implements " + daoClassName + " {\n"+
					"	/**实体插入*/\n"+
					"	public int addPojo(" + entityClassName + " obj)throws Exception{\n"+
					"		return super.add(obj);\n"+
					"	}\n"+
					"	/**实体删除*/\n"+
					"	public void deletePojo(" + entityClassName + " obj)throws Exception{\n"+
					"		super.del(obj);\n"+
					"	}\n"+
					"	/**实体编辑*/\n"+
					"	public void updatePojo(" + entityClassName + " obj)throws Exception{\n"+
					"		super.update(obj);\n"+
					"	}\n"+
					"	/**HQL查询*/\n"+
					"	public List<" + entityClassName + "> findByHql(String hql)throws Exception{\n"+
					"		return super.getAll(hql);\n"+
					"	}\n"+
					"	/**SQL查询*/\n"+
					"	public List<Object> findBySql(String sql)throws Exception{\n"+
					"		return super.findBySql(sql);\n"+
					"	}\n"+
					"	/**HQL查询分页*/\n"+
					"	public List<" + entityClassName + "> findByHqlPage(String hql,String hqlcount,PageBean pb)throws Exception{\n"+
					"		return super.findByPage(hql, hqlcount, pb);\n"+
					"	}\n"+
					"	/**HQL查询数量*/\n"+
					"	public int findByHqlCount(String hql)throws Exception{\n"+
					"		return super.count(hql);\n"+
					"	}\n"+
					"}\n";
			
			CalssGeneratorConfig.outputClassFile(daoImplTargetDir, className + ".java", daoImplTemplate);
			System.out.println("DAO接口实现类" + className + ".java自动生成完成...");
		}
		
	}

}
