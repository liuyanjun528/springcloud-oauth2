package springcloud.outh2.project.common.result;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import springcloud.outh2.project.common.em.CommonCodeEnum;
import springcloud.outh2.project.common.exception.CommonException;
import springcloud.outh2.project.common.utils.Tools;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;


public class ValidatorUtils {
	private static final Logger log = LoggerFactory.getLogger(ValidatorUtils.class);
	private static Validator validator = null;

	public static void validate(Object object) throws CommonException {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, new Class[0]);
		if (constraintViolations != null && constraintViolations.size() > 0) {
			String errorMessage = null;
			StringBuffer buff = new StringBuffer();
			Iterator<ConstraintViolation<Object>> var4 = constraintViolations.iterator();

			while (var4.hasNext()) {
				ConstraintViolation<Object> constraintViolation = (ConstraintViolation<Object>) var4.next();
				String property = constraintViolation.getPropertyPath().toString();
				errorMessage = constraintViolation.getMessage();
				buff.append("[");
				buff.append(property);
				buff.append("]");
				buff.append(errorMessage);
			}

			throw ExceptionUtils.create(CommonCodeEnum.VALIDATE_ERROR, buff.toString());
		}
	}

	public static void validate(String value, String name) throws CommonException {
		if (Tools.isBlank(value)) {
			throw ExceptionUtils.create(CommonCodeEnum.VALIDATE_ERROR, name + "不能为空");
		}
	}

	public static void validate(Integer value, String name) throws CommonException {
		if (value == null) {
			throw ExceptionUtils.create(CommonCodeEnum.VALIDATE_ERROR, name + "不能为空");
		}
	}

	public static void validate(Long value, String name) throws CommonException {
		if (value == null) {
			throw ExceptionUtils.create(CommonCodeEnum.VALIDATE_ERROR, name + "不能为空");
		}
	}

	public static void validate(Double value, String name) throws CommonException {
		if (value == null) {
			throw ExceptionUtils.create(CommonCodeEnum.VALIDATE_ERROR, name + "不能为空");
		}
	}

	public static void validate(String[] values) throws CommonException {
		if (values == null) {
			throw ExceptionUtils.create(CommonCodeEnum.VALIDATE_ERROR);
		} else {
			String[] var1 = values;
			int var2 = values.length;

			for (int var3 = 0; var3 < var2; ++var3) {
				String value = var1[var3];
				if (Tools.isBlank(value)) {
					throw ExceptionUtils.create(CommonCodeEnum.VALIDATE_ERROR);
				}
			}

		}
	}

	public static void validate(String[] values, String name) throws CommonException {
		if (values == null) {
			throw ExceptionUtils.create(CommonCodeEnum.VALIDATE_ERROR, name + "不能为空");
		} else {
			String[] var2 = values;
			int var3 = values.length;

			for (int var4 = 0; var4 < var3; ++var4) {
				String value = var2[var4];
				if (Tools.isBlank(value)) {
					throw ExceptionUtils.create(CommonCodeEnum.VALIDATE_ERROR, name + "不能为空");
				}
			}

		}
	}

	static {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		log.debug(" >>> [fast-common-web.meditrusthealth.com] >>> validator inited");
	}
}
