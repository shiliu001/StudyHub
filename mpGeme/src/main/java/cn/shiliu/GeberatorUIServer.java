package cn.shiliu;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

/**
 * @author shiliu
 * @date 2024/1/27
 * @apiNote 代码生成器
 */
public class GeberatorUIServer {
    private static final Logger log = LoggerFactory.getLogger(GeberatorUIServer.class);

    /**
     * 启动入口
     * 入口：localhost:8068
     *
     * @param args
     */
//    public static void main(String[] args) {
//
//        FastAutoGenerator.create("127.0.0.1", "root", "shiliu")
//                .globalConfig(builder -> builder
//                        .author("Baomidou")
//                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java")
//                        .commentDate("yyyy-MM-dd")
//                )
//                .packageConfig(builder -> builder
//                        .parent("cn.shiliu.mp.test")
//                        .entity("entity")
//                        .mapper("mapper")
//                        .service("service")
//                        .serviceImpl("service.impl")
//                        .xml("mapper.xml")
//                )
//                .strategyConfig(builder -> builder
//                        .entityBuilder()
//                        .enableLombok()
//                )
//                .templateEngine(new FreemarkerTemplateEngine())
//                .execute();
//    }
    public static void main(String[] args) {
        try {
            //使用FastAutoGenerator 快速配置代码生成器
            FastAutoGenerator.create("jdbc:mysql://localhost:3306/shiliu?serverTimezone=GMT%2B8", "root", "shiliu").globalConfig(builder -> {
                        builder.author("shiliu") // 设置作者
                                .outputDir("D:\\01-WORK\\00-project\\00-project\\01-own\\StudyHub\\mpGeme\\src\\main\\java\\cn\\shiliu\\test\\"); // 输出目录
                    }).packageConfig(builder -> {
                        builder.parent("com.example") // 设置父包名
                                .entity("model") // 设置实体类包名
                                .mapper("dao") // 设置 Mapper 接口包名
                                .service("service") // 设置 Service 接口包名
                                .serviceImpl("service.impl") // 设置 Service 实现类包名
                                .xml("mappers"); // 设置 Mapper XML 文件包名
                    }).strategyConfig(builder -> {
                        builder.addInclude("user_basic_info", "login_auth_record") // 设置需要生成的表名
                                .entityBuilder().enableLombok() // 启用 Lombok
                                .enableTableFieldAnnotation() // 启用字段注解
                                .controllerBuilder().enableRestStyle(); // 启用 REST 风格
                    }).templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker 模板引擎
                    .execute(); // 执行生成
        } catch (Exception e) {
            log.error("异常信息为：{}",e.getMessage());
        }
    }

}
