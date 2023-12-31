package com.example.mygamesdk.bean;

import com.example.mygamesdk.tool.GameMD5Tool;

import org.json.JSONException;
import org.json.JSONObject;


public class GameVerifyBean {
	public String sid;
	public String channel;
	public String version;
	public String userId;
	public String gameId;
	
	public String toJson(String appKey) {
		String preSign = gameId + "|" + channel + "|" + userId + "|" + sid + "|" + version + "|" + appKey;
		try {
			JSONObject object = new JSONObject();
			object.put("sid", sid);
			object.put("channel", channel);
			object.put("version", version);
			object.put("userId", userId);
			object.put("gameId", gameId);
			object.put("sign", GameMD5Tool.calcMD5(preSign.getBytes()));
			return object.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
}
