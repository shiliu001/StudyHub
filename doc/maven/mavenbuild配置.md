 <build>
        <!--        打包后的jar包名-->
        <finalName>${project.parent.artifactId}-${profileActive}</finalName>
        <plugins>
            <!-- 打包时跳过测试 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.22.1</version>
                <configuration>
                    <skipTests>true</skipTests>
                </configuration>
            </plugin>

            <!--打包插件-->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring.boot.version}</version>
                <configuration>
                    <additionalProperties>
                       <package>true</package>
                        <encoding.source>UTF-8</encoding.source>
                        <encoding.reporting>UTF-8</encoding.reporting>
                    </additionalProperties>
                    <profiles>
                        <profile>
                            ${profileActive}
                        </profile>
                    </profiles>
                    <!-- 如果没有该项配置devtools不会起作用，即应用不会restart -->
                    <fork>true</fork>
                    <!--支持静态文件热部署-->
                    <addResources>true</addResources>
                    <!-- 指定该Main Class为全局的唯一入口 -->
                    <mainClass>com.newchinalife.starter.WebStartApplication</mainClass>

                    <!-- spring-boot-maven-plugin这个插件打包的Jar包可以直接运行，但是不可依赖, 添加如下配置使其可被依赖 -->
                    <!--<classifier>execute</classifier>-->
                    <!--引入外部包(非maven导入),nci-db-opreation-old模块中有引入外部jar包-->
                    <includeSystemScope>true</includeSystemScope>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                            <!--可以把依赖的包都打包到生成的Jar包中-->
                            <goal>build-info</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <!--资源解析插件-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <!-- 解析文件占位符 比如配置文件中的@profileActive@-->
                    <delimiters>
                        <delimiter>${*}</delimiter>
                    </delimiters>
                    <useDefaultDelimiters>true</useDefaultDelimiters>
                    <resources>
                        <resource>
                            <directory>src/main/resources/</directory>
                            <filtering>true</filtering>
                        </resource>
                    </resources>
                </configuration>
            </plugin>
            <!-- 打包定义规则插件-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <includes>
                        <!-- 只打包指定目录的文件 -->
                        <include>com/newchinalife/**</include>
                        <include>application.properties</include>
                        <include>application-${profileActive}.properties</include>
                        <include>*.xml</include>
                        <include>**/*.xml</include>
                        <include>templates/**</include>
                        <include>*.txt</include>
                        <include>properties-config/**</include>
                        <include>META-INF/**</include>
                    </includes>
                </configuration>
            </plugin>

        </plugins>
        <!--用idea启动时配置文件解析-->
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>