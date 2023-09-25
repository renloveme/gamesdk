package com.example.mygamesdk.mvp.presenterImp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.mygamesdk.activity.ActivityFactory;
import com.example.mygamesdk.activity.GameSdKActivity;
import com.example.mygamesdk.callback.SdkCallbackListener;
import com.example.mygamesdk.mvp.model.MVPLogingBean;
import com.example.mygamesdk.mvp.presenter.LogingPresenter;
import com.example.mygamesdk.mvp.view.LoginView;
import com.example.mygamesdk.mvpbase.BasePresenterImpl;
import com.example.mygamesdk.services.IDataService;
import com.example.mygamesdk.services.SharedPrefDataService;
import com.example.mygamesdk.solid.GameSdkConstants;
import com.example.mygamesdk.tool.GameMD5Tool;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LogingPresenterImp extends BasePresenterImpl<LoginView> implements
		LogingPresenter {
	private static final MediaType JSON = MediaType
			.parse("application/json;charset=utf-8");

	private LoginView mainBaseView;
	private int errorTime = 0;
	private static IDataService dao = null;
	private Context mContext;
	private String userName;
	private String passWord;

	private String userParams;
	private String requestParams;
	public SdkCallbackListener<String> listener = null;


	public IDataService getIdaoFactory(Context context) {
		if (dao == null) {
			dao = new SharedPrefDataService(context);
		}
		return dao;
	}

	private void loginHttp(String param, String Url, final Activity activity) {
		RequestBody requestBody = RequestBody.create(JSON, param);

	}

	public String createdPrams(String name, String word) {
		StringBuilder preSign = new StringBuilder();
		preSign.append("appId=").append(GameSdkConstants.APPINFO.appId);
		preSign.append("&type=").append(GameSdkConstants.PLATFORM);
		preSign.append("&account=").append(name);
		preSign.append("&password=").append(word);
		preSign.append("&ext=").append(GameSdkConstants.APPINFO.ext);
		preSign.append("||").append(GameSdkConstants.APPINFO.appKey);

		String sign = GameMD5Tool.calcMD5(preSign.toString().getBytes());

		try {
			JSONObject param = new JSONObject();
			param.put("appId", GameSdkConstants.APPINFO.appId);
			param.put("type", GameSdkConstants.PLATFORM);
			param.put("account", name);
			param.put("password", word);
			param.put("ext", GameSdkConstants.APPINFO.ext);
			param.put("version", GameSdkConstants.VERSION);

			param.put("platformType", "2");
			param.put("sign", sign);

			requestParams = param.toString();

		} catch (Exception e) {

		}

		return requestParams;
	}

	// 跳转到验证的Activity
	public void showActivity(Context context, ActivityFactory layoutId,
			Map<String, String> param) {
		Intent intent = new Intent(context, GameSdKActivity.class);

		intent.putExtra("layoutId", layoutId.toString());
		if (param != null) {
			Set<Map.Entry<String, String>> keySet = param.entrySet();
			Iterator<Map.Entry<String, String>> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				intent.putExtra(entry.getKey(), entry.getValue());
			}
		}

		context.startActivity(intent);

		if (context instanceof Activity) {
			((Activity) context).finish();
		}
	}

	@Override
	public void login(MVPLogingBean user, Context context) {
		// TODO Auto-generated method stub
		
	}



}
