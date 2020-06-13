package myFramework.myMybatis;

/**
 * @author YangWenjun
 * @date 2020/6/8 8:55
 * @project MockFramework
 * @title: Main
 * @description:
 *          0.基础实现
 *                  - jdbc 6步走  - 方法重构 - 工具类重构 - 类重构 - 框架 - 哪些拆解为属性，哪些拆解为类，哪些方法放到哪些类中，是依赖还是组合 是否过度设计？结构如何划分合理/
 *                  - 基本功能实现
 *                               - 封装数据源(sqlsession-sesqlssesionbuilder factory)+分离接口实现(解析xml namesapce select等标签解析 、sql预编译，返回值。) - 初始化加载
 *                               - 调用时查找执行器  namesapce-id-塞值 excutor执行
 *                               - 返回值封装。
 *                                      1.搭起大概的结构后，对于xml解析，路径扫描...都可以先new假对象返回，对于这么模块只是可以专项突破。既然是框架就要安全，完整
 *
 *                               - 动态sql
 *                               -
 *
 *                     设计类结构
 *                   - 安全...
     *               - 领域知识：类型转换器 超时 id生成返回 返回对象关联  session参数-线程
 *                   - 设计模式
 *                   - 与spring融合

 *
 */
public class Main {



}
