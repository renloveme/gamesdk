package com.example.mygamesdk;




 ;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

 

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
 
import android.widget.Toast;

import com.example.mygamesdk.bean.AppInfo;
import com.example.mygamesdk.bean.Mode;
import com.example.mygamesdk.callback.SdkCallbackListener;
import com.example.mygamesdk.exception.GameSDKException;
import com.example.mygamesdk.solid.GameSdkConstants;
import com.example.mygamesdk.solid.GameSdkLogic;
import com.example.mygamesdk.solid.GameStatusCode;



public class DemoMainActivity extends Activity {


    private static final String TAG = "MainActivity";

    private static boolean loginStats = false;
    private Button testbtu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        int mainid = DemoMainActivity.this.getResources().getIdentifier("activity_main", "layout", DemoMainActivity.this.getPackageName());

        setContentView(mainid);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


//		全局配置横竖屏 默认是横屏 这个是竖屏的设置 GameSdkConstants.isPORTRAIT = true;
//      (因为很多游戏都是横屏嘛~但是有些游戏是竖屏，所以也要设计竖屏的界面以及对外设置的接口)
        GameSdkConstants.isPORTRAIT = true;

        AppInfo appInfo = new AppInfo();

//		这里是渠道SDK的请求参数

        appInfo.appId = "12306";
        appInfo.appKey = "3x5975397917b2f62031d0";
        appInfo.ext = "1|2";

        try {

            GameSdkLogic.getInstance().setSdkMode(Mode.debug);

            GameSdkLogic.getInstance().init(this, appInfo,
                    new SdkCallbackListener<String>() {
                        @Override
                        public void callback(int code, String response) {
                            Log.i(TAG, "初始化处理代码：" + code + "，返回信息：" + response);
                            if (GameStatusCode.SUCCESS == code) {
                                Toast.makeText(DemoMainActivity.this, "初始化成功",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DemoMainActivity.this, response,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(DemoMainActivity.this,
                    "SDK初始化异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        //登录接口
        /***
         * 一般来说 需要在用户登录成功以后，才可以登录
         */

        int loginid = DemoMainActivity.this.getResources().getIdentifier("gameLoginBtn", "id", DemoMainActivity.this.getPackageName());
        Button button = (Button) findViewById(loginid);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    GameSdkLogic.getInstance().login(DemoMainActivity.this,
                            new SdkCallbackListener<String>() {
                                @Override
                                public void callback(int code, String response) {
                                    Log.i(TAG, "登录返回代码：" + code + "，返回信息是："
                                            + response);
                                    if (GameStatusCode.SUCCESS == code) {
                                        loginStats = true;
                                        // TODO: 登录成功，response为sid，进行处理
                                        Toast.makeText(DemoMainActivity.this,
                                                        "登录成功", Toast.LENGTH_SHORT)
                                                .show();

                                    } else if (GameStatusCode.REG_SUCCESS == code) {
                                        // 登录成功，response为错误信息
                                        Toast.makeText(DemoMainActivity.this,
                                                        "注册成功", Toast.LENGTH_SHORT)
                                                .show();
                                    } else {
                                        Toast.makeText(DemoMainActivity.this,
                                                        response, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                }
                            });
                } catch (Exception e) {
                    Toast.makeText(DemoMainActivity.this,
                                    "SDK登录异常：" + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

//		支付接口
        /***
         * 一般来说 需要在用户登录成功以后，才可以支付
         */
        int payid = DemoMainActivity.this.getResources().getIdentifier("gamePayBtn", "id", DemoMainActivity.this.getPackageName());
        Button payBtn = (Button) findViewById(payid);
        payBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    GameSdkLogic.getInstance().startPay(DemoMainActivity.this,
                            "CP_3456345",
                            "12306" ,
                            "游戏金币",
                            1,
                            "梦幻西游",
                            new SdkCallbackListener<String>() {
                                @Override
                                public void callback(int code, String response) {
                                    // TODO Auto-generated method stub

                                }
                            });
                } catch (GameSDKException e) {
                    Toast.makeText(DemoMainActivity.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

//		提交游戏信息
        int subInfoid = DemoMainActivity.this.getResources().getIdentifier("subInfo", "id", DemoMainActivity.this.getPackageName());
        Button sub = (Button) findViewById(subInfoid);
        sub.setOnClickListener(new OnClickListener() {
            /**
             * @param
             * @param
             * @param    角色ID
             * @param ro leName 角色名
             * @param role Level 角色等级
             * @param zone Id  区ID
             * @param zone Name 区名称
             * @param data Type 数据类型
             * @throws Exception
             */
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    GameSdkLogic.getInstance().submitGameInfo(DemoMainActivity.this, "uid:111", "123", "张三", "129", "9527", "齐云楼", "1");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });


//		显示浮标
        /***
         * 一般来说 需要在用户登录成功以后，才可以显示浮标
         */
        int showflowid = DemoMainActivity.this.getResources().getIdentifier("showFloatBtn", "id", DemoMainActivity.this.getPackageName());
        Button showFloatBtn = (Button) findViewById(showflowid);
        showFloatBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                GameSdkLogic.getInstance().showToolBar(DemoMainActivity.this);
            }
        });

//		隐藏浮标
        /***
         * 一般来说 需要在用户登录成功以后，才可以隐藏浮标
         */
        int hideid = DemoMainActivity.this.getResources().getIdentifier("hideFloatBtn", "id", DemoMainActivity.this.getPackageName());
        Button hideFloatBtn = (Button) findViewById(hideid);
        hideFloatBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                GameSdkLogic.getInstance().hideToolBar(DemoMainActivity.this);
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        GameSdkLogic.getInstance().onSdkDestory(this);
        super.onDestroy();
    }
}