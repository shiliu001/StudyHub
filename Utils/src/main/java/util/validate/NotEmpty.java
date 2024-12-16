package util.validate;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.TYPE})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotEmpty {
    /**
     * 失败原因
     * */
    String failMsg();
    /**
     * 是否禁止集合元素为空
     * */
    boolean isBanListSizeZero() default false;
    /**
     * 字符串时 是否禁止字符串为“”或“  ”
     * */
    boolean isBanStrBlank() default true;
    /**
     * 字符串时 是否禁止前端传入undefined
     * */
    boolean isBanStrUndefined() default true;
}
