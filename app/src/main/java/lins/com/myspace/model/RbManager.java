package lins.com.myspace.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;

import lins.com.myspace.entity.ChatMessageInfo;
import lins.com.myspace.util.ConfigUtil;
import lins.com.myspace.util.LogUtil;

/**
 * Created by LINS on 2016/12/26.
 * Please Try Hard
 */
public class RbManager {



    public static ChatMessageInfo sendMessage(String str){
        ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
        //String str = doGet(jsonRes);
        try {
            JSONObject jsonObject = new JSONObject(str);
            String text = jsonObject.getString("text");
            if (jsonObject.isNull("url")){
                chatMessageInfo.setMsg(text);
            }else{
                String url = jsonObject.getString("url");
                LogUtil.d("RbManager获得连接：",url);
                chatMessageInfo.setMsg(text);
                chatMessageInfo.setUrl(url);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            chatMessageInfo.setMsg("服务器繁忙。。。。");
        }
        chatMessageInfo.setDate(new Date());
        chatMessageInfo.setType(ChatMessageInfo.Type.INCOMING);
        return chatMessageInfo;
    }

    public static String doGet(String msg){
        String result = "";
        InputStream is=null;
        ByteArrayOutputStream baos = null;
        String url = setParams(msg);
        try {
            java.net.URL urlNet = new java.net.URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlNet.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");

            is = conn.getInputStream();
            int len = -1;
            byte[] buf = new byte[128];
            baos = new ByteArrayOutputStream();

            while((len = is.read(buf)) != -1){
                baos.write(buf,0,len);
            }
            baos.flush();
            result = new String(baos.toByteArray());
        } catch (MalformedURLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } finally{
            if(baos !=null)
            {
                try {
                    baos.close();
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }
            if(is !=null){
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }
        }
        return result;

    }


    //拼接url,把文字转换格式，避免构造的url中文识别问题
    public static String setParams(String msg) {
        String url = "";
        try {
            url = ConfigUtil.URL + "?key=" + ConfigUtil.APIKEY + "&info=" + URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
