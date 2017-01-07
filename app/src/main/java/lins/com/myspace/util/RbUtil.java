package lins.com.myspace.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;

import lins.com.myspace.entity.ChatMessageInfo;
import lins.com.myspace.entity.ChatMessageResult;

/**
 * Created by LINS on 2016/12/26.
 * Please Try Hard
 */
public class RbUtil {
    private static final String URL="http://www.tuling123.com/openapi/api";
    private static final String APIKEY="62422428c1a7484b80a5036616058972";

    /**发送一个消息，得到返回的消息
     * @param
     * @return
     */
    public static ChatMessageInfo sendMessage(String msg){
        ChatMessageInfo chatMessage = new ChatMessageInfo();
       String jsonRes = doGet(msg);
        LogUtil.d(jsonRes);
        Gson gson = new Gson();
        ChatMessageResult result = null;
        try {
            result =gson.fromJson(jsonRes, ChatMessageResult.class);
            chatMessage.setMsg(result.getText());
        } catch (JsonSyntaxException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
            chatMessage.setMsg("服务器繁忙，请稍后再试");
        }
        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessageInfo.Type.INCOMING);
        return chatMessage;
    }


    public static String doGet(String msg){
        String result = "";
        InputStream is=null;
        ByteArrayOutputStream baos = null;
        String url = setParams(msg);
        LogUtil.d(url);
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

    //拼接url
    private static String setParams(String msg) {

        String url="";
        try {
            url = URL+"?key="+APIKEY+"&info="+ URLEncoder.encode(msg,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return url;
    }
}
