package lins.com.myspace.model.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

//import cn.bmob.push.PushConstants;

/**
 * Created by LINS on 2017/1/7.
 * Please Try Hard
 */
public class MyBmobRecever extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
//        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
//            Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra("msg"));
//        }
    }
}
