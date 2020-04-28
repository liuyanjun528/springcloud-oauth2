
package springcloud.outh2.project.common.result;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import springcloud.outh2.project.common.em.CommonCodeEnum;

import java.io.Serializable;

/**
 *
 * @author 刘彦军
 *
 * @param <E>
 */

@Data
public class FastResponse<E> implements Serializable {

	private static final long serialVersionUID = 7010064721473780599L;

	private String code;
	private String message;
	private String subMessage;

	private E result;

	public FastResponse() {
		super();
	}

	public FastResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public FastResponse(String code, String message, String subMessage) {
		super();
		this.code = code;
		this.message = message;
		this.subMessage = subMessage;
	}

	public FastResponse(String code, String message, E result) {
		super();
		this.code = code;
		this.message = message;
		this.result = result;
	}

	public FastResponse(String code, String message, String subMessage, E result) {
		super();
		this.code = code;
		this.message = message;
		this.subMessage = subMessage;
		this.result = result;
	}

	public boolean isSuccess() {
		if (CommonCodeEnum.SUCCESS.getCode().equals(code)) {
			return true;
		}
		return false;
	}

	public boolean fail() {
		if (!isSuccess()) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "FastResponse [code=" + code + ", message=" + message + ", subMessage=" + subMessage + ", result="
				+ JSON.toJSONString(result) + "]";
	}

}
