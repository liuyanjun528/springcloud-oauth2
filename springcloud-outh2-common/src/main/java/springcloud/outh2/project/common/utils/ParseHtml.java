package springcloud.outh2.project.common.utils;

/**
 * @author Sir_小三
 * @date 2019/12/31--22:26
 */
public class ParseHtml {

    public static String parseHtml(String html) {

        if(html == null || html == "") {
            return  "小编正在忙着收集消息哦";
        }else {

                /*
                 * <.*?>为正则表达式，其中的.表示任意字符，*?表示出现0次或0次以上，此方法可以去掉双头标签(双头针对于残缺的标签)
                 * "<.*?"表示<尖括号后的所有字符，此方法可以去掉残缺的标签，及后面的内容
                 * " "，若有多种此种字符，可用同一方法去除
                 */
                html = html.replaceAll("<.*?>", " ").replaceAll("", "");
                html = html.replaceAll("<.*?", "");
                return html;

        }
    }
}
