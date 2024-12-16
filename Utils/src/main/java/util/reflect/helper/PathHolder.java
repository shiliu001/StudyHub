package util.reflect.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/4/13
 * @description: 属性路径记录器
 */
public class PathHolder {
    private final List<String> classNames=new ArrayList<>();
    public String pop(){
        String last = CollUtil.getLast(classNames);
        classNames.remove(classNames.size()-1);
        return last;
    }
    public void push(String className){
        classNames.add(className);
    }
    public String getPath(){
        StrBuilder strBuilder=new StrBuilder();
        for (String className : classNames) {
            strBuilder.append(className).append(".");
        }
        return strBuilder.toString();
    }
}
