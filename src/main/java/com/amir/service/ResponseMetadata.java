package com.amir.service;

public class ResponseMetadata {

    private int status;
    private String message;
    private Object data;
    
    public ResponseMetadata() {
    }
    public ResponseMetadata(int status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    public boolean equals(Object obj) {
        return (obj instanceof ResponseMetadata) && 
  	  ((ResponseMetadata)obj).getStatus()== status && 
  	  ((ResponseMetadata)obj).getMessage().equals(message) &&
  	  ((ResponseMetadata)obj).getMessage().equals(data);
    }
}
