package com.example.mygamesdk.mvp.view;

import com.example.mygamesdk.mvpbase.BaseView;

public interface LoginView extends BaseView{
	    void loginSuccess(String msg) ;
	    void loginFailed(String msg) ;

}
