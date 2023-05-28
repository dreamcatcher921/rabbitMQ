Linux命令

​	rm -rf   f(强制删除) r(递归删除)、





配置全局环境

RABBIT_HOME=/usr/local/rabbitmq_server-3.10.11 PATH=\$PATH:$RABBIT_HOME/sbin     export RABBIT_HOME PATH 	 

  

| 名称                       | 命令                                             |
| -------------------------- | ------------------------------------------------ |
| 后端启动                   | rabbitmq-server -detached                        |
| 查看进程                   | ps -ef\|grep rabbit                              |
| 查看本机服务器的状态       | rabbitmqctl status                               |
| 停止rabbit                 | rabbitmqctl shutdown                             |
| 创建一个rabbitmq用户       | rabbitmqctl add_user admin 123456                |
| 查看用户                   | rabbitmqctl list_users                           |
| 赋予角色                   | rabbitmqctl set_user_tags admin administrator    |
| 授予权限                   | rabbitmqctl set_permissions admin ".*" ".*" ".*" |
| 查看权限                   | rabbitmqctl list_permissions                     |
| 刷新文件                   | source /etc/profile                              |
| 列出插件                   | rabbitmq-plugins list                            |
| 启动rabbitmq管理插件       | rabbitmq-plugins enable rabbitmq_management      |
| 查看防火墙状态             | systemctl status firewalld                       |
| 开机不自启                 | systemctl disable  firewalld                     |
| 关闭防火墙                 | systemctl stop firewalld                         |
| 重启网络                   | systemctl restart network                        |
| 修改域名和ip地址的映射文件 | vi /etc/hosts                                    |
|                            |                                                  |
|                            |                                                  |
|                            |                                                  |



# springboot工程

```xml
 <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.6.13</version>
        <relativePath/>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.24</version>
        </dependency>
        <!--    添加rabbitmq依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

# 启动器

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

```





















