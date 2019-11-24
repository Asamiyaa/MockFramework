package util.enum_use;

/**
 * 
 *  @author  YangWenjun
 *  @date   2018年10月4日
 *  @description       JDK1.5   enum作为业务逻辑中“约定”的载体，是完成前台输入，业务逻辑处理，后台落地的流转标识。
 *                                 
 *                                 是什么
 *                                                  --->   前台输入<s:select   list{aa:aa ,bb:bb .....}  
 *                                                         --->  很多是业务逻辑控制不可能都来自前台， 业务中使用枚举  
 *                                                                   --->  反显 是在业务层塞值通过工具类(在枚举类中获取码值对应的翻译值 - 将所有枚举统一放到一起其实也用到了cache)+get()(这里还不能将默认的get修改 应该将需要前台展示的值标识出来  比如 edType
 *                                                                           需要前台展示   则创建cdTypeIng属性和get方法 在前台展示该值  。。) 
 *                                                                            vs   
 *                                                                           cache(spring 加载map ) + 自定义标签(前台转化 )
 *                                 为什么
 *                                 如何做
 *                                                    七大用法；
 *                                                             1.常量      无需使用public static fianl 修饰
 *                                                             2.switch   .....method() {                                 
 *                                                                                 switch(color){
 *                                                                                    case(RED):
 *                                                                                    .......
 *                                                                                    break;
 *                                                                                    case():
 *                                                                                    ...
 *                                                                                 }
 *                                                                                } 
 *                                                              3.向枚举中添加新方法   问题：重构后的enum2,使用了name ，index 为什么这里继承自父类的toString方法不起作用呢？toString中修改返回个格式
 *                                                                                                getName(int index)
 *                                                              4.覆盖枚举的方法  toString()
 *                                                              5.实现接口
 *                                                              6.使用接口组织枚举         获取方式：    System.out.println(Food.Coffee.BLACK_COFFEE);  使用接口实现更高层次抽象
 *                                                              7.枚举集合  EnumSet  EnumMap
 *                                                              
 *                                                              
 *                                 对比 原理  改进 
 *                                                 常量类   接口     枚举使用对比
 *                                                      常量类：
 *                                                              常量作为参数时，是String，int等弱类型，开发人员可以传入没有在常量接口里定义的值，这个问题无法通过编译器发现。
																由于开发人员可以直接写常量，所以不能用==对比，只能用equals对比，不能优化性能。
																开发人员没有参考资料时，不可能知道某个int类型的参数到底应该赋什么内容。注释不规范，通过enum强制记录descripttion
																编译时，是直接把常量的值编译到类的二进制代码里，常量的值在升级中变化后，需要重新编译引用常量的类，因为里面存的是旧值。
																如果常量类的构造器不私有，无法限制开发员继承/实现接口，开发员能够在子接口里继续添加常量.而这些常量可能得不到祖先层的支持。
                                                        接口：不考虑单独作为字典工具
                                                        枚举：
                                                                默认继承自Enum  实现了Compare接口    
 *                                                 
 *                                                 
 *  
 *  
 *  
 *  @todo  TODO
 */


	public class EnumLearn {
				 public static void main(String[] args) {
					      System.out.println(Constant.RED);
					      System.out.println('a' == 'a');
					      System.out.println((EnumTest.RED.getValue()  ==1) == false );
					      System.out.println(EnumTest.RED.getDesc() == "红色");  //枚举可以使用== 判断 即使是string  而无须equals    
					      String s  =  EnumTest2.GREEN .toString();
					      System.out.println(EnumTest2.getNameByIndex(1));
					      
					      System.out.println(Food.Coffee.BLACK_COFFEE);
				}
}
	
     enum  EnumTest{ 
    	        //  RED   	                           //输出RED 其实是没有意义的，无论作为码值还是反显(desc)
    	        // RED(1) , black(2) ;             //EnumTest的一个实例  相当于自己new了自己
    	 RED(1,"红色") , black(2,"黑色") ;  //因为真实项目要比这个复杂的多，不可能一个颜色单词就可以表示，比“清算结算排队状态”这样用一个单词是无法表示的。所以不能将“解释权”放在单词上。
    	                                                   //这个时候red可能在项目中就是constant_903..代号，1：存入数据库  码值     红色：翻译值          
    	 public int num ;
    	  public String desc ;
    	  
    	  EnumTest(int num , String desc){
    		  this.num = num ; 
    		  this.desc  = desc ;
    	 }
    	  
    	  public int getValue(){
    		  return num ;
    	  }
    	  public  String  getDesc(){
      		return  desc ;
     }
     }    
       
     /**
       * enum类通常定义描述(翻译 反显)和index(码值) 。所以将继承自Enum利用最大化   enum已经重写了toString( return name)
      */
     enum  EnumTest2{ 
    	 RED("红色",1) , GREEN("绿色",2);    // ,    最后；
    	 
    	 private String name ;
    	 private  int   index ;
    	 
    	 private EnumTest2(String name , int index){
    		   this.name = name ;
    		   this.index = index ;
    	 }
    	 
    	 public int getIndex(){         //无须提供set  和 name的get/set     构造方法私有化
    		 return index;
    	 }
    	 
    	 //values 获得实例
    	 public  static String  getNameByIndex(int index){
    	 //现在没有index和name的对应关系  -> 创建   1 .map    2.index-对象 -对象的name值
    		    for (EnumTest2  t : EnumTest2.values()) {
					if(t.index == index){
						return t.name;
					}
				}
    		 return  null ;       //当写了方法时，直接就写return null ,避免到最后忘了
    				 
    	 }
    	 
    	 public String toString() {
             return this.index + "_" + this.name;
         }

     }    
     
 //-----------使用接口组织枚举         获取方式：    System.out.println(Food.Coffee.BLACK_COFFEE);  使用接口实现更高层次抽象------------
      interface Food {        
    	                     enum Coffee implements Food {            BLACK_COFFEE, DECAF_COFFEE, LATTE, CAPPUCCINO        }         
    	                     enum Dessert implements Food {            FRUIT, CAKE, GELATO        }
     }

   
     
 
     class   Constant{    	 //常量
    	 public static final int  RED  =1  ;   	 
     }
