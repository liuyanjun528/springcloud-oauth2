package springcloud.outh2.project.common.resource;
import springcloud.outh2.project.common.utils.Tools;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {

	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	private FileUtils() {
	}

	public static String[] read2Array(InputStream in) throws IOException {
		return read2Array(in, Charset.defaultCharset());
	}

	public static String[] read2Array(InputStream in, Charset charset) throws IOException {
		if (in == null || charset == null) {
			return EMPTY_STRING_ARRAY;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
		List<String> lines = new ArrayList<String>();
		for (String line = null; (line = reader.readLine()) != null;) {
			lines.add(line.trim());
		}
		return lines.toArray(new String[lines.size()]);
	}

	public static String[] read2Array(ClassLoader loader, String resource) {
		return read2Array(loader, resource, Charset.defaultCharset());
	}

	public static String[] read2Array(ClassLoader loader, String resource, Charset charset) {
		if (loader == null || Tools.isBlank(resource)) {
			return EMPTY_STRING_ARRAY;
		}
		InputStream in = null;
		try {
			in = loader.getResourceAsStream(resource);
			return read2Array(in, charset);
		} catch (IOException e) {
			return EMPTY_STRING_ARRAY;
		} finally {
			closeIO(in);
		}
	}

	public static String[] read2Array(Class<?> clazz, String resource) {
		return read2Array(clazz, resource, Charset.defaultCharset());
	}

	public static String[] read2Array(Class<?> clazz, String resource, Charset charset) {
		if (clazz == null || Tools.isBlank(resource)) {
			return EMPTY_STRING_ARRAY;
		}
		String name = resource;
		if (clazz.getPackage() != null) {
			name = clazz.getPackage().getName().replace('.', '/') + '/' + resource;
		}
		return read2Array(clazz.getClassLoader(), name, charset);
	}

	private static void closeIO(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
			}
		}
	}
}
