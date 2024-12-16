package util.copy.helper;

import cn.hutool.core.lang.Pair;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaowenpeng
 * @version 1.0
 * @date 2024/4/13
 * @description: 复制报告
 */
@Getter
@Setter
public class CopyReport <T>{
    T data;
    /**
     * 没有能从源数据copy过来的属性 就是没发现名字对应的属性
     * 属性路径集合
     * */
    List<String> notCopyFieldName=new ArrayList<>();
    /**
     * 在源数据中发现了名字匹配的属性 但是类型转换失败或者无法类型转换的属性路径
     * pair的key为源数据的路径 value为目标数据的属性路径
     * */
    List<Pair<String,String>> sameNameButTypeConvertFailed=new ArrayList<>();
    public CopyReport(T data){
        this.data=data;
    }
    public void addNotCopyFormSource(String s){
        notCopyFieldName.add(s);
    }
    public void addConvertFailed(String source,String target){
        Pair<String,String> pair=new Pair<>(source,target);
        sameNameButTypeConvertFailed.add(pair);
    }
}
