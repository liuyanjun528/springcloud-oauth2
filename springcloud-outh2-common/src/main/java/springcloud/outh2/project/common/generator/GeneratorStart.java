package springcloud.outh2.project.common.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class GeneratorStart {
	/**
	 * <p>
	 * 分页插件
	 * </p>
	 *
	 * @author xiaoyu.wang
	 * @date 2017年10月29日 下午5:11:02
	 * @version 1.0.0
	 */
	public static void main(String[] args) throws Exception {
		try {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("generator-config.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(is);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
