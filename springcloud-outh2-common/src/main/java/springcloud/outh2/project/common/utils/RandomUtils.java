package springcloud.outh2.project.common.utils;


import java.util.Random;

public class RandomUtils {
    public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String numberChar = "0123456789";

    public RandomUtils() {
    }

    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        for(int i = 0; i < length; ++i) {
            sb.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
        }

        return sb.toString();
    }

    public static String generateNumber(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        for(int i = 0; i < length; ++i) {
            sb.append("0123456789".charAt(random.nextInt("0123456789".length())));
        }

        return sb.toString();
    }

    public static String generateMixString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        for(int i = 0; i < length; ++i) {
            sb.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
        }

        return sb.toString();
    }

    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    public static String generateZeroString(int length) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            sb.append('0');
        }

        return sb.toString();
    }

    public static String toFixedLengthString(long num, int fixedlength) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixedlength - strNum.length() >= 0) {
            sb.append(generateZeroString(fixedlength - strNum.length()));
            sb.append(strNum);
            return sb.toString();
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixedlength + "的字符串发生异常！");
        }
    }

    public static String toFixedLengthString(int num, int fixedlength) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixedlength - strNum.length() >= 0) {
            sb.append(generateZeroString(fixedlength - strNum.length()));
            sb.append(strNum);
            return sb.toString();
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixedlength + "的字符串发生异常！");
        }
    }

    public static void main(String[] args) {
        System.out.println(generateString(6));
        System.out.println(generateMixString(15));
        System.out.println(generateLowerString(15));
        System.out.println(generateUpperString(15));
        System.out.println(generateZeroString(15));
        System.out.println(toFixedLengthString(123, 15));
        System.out.println(toFixedLengthString(123L, 15));
        System.out.println(generateNumber(6));
    }
}
