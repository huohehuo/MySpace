package lins.com.myspace.util;

import android.os.Environment;

/**
 * Created by LINS on 2016/12/29.
 * Please Try Hard
 */
public class ConfigUtil {
    //图灵
    public static final String URL = "http://www.tuling123.com/openapi/api";
    public static final String APIKEY = "62422428c1a7484b80a5036616058972";

    //七牛
    static final String Qiniu_BucketName = "myspace";
    static final String Qiniu_AccessKey  = "1z9s_GAMHFa23N_E15oSlfgtA3gDLDMl5qyo9MmT";
    static final String Qiniu_SecretKey  = "IYCo_Oe40zLn0cn8K91anzjqJZM1nWna8xFwdS0M";
    static final String Qiniu_BucketUrl  = "http://om4nwud6z.bkt.clouddn.com/";
    //头像
    public static final String PATH_SELECT_AVATAR      = Environment.getExternalStorageDirectory().getAbsolutePath()+"/myspace/avatar";

    public static final String PATH_SELECT_COVER     =Environment.getExternalStorageDirectory().getAbsolutePath()+"/live36G/cover";


}
