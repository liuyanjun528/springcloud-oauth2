package springcloud.outh2.project.common.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;
import java.util.Properties;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月15日 下午7:45:58
 * @version 1.0.0
 */
public class EntityNullPlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass,
			IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		Properties properties = this.context.getJavaModelGeneratorConfiguration().getProperties();
		boolean rc = StringUtility.isTrue(properties.getProperty("trimStrings"));
		StringBuilder sb = new StringBuilder();
		if (rc && introspectedColumn.isStringColumn()) {
			String property = introspectedColumn.getJavaProperty();
			List<String> bl = method.getBodyLines();
			bl.clear();
			sb.append("this.");
			sb.append(property);
			sb.append(" = (");
			sb.append(property);
			sb.append(" == null || ");
			sb.append(property);
			sb.append(".trim().isEmpty() ) ? null : ");
			sb.append(property);
			sb.append(".trim();");
			method.addBodyLine(sb.toString());
		}

		return true;
	}

}
