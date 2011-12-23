package com.mcbouncer.http.response;

/**
 * HTTP error code container class. Setting the code or message
 * will automatically set the other variable as well.
 * 
 * @author yetanotherx
 */
public class HTTPCode {
    
    protected Integer code;
    protected String message;

    public HTTPCode(Integer code) {
        this.code = code;
        this.message = WebResponseCodes.getError(code);
    }

    public HTTPCode(String message) {
        this.message = message;
        this.code = WebResponseCodes.getError(message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
        this.message = WebResponseCodes.getError(code);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.code = WebResponseCodes.getError(message);
    }
    
}
