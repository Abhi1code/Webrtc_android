package com.socket.webrtc.util;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.IceCandidate;
import org.webrtc.SessionDescription;

public class Type_converter {

    public static String string_to_json(String name, String meta){
        String json = null;

        try {
            JSONObject jObj = new JSONObject();

            jObj.put("name", name);
            jObj.put("meta", meta);

            json = jObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static String string_to_json_offer(String name, String meta, String conn_name, SessionDescription offer){
        String json = null;

        try {
            JSONObject jObj = new JSONObject();

            jObj.put("user", name);
            jObj.put("meta", meta);
            jObj.put("offer", offer.description);
            jObj.put("connecteduser", conn_name);
            jObj.put("extra", "app");

            //jObj.put("type", offer.type.canonicalForm());

            json = jObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static String string_to_json_answer(String name, String meta, String conn_name, SessionDescription answer){
        String json = null;

        try {
            JSONObject jObj = new JSONObject();

            jObj.put("user", name);
            jObj.put("meta", meta);
            jObj.put("answer", answer.description);
            jObj.put("connecteduser", conn_name);
            jObj.put("extra", "app");
            //jObj.put("type", answer.type.canonicalForm());

            json = jObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static String string_to_json_candidate(String name, String meta, String conn_name, IceCandidate iceCandidate){
        String json = null;

        try {
            JSONObject jObj = new JSONObject();

            jObj.put("user", name);
            jObj.put("meta", meta);
            jObj.put("candidate", iceCandidate.sdp);
            jObj.put("connecteduser", conn_name);
            jObj.put("type", iceCandidate.sdpMLineIndex);
            jObj.put("id", iceCandidate.sdpMid);
            jObj.put("extra", "app");

            json = jObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static String get_name(JSONObject jsonObject) throws JSONException{
        return jsonObject.getString("name");
    }

    public static String get_status(JSONObject jsonObject) throws JSONException{
        return jsonObject.getString("status");
    }

    public static String get_meta(JSONObject jsonObject) throws JSONException{
        return jsonObject.getString("meta");
    }

    public static String get_offer(JSONObject jsonObject) throws JSONException{
        return jsonObject.getString("offer");
    }

    public static String get_sender_name(JSONObject jsonObject) throws JSONException{
        return jsonObject.getString("sender");
    }

    public static String get_answer(JSONObject jsonObject) throws JSONException{
        return jsonObject.getString("answer");
    }

    public static String get_type(JSONObject jsonObject) throws JSONException{
        return jsonObject.getString("type");
    }

    public static Integer get_type_int(JSONObject jsonObject) throws JSONException{
        return Integer.parseInt(jsonObject.getString("type"));
    }

    public static String get_id(JSONObject jsonObject) throws JSONException{
        return jsonObject.getString("id");
    }

    public static String get_candidate(JSONObject jsonObject) throws JSONException{
        return jsonObject.getString("icecand");
    }

    public static String getTime(String date){
        String[] split1 = date.split(" ");
        String[] split2 = split1[1].split(":");
        int hour = Integer.parseInt(split2[0]);
        int minute = Integer.parseInt(split2[1]);

        if (hour >= 12){
            return (hour-12)+":"+minute+" PM";
        } else {
            return hour+":"+minute+" AM";
        }
    }
}
