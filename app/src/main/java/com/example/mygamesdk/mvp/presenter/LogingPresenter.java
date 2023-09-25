package com.example.mygamesdk.mvp.presenter;

import android.content.Context;

import com.example.mygamesdk.mvp.model.MVPLogingBean;
import com.example.mygamesdk.mvp.view.LoginView;
import com.example.mygamesdk.mvpbase.BasePresenter;

public interface LogingPresenter extends BasePresenter<LoginView>{
	 void login(MVPLogingBean user ,Context context) ;
}
