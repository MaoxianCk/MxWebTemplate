package com.mx.server.util.captcha;

 
import lombok.Data;

/**
 * @author Ck
 */
@Data
 
public class CaptchaData {
     
    private String code;

     
    private String imgCode;

     
    private String key;

     
    private Long expireTime;

     
    private String headerKey;
     
    private String headerTimeKey;

    public CaptchaData() {
    }

    public CaptchaData(String code, String imgCode, String key) {
        this.code = code;
        this.imgCode = imgCode;
        this.key = key;
    }

    public CaptchaData(String code, String imgCode, String key, Long time) {
        this.code = code;
        this.imgCode = imgCode;
        this.key = key;
        this.expireTime = time;
    }

    public CaptchaData(String code, String imgCode, String key, Long time, String headerKey, String headerTimeKey) {
        this.code = code;
        this.imgCode = imgCode;
        this.key = key;
        this.expireTime = time;
        this.headerKey = headerKey;
        this.headerTimeKey = headerTimeKey;
    }
}
