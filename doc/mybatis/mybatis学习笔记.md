

###### Mybatis

## 2、第一个mybatis

### 2.1、搭建环境

创建一个纯净的maven项目删除src

### 2.2、配置文件内容

mybatis核心配置文件：mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--mybatis核心配置文件-->
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;uesUnicode=true&amp;characterEncoding=UTF-8"/>
                <property name="username" value="root"/>
                <property name="password" value="199969"/>
            </dataSource>
        </environment>
    </environments>
    <!--每个mapper文件都需要指定路径-->
    <mappers>
        <mapper resource="com/kuang/mapper/UserMapper.xml"/>
    </mappers>
</configuration>

```

pom.xml

```xml
<!--资源顾导不出xml文件的时候在pom中添加，添加后会自动到处到target中-->
    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
    </build>
```

目录结构

### 2.3、编写代码

mapper包下：

UserMapper接口

```java
public interface UserMapper {
    List<User> gerUserList();
}
```

UserMapper.xml  SQL

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="mapper.cn.shiliu.UserMapper">
    <select id="gerUserList" resultType="pojo.cn.shiliu.User">
        select * from mybatis.user
</select>
</mapper>
```

pojo包下：

User.java

```java
package com.kuang.pojo;

public class User {
    public int id;
    public String user;
    public String pwd;

    public User() {
    }

    public User(int id, String user, String pwd) {
        this.id = id;
        this.user = user;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
```

utils包下：

MyBatisUtils.java

```java
public class MyBatisUtils {

    private static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            //使用mybatis第一步，先获取SqlSessionFactory对象,必须做
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
             sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //有了SqlSessionFactory我们就可以获取SqlSession实例
    //Sqlsession包含了面向数据库执行sql的所有方法
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
```



### 2.4、测试

UserDaoTest.java

```java
public class UserDaoTest {
    @Test
    public void Test01(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.gerUserList();
        for (User user : userList) {
            System.out.println(user);
        }
        sqlSession.close();
    }
}

```

## 3、CRUD增删改查

### 3.1、namespace

namespace中的包名要跟Mapper/dao中的接口名一样

### 3.2、select

选择，查询语句

- id:就是对应namespace中的方法名

- resultType：Sql执行语句的返回值

- parameterType：参数类型

- 

  UserMapper接口

```java
//通过Id获取对象
User getId(int id) ;
```

UserMapper.xml  SQL

```xml
<select id="getId" parameterType="int" resultType="pojo.cn.shiliu.User">
    select *from mybatis.user where id=#{id}
</select>
```

UserDaoTest.java

```java
@Test
public void getId(){
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    User id = mapper.getId(2);
    System.out.println(id);
}
```

### 3.3、insert

UserMapper接口

```java
//增加用户
int addUser(User user);
```

UserMapper.xml  SQL

```xml
<insert id="addUser" parameterType="pojo.cn.shiliu.User">
    insert into mybatis.user(id, user, pwd) value (#{id},#{user},#{pwd})
</insert>
```

UserDaoTest.java

```java
@Test
public  void addUser(){
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    int user =mapper.addUser(new User(2,"tyl1","199969"));
    if (user>0){
        System.out.println("插入成功！");
    }
    //提交事务
    sqlSession.commit();
    sqlSession.close();
}
```

### 3.4、update

UserMapper接口

```java
 //修改用户
    int updateUser(User user);
```

UserMapper.xml  SQL

```xml
<update id="updateUser" parameterType="pojo.cn.shiliu.User">
    update mybatis.user
    set user.user =#{user},user.pwd=#{pwd}
    where user.id=#{id};
</update>
```

UserDaoTest.java

```java
   @Test
    public void updateUser(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int temp = mapper.updateUser(new User(1, "hxy", "20000418"));
        sqlSession.commit();
        if(temp>0){
            System.out.println("修改成功！");
        }
        sqlSession.close();
    }
```

### 3.5、delete

UserMapper接口

```java
//删除用户
    int deleteUser(int id);
```

UserMapper.xml  SQL

```xml
<delete id="deleteUser" parameterType="int">
    delete from mybatis.user where id=#{id}
</delete>
```

UserDaoTest.java

```java
@Test
public void Delete(){
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    int temp = mapper.deleteUser(2);
    sqlSession.commit();
    if (temp>0){
        System.out.println("删除成功!");
    }
    sqlSession.close();
}
```

### 3.6、Map

数据库表中字段过多或者实体类参数过多推荐使用

UserMapper接口

```java
//增加用户传进去map
int addUser1(Map<String, Object> map);
```

UserMapper.xml

```xml
<insert id="addUser1" parameterType="map">
    insert into mybatis.user(id, name ,pwd)
        value
    (#{userId},#{userName},#{passWord})
</insert>
```

UserDaoTest.java

```java
@Test
public void addUser1(){
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("userId",2);
    map.put("userName","ttt");
    map.put("passWord","199969");
    mapper.addUser1(map);
    sqlSession.commit();
    sqlSession.close();
}
```

### 3.7、模糊查询

```xml
select * from mybatis.user where name like "%"#{value}"%"
```



```java
List<User> userList = mapper.getUserlike("%t%");
```

## 4、配置解析

### 4.1、核心配置文件config

```xml
configuration 配置
properties 属性
settings 设置
typeAliases 类型命名
typeHandlers 类型处理器
objectFactory 对象工厂
plugins 插件
environments 环境
environment 环境变量
transactionManager 事务管理器
dataSource 数据源
databaseIdProvider 数据库厂商标识
mappers 映射器
```

### 4.2配置环境（environments）

MyBatis 可以配置成适应多种环境

**不过要记住：尽管可以配置多个环境，每个 SqlSessionFactory 实例只能选择其一。**

MyBatis的默认事务管理是JDBC，连接池POOLED

### 4.3、properties属性

我们可以使用properties属性来实现引用配置文件

这些属性都是可外部配置且可动态替换的，既可以在典型的 Java 属性文件中配置，亦可通过 properties 元素的子元素来传递

编写一个properties文件

db.properties

```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?useSSL=true&uesUnicode=true&characterEncoding=UTF-8
username=root
password=199969
```

也可引入外部配置文件来实现

```xml
    <!--引入外部配置i文件-->
    <properties resource="db.properties">
        <property name="username" value="root"/>
        <property name="password" value="199969"/>
    </properties>
```

### 4.4、typeAliases别名

- 类型别名是为 Java 类型设置一个短的名字。它只和 XML 配置有关，存在的意义仅在于用来减少类完全限定名的冗余例如:

mybatis-config.xml

```xml
<!--给实体类起别名-->
<typeAliases>
    <typeAlias type="cn.shiliu.mybatis2.pojo.User" alias="User"/>
</typeAliases>
```

UserMapper.xml 

```xml
<select id="getUserList" resultType="User">
    select * from mybatis.user
</select>
```

- 也可以指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean，（建议使用小写字母调用）比如:

mybatis-config.xml

```xml
<!--扫描实体类的包-->
<typeAliases>
    <package name="com.tyl.pojo"/>
</typeAliases>
```

UserMapper.xml 

```xml
<select id="getUserList" resultType="user">
    select * from mybatis.user
</select>
```

- 也可以使用注解，在实体类上方

User.java

```java
@Alias("HELLO")
```

mybatis-config.xml

```xml
<select id="getUserList" resultType="HELLO">
    select * from mybatis.user
</select>
```

总结：

实体类比较多的时候可以使用扫包

实体类不多的时候可以使用注解或者直接设置一个短的名字

### 4.5、settings设置

参考官网

### 4.6、其他配置

参考官网，讲插件

### 4.7、映射器

- 方法一

指定路径

```xml
<!--每个mapper文件都需要指定路径-->
<mappers>
    <mapper resource="com/tyl/mapper/UserMapper.xml"/>
</mappers>
```

- 方法二

通过指定类名来注入

```xml
<mappers>
    <mapper class="cn.shiliu.mybatis2.mapper.UserMapper"/>
</mappers>
```

注意点：接口名必须与配置文件名字一致并且在同一个包下

- 方法三

通过扫包来注入

```xml
<mappers>
    <package name="com.tyl.mapper"/>
</mappers>
```

注意点：接口名必须与配置文件名字一致并且在同一个包下

### 4.8生命周期与作用域


#### 

**SqlSessionFactoryBuilder：**

- 一旦创建了SqlSessionFactory之后就不需要它了
- 局部变量相当于

**SqlSessionFactory：**

- 说白了就是数据库连接池
- SqlSessionFactory一旦被创建就一直在应用运行期间一直存在，****没有任何理由丢弃，或者可以重新创建另外一个实例**
- 因此SqlSessionFactory是最佳作用域，是应用作用域
- 最简单的就是使用**单例模式**或者静态代理模式

**SqlSession：**

- 连接到连接池的请求
- 用完之后需要赶紧关闭

## 5、resultMap解决属性名和字段名不一致的问题

实体类属性

```java
public class User {
    public int id;
    public String user;
    public String password;
    }
```

数据库子字段名




### 5.1、起别名（不推荐）

```xml
<select id="getUserList" resultType="HELLO">
   	<!--表里字段名as实体类参数名-->
    select id ,name as user, pwd as password from mybatis.user where name =#{user}
</select>
```

### 5.2resultMap映射

```xml
  <resultMap id="UserMap" type="User">
        <!--column对应数据库字段，property对应实体类中的字段-->
        <result column="id" property="id"/>
        <result column="name" property="user"/>
        <result column="pwd" property="password"/>
    </resultMap>

    <select id="getUserList" resultMap="UserMap">
            select *from mybatis.user where name =#{name}
    </select>
```

## 6、日志

### 6.1、日志工厂

如果数据库出现异常，我们需要排错，日志是最好的选择

曾经：sout、debug

现在：日志工厂


- SLF4J 

- LOG4J （掌握）

- LOG4J2

- JDK_LOGGING COMMONS_LOGGING

- STDOUT_LOGGING（掌握）标准日志暑促

- NO_LOGGING

具体使用哪一个在Mybatis配置文件中设定

mybatis-config.xml：

```xml
<!--标准日志工厂-->
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
```

打印出的日志记录：


### 6.2、log4j

- Log4j是[Apache](https://baike.baidu.com/item/Apache/8512995)的一个开源项目，通过使用Log4j，我们可以控制日志信息输送的目的地是[控制台](https://baike.baidu.com/item/控制台/2438626)、文件、[GUI](https://baike.baidu.com/item/GUI)组件，甚至是套接口服务器、[NT](https://baike.baidu.com/item/NT/3443842)的事件记录器、[UNIX](https://baike.baidu.com/item/UNIX) [Syslog](https://baike.baidu.com/item/Syslog)[守护进程](https://baike.baidu.com/item/守护进程/966835)等
- 我们可以控制每一条日志的输出过程
- 通过定义每一条日志信息的级别，我们能够更加细致地控制日志的生成过程

- 我们可以通过一个[配置文件](https://baike.baidu.com/item/配置文件/286550)来灵活地进行配置，而不需要修改应用的代码。

实现过程

1、导包

```xml
<!--log4j日志依赖-->
<!-- https://mvnrepository.com/artifact/log4j/log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

2、配置日志

```properties
log4j.rootLogger=DEBUG,console,dailyFile,im
log4j.additivity.org.apache=true
# 控制台(console)
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.Threshold=DEBUG
log4j.appender.console.ImmediateFlush=true
log4j.appender.console.Target=System.err
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=[%-5p] %d(%r) --> [%t] %l: %m %x %n

# 日志文件(logFile)
log4j.appender.logFile=org.apache.log4j.FileAppender
log4j.appender.logFile.Threshold=DEBUG
log4j.appender.logFile.ImmediateFlush=true
log4j.appender.logFile.Append=true
log4j.appender.logFile.File=D:/logs/log.log4j
log4j.appender.logFile.layout=org.apache.log4j.PatternLayout
log4j.appender.logFile.layout.ConversionPattern=[%-5p] %d(%r) --> [%t] %l: %m %x %n
# 回滚文件(rollingFile)
log4j.appender.rollingFile=org.apache.log4j.RollingFileAppender
log4j.appender.rollingFile.Threshold=DEBUG
log4j.appender.rollingFile.ImmediateFlush=true
log4j.appender.rollingFile.Append=true
log4j.appender.rollingFile.File=D:/logs/log.log4j
log4j.appender.rollingFile.MaxFileSize=200KB
log4j.appender.rollingFile.MaxBackupIndex=50
log4j.appender.rollingFile.layout=org.apache.log4j.PatternLayout
log4j.appender.rollingFile.layout.ConversionPattern=[%-5p] %d(%r) --> [%t] %l: %m %x %n
# 定期回滚日志文件(dailyFile)
log4j.appender.dailyFile=org.apache.log4j.DailyRollingFileAppender
log4j.appender.dailyFile.Threshold=DEBUG
log4j.appender.dailyFile.ImmediateFlush=true
log4j.appender.dailyFile.Append=true
log4j.appender.dailyFile.File=D:/logs/log.log4j
log4j.appender.dailyFile.DatePattern='.'yyyy-MM-dd
log4j.appender.dailyFile.layout=org.apache.log4j.PatternLayout
log4j.appender.dailyFile.layout.ConversionPattern=[%-5p] %d(%r) --> [%t] %l: %m %x %n
# 应用于socket
log4j.appender.socket=org.apache.log4j.RollingFileAppender
log4j.appender.socket.RemoteHost=localhost
log4j.appender.socket.Port=5001
log4j.appender.socket.LocationInfo=true
# Set up for Log Factor 5
log4j.appender.socket.layout=org.apache.log4j.PatternLayout
log4j.appender.socket.layout.ConversionPattern=[%-5p] %d(%r) --> [%t] %l: %m %x %n
# Log Factor 5 Appender
log4j.appender.LF5_APPENDER=org.apache.log4j.lf5.LF5Appender
log4j.appender.LF5_APPENDER.MaxNumberOfRecords=2000
# 发送日志到指定邮件
log4j.appender.mail=org.apache.log4j.net.SMTPAppender
log4j.appender.mail.Threshold=FATAL
log4j.appender.mail.BufferSize=10
log4j.appender.mail.From = xxx@mail.com
log4j.appender.mail.SMTPHost=mail.com
log4j.appender.mail.Subject=Log4J Message
log4j.appender.mail.To= xxx@mail.com
log4j.appender.mail.layout=org.apache.log4j.PatternLayout
log4j.appender.mail.layout.ConversionPattern=[%-5p] %d(%r) --> [%t] %l: %m %x %n
# 应用于数据库
log4j.appender.database=org.apache.log4j.jdbc.JDBCAppender
log4j.appender.database.URL=jdbc:mysql://localhost:3306/test
log4j.appender.database.driver=com.mysql.jdbc.Driver
log4j.appender.database.user=root
log4j.appender.database.password=
log4j.appender.database.sql=INSERT INTO LOG4J (Message) VALUES('=[%-5p] %d(%r) --> [%t] %l: %m %x %n')
log4j.appender.database.layout=org.apache.log4j.PatternLayout
log4j.appender.database.layout.ConversionPattern=[%-5p] %d(%r) --> [%t] %l: %m %x %n

# 自定义Appender
log4j.appender.im = net.cybercorlin.util.logger.appender.IMAppender
log4j.appender.im.host = mail.cybercorlin.net
log4j.appender.im.username = username
log4j.appender.im.password = password
log4j.appender.im.recipient = corlin@cybercorlin.net
log4j.appender.im.layout=org.apache.log4j.PatternLayout
log4j.appender.im.layout.ConversionPattern=[%-5p] %d(%r) --> [%t] %l: %m %x %n
```

3、配置log4j的日志实现

```xml
<--log4j-->
<settings>
    <setting name="logImpl" value="LOG4J"/>
</settings>
```

4、log4j的使用

- 导入包（容易导错）

```java
import org.apache.log4j.Logger;
```

- 实例化

```java
static Logger logger = Logger.getLogger(UserDaoTest.class);
```

- 使用

```java
@Test
public void log4jTest(){
    logger.info("[info]:进入了log4jTest！");
    logger.debug("[debug]:进入了log4jTest！");
    logger.error("[error]:进入了log4jTest！");
}
```

## 7、分页

为什么要使用分页：减少数据处理量

### 7.1使用Limit实现分页

基本语法

```sql
select *from mybatis.`user`limit startIndex,pageSize;
select *from mybatis.`user`limit 0,2;
```

UserMapper接口

```java
//分页查询
List<User> getListByLimit(Map<String, Integer> map);
```

UserMapper.xml

```xml
<select id="getListByLimit" resultMap="UserMap" resultType="User">
    select *from mybatis.user limit #{startIndex},#{pageSize}
</select>
```

UserDaoTest.java

```java
@Test
public void getListByLimitTest(){
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    Map<String, Integer> map=new HashMap<>();
    map.put("startIndex",2);
    map.put("pageSize",2);
    List<User> userList = mapper.getListByLimit(map);
    for (User user : userList) {
        System.out.println(user);
    }
    sqlSession.close();
}
```

### 7.2使用RowBounds实现分页

直接查询全部用户用

UserMapper.xml

```xml
<select id="getUserByRowBounds"  resultType="User">
    select id ,name as user, pwd as password from mybatis.user
</select>
```

UserMapper.java接口

```java
List<User> getUserByRowBounds();
```

UserDaoTest.java测试类

```java
@Test
public  void getUserByRowBounds(){
    RowBounds rowBounds = new RowBounds(1, 2);//new出RowBounds对象，万物皆可对象
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    List<User> userList = 	sqlSession.selectList("cn.shiliu.mybatis2.mapper.UserMapper.getUserByRowBounds",null,rowBounds);//直接用，不会用看源码
    for (User user : userList) {
        System.out.println(user);
    }
    sqlSession.close();
}
```

### 7.3分页插件


## 8、使用注解开发

### 8.1、CRUD

- 不需要使用xml配置文件，直接添加注解

UserMapper.java接口

```java
public interface UserMapper {
    //通过用户名查询用户
    @Select("select id as id, name as user ,pwd as password  from mybatis.user")
    List<User> getUserList();

    //通过id获取用户
    @Select("select id as id, name as user ,pwd as password  from mybatis.user where id=#{uid}")
    User getIdBy(@Param("uid") int id);

    //新增用户
    @Insert("insert into mybatis.user(id, name, pwd) value (#{id},#{user},#{password})")
    int addByUser(User user);

    //按Id删除用户
    @Delete("delete from mybatis.user where id=#{uid}")
    int deleteByUser(@Param("uid") int id);

}
```

UserMapperTest.java

```java
public class UserMapperTest {
    @Test
    public void  getUserList(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        //删除
        mapper.deleteByUser(6);
        sqlSession.commit();
        //插入
        //mapper.addByUser(new User(6, "ttt", "123"));
        //sqlSession.commit();

        //通过id查询
        //User idBy = mapper.getIdBy(1);
        //System.out.println(idBy);

        //查询
        //List<User> userList = mapper.getUserList();
        //for (User user : userList) {
        //    System.out.println(user);
        //}
        sqlSession.close();
    }

}
```

## 9、Lombok的使用（偷懒专用）

### 9.1注解

```java
@Getter and @Setter
@FieldNameConstants
@ToString
@EqualsAndHashCode
@AllArgsConstructor, @RequiredArgsConstructor and @NoArgsConstructor
@Log, @Log4j, @Log4j2, @Slf4j, @XSlf4j, @CommonsLog, @JBossLog, @Flogger, @CustomLog
@Data
@Builder
@SuperBuilder
@Singular
@Delegate
@Value
@Accessors
@Wither
@With
@SneakyThrows
@val
@var

```

常用：

```
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
```

### 9.2、使用

- 安装插件Lombok

- 导入依赖

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.4</version>
</dependency>
```

- 在实体类上加注解



## 10、复杂查询一对多&多对一



### 10.1、多对一（多个学生有一个老师）

- #### 建表

```sql
use mybatis
CREATE TABLE `teacher` (
`id` INT(10) NOT NULL,
`name` VARCHAR(30) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

INSERT INTO teacher(`id`, `name`) VALUES (1, '唐老师');

CREATE TABLE `student` (
`id` INT(10) NOT NULL,
`name` VARCHAR(30) DEFAULT NULL,
`tid` INT(10) DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `fktid` (`tid`),
CONSTRAINT `fktid` FOREIGN KEY (`tid`) REFERENCES `teacher` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8


INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('1', '小明', '1');
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('2', '小红', '1');
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('3', '小张', '1');
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('4', '小李', '1');
INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('5', '小王', '1');
```

- #### pojo实体类

Student.java

```java
@Data //get set toString 
public class Student {
    public int id;
    public String name;
    //学生需要关联一个老师
    public Tercher tercher;
}
```

Tercher.java

```java
@Data
public class Tercher {
    public int id;
    public String name;
}
```

- #### utils

MyBatisUtils.java

```java
public class MyBatisUtils {

    private static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            //使用mybatis第一步，先获取SqlSessionFactory对象,必须做
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
             sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //有了SqlSessionFactory我们就可以获取SqlSession实例
    //Sqlsession包含了面向数据库执行sql的所有方法
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
```

- #### mapper接口

StudentMapper

```java
public interface StudentMapper {
    List<Student> getStudent();

    List<Student> getStudent2();
}
```

- #### mapper配置文件

StudentMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--mybatis核心配置文件-->
<mapper namespace="cn.shiliu.mybatis6.mapper.StudentMapper">

    <!--思路
    1、查询所有的学生信息
    2、根据查询出来的学生tid来寻找到对应的老师
    -->
    <select id="getStudent" resultMap="StudentTeacher">
        select *from student
    </select>
    <!--按照查询嵌套处理-->
    <resultMap id="StudentTeacher" type="Student">
        <result property="id" column="id"/>
        <result property="name" column="name"/>
        <!--复杂的属性，我们需要单独处理 对象使用association 集合使用collection-->
        <association property="teacher" column="tid" javaType="Teacher" select="getTeacher"/>
    </resultMap>
    <select id="getTeacher" resultType="Teacher">
        select *from teacher where  id=#{id}
    </select>


    <!--按照结果嵌套处理-->
    <select id="getStudent2" resultMap="StudentTeacher2">
        SELECT s.id sid,s.name sname,t.name tname
        FROM student s,teacher t
        WHERE s.tid=t.id
    </select>
    <resultMap id="StudentTeacher2" type="Student">

        <result property="id" column="sid"/>
        <result property="name" column="sname"/>
        <association property="teacher" javaType="Teacher">
            <result property="name" column="tname"/>
        </association>
    </resultMap>

</mapper>

```

- #### mybatis-config核心配置文件参考之前

### 10.2、一对多（一个老师对多个学生）

- #### mapper接口

TeacherMapper.java

```java
public interface TeacherMapper {
    //获取指定老师下所有的学生
    Teacher getTeacher(@Param("tid") int id);
}
```

- #### pojo

Student.java

```java
@Data
public class Student {
    private int id;
    private String name;
    private int tid;
}
```

Teacher.java

```java
@Data
public class Teacher {
    private int id;
    private String name;
    //一个老师有多个学生
    private List<Student> students;
}
```

- #### utils

MyBatisUtils.java

```java
public class MyBatisUtils {

    private static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            //使用mybatis第一步，先获取SqlSessionFactory对象,必须做
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
             sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //有了SqlSessionFactory我们就可以获取SqlSession实例
    //Sqlsession包含了面向数据库执行sql的所有方法
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
```



- mapper配置文件

TeacherMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--mybatis核心配置文件-->
<mapper namespace="cn.shiliu.mybatis6.mapper.TeacherMapper">

    <!--按照结果嵌套处理-->
    <select id="getTeacher" resultMap="TeacherStudent">
        select s.id sid ,s.name sname,t.name tname,t.id tid
        from student s ,teacher t
        where s.tid=t.id and t.id=#{tid}
    </select>
    <resultMap id="TeacherStudent" type="Teacher">
        <result property="id" column="tid"/>
        <result property="name" column="tname"/>
        <collection property="students" ofType="Student">
            <result property="id" column="sid"/>
            <result property="name" column="sname"/>
            <result property="tid" column="tid"/>
        </collection>
    </resultMap>
    
    
    
    <!--按照嵌套查询-->
    <select id="getTeacher2" resultMap="TeacherStudent2">
        select * from mybatis.teacher where id=#{tid}
    </select>
    <resultMap id="TeacherStudent2" type="Teacher">
        <result property="id" column="id"/>
        <collection property="students"
                    javaType="ArrayList"
                    ofType="Student"
                    select="getStudentTeacherById"
                    column="id"/>
    </resultMap>
    <select id="getStudentTeacherById" resultType="Student">
        select *from mybatis.student where tid=#{tid}
    </select>
    
</mapper>
```

StudentMapper.xml

```java
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--mybatis核心配置文件-->
<mapper namespace="cn.shiliu.mybatis6.mapper.StudentMapper">
</mapper>
```

### 10.3、总结

- 关联-使用association【多对一】
- 集合-集合使用collection【一对多】
- Java type指定实体类中的属性的类型
- ofType用来映射到List或者集合中的pojo类型，泛型中的约束类型

## 11、动态sql

### 11.1、环境搭建

- mapper

BlogMapper接口

```java
public interface BlogMapper {
    int addData(Blog blog);
}
```

BlogMapperxml

```XML
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--mybatis核心配置文件-->
<mapper namespace="cn.shiliu.mybatis8.mapper.BlogMapper">

    <insert id="addData" parameterType="blog">
        insert into mybatis.blog(id,title,author,create_time,views)
        value (#{id},#{title},#{author},#{createTime},#{views})
    </insert>
</mapper>
```

- pojo

Blog.java

```java
@Data
public class Blog {
    private  String id;
    private String title;
    private String author;
    private Date createTime;
    private int views;
}
```

- utils

IdUtils.java

```java
public class IdUtils {
    public static String getId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    @Test
    public void IdUtilsTest(){
        System.out.println(IdUtils.getId());
        System.out.println(IdUtils.getId());
        System.out.println(IdUtils.getId());
    }
}
```

MyBatisUtils

```java
public class MyBatisUtils {

    private static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            //使用mybatis第一步，先获取SqlSessionFactory对象,必须做
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
             sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //有了SqlSessionFactory我们就可以获取SqlSession实例
    //Sqlsession包含了面向数据库执行sql的所有方法
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
```

- resources

  参考上面基本配置文件

- 测试

MyTest.java

```java
public class MyTest {
    @Test
    public  void addData(){
        Logger logger = Logger.getLogger(String.valueOf(MyTest.class));

        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = new Blog();
        blog.setId(IdUtils.getId());
        blog.setTitle("tyl的博客");
        blog.setAuthor("tyl");
        blog.setCreateTime(new Date());
        blog.setViews(999999);
        mapper.addData(blog);
        
        blog.setId(IdUtils.getId());
        blog.setTitle("hxy的博客");
        blog.setAuthor("hxy");
        blog.setCreateTime(new Date());
        blog.setViews(999999);
        mapper.addData(blog);
        
        blog.setId(IdUtils.getId());
        blog.setTitle("tyl1的博客");
        blog.setAuthor("tyl1");
        blog.setCreateTime(new Date());
        blog.setViews(9999999);
        mapper.addData(blog);

        blog.setId(IdUtils.getId());
        blog.setTitle("hxy1的博客");
        blog.setAuthor("hxy1");
        blog.setCreateTime(new Date());
        blog.setViews(9999999);
        int temp = mapper.addData(blog);

        sqlSession.commit();
        if(temp>0){
            logger.info("=========================插入了数据===============================");
        }
    }
}
```

- 工程结构


### 11.2、if

- BlogMapper接口

```java
List<Blog> getTitleIF(Map map);
```

- BlogMapper.xml

```xml

    <select id="getTitleIF" parameterType="map" resultType="Blog">
        select *from mybatis.blog
        <where>
            <if test="title!=null">
                and title=#{title}
            </if>
            <if test="author!=null">
                and  author=#{author}
            </if>
        </where>
    </select>
```

- MyTest.java

```java
@Test
public  void getTitleIF(){
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    HashMap map = new HashMap();
    map.put("title","tyl的博客");
    map.put("author","tyl");
    List<Blog> titleIF = mapper.getTitleIF(map);
    for (Blog blog : titleIF) {
        System.out.println(blog);
    }
}
```

### 11.3、choose（when、otherwise）

- BlogMapper接口

```java
//用choose（when、otherwise）
List<Blog> getTitleIF2(Map map);
```

- BlogMapper.xml

```xml
<select id="getTitleIF2" parameterType="map" resultType="Blog">
    select *from mybatis.blog
    <where>
        <choose>
            <when test="title!=null">
                title=#{title}
            </when>
            <when test="author!=null">
                author=#{author}
            </when>
            <otherwise>
                views=#{views}
            </otherwise>
        </choose>
    </where>
</select>
```

- MyTest.java

```java
@Test
public  void getTitleIF2(){
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    HashMap map = new HashMap();
    map.put("title","tyl的博客");
    map.put("author","tyl");
    map.put("views",999999);
    List<Blog> titleIF = mapper.getTitleIF(map);
    for (Blog blog : titleIF) {
        System.out.println(blog);
    }
}
```

### 11.4、update

- BlogMapper接口

```java
//更新博客
int updateBlog(Map map);
```

- BlogMapper.xml

```xml
<update id="updateBlog" parameterType="map">
    update mybatis.blog
    <set>
        <if test="title!=null">
            title=#{title},
        </if>
        <if test="author!=null">
            author=#{author},
        </if>
        <if test="views!=null">
            views=#{views},
        </if>
    </set>
    where id=#{id}
</update>
```

- MyTest.java

```java
@Test
public  void updateBlog(){
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    HashMap map = new HashMap();
    map.put("title","tyl的博客tyl");
    map.put("author","tyltyl");
    map.put("views",999);
    map.put("id","fc2d7591feb4411483a3fbe7a5994f6f");
    mapper.updateBlog(map);
    sqlSession.commit();
    sqlSession.close();
}
```

### 11.5、SQL片段

可以取出来公共部分，方便复用

- BlogMapper.xml

```xml
<!--取出公告部分-->
<sql id="getTitleIF2sql">
    <choose>
        <when test="title!=null">
            title=#{title}
        </when>
        <when test="author!=null">
            author=#{author}
        </when>
        <otherwise>
            views=#{views}
        </otherwise>
    </choose>
</sql>
<!--导入-->
<select id="getTitleIF2" parameterType="map" resultType="Blog">
    select *from mybatis.blog
    <where>
        <include refid="getTitleIF2sql"/>
    </where>
</select>
```



### 11.6、Foreach

- 修改id


- 接口

```java
List<Blog>getBlogForeach(Map map);
```

- BlogMapper.xml

```xml
<select id="getBlogForeach" parameterType="map" resultType="Blog">
    select *from mybatis.blog
    <where>
        <foreach collection="ids" item="id" open="and (" close=")" separator="or">
            id=#{id}
        </foreach>
    </where>
</select>
```

- MyTest.java

```java
@Test
public void getBlogForeach(){
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    HashMap map = new HashMap();
    ArrayList<Integer> ids = new ArrayList<Integer>();
    ids.add(1);
    ids.add(2);
    map.put("ids",ids);
    List<Blog> blogs = mapper.getBlogForeach(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }
    sqlSession.close();
}
```

### 总结：

### 动态sql就是在拼接sql

sql不熟练，要着重掌握

掌握要点：mysql、innodb底层原理、索引、索引优化



## 12、缓存

### 12.1简介

**1、什么是缓存 [ Cache ]？**

- **存在内存中的临时数据。**
- **将用户经常查询的数据放在缓存（内存）中，用户去查询数据就不用从磁盘上(关系型数据库数据文件)查询，从缓存中查询，从而提高查询效率，解决了高并发系统的性能问题。**

**2、为什么使用缓存？**

- **减少和数据库的交互次数，减少系统开销，提高系统效率。**

**3、什么样的数据能使用缓存？**

- **经常查询并且不经常改变的数据。**



### 12.2一级缓存

**一级缓存也叫本地缓存：**

- **与数据库同一次会话期间查询到的数据会放在本地缓存中。**
- **以后如果需要获取相同的数据，直接从缓存中拿，没必须再去查询数据库；**

### 12.3测试

- 查询不会刷新缓存，第二次查询会从缓存中取数据


- 增删改会刷新缓存

- 手动刷新缓存

```java
@Test
public  void getId(){
    Logger logger = Logger.getLogger(String.valueOf(MyTest.class));
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);


    mapper.updateUserT(new User(1,"aaaa","bbbb"));
    User id = mapper.getId(1);
    logger.info("==============================================================================");
    System.out.println(id);
    sqlSession.clearCache();//刷新缓存
    logger.info("==============================================================================");
    User id2 = mapper.getId(1);
    System.out.println(id2);
    logger.info("======================================================================================");
    System.out.println(id==id2);
    sqlSession.close();
}
```


### 12.4、二级缓存

- 二级缓存也叫全局缓存，一级缓存作用域太低了，所以诞生了二级缓存

- 基于namespace级别的缓存，一个名称空间，对应一个二级缓存；

- 工作机制

- - 一个会话查询一条数据，这个数据就会被放在当前会话的一级缓存中；
  - 如果当前会话关闭了，这个会话对应的一级缓存就没了；但是我们想要的是，会话关闭了，一级缓存中的数据被保存到二级缓存中；
  - 新的会话查询信息，就可以从二级缓存中获取内容；
  - 不同的mapper查出的数据会放在自己对应的缓存（map）中



### 12.5、测试

mybatis-config.xml

- ```xml
  <!--开启全局缓存（二级缓存）-->
  <setting name="cacheEnabled" value="ture"/>
   UserMapper.xml
  ```

UserMapper.xml

- ```xml
  <!--在当前的mapper中使用二级缓存-->
  <cache/>
  ```

User.java

```java
//原因:在mybatis中使用二级缓存时候就必须需要将实体类序列化implements Serializable,
// mapper文件使用了<cache/>标签，使用了mybatis提供的二级缓存，所以在我的实体类里面必须序列化
public class User implements Serializable {
    private  int id;
    private  String name;
    private  String pwd;
}
```

MyTest.java

```java
@Test
public  void getId(){
    Logger logger = Logger.getLogger(String.valueOf(MyTest.class));
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    SqlSession sqlSession2 = MyBatisUtils.getSqlSession();

    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    User id = mapper.getId(1);
    System.out.println(id);
    sqlSession.close();
    logger.info("==============================================================================");
    UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
    User id2 = mapper2.getId(1);
    System.out.println(id2);

    logger.info("======================================================================================");
    System.out.println(id==id2);
    sqlSession2.close();
}
```


### 12.6、缓存原理图



### 12.7、EhCache

- 导包

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis.caches/mybatis-ehcache -->
<dependency>
   <groupId>org.mybatis.caches</groupId>
   <artifactId>mybatis-ehcache</artifactId>
   <version>1.1.0</version>
</dependency>
```

- 在mapper中添加

```xml
 <cache type = “org.mybatis.caches.ehcache.EhcacheCache” />
```

```
<mapper namespace = “org.acme.FooMapper” >
   <cache type = “org.mybatis.caches.ehcache.EhcacheCache” />
</mapper>
```

- 编写ehcache.xml文件，如果在加载时未找到/ehcache.xml资源或出现问题，则将使用默认配置。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
        updateCheck="false">
   <!--
      diskStore：为缓存路径，ehcache分为内存和磁盘两级，此属性定义磁盘的缓存位置。参数解释如下：
      user.home – 用户主目录
      user.dir – 用户当前工作目录
      java.io.tmpdir – 默认临时文件路径
    -->
   <diskStore path="./tmpdir/Tmp_EhCache"/>
   
   <defaultCache
           eternal="false"
           maxElementsInMemory="10000"
           overflowToDisk="false"
           diskPersistent="false"
           timeToIdleSeconds="1800"
           timeToLiveSeconds="259200"
           memoryStoreEvictionPolicy="LRU"/>

   <cache
           name="cloud_user"
           eternal="false"
           maxElementsInMemory="5000"
           overflowToDisk="false"
           diskPersistent="false"
           timeToIdleSeconds="1800"
           timeToLiveSeconds="1800"
           memoryStoreEvictionPolicy="LRU"/>
   <!--
      defaultCache：默认缓存策略，当ehcache找不到定义的缓存时，则使用这个缓存策略。只能定义一个。
    -->
   <!--
     name:缓存名称。
     maxElementsInMemory:缓存最大数目
     maxElementsOnDisk：硬盘最大缓存个数。
     eternal:对象是否永久有效，一但设置了，timeout将不起作用。
     overflowToDisk:是否保存到磁盘，当系统当机时
     timeToIdleSeconds:设置对象在失效前的允许闲置时间（单位：秒）。仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。
     timeToLiveSeconds:设置对象在失效前允许存活时间（单位：秒）。最大时间介于创建时间和失效时间之间。仅当eternal=false对象不是永久有效时使用，默认是0.，也就是对象存活时间无穷大。
     diskPersistent：是否缓存虚拟机重启期数据 Whether the disk store persists between restarts of the Virtual Machine. The default value is false.
     diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。
     diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。
     memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。
     clearOnFlush：内存数量最大时是否清除。
     memoryStoreEvictionPolicy:可选策略有：LRU（最近最少使用，默认策略）、FIFO（先进先出）、LFU（最少访问次数）。
     FIFO，first in first out，这个是大家最熟的，先进先出。
     LFU， Less Frequently Used，就是上面例子中使用的策略，直白一点就是讲一直以来最少被使用的。如上面所讲，缓存的元素有一个hit属性，hit值最小的将会被清出缓存。
     LRU，Least Recently Used，最近最少使用的，缓存的元素有一个时间戳，当缓存容量满了，而又需要腾出地方来缓存新的元素的时候，那么现有缓存元素中时间戳离当前时间最远的元素将被清出缓存。
  -->

</ehcache>
```