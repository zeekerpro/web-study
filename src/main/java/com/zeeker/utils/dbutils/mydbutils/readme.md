## 自建数据库框架

#### JdbcUtil
提供和数据库交互的方法
   
#### JdbcConnectionPool
自定义数据库连接池

#### DbNameConveter
提供转化方法：
* 类名 -> 数据库表名
* 类成员名 -> 表字段名

#### ResultSetHandler
接口，结果集处理器
    
   BeanListHandler 是 ResultSetHandler 实现类，实现了将数据库查询结果
   转为Bean对象list