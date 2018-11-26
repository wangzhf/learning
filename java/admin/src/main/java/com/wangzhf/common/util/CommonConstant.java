package com.wangzhf.common.util;

/**
 * 全局常量
 */
public class CommonConstant {

    // 用户token异常
//    public static final Integer EX_USER_INVALID_CODE = 40101;
//
//    public static final Integer EX_USER_PASS_INVALID_CODE = 40001;
//
//    // 客户端token异常
//    public static final Integer EX_CLIENT_INVALID_CODE = 40301;
//
//    public static final Integer EX_CLIENT_FORBIDDEN_CODE = 40331;
//
//    public static final Integer EX_OTHER_CODE = 500;

    public enum Http {
        STATUS_OK(200, "ok"),

        EX_USER_INVALID_CODE(40101, "invalid user"),
        EX_USER_PASS_INVALID_CODE(40001, "invalid password"),

        EX_REQUEST_PARAM_MISSING_CODE(40201, "missing parameter"),

        EX_CLIENT_INVALID_CODE(40301, "invalid client"),
        EX_CLIENT_FORBIDDEN_CODE(40331, "forbidden"),

        EX_OTHER_CODE(500, "error");

        private int status;

        private String message;

        private Http(int status, String message) {
            this.setStatus(status);
            this.setMessage(message);
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public enum File {
        DOWNLOAD_SUCCESS("下载成功"),

        DOWNLOAD_FAILURE("下载失败");

        private String message;

        File(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
