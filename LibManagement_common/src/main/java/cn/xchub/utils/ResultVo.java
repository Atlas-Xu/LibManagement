package cn.xchub.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * VO对应于页面上需要显示的数据（表单）
 * CRUD结果返回显示
 * */

@Data
@AllArgsConstructor
public class ResultVo<T> {
    private String msg;
    private int code;
    private T data;
}
