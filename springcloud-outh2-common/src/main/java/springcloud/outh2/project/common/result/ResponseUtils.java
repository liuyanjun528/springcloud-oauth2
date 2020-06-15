package springcloud.outh2.project.common.result;

import springcloud.outh2.project.common.em.CommonCodeEnum;
import springcloud.outh2.project.common.em.FastEnum;

public final class ResponseUtils {
    public static FastResponse<Void> success() {
        return create(CommonCodeEnum.SUCCESS);
    }
//自定义返回消息需要
//	public static FastResponse<Void> success(String subMessage) {
//		return create((FastEnum) CommonCodeEnum.SUCCESS, (String) subMessage);
//	}

    public static <E> FastResponse<E> success(E result) {
        return create((FastEnum) CommonCodeEnum.SUCCESS, result);
    }

    public static <E> FastResponse<E> success(String subMessage, E result) {
        return create((FastEnum) CommonCodeEnum.SUCCESS, subMessage, result);
    }

    public static FastResponse<Void> exception() {
        return create(CommonCodeEnum.EXCEPTION);
    }

    public static FastResponse<Void> error() {
        return create(CommonCodeEnum.ERROR);
    }

    public static FastResponse<Void> error(String subMessage) {
        return create((FastEnum) CommonCodeEnum.ERROR, (String) subMessage);
    }

    public static FastResponse<Void> error(FastEnum fastEnum, String message) {
        return new FastResponse<Void>(fastEnum.getCode(), message);
    }

    public static <E> FastResponse<E> error(FastEnum code, String message, E result) {
        return new FastResponse<E>(code.getCode(), message, result);
    }

    public static FastResponse<Void> create(FastEnum fastEnum) {
        return create(fastEnum.getCode(), fastEnum.getMessage());
    }

    public static FastResponse<Void> create(FastEnum fastEnum, String subMessage) {
        return create(fastEnum.getCode(), fastEnum.getMessage(), subMessage);
    }

    public static FastResponse<Void> create(String code, String message) {
        return new FastResponse<Void>(code, message);
    }

    public static FastResponse<Void> create(String code, String message, String subMessage) {
        return new FastResponse<Void>(code, message, subMessage);
    }

    public static <E> FastResponse<E> create(String code, String message, String subMessage, E result) {
        return new FastResponse<E>(code, message, subMessage, result);
    }

    public static <E> FastResponse<E> create(FastEnum fastEnum, E result) {
        return new FastResponse<E>(fastEnum.getCode(), fastEnum.getMessage(), result);
    }

    public static <E> FastResponse<E> create(FastEnum fastEnum, String subMessage, E result) {
        return new FastResponse<E>(fastEnum.getCode(), fastEnum.getMessage(), subMessage, result);
    }
}
