package com.perfree.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private final static String SEPARATOR = System.getProperty("file.separator");
    /**
     * 首字母转大写
     *
     * @param s 字符串
     * @return String
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转小写
     *
     * @param s 字符串
     * @return String
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 判断字符串是否全部大写
     *
     * @param s 字符串
     * @return boolean
     */
    public static boolean isUpperCase(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将包名转换为路径
     *
     * @param packageStr 包名
     * @return String
     */
    public static String packageNametoDirStr(String packageStr) {
        String[] split = packageStr.split("\\.");
        StringBuilder packageName = new StringBuilder();
        for (String packageSplit : split) {
            packageName.append(packageSplit).append(SEPARATOR);
        }
        return packageName.toString();
    }

    /**
     * 首字母大写(处理下划线)
     *
     * @param s 字符串
     * @return String
     */
    public static String strUpperFirst(String s) {
        StringBuilder result = new StringBuilder();
        if (s.contains("_")) {
            String[] splits = s.split("_");
            for (String split : splits) {
                result.append(StringUtils.toUpperCaseFirstOne(split.toLowerCase()));
            }
        } else {
            if (StringUtils.isUpperCase(s)) {
                result.append(StringUtils.toUpperCaseFirstOne(s.toLowerCase()));
            } else {
                result.append(StringUtils.toUpperCaseFirstOne(s));
            }
        }
        return result.toString();
    }

    /**
     * 首字母小写(处理下划线)
     *
     * @param s 字符串
     * @return String
     */
    public static String strLowerFirst(String s) {
        StringBuilder result = new StringBuilder();
        if (s.contains("_")) {
            String[] splits = s.split("_");
            result.append(splits[0].toLowerCase());
            for (int i = 1; i < splits.length; i++) {
                result.append(StringUtils.toUpperCaseFirstOne(splits[i].toLowerCase()));
            }
        } else {
            if (StringUtils.isUpperCase(s)) {
                result.append(s.toLowerCase());
            } else {
                result.append(s);
            }
        }
        return result.toString();
    }

    /**
     * 将字符穿中所有的换行符转为逗号
     * @param str 字符串
     * @return String
     */
    public static String replaceWrap(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("[\r\n]");
            Matcher m = p.matcher(str);
            dest = m.replaceAll(",");
        }
        return dest;
    }
}
