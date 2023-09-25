package com.example.mygamesdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.mygamesdk.services.Dispatcher;
import com.example.mygamesdk.solid.GameSdkConstants;
import com.example.mygamesdk.tool.GameAssetTool;
import com.example.mygamesdk.tool.GameSmsTool;
import com.example.mygamesdk.tool.GameUiTool;
import com.example.mygamesdk.widget.GameSdkHeadererLayout;
import com.example.mygamesdk.widget.GameSdkToast;

final class GameFindPwdTypeActivity extends GameFindPwdActivity {
	private String prefix  = "gamesdk/images/";
	GameFindPwdTypeActivity() {
	}

	@Override
	public LinearLayout onCreate(final Activity activity) {
		Intent intent = activity.getIntent();
		final String account = intent.getStringExtra("account");
		final String phone = intent.getStringExtra("phone");
		final String email = intent.getStringExtra("email");
		final RadioButton phoneFiPwdBtn = new RadioButton(activity);
		final RadioButton emailFiPwdBtn = new RadioButton(activity);

		LinearLayout.LayoutParams radioParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		radioParams.setMargins(0, (int)(GameSdkConstants.DEVICE_INFO.windowHeightPx * 0.05), 0, (int)(GameSdkConstants.DEVICE_INFO.windowHeightPx * 0.05));
		if (!TextUtils.isEmpty(phone)) {
			String text = asset.getLangProperty(activity, "findpwd_phone_radio_text");
			int start = text.indexOf("（");
			if (start < 0) {
				start = text.indexOf("(");
			}

			if (start < 0) {
				phoneFiPwdBtn.setText(text);
			} else {
				SpannableStringBuilder builder = new SpannableStringBuilder(text);
				builder.setSpan(new ForegroundColorSpan(Color.RED), start + 1, text.length() - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
				phoneFiPwdBtn.setText(builder);
			}
			phoneFiPwdBtn.setLayoutParams(radioParams);
			phoneFiPwdBtn.setTextColor(Color.rgb(90, 102, 127));
			phoneFiPwdBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, GameUiTool.getInstance().textSize(22F, true));
			phoneFiPwdBtn.setButtonDrawable(GameAssetTool.getInstance().decodeDrawableFromAsset(activity,
					prefix+"gamesdk_radio_uncheck.png",1.2f));
			phoneFiPwdBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						phoneFiPwdBtn.setButtonDrawable(GameAssetTool.getInstance().decodeDrawableFromAsset
								(activity,  prefix+"gamesdk_radio_checked.png",1.2f));
					} else {
						phoneFiPwdBtn.setButtonDrawable(GameAssetTool.getInstance().decodeDrawableFromAsset(activity,
								prefix+"gamesdk_radio_uncheck.png",1.2f));
					}
				}
			});
		} else {
			phoneFiPwdBtn.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
			phoneFiPwdBtn.setEnabled(false);
		}

		if (!TextUtils.isEmpty(email)) {
			emailFiPwdBtn.setLayoutParams(radioParams);
			emailFiPwdBtn.setText(asset.getLangProperty(activity, "findpwd_email_radio_text"));
			emailFiPwdBtn.setTextColor(Color.rgb(90, 102, 127));
			emailFiPwdBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, GameUiTool.getInstance().textSize(22F, true));
			emailFiPwdBtn.setButtonDrawable(GameAssetTool.getInstance().decodeDrawableFromAsset(activity,
					prefix+"gamesdk_radio_uncheck.png",1.2f));
			emailFiPwdBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						emailFiPwdBtn.setButtonDrawable(GameAssetTool.getInstance().decodeDrawableFromAsset(activity,
								prefix+"gamesdk_radio_checked.png",1.2f));
					} else {
						emailFiPwdBtn.setButtonDrawable(GameAssetTool.getInstance().decodeDrawableFromAsset(activity,
								prefix+"gamesdk_radio_uncheck.png",1.2f));
					}
				}
			});
		} else {
			emailFiPwdBtn.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
			emailFiPwdBtn.setEnabled(false);
		}

		View.OnClickListener clickListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isFastClick()) {
					return;
				}
				if (TextUtils.isEmpty(account)) {
					GameSdkToast.getInstance().show(activity, asset.getLangProperty(activity, "findpwd_no_account"));
					return;
				}

				if (view == phoneFiPwdBtn) {
					try {
						Dispatcher.getInstance().resetPwd(activity, account, 1, phone);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (view == emailFiPwdBtn) {
					try {
						Dispatcher.getInstance().resetPwd(activity, account, 2, email);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};

		phoneFiPwdBtn.setOnClickListener(clickListener);
		emailFiPwdBtn.setOnClickListener(clickListener);

		 GameSdkHeadererLayout header = GameUiTool.getInstance().getTitle(activity, "findpwd_vcode_btn");
		 LinearLayout edtxLayout = new LinearLayout(activity);
			LinearLayout.LayoutParams radio = new LinearLayout.LayoutParams(-1, -2);
			radio.setMargins(0, 0, 0, (int)(GameSdkConstants.DEVICE_INFO.windowHeightPx * 0.2));
			edtxLayout.setLayoutParams(radio);
			edtxLayout.setGravity(	Gravity.LEFT);
			edtxLayout.setOrientation(LinearLayout.VERTICAL);
			edtxLayout.addView(phoneFiPwdBtn);
			edtxLayout.addView(emailFiPwdBtn);
		GameSmsTool.startSmsListen(activity);
		return GameUiTool.getInstance().parent(activity, header, edtxLayout);
	}

}
