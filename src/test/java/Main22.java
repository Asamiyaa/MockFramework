import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author YangWenjun
 * @date 2020/3/30 14:14
 * @project MockFramework
 * @title: Main22
 * @description:
 */
public class Main22 {

    private static Pattern compile = Pattern.compile("^\\d{4}|\\d{5}$");
    //更不能放到循环中。
    public static void main(String[] args) {

        Matcher matcher = compile.matcher("12345");
        Matcher matcher2 = compile.matcher("1234");

        System.out.println(matcher.matches());
        System.out.println(matcher2.matches());


    }
}
