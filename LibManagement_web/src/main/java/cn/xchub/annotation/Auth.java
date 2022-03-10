package cn.xchub.annotation;

import java.lang.annotation.*;

/**
 * 使用该注解的方法一定需要经过token验证
 * */


@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
}
