import java.util.ArrayList;
import java.util.List;

/**
 * 
 *  @author  YangWenjun
 *  @date   2018年10月4日  基础学习
 *  @description          泛型的使用通常会结合反射 ，作为项目中工具类，或者更高层次的基类。 所以对泛型的理解是建立在对工具类理解之上的再抽象。
 *                                 1.是什么
 *                                                 JDK1.5      参数化类型，形参参数化 （类比与形参）   也就是说所操作的数据类型被指定为一个参数   
 *                                                扩展： 
 *                                                      1.Java中类型：Type   Class（类）是Java对现实对象的抽象，而Type是对Java语言对象的抽象。
 *                                                      2.注意区分类型（Type）与类（Class）的区别，这里Class是Type的一种，而像数组、枚举等“类型”是相对于Class来说。
 *                                                      3.type直接子接口
 *                                                                             1.ParameterizedType： 表示一种参数化的类型，比如Collection，即普通的泛型。 
																				2.TypeVariable：是各种类型变量的公共父接口，就是泛型里面的类似T、E。 
																				3.GenericArrayType：表示一种元素类型是参数化类型或者类型变量的数组类型，比如List<>[]，T[]这种。 
																				4.WildcardType：代表一种通配符类型表达式，类似? super T这样的通配符表达式。

 *                                                                      子类  
 *                                                                             1.Class，代表着类型中的原始类型以及基本类型
 *                                                       4.type类型是随着  泛型 的出现而出现的。扩展了整个数据类型。
 *                                                         以上内容内容详见：https://blog.csdn.net/a327369238/article/details/52621043      /  本地：D:\Data\Java\泛型
 *                                                         
 *                                      -------------------------
 *                                                      1.Java中Class  和 enum ,  interface ,
 *                                                           public enum   Enumtest{}
 *                                                           public interface  interfaceTest{}
 *                                                           二者都是：特殊的Class  class对应现实事物抽象    class实例对应现实实例对象      而作为特殊的class的interface定义了事物的准则   enum定义了事物的分类  这些
 *                                                                           都是在class基础上的包装。 
 *                                 2.为什么 场景
 *                                                      1.集合   Collection<T>          检查 - 安全 
 *                                                      2.工具类   简化开发，且能很好的保证代码质量。
 *                                 3.如何做
 *                                                      1.申明
 *                                                                1.泛型类               public Class  Test1<T,E>{ 
 *                                                                                                    public T  t ;
 *                                                                                                    public E  e ;
 *                                                                                                    
 *                                                                                                    publc Test1(T t ,E e){
 *                                                                                                        this.t = t ;
 *                                                                                                        this.e  = e ;
 *                                                                                                    }
 *                                                                                                    ....
 *                                                                                                    初始化/方法定义  <---使用T / E的方法   Object中的方法
 *                                                                                                                
 *                                                                                             }
 *                                                                                      占位符 ，类创建时传入 / 占位符可以在整个类中使用，除了“静态方法”/ 泛型的类型参数只能是类类型，不能是简单类型。/不能对确切的泛型类型使用instanceof操作。如下面的操作是非法的，编译时会出错。
 *                                                                2.泛型接口
 *                                                                                            public interface Generator<T> {
																										    public T next();
																										}      
																					      各种类的生产器      
																					      
																					      当实现泛型接口的类，未传入泛型实参时：class FruitGenerator<T> implements Generator<T>{...}
																					      当实现泛型接口的类，传入泛型实参时：   public class FruitGenerator implements Generator<String> {
																																					    private String[] fruits = new String[]{"Apple", "Banana", "Pear"};		//实现规则中需要的元素																																		
																																					    @Override
																																					    public String next() {
																																					        Random rand = new Random();           //自定义“实现类”中的“实现规则”。
																																					        return fruits[rand.nextInt(3)];
																																					    }
																																					}
																					   ****定义一个生产器实现这个接口,虽然我们只创建了一个泛型接口Generator<T>. 但是我们可以为T传入无数个实参，形成无数种类型的Generator接口。***

 *                                                                3.泛型方法
																					 *     1）public 与 返回值中间<T>非常重要，可以理解为声明此方法为泛型方法。
																					 *     2）只有声明了<T>的方法才是泛型方法，泛型类中的使用了***泛型的成员方法并不是泛型方法***。
																					 *     3）<T>表明该方法将使用泛型类型T，此时才可以在方法中使用泛型类型T。
																					 *     4）与泛型类的定义一样，此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型。
																					public <T> T genericMethod(Class<T> tClass)throws InstantiationException ,	 IllegalAccessException{
																					             T instance = tClass.newInstance();
																					              return instance;
																					  泛型类中同样可以包含泛型方法
																					  泛型方法和泛型类均可以是同一T ，但是二者代表的类可以是不同的。
                                                              
 *                                                      2.通配  -  窄化  - 赋值  - 可变参数  - 静态方法 - 泛型数组
 *                                                                  1.  ？
 *                                                                      public void showKeyValue1(Generic<?> obj){      类型通配符一般是使用？代替具体的类型实参，注意了，此处’？’是类型实参，而不是类型形参 当操作类型时，不需要使用类型的具体功能时，只使用Object类中的功能。
 *                                                                      ？通常会限定   <? super A> 限定传入值必须是A或者A的父类
 *                                                                      这里的？和前面泛型中的T E区别在哪里，为社么不用T E ?
 *                                                                      
 *                                                                  2. 窄化 - 上下边界控制
 *                                                                                  public <T extends Number> T showKeyName(Generic<T> container){               //无论是类还是方法泛型窄化都和申明放在一起。 //泛型方法中方法参数
 *                                                                                                                                                                                                           一定作为了泛型入口，所以一定也包含泛型。可能是T，也可能是包T
 *                                                                  3.赋值   
 *                                                                                  显示赋值    调用时赋值   testInstance.<Student>f1();
 *                                                                                  隐式赋值    在返回值中控制
 *                                                                  4.  可变参数
					 *                                                             public <T> void printMsg( T... args){
																											    for(T t : args){
																											        Log.d("泛型测试","t is " + t);
															                        printMsg("111",222,"aaaa","2323.4",55.55);
															         5.静态方法
															                       静态方法无法访问类上定义的泛型；如果静态方法操作的引用数据类型不确定的时候，必须要将泛型定义在方法上。
															         6.泛型数组
															                       不能创建一个确切的泛型类型的数组
															                       不可以：List<String>[] ls = new ArrayList<String>[10];  可以： List<?>[] ls = new ArrayList<?>[10];  List<String>[] ls = new ArrayList[10];
								4.对比 原理  改进 
 *                                              1.泛型方法能使方法独立于类而产生变化，以下是一个基本的指导原则：无论何时，如果你能做到，你就该尽量使用泛型方法。也就是说，如果使用泛型方法将整个类泛型化，
 *                                                      那么就应该使用泛型方法。另外对于一个static的方法而已，无法访问泛型类型的参数。所以如果static方法要使用泛型能力，就必须使其成为泛型方法。
 *                                              2.未给泛型传值时，默认是object , 窄化后则为extens后面的
 *                                              
 *    
 *  @ TODO
  */


/**
 * 
								  以下内容是bbsp baseDao中定义的curd基础方法以便类比实现           --> 哪些curd是基本的，base中又应该定义哪些方法算作是基础呢？
								  1.curd  saveOrUpdate()
								  2.增加list  删除list  增删操作参数为                 ---具体业务结合方法---
								 3.	--方法的实现参数可以以“最终的实操对象参数为模板”为依据，前面进行校验......	
								   ---假设写的是个固定类  返回/参数/处理逻辑   -->再将固定类参数化 泛型	（再考虑申明哪些参数 几个参数）				  
										  public  <T,PK extends Serializable> T  getEntity(final Class<T> entity, PK id) throws Exception{
										    if(Branch.class != entity && id instanceof java.lang.String){
										    id=Long.valueOf(id.toString())}
		            	                   return (T)this.getHibernate().get(entiry,id);}
		            	                   
		            	                   public  <T,PK extends Serializable> PK saveEntity(final T entity) {
		            	                       return (PK) this.getHibernateTemplate.save(entity);
		            	                   }
		            	                   
		            	                 public  <T> void  saveOrUpdateAll(final Collection<T>  entity) {
		            	                       return (PK) this.getHibernateTemplate.saveOrUpdateAll(entity);
		            	                   }
            	 
 */

/**
 *   2019-1-1 对泛型使用  实现前台String[] 转为int[] 并扩展为工具类
 */
public class MainClass {
	public static <T> void  convert( T source , T  destination){ //2.泛型的声明必须使用<T> 3.这里的泛型不都是T 为什么可以接受不同类型的参数
		//check                     //4.可以声明多个泛型变量
		//securecopy
		System.out.println("abc" + source.toString()+destination.toString());
		//return  ;
	}
	//public static <T，E> void  convert1( T source , E  destination){ //5.这里要求的是类型参数，所以这样可能E进来值
	public static <T> void  convert1( T source , Class  desc){  //这样就控制了
		System.out.println("abc" + source.toString()+desc.getName());
		//return  ;
	}

	public static <T> void  convert2( T[] source , Class  desc){   //定义分割符String splitType 胡扯 数组没有分割符
		//source.                     //加入未知类型的状态形式 这种状态如x.class 如 x[] 他们同样还是对象还是Object T
		System.out.println("abc" + desc.getName());
		//return  ;
	}

	public static <T> void  convert3( T[] source , Class  desc) throws Exception{
		// 更具反射创建对应的对象
		// System.out.println(desc.newInstance());
		System.out.println(desc.getName());
		//desc.newInstance();  //Interger就不提供
		System.out.println(desc.getDeclaredConstructor(int.class).newInstance(1));//int.class指的是指定构造器固定参数  //int integer独立不可公用
		System.out.println("abc" );                 //注意有 参数                     //注意这里必须塞入值
		//return  ;
	}

	public static <T> void  convert4( T[] source , Class  desc) throws Exception{
		// System.out.println(desc.getDeclaredConstructor(int.class).newInstance(1));
		//更具反射创建对应的构造器和  --有没有无参构造器 比如String就有 Integer就没有
		// if(desc instanceof  Integer.class){
		if(desc == Integer.class){  //不是使用instatnceof？ 是否可以使用getType？？
			System.out.println("int.class");}

		else
		{System.out.println("else");}
	}
	public static <T>  T[]  convert5( T[] source , Class  desc) throws Exception{
		List list  =  new ArrayList();
		if(desc == Integer.class){
			System.out.println("int.class");
			System.out.println(desc.getDeclaredConstructor(int.class).newInstance(Integer.parseInt("1122")));
			// List list = null ;空指针
			// List  list  =  new ArrayList();放大域
			for (T t:source) {
				//desc.getDeclaredConstructor(int.class).newInstance(Integer.parseInt(t.toString())).getClass() 验证类型是否转化
				// ;  这里设置临时变量更复杂不如直接塞入
				list.add(desc.getDeclaredConstructor(int.class).newInstance(Integer.parseInt(t.toString())));
			}
			// return (T[]) list.toArray();均为分支所以使用最后的return  每修改一节就测试一下
		}
		else
		{
			System.out.println("else");
		}
		// return (T[])new Object();  //为了不报错  注意这里的转换明白泛型和[] .class关系
		return (T[]) list.toArray();
	}


	public static <T>  T[]  convert6( T[] source , Class  desc) throws Exception{
		List  list  =  new ArrayList();

		if(desc == Integer.class){  //不提供无参构造器方法 byte-....Long ----包装器类型均不提供 从String才开始允许有无参构造器的。
			for (T t:source) {                                                          //String作为桥梁
				list.add(desc.getDeclaredConstructor(int.class).newInstance(Integer.parseInt(t.toString())));
			}
		}
        /*else if(){

        }
        */else
		{
			System.out.println("else");
		}
		return (T[]) list.toArray(); //我们假设T[]就是一个String[] 增加判断操作，当然细致考虑特殊地点比如这里的构造方法
	}



	public static void main(String[] args) throws Exception {
		String[] a = new String[]{"1","2","3"};
		Integer[] b = new Integer[]{5,6,7};
		String c = "c";
		//  int d  = 1;
		Integer i = 1 ;
		// convert1( c , i ) ; //  1.String - int 有问题吗？ 不是 方法定义都是Object 两个参数必须类型相同吗？？都是object就不能代表不同值吗？
		//convert1( c , Integer[].class ) ;
		//convert2( a , Integer[].class ) ;
		//convert3( b , Integer.class ) ;  //Caused by: java.lang.NoSuchMethodException: [Ljava.lang.Integer;.<init>()是不是不可以初始化[] 因为他不是一个类型 --错误不是这个原因
		//将Integer改为String可以成功convert3( a , String.class ) ; 可以 --》为什么
		//因为newInsatance调用无参构造器，而interger没有提供。--coun https://blog.csdn.net/qq_28325291/article/details/79342334
		//所以修改conver3方法中反射的部分
		/**convert3( b , String.class ) ;  --》将int - > interger
		 中对泛型的认识提升* Error:(43, 9) java: 无法将类 util.convertOfType.convertArray中的方法 convert3应用到给定类型;
		 需要: T[],java.lang.Class
		 找到: int[],java.lang.Class<java.lang.String>
		 原因: 推论变量 T 具有不兼容的限制范围
		 等式约束条件: int
		 上限: java.lang.Object
		 */
		//convert4( b , String.class ) ;
		//System.out.println(convert5(b, Integer.class));
		System.out.println(convert6(b, Integer.class));


	}
	
	
}
/**
 * effective java中对泛型*/