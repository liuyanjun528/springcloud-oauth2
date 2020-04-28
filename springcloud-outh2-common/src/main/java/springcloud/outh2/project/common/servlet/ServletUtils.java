
package springcloud.outh2.project.common.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import springcloud.outh2.project.common.utils.EmptyConstants;
import springcloud.outh2.project.common.utils.Tools;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static springcloud.outh2.project.common.resource.FileUtils.read2Array;
import static springcloud.outh2.project.common.utils.Tools.*;


/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月24日 上午10:41:09
 * @version 1.0.0
 */

public final class ServletUtils {

	private static final Logger LOG = LoggerFactory.getLogger(ServletUtils.class);

	private static final String[] PROXY_REMOTE_IP_ADDRESS;

	private static final long IP_MASK = 0xffffffffL;

	static {
		String[] proxyRemoteHeaders = read2Array(Thread.currentThread().getContextClassLoader(), "remote-ip-headers");
		if (proxyRemoteHeaders.length == 0) {
			proxyRemoteHeaders = read2Array(ServletUtils.class, "remote-ip-headers-default");
		}
		PROXY_REMOTE_IP_ADDRESS = proxyRemoteHeaders;

		LOG.info("remote-ip-headers : {}",
				PROXY_REMOTE_IP_ADDRESS == null ? "" : Arrays.toString(PROXY_REMOTE_IP_ADDRESS));
	}

	private ServletUtils() {
	}

	/**
	 * <p>
	 * 获取请求的客户端的 IP 地址。若应用服务器前端配有反向代理的 Web 服务器， 需要在 Web 服务器中将客户端原始请求的 IP 地址加入到 HTTP
	 * header 中。
	 * </p>
	 *
	 * <p>
	 * Web 服务器反向代理中用于存放客户端原始 IP 地址的 Http header 名字，默认值为根据优先级为 "X-Forwarded-For" 和
	 * "X-Real-IP"， 若有需要使用其他的 HTTP header，需要在 classpath 的 remote-ip-headers
	 * 文件中添加所需要的 HTTP header 名字， 根据优先级依次在该文件中增加数据，格式为每行一个 headers 名称。
	 * </p>
	 *
	 * @param request
	 *            HTTP 请求
	 * @return 远程客户端的 IP 地址
	 */
	public static String getRemoteIp(HttpServletRequest request) {
		for (int i = 0; i < PROXY_REMOTE_IP_ADDRESS.length; i++) {
			String ip = request.getHeader(PROXY_REMOTE_IP_ADDRESS[i]);
			if (!isBlank(ip)) {
				return getRemoteIpFromForward(ip);
			}
		}
		return request.getRemoteAddr();
	}

	/**
	 * <p>
	 * 从 HTTP Header 的 X-Forward-IP 头中截取客户端连接 IP 地址。如果经过多次反向代理， 在 X-Forward-IP
	 * 中获得的是以“,&lt;SP&gt;”分隔 IP 地址链，第一段为客户端 IP 地址。
	 * </p>
	 *
	 * @param xforwardIp
	 * @return 通过代理服务器后获取的客户端 IP 地址
	 */
	private static String getRemoteIpFromForward(String xforwardIp) {
		int commaOffset = xforwardIp.indexOf(',');
		if (commaOffset < 0) {
			return xforwardIp;
		}
		return xforwardIp.substring(0, commaOffset);
	}

	/**
	 * <p>
	 * 将 IP 地址转换成为 long 类型数字
	 * </p>
	 *
	 * @param ip
	 *            以“.”分隔 4 段的 IPv4 地址
	 * @return 表示 IP 地址的 long 类型数值
	 */
	public static long parseIp(String ip) {
		if (ip == null || ip.length() == 0) {
			return -1;
		}
		char[] chs = ip.toCharArray();
		long t = 0;
		int n = 0;
		for (int i = 0, k = 0; i < chs.length; i++) {
			if (chs[i] == '.') {
				if (k++ > 2) {
					break;
				}
				t = (t << SHIFT_BIT_8) | (n & BYTE_MASK);
				n = 0;
				continue;
			}
			if (chs[i] >= '0' && chs[i] <= '9') {
				n = n * DECIMAL_MULTIPLES + (chs[i] - '0');
				continue;
			}
			break;
		}
		t = (t << SHIFT_BIT_8) | (n & BYTE_MASK);
		return (t & IP_MASK);
	}

	/**
	 * <p>
	 * 将数值的 IPv4 地址转换成为四段的 IPv4 地址
	 * </p>
	 *
	 * @param numberIp
	 *            IPv4 地址的数值形式
	 * @return 四段格式的 IPv4 地址。若参数中的数值小于 0 或者大于 0xffffffffL 时将返回 null 值
	 */
	public static String toIPv4(long numberIp) {
		if (numberIp < 0 || numberIp > IP_MASK) {
			return null;
		}
		StringBuilder builder = new StringBuilder(16);
		builder.append((numberIp >>> SHIFT_BIT_24) & BYTE_MASK).append('.')
				.append((numberIp >>> SHIFT_BIT_16) & BYTE_MASK).append('.')
				.append((numberIp >>> SHIFT_BIT_8) & BYTE_MASK).append('.').append(numberIp & BYTE_MASK);
		return builder.toString();
	}

	public static String getCookieValue(Cookie[] cookies, String name) {
		Cookie cookie = getCookie(cookies, name);
		if (cookie == null) {
			return null;
		}
		return cookie.getValue();
	}

	public static Cookie getCookie(Cookie[] cookies, String name) {
		if (isBlank(cookies)) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie;
			}
		}
		return null;
	}

	public static Map<String, String> getHeaders(HttpServletRequest request) {
		return getHeaders(request, EmptyConstants.EMPTY_STRING_ARRAY);
	}

	public static Map<String, String> getHeaders(HttpServletRequest request, String[] ignoreHeaders) {
		return getHeaders(request, null, ignoreHeaders);
	}

	public static Map<String, String> getHeaders(HttpServletRequest request, String prefix) {
		return getHeaders(request, prefix, EmptyConstants.EMPTY_STRING_ARRAY);
	}

	public static Map<String, String> getHeaders(HttpServletRequest request, String prefix, String[] ignoreHeaders) {
		return getHeaders(request, prefix, false, ignoreHeaders);
	}

	public static Map<String, String> getHeaders(HttpServletRequest request, String prefix, boolean sort,
                                                 String[] ignoreHeaders) {
		if (request == null) {
			return Collections.emptyMap();
		}
		if (prefix != null) {
			prefix = prefix.toLowerCase();
		}
		Map<String, String> headers = sort ? new TreeMap<String, String>() : new HashMap<String, String>(16);
		for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements();) {
			String name = e.nextElement().toLowerCase();
			if (prefix != null && !name.startsWith(prefix)) {
				continue;
			}
			if (Tools.isInArrayIgnoreCase(name, ignoreHeaders)) {
				continue;
			}
			headers.put(name.toLowerCase(), request.getHeader(name));
		}
		return headers;
	}

	public static Map<String, String> getCookies(HttpServletRequest request) {
		return getCookies(request, false);
	}

	public static Map<String, String> getCookies(HttpServletRequest request, boolean sort) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length == 0) {
			return Collections.emptyMap();
		}
		Map<String, String> params = sort ? new TreeMap<String, String>() : new HashMap<String, String>(cookies.length);
		for (Cookie cookie : cookies) {
			params.put(cookie.getName(), cookie.getValue());
		}
		return params;
	}

	public static Map<String, String> getParamaters(HttpServletRequest request) {
		return getParamaters(request, false);
	}

	public static Map<String, String> getParamaters(HttpServletRequest request, boolean sort) {
		Map<String, String> params = (sort ? new TreeMap<String, String>() : new HashMap<String, String>());
		for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
			String name = e.nextElement();
			String value = request.getParameter(name);
			params.put(name, value);
		}
		return params;
	}
}
