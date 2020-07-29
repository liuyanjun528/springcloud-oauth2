package springcloud.outh2.project.common.utils;


import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class StringUtils {
    public static final int INT_NEED = 4;
    public static final int SCALE_NEED = 3;
    public static final int NOT_NEED = 2;
    public static final int ALL_NEED = 1;
    public static int defaultType = 3;

    public StringUtils() {
    }

    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (str != null && str.length() != 0) {
            StringBuffer buf = new StringBuffer(str.length());
            if (capitalize) {
                buf.append(Character.toUpperCase(str.charAt(0)));
            } else {
                buf.append(Character.toLowerCase(str.charAt(0)));
            }

            buf.append(str.substring(1));
            return buf.toString();
        } else {
            return str;
        }
    }

    public static String defaultIfEmpty(String str, String defaultString) {
        if (str == null) {
            return defaultString;
        } else {
            return str.trim().length() == 0 ? " " : str;
        }
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Date) {
            return obj instanceof Timestamp ? DateUtils.toDateTimeStr((Timestamp) obj) : DateUtils.toDateStr((Date) obj);
        } else {
            return obj.toString();
        }
    }

    public static String getFormat(int intLength, int scaleLength) {
        return getFormat(intLength, scaleLength, defaultType);
    }

    public static String getFormat(int intLength, int scaleLength, int type) {
        StringBuffer formatStr = new StringBuffer(intLength + scaleLength + 1);
        int i;
        if (type == 4 || type == 1) {
            for (i = 0; i < intLength - 1; ++i) {
                formatStr.append("0");
            }
        }

        formatStr.append("0");
        if (scaleLength > 0) {
            formatStr.append(".");
            if (type != 3 && type != 1) {
                for (i = 0; i < scaleLength; ++i) {
                    if (i == 0) {
                        formatStr.append("0");
                    } else {
                        formatStr.append("#");
                    }
                }
            } else {
                for (i = 0; i < scaleLength; ++i) {
                    formatStr.append("0");
                }
            }
        }

        return formatStr.toString();
    }

    public static String toString(Object obj, int filedLength, int scaleLength) {
        int intLength = filedLength;
        if (scaleLength > 0) {
            intLength = filedLength - scaleLength - 1;
        }

        if (obj == null) {
            return null;
        } else {
            DecimalFormat format;
            if (obj instanceof Integer) {
                if (((Integer) obj).intValue() < 0) {
                    --intLength;
                }

                format = new DecimalFormat(getFormat(intLength, scaleLength));
                return format.format((Integer) obj);
            } else if (obj instanceof Long) {
                if (((Long) obj).longValue() < 0L) {
                    --intLength;
                }

                format = new DecimalFormat(getFormat(intLength, scaleLength));
                return format.format((Long) obj);
            } else if (obj instanceof Float) {
                if (((Float) obj).floatValue() < 0.0F) {
                    --intLength;
                }

                format = new DecimalFormat(getFormat(intLength, scaleLength));
                return format.format((Float) obj);
            } else if (obj instanceof Double) {
                if (((Double) obj).doubleValue() < 0.0D) {
                    --intLength;
                }

                format = new DecimalFormat(getFormat(intLength, scaleLength));
                return format.format((Double) obj);
            } else if (obj instanceof BigDecimal) {
                if (((BigDecimal) obj).doubleValue() < 0.0D) {
                    --intLength;
                }

                format = new DecimalFormat(getFormat(intLength, scaleLength));
                return format.format((BigDecimal) obj);
            } else if (obj instanceof Date) {
                return obj instanceof Timestamp ? DateUtils.toDateTimeStr((Date) obj) : DateUtils.toDateStr((Date) obj);
            } else {
                return obj.toString();
            }
        }
    }

    public static String toString(Object obj, int filedLength, int scaleLength, int type) {
        int intLength = filedLength;
        if (scaleLength > 0) {
            intLength = filedLength - scaleLength - 1;
        }

        if (obj == null) {
            return null;
        } else {
            DecimalFormat format;
            if (obj instanceof Integer) {
                if (((Integer) obj).intValue() < 0) {
                    --intLength;
                }

                format = new DecimalFormat(getFormat(intLength, scaleLength, type));
                return format.format((Integer) obj);
            } else if (obj instanceof Long) {
                if (((Long) obj).longValue() < 0L) {
                    --intLength;
                }

                format = new DecimalFormat(getFormat(intLength, scaleLength, type));
                return format.format((Long) obj);
            } else if (obj instanceof Float) {
                if (((Float) obj).floatValue() < 0.0F) {
                    --intLength;
                }

                format = new DecimalFormat(getFormat(intLength, scaleLength, type));
                return format.format((Float) obj);
            } else if (obj instanceof Double) {
                if (((Double) obj).doubleValue() < 0.0D) {
                    --intLength;
                }

                format = new DecimalFormat(getFormat(intLength, scaleLength, type));
                return format.format((Double) obj);
            } else if (obj instanceof BigDecimal) {
                if (((BigDecimal) obj).doubleValue() < 0.0D) {
                    --intLength;
                }

                format = new DecimalFormat(getFormat(intLength, scaleLength, type));
                return format.format((BigDecimal) obj);
            } else {
                return obj instanceof Date ? DateUtils.toDateStr((Date) obj) : obj.toString();
            }
        }
    }

    public static boolean hasContent(String s) {
        if (s == null) {
            return false;
        } else {
            return s.trim().length() != 0;
        }
    }

    public static String compress(String str) {
        try {
            return compressByte(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String compressByte(byte[] byteArray) {
        BufferedInputStream bis = null;
        GZIPOutputStream gzip = null;
        //修改处
        String var8 = null;
        ByteArrayOutputStream baos;
        try {
            int size = 1024;
            bis = new BufferedInputStream(new ByteArrayInputStream(byteArray));
            baos = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(baos);
            byte[] buffer = new byte[size];

            int len;
            while ((len = bis.read(buffer, 0, size)) != -1) {
                gzip.write(buffer, 0, len);
            }

            gzip.finish();
            byte[] bytes = baos.toByteArray();
//            String var8 = DatatypeConverter.printBase64Binary(bytes);
            var8 = DatatypeConverter.printBase64Binary(bytes);
//            return var8;
        } catch (Exception var22) {
            var22.printStackTrace();
            baos = null;
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException var21) {
                var21.printStackTrace();
            }

            try {
                if (gzip != null) {
                    gzip.close();
                }
            } catch (IOException var20) {
                var20.printStackTrace();
            }

        }

//        return baos;
        return var8;
    }

    public static String decompress(String data) {
        try {
            return new String(decompressByte(data), "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("null")
    public static byte[] decompressByte(String data) {
        GZIPInputStream gzip = null;

        ByteArrayOutputStream baos;
        try {
            int size = 1024;
            gzip = new GZIPInputStream(new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(data)));
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[size];

            int len;
            while ((len = gzip.read(buffer, 0, size)) != -1) {
                baos.write(buffer, 0, len);
            }

            byte[] var6 = baos.toByteArray();
            return var6;
        } catch (Exception var16) {
            var16.printStackTrace();
            baos = null;
        } finally {
            try {
                if (gzip != null) {
                    gzip.close();
                }
            } catch (IOException var15) {
                var15.printStackTrace();
            }

        }
        byte[] bytes = baos.toByteArray();
        return bytes;
//        return (byte[])baos;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static boolean isNotEmpty(String trim) {
        return trim != null && trim.length() != 0;
    }

    public static boolean isNotBlank(String... trim) {
        if (trim.length <= 0) {
            return false;
        } else {
            String[] var1 = trim;
            int var2 = trim.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                String s = var1[var3];
                if (s == null || s.length() == 0) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String removeLastLetter(String str) {
        return str != null && str.length() != 0 ? str.substring(0, str.length() - 1) : str;
    }

    public static String getFieldListFromJsonStr(String jsonStr, String fieldName) {
        List<String> fieldValues = new ArrayList<>();
        String regex = "/\"" + fieldName + "\":\".*?\"/";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(jsonStr);
        while (matcher.find()) {
            if (StringUtils.isNotEmpty(matcher.group().trim())) {
                fieldValues.add(matcher.group().trim());
            }
        }
        if (fieldValues != null && fieldValues.size() > 0) {
            return fieldValues.get(0);
        }
        return null;
    }

    /**
     * 从map中查询想要的map项，根据key
     */
    public static Map<String, String> parseMapForFilter(Map<String, String> map, String filters) {
        if (map == null) {
            return null;
        } else {
            map = map.entrySet().stream()
                    .filter((e) -> checkKey(e.getKey(), filters))
                    .collect(Collectors.toMap(
                            (e) -> (String) e.getKey(),
                            (e) -> e.getValue()
                    ));
        }
        return map;
    }

    /**
     * 通过indexof匹配想要查询的字符
     */
    private static boolean checkKey(String key, String filters) {
        if (key.indexOf(filters) > -1) {
            return true;
        } else {
            return false;
        }
    }

    public static String parseMapToStringForFilter(Map<String, String> map, String filters) {
        Map<String, String> map_ = parseMapForFilter(map, filters);
        if (map_ != null && map_.size() > 0) {
            Iterator it = map_.values().iterator();
            if (it.hasNext()) {
                return (String) it.next();
            }
        }
        return null;
    }

//    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<>();
//        map.put("松江区", "12333");
//        map.put("上海市", "1222");
//        map.put("北京市", "12");
//        Map mm = parseMapForFilter(map, "贵阳");
//        System.out.println(mm.values().iterator().next());
//    }
}
