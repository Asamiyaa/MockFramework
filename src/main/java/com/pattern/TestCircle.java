package com.pattern;

/**
 * 
 *  @author  YangWenjun
 *  @date   2018年10月6日
 *  @description
 *                      fore  / for  / 迭代器   
 *                      递归
 *                      
 *                      1.是什么
 *                                                ***相同的处理逻辑，重复执行，直至条件满足***
 *                      2.为什么
 *                                                编程本身就是将逻辑模板抽象实现重复使用，循环则是代码逻辑的重复使用
 *                      3.如何做
 *                                                1. 流结构图      嵌套(for-for   for-if  )     return/break/continue
																				             |
																				            i  init
																				             |
																			--  --->< 判断条件 > --否-----------------------------------
																			 |                |是                                                                     |
																	      goto             |
																			|             代码块(***注意代码块中可能有for结构/if结构)  - (内外层循环之间可能存在联系 比如 j=i+1 ......) 
																		    | 	               |
																			 - --   - --   i++  (循环必有条件变化，避免死循环)                    |
																				                 （一定是条件不满足为止 ，所以这里没有通路）   |
																				                                                                                        |
																				              ---------------------------------------------- -------                                                                                
																				              |
																				           .....                                             
 *                      4.对比 原理  改进  
 *                                               1.for  / fore / 迭代器
 *                                                    1.相同点：遍历数组和集合
 *                                                                     for(int i = 0 ; i<20 ; i++){}  
 *                                                                     for (Student s : stuList ){}
 *                                                                     Iterator ite = list.interator()               while(ite.hasNext()){ ite.next() ......}         等价于：for (; iterator.hasNext();) 注意这里需要判断list是否未null   如果来自return          
 *                                                                                                         ***   且有new ArrayList()则list一定不为null ,只是内容未空 ，这样调用没有问题。如果没有初始化，那么会报NullPointException
 *                                                     2.不同点
 *                                                                   效率：ArrayList，用三种方式遍历的速度是for>Iterator>foreach，但基本上属于同一个速度级别； 采用ArrayList对随机访问比较快，而for循环中的get()方法，采用的即是随机访问的方法
                                                                               LinkedList，则三种方式遍历的差距很大了，用for遍历的效率远远落后于foreach和Iterator，Iterator>foreach>>>for； 采用LinkedList则是顺序访问比较快，iterator中的next()方法，采用的即是顺序访问的方法，
                                                                               hashset  / treeset  /hashmap /treemap 底层来自  hash表=数组+链表
                                                                      场景：
                                                                       从数据结构角度分析,for循环适合访问顺序结构,可以根据下标快速获取指定元素.而Iterator 适合访问链式结构,因为迭代器是通过next()和Pre()来定位的.可以访问没有顺序的集合.
																	   而使用 Iterator 的好处在于可以使用相同方式去遍历集合中元素，而不用考虑集合类的内部实现（只要它实现了 java.lang.Iterable 接口），如果使用 Iterator 来遍历集合中元素，一旦不再使用 List 转而使用 Set 来组织数据，那遍历元素的代码不用做任何修改，如果使用 for 来遍历，那所有遍历此集合的算法都得做相应调整,因为List有序,Set无序,结构不同,他们的访问算法也不一样.
                                                                   
                                                                   总结：数组 /arrayList - for/fore     linklist -  iterater   set - iterater   iterater可以遍历所有的实现了iterater接口的而无须关注内部分类

 *                                                                         
 *                                               2.递归
 *                                                      1.可以相互转化的
 *                                                      2.栈 主要是用来存放栈帧的，每执行一个方法就会出现压栈操作，所以采用递归的时候产生的栈帧比较多，递归就会影响到内存，非常消耗内存，而使用for循环就执行了一个方法，压入栈帧一次，只存在一个栈帧，所以比较节省内存。
 *                                                          method(count){
 *                                                          count--
 *                                                          if(count <=0 ) return ;
 *                                                          xxxxxxx    满足条件count>0进行的操作
 *                                                          method(count);
 *                                                          }
 *                                               3.题：https://blog.csdn.net/u014042066/article/details/78317325
 *                                               4.维度
 *                                                        for行列？
 *  @todo  TODO
 */
public class TestCircle {
  
}
