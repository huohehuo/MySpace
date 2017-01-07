package lins.com.myspace.util;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences存储工具类
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class SharedPreferencesUtil {
    /**
     * 此方法用于注册或登录后，保存解析得到的内容
     */
//	public static void saveRegister(Context context,BaseEntity<Register> register){
//		SharedPreferences sp = context.getSharedPreferences("register",Context.MODE_PRIVATE);
//		Editor editor = sp.edit();
//		editor.putString("message", register.getMessage());
//		editor.putInt("status", Integer.parseInt(register.getStatus()));
//		Register data = register.getData();
//		editor.putString("result", data.getResult());
//		editor.putString("token", data.getToken());
//		editor.putString("explain", data.getExplain());
//		editor.commit();
//	}

    /**
     * 保存用户数据
     */
//	 public static void saveUser(Context context,BaseEntity<User> user){
//		 /**
//		  *  b保存文件名为user
//		  *  其中用户名保存的键值对为：userName;
//		  *  头像的路径为：headImage
//		  */
//		 SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
//		 Editor editor = sp.edit();
//		 User core = user.getData();
//		 editor.putString("userName", core.getUid());
//		 editor.putString("headImage", core.getPortrait());
//		 editor.commit();
//	 }
    /**
     * 清楚用户数据
     */
    public static void clearUser(Context context){
        //将本地保存的用户数据“user”文件清除
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
    /**
     * 获取用户令牌
     */
    public static String getToken(Context context){
        SharedPreferences sp = context.getSharedPreferences("register", Context.MODE_PRIVATE);
        return sp.getString("token", "");
    }
    /**
     * 获取用户名和用户头像地址
     */
    public static String[] getUserNameAndPhoto(Context context){
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return new String[]{sp.getString("userName", ""),sp.getString("headImage", "")};
    }

    /**
     * 保存用户头像本地路径
     */
    public static void saveUserLocalIcon(Context context,String path){
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("imagePath", path);
        editor.commit();
    }
    /**
     * 获取保存的本地头像路径
     */
    public static String getuserLocalIcon(Context context){
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sp.getString("imagePath", null);
    }

    //判断是否已登录
    public static boolean hasUser(Context context){
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        if (sp.getString("userName", null)==null) {
            return false;
        }
        return true;

    }
    /**
     * 获取用户名
     */
    public static String getUserName(Context context){
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sp.getString("userName", "");
    }
}

