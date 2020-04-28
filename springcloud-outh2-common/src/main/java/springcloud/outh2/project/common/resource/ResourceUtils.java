package springcloud.outh2.project.common.resource;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public final class ResourceUtils {

	private ResourceUtils() {
	}

	public static InputStream loadResource(Class<?> clazz, String resource) {
		return clazz.getResourceAsStream(resource);
	}

	public static InputStream loadResource(ClassLoader loader, String resource) {
		return loader.getResourceAsStream(resource);
	}

	public static Properties mergeLoadProperties(Class<?> clazz, ClassLoader loader) {
		Properties origin = loadProperties(clazz, clazz.getName());
		Properties custome = loadProperties(loader, clazz.getName());
		if (custome == null) {
			return origin;
		}
		for (Map.Entry<Object, Object> entry : custome.entrySet()) {
			origin.put(String.valueOf(entry.getKey()).trim(), String.valueOf(entry.getValue()).trim());
		}
		return origin;
	}

	public static Properties loadProperties(Class<?> clazz, String resource) {
		InputStream in = null;
		try {
			in = loadResource(clazz, resource);
			if (in == null) {
				return null;
			}
			Properties properties = new Properties();
			properties.load(in);
			return properties;
		} catch (IOException e) {
			throw new IllegalStateException("load resource '" + resource + "', class '" + clazz + "' cause error", e);
		} finally {
			if (in != null) {
				close(in);
			}
		}
	}

	public static Properties loadProperties(ClassLoader loader, String resource) {
		InputStream in = null;
		try {
			in = loadResource(loader, resource);
			if (in == null) {
				return null;
			}
			Properties properties = new Properties();
			properties.load(in);
			return properties;
		} catch (IOException e) {
			throw new IllegalStateException(
					"load resource '" + resource + "', class loader '" + loader + "' cause error", e);
		} finally {
			if (in != null) {
				close(in);
			}
		}
	}

	public static void close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException ignore) {
			}
		}
	}
}
