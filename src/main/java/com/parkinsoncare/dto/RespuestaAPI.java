package com.parkinsoncare.dto;

public class RespuestaAPI {
    private boolean success;
    private String message;
    private Object data;
    

    public RespuestaAPI(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    public RespuestaAPI(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    

    public static RespuestaAPI success(String message, Object data) {
        return new RespuestaAPI(true, message, data);
    }
    
    public static RespuestaAPI error(String message) {
        return new RespuestaAPI(false, message);
    }
    

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}
