/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.generator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * <p>
 * 注释插件
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年10月29日 下午4:32:42
 * @version 1.0.0
 */
public class CommentExtGenerator implements CommentGenerator {

	private Properties properties;
	private Properties systemPro;
	private boolean suppressDate;
	private boolean suppressAllComments;
	private String currentDateStr;

	public CommentExtGenerator() {
		super();
		properties = new Properties();
		systemPro = System.getProperties();
		suppressDate = false;
		suppressAllComments = false;
		currentDateStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS")).format(new Date());
	}

	public void addConfigurationProperties(Properties properties) {
		this.properties.putAll(properties);
		suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
		suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
	}

	protected String getDateString() {
		String result = "";
		if (!suppressDate) {
			result = currentDateStr;
		}
		return result;
	}

	public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		if (suppressAllComments) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		topLevelClass.addJavaDocLine("/**");
		topLevelClass.addJavaDocLine(" * <p> ");
		sb.append(" * ");
		sb.append(introspectedTable.getRemarks());
		sb.append(" ");
		sb.append(introspectedTable.getFullyQualifiedTable());
		topLevelClass.addJavaDocLine(sb.toString().replace("\n", " "));
		topLevelClass.addJavaDocLine(" * </p> ");
		topLevelClass.addJavaDocLine(" * ");
		sb.setLength(0);
		sb.append(" * @author ");
		sb.append(systemPro.getProperty("user.name"));
		sb.append(" ");
		sb.append(getDateString());
		topLevelClass.addJavaDocLine(sb.toString().replace("\n", " "));
		topLevelClass.addJavaDocLine(" */");
	}

	public void addFieldComment(Field field, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		if (suppressAllComments) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		field.addJavaDocLine("/**");
		sb.append(" * ");
		sb.append(introspectedColumn.getActualColumnName());
		sb.append(" 描述:");
		sb.append(introspectedColumn.getRemarks());
		field.addJavaDocLine(sb.toString().replace("\n", " "));
		field.addJavaDocLine(" */");
	}

	public void addGetterComment(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		// if (suppressAllComments) {
		// return;
		// }
		// method.addJavaDocLine("/**");
		// StringBuilder sb = new StringBuilder();
		// sb.append(" * @return ");
		// sb.append(introspectedColumn.getActualColumnName());
		// sb.append(" ");
		// sb.append(introspectedColumn.getRemarks());
		// method.addJavaDocLine(sb.toString().replace("\n", " "));
		// method.addJavaDocLine(" */");
	}

	public void addSetterComment(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		// if (suppressAllComments) {
		// return;
		// }
		// method.addJavaDocLine("/**");
		// StringBuilder sb = new StringBuilder();
		// Parameter parm = method.getParameters().get(0);
		// sb.append(" * @param ");
		// sb.append(parm.getName());
		// sb.append(" ");
		// sb.append(introspectedColumn.getRemarks());
		// method.addJavaDocLine(sb.toString().replace("\n", " "));
		// method.addJavaDocLine(" */");
	}

	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub
	}

	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub
	}

	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
		// TODO Auto-generated method stub
	}

	public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub
	}

	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub
	}

	public void addJavaFileComment(CompilationUnit compilationUnit) {
		// TODO Auto-generated method stub
	}

	public void addComment(XmlElement xmlElement) {
		// TODO Auto-generated method stub
	}

	public void addRootComment(XmlElement rootElement) {
		// TODO Auto-generated method stub
	}

}
