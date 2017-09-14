package com.spring.common.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    /**
     * 列名
     * @return
     */
    public String name() default "";
    /**
     * 日期格式化字符串
     * @return
     */
    public String dateFormat() default "";
    /**
     * 导出次序
     * @return
     */
    public int order() default 0;
    
    /**
     * 加多一个类型，实现一个model里面可导多个Excel
     * @author Jevin.Xu
     * @return
     * @since JDK 1.7
     */
    public String type() default "";
    
}