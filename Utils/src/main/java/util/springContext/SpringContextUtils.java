package util.springContext;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2023/8/29
 * @description: TODO
 */
public class SpringContextUtils {
    /**
     * demo中避免spring框架启动 太过笨重 拿到自己想要的bean就行了
     * 第一个参数 想要扫描的包路径 {@link Component}等注解注释的类会被注册成bean
     * 第二个参数 yml配置文件路径
     * */
    public static ApplicationContext getContext(List<String> packagesPath, List<String>ymlFiledPath) throws IOException {
        //一个干净的spring容器
        GenericApplicationContext context = new GenericApplicationContext();

        //赋予scanner容器类
        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(context);
        //扫描指定包下的类 然后交给容器
        packagesPath.forEach(classPathBeanDefinitionScanner::scan);

        //获取容器中的环境变量
        ConfigurableEnvironment environment = context.getEnvironment();

        //yml文件资源加载器
        YamlPropertySourceLoader propertySourceLoader = new YamlPropertySourceLoader();
        //资源加载器
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        //加载文件资源
        List<Resource>resources=new ArrayList<>();
        ymlFiledPath.forEach(item->resources.add(resourceLoader.getResource(item)));
        //yml文件加载器解析yml文件
        List<PropertySource<?>> load=new ArrayList<>();
        resources.forEach(item->{
            try {
                load.addAll(propertySourceLoader.load(item.getFilename(),item));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        //解析出来的属性赋予environment属性
        load.forEach(item -> {
            environment.getPropertySources().addLast(item);
        });

        //容器刷新 bean生命周期正式开始
        context.refresh();
        return context;
    }
}
