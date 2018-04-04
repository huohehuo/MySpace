//package lins.com.myspace.util;
//
//import android.util.Base64;
//import android.util.Log;
//
//import com.qiniu.android.http.ResponseInfo;
//import com.qiniu.android.storage.UpCompletionHandler;
//import com.qiniu.android.storage.UploadManager;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//
//import lins.com.myspace.base.LinsApp;
//
///**
// * Created by aid on 8/10/16.
// */
//
//public class Qiniu {
//
//    public interface Callback{
//        public void uploadResult(String remoteUrl, boolean ok, String error);
//    }
//
//    private static final String tag = "Qiniu";
//
//    public static void uploadFile(String fileUrl, final Callback callback){
//
//        String token = Qiniu.createUploadToken(ConfigUtil.Qiniu_BucketName);
//
////        UploadOptions options = new UploadOptions(null, null, true, new UpProgressHandler() {
////            @Override
////            public void progress(String key, double percent) {
//////                Logger.e(key+": "+percent);
////                // TODO
////            }
////        }, null);
//
//        UploadManager qiniuUploadManager =  LinsApp.getQiniuUploadManager();
//        qiniuUploadManager.put(fileUrl, null, token, new UpCompletionHandler() {
//            @Override
//            public void complete(String key, ResponseInfo info, JSONObject response) {
//
//                if (!info.isOK()){
//                    callback.uploadResult(null, false, info.error);
//                    return;
//                }
//
//                String imagekey = "";
//                try {
//                    imagekey = response.getString("key");
//                }catch (JSONException e){
//                    callback.uploadResult(null, false, Log.getStackTraceString(e));
//                    return;
//                }
//
//                callback.uploadResult(ConfigUtil.Qiniu_BucketUrl+imagekey, true, null);
//            }
//        }, null);
//    }
//
//    // 七牛，创建上传Token
//    private static String createUploadToken(String bucket){
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("scope", bucket);
//            jsonObject.put("deadline", System.currentTimeMillis()/1000L+60*60);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String putPolicy = jsonObject.toString();
//        String encodedPutPolicy = Base64.encodeToString(putPolicy.getBytes(), Base64.URL_SAFE| Base64.NO_WRAP);
//        byte[] sign = hmacSha1(encodedPutPolicy, ConfigUtil.Qiniu_SecretKey);
//        String encodedSign = Base64.encodeToString(sign, Base64.URL_SAFE| Base64.NO_WRAP);
//
//        String tokent = new StringBuffer()
//                .append(ConfigUtil.Qiniu_AccessKey)
//                .append(":")
//                .append(encodedSign)
//                .append(":")
//                .append(encodedPutPolicy)
//                .toString();
//
//        return tokent;
//    }
//
//    private static byte[] hmacSha1(String value, String key){
//        String type = "HmacSHA1";
//        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), type);
//        Mac mac = null;
//        byte[] bytes = null;
//        try {
//            mac = Mac.getInstance(type);
//            mac.init(secret);
//            bytes = mac.doFinal(value.getBytes());
//        }catch (InvalidKeyException e1){
//            Log.e(tag, Log.getStackTraceString(e1));
//            return null;
//        }catch (NoSuchAlgorithmException e2){
//            Log.e(tag, Log.getStackTraceString(e2));
//            return null;
//        }
//        return bytes;
//    }
//}
