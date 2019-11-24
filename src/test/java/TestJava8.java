import java.util.Arrays;
import java.util.List;

/**
 * 这个版本包含语言、编译器、库、工具和JVM等方面的十多个新特性。
 * https://www.cnblogs.com/xingzc/p/6002873.html
 *
 */

public class TestJava8 {
    /**
     * lambda  相对于原来有啥好处呢？
     *      1.允许我们将函数当成参数传递给某个方法.原来只能只能使用匿名内部类代替Lambda表达式
     *      2.类型推断  这里t 就是String类型  .forEach( ( String e ) -> System.out.println( e ) )
     *      3. Lambda表达式可以引用类成员和局部变量（会将这些变量隐式得转换成final的） final String separator = ",";
     *      4.Lambda表达式有返回值，返回值的类型也由编译器推理得出。如果Lambda表达式中的语句块只有一行，
     *          则可以不用使用return语句
     *       5.Lambda的设计者们为了让现有的功能与Lambda表达式良好兼容，考虑了很多方法，于是产生了函数接口这个概念。函数接口指的是只有一个函数的接口，这样的接口可以隐式转换为Lambda表达式。java.lang.Runnable和java.util.concurrent.Callable是函数式接口的最佳例子。在实践中，函数式接口非常脆弱：只要某个开发者在该接口中添加一个函数，则该接口就不再是函数式接口进而导致编译失败。为了克服这种代码层面的脆弱性，并显式说明某个接口是函数式接口，Java 8 提供了一个特殊的注解@FunctionalInterface（Java 库中的所有相关接口都已经带有这个注解了），举个简单的函数式接口的定义：
     @FunctionalInterface
     public interface Functional {
     void method();
     }
     不过有一点需要注意，默认方法和静态方法不会破坏函数式接口的定义，因此如下的代码是合法的。

     @FunctionalInterface
     public interface FunctionalDefaultMethods {
     void method();

     default void defaultMethod() {
     }
     }
     */
    public static void main(String[] args) {
        //java.util.function.Consumer 参数就是可以lambda
        Arrays.asList(new String[]{"a","b","c"}).forEach(t -> {
            System.out.println(t);
            System.out.println(t);
        });

        String separator = ",";
        Arrays.asList( "a", "b", "d" ).forEach(
                ( String e ) -> System.out.print( e + separator ) );

        //1.如何接受返回的数据
        //2.lambda 分组 https://blog.csdn.net/make1828/article/details/54632786
        // 3.集合处理 https://www.cnblogs.com/JoeyWong/p/9600109.html
        Arrays.asList( "a", "e", "d" ).sort( (e1, e2) -> {
            return e1.compareTo(e2);
        });

        //Supplier接口和Consumer接口
        //这两个接口方法很少,但是实用性很强,并且可以看成是一对逆运算.一个作为生产工厂生产对象(Supplier), -- 有啥用？
        // 另一个作为消费者去消费对象(Consumer).
        //由于JVM上的默认方法的实现在字节码层面提供了支持，因此效率非常高。默认方法允许在不打破现有
        // 继承体系的基础上改进接口。该特性在官方库中的应用是：给java.util.Collection接口添加新方法，如stream()、parallelStream()、forEach()和removeIf()等等


    }



}
