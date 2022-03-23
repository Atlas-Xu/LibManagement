package cn.xchub.exception_advice;

public enum BusinessExceptionEnum {
    SERVER_ERROR(500, "服务器异常！"),
    NO_STOCK(1001,"---->库存不足!"),
    FAIL(1, "失败"),
    FACE_ERROR(600,"未检测到人脸信息！")
            ;

    private Integer code;
    private String message;

    BusinessExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
