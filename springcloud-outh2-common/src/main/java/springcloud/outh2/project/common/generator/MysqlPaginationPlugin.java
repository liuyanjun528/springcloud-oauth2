/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package springcloud.outh2.project.common.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * <p>
 * 分页插件
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年10月29日 下午5:11:02
 * @version 1.0.0
 */
public class MysqlPaginationPlugin extends PluginAdapter {

	private static final String PAGE_QUERY_ID = "page";

	private static final String PAGE_COUNT_ID = "count";

	private static final String PAGING_TYPE = "springcloud.outh2.project.common.page.Pagingable";

	private static final String MAPPER_TYPE = "org.apache.ibatis.annotations.Mapper";

	final FullyQualifiedJavaType mapper = new FullyQualifiedJavaType(MAPPER_TYPE);

	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		final FullyQualifiedJavaType pagingable = new FullyQualifiedJavaType(PAGING_TYPE);
		final FullyQualifiedJavaType recoredType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		pagingable.addTypeArgument(recoredType);
		pagingable.addTypeArgument(recoredType);
		interfaze.addImportedType(mapper);
		interfaze.addSuperInterface(pagingable);
		interfaze.addAnnotation("@Mapper");
		return true;
	}

	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
		XmlElement parentElement = document.getRootElement();

		countMapDocumentGenerated(parentElement, introspectedTable);
		pagingMapDocumentGenerated(parentElement, introspectedTable);
		List<Element> elements = parentElement.getElements();

		for (int size = elements.size(), i = 0; i < size; i++) {
			parentElement.addElement(2 * i, new TextElement(""));
		}
		return true;
	}

	private void countMapDocumentGenerated(XmlElement parentElement, IntrospectedTable introspectedTable) {
		XmlElement answer = new XmlElement("select");
		answer.addAttribute(new Attribute("id", PAGE_COUNT_ID));
		answer.addAttribute(new Attribute("resultType", "java.lang.Integer"));
		context.getCommentGenerator().addComment(answer);
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(1) FROM ");
		sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
		sb.append(" WHERE 1=1 ");
		answer.addElement(new TextElement(sb.toString()));
		for (IntrospectedColumn introspectedColumn : ListUtilities
				.removeGeneratedAlwaysColumns(introspectedTable.getAllColumns())) {
			XmlElement isNotNullElement = new XmlElement("if");
			sb.setLength(0);
			sb.append("entity.");
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(" != null");
			sb.append(" and ");
			sb.append("entity.");
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(" != ''");
			isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
			answer.addElement(isNotNullElement);

			sb.setLength(0);
			sb.append(" AND ");
			sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
			sb.append(" = ");
			sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "entity."));

			isNotNullElement.addElement(new TextElement(sb.toString()));
		}
		parentElement.addElement(answer);
	}

	private void pagingMapDocumentGenerated(XmlElement parentElement, IntrospectedTable introspectedTable) {
		XmlElement answer = new XmlElement("select");
		answer.addAttribute(new Attribute("id", PAGE_QUERY_ID));
		answer.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		context.getCommentGenerator().addComment(answer);
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		if (stringHasValue(introspectedTable.getSelectByPrimaryKeyQueryId())) {
			sb.append('\'');
			sb.append(introspectedTable.getSelectByPrimaryKeyQueryId());
			sb.append("' as QUERYID,");
		}
		answer.addElement(new TextElement(sb.toString()));
		answer.addElement(getBaseColumnListElement(introspectedTable));
		if (introspectedTable.hasBLOBColumns()) {
			answer.addElement(new TextElement(","));
			answer.addElement(getBlobColumnListElement(introspectedTable));
		}
		sb.setLength(0);
		sb.append("FROM ");
		sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
		sb.append(" WHERE 1=1 ");
		answer.addElement(new TextElement(sb.toString()));
		for (IntrospectedColumn introspectedColumn : ListUtilities
				.removeGeneratedAlwaysColumns(introspectedTable.getAllColumns())) {
			XmlElement isNotNullElement = new XmlElement("if");
			sb.setLength(0);
			sb.append("entity.");
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(" != null");
			sb.append(" and ");
			sb.append("entity.");
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(" != ''");
			isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
			answer.addElement(isNotNullElement);

			sb.setLength(0);
			sb.append(" AND ");
			sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
			sb.append(" = ");
			sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "entity."));

			isNotNullElement.addElement(new TextElement(sb.toString()));
		}
		// 分页
		{
			XmlElement isNotNullElement = new XmlElement("if");
			sb.setLength(0);
			sb.append("paging !=null and paging.offset >= 0 and paging.pageSize > 0");
			isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
			answer.addElement(isNotNullElement);

			sb.setLength(0);
			sb.append(" limit ${paging.offset}, ${paging.pageSize} ");
			isNotNullElement.addElement(new TextElement(sb.toString()));
		}

		parentElement.addElement(answer);

	}

	protected XmlElement getBaseColumnListElement(IntrospectedTable introspectedTable) {
		XmlElement answer = new XmlElement("include");
		answer.addAttribute(new Attribute("refid", introspectedTable.getBaseColumnListId()));
		return answer;
	}

	protected XmlElement getBlobColumnListElement(IntrospectedTable introspectedTable) {
		XmlElement answer = new XmlElement("include");
		answer.addAttribute(new Attribute("refid", introspectedTable.getBlobColumnListId()));
		return answer;
	}

	public boolean validate(List<String> warnings) {
		return true;
	}

}
