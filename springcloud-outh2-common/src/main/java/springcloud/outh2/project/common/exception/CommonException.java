
package springcloud.outh2.project.common.exception;


import springcloud.outh2.project.common.em.FastEnum;
import springcloud.outh2.project.common.utils.Tools;

/**
 *
 * @author 刘彦军
 *
 */
public class CommonException extends Exception {

	private static final long serialVersionUID = 946098281133332679L;

	private String code = Tools.EMPTY_STRING;
	private String subMessage = Tools.EMPTY_STRING;

	public CommonException(FastEnum fastEnum) {
		this(fastEnum.getCode(), fastEnum.getMessage());
	}

	public CommonException(String code, String message) {
		super(message);
		this.code = code;
	}

	public CommonException(String code, String message, String subMessage) {
		super(message);
		this.code = code;
		this.subMessage = subMessage;
	}

	public CommonException(String code, String message, Throwable throwable) {
		super(message, throwable);
		this.code = code;
	}

	public CommonException(String code, String message, String subMessage, Throwable throwable) {
		super(message, throwable);
		this.code = code;
		this.subMessage = subMessage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSubMessage() {
		return subMessage;
	}

}
