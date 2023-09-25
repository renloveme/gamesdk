package com.example.mygamesdk.callback;


public interface SdkCallbackListener<T> {
	
	public void callback(int code, T response);
}
