package lins.com.myspace.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lins.com.myspace.R;
import lins.com.myspace.adapter.RbMessageAdapter;
import lins.com.myspace.adapter.TextBoxAdapter;
import lins.com.myspace.base.App;
import lins.com.myspace.base.MyBaseActivity;
import lins.com.myspace.entity.ChatMessageInfo;
import lins.com.myspace.entity.TextBoxInfo;
import lins.com.myspace.model.RbManager;

/**
 * Created by LINS on 2016/12/26.
 * Please Try Hard
 */
public class ChatShowActivity extends MyBaseActivity {
    private ListView listView;
    private RbMessageAdapter mAdapter;
    private List<ChatMessageInfo> mDatas;
    private RecyclerView recyclerView;
    private String toMsg;
    private EditText mInputMsg;
    private ImageView iv_left,iv_right;
    private Button mSendMsg;

    private TextBoxAdapter textBoxAdapter;
    private boolean isShow=false;
    private ArrayList<TextBoxInfo> setRightData() {
        ArrayList<TextBoxInfo> newsList = new ArrayList<TextBoxInfo>();
        newsList.add(0, new TextBoxInfo("", "可选例子："));
        newsList.add(1, new TextBoxInfo("", "南宁今天天气怎样"));
        newsList.add(2, new TextBoxInfo("", "查快递单号"));
        newsList.add(3, new TextBoxInfo("", "今日农历"));
        newsList.add(4, new TextBoxInfo("", "自己看着办吧"));
        newsList.add(5, new TextBoxInfo("", "随便稳点什么"));
        newsList.add(6, new TextBoxInfo("", "机器人，你懂的，都不够作者聪明"));
        return newsList;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            //等待接受子线程完成数据的返回
            ChatMessageInfo fromMessage = (ChatMessageInfo) msg.obj;
            mDatas.add(fromMessage);
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_show);
        //初始化控件
        initView();
        //事先添加一点数据
        initDatas();
        //初始化事件
        //initListener();
//        rc_mDatas = TextBoxManager.query();
        textBoxAdapter = new TextBoxAdapter(App.getContext(), setRightData());
        recyclerView.setAdapter(textBoxAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));

        textBoxAdapter.setmOnItemClickListener(new TextBoxAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, TextBoxInfo textBoxInfo) {
                mInputMsg.setText(textBoxInfo.getName());
            }


        });
    }

    public String getDataFromWeb(String msg) {
        String result = "";
        String url = RbManager.setParams(msg);
        RequestQueue queue = Volley.newRequestQueue(ChatShowActivity.this);
        //String toMsg = mInputMsg.getText().toString();
        Log.d("拼接url并把中文转码：", url);
        //?dtype=json&city=北京&bus=478&key=6f0e2a5d983cd6045f11eb0086eb5b3c
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 将会获取到返回的数据
                        Log.d("控制台打印出json数据：", response);
                        ChatMessageInfo fromMessage = RbManager.sendMessage(response);
                        Message m = Message.obtain();
                        m.obj = fromMessage;
                        mHandler.sendMessage(m);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ChatMessageInfo toMessage = new ChatMessageInfo();
                toMessage.setDate(new Date());
                toMessage.setMsg("bibibi~ 网络有些问题哟~ ");
                toMessage.setType(ChatMessageInfo.Type.INCOMING);
                mDatas.add(toMessage);
                mAdapter.notifyDataSetChanged();
                Log.d("ChatShowActivity:", "网络错误。。。。");
                Toast.makeText(ChatShowActivity.this, "网络错误，请重试", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
        return result;

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_chat_left:
                    myFinish();
                    break;
                case R.id.iv_chat_right:
                    if (!isShow){
                        recyclerView.setVisibility(View.VISIBLE);
                        isShow=true;
                    }else{
                        recyclerView.setVisibility(View.GONE);
                        isShow=false;
                    }
                    break;
                case R.id.bt_chat_send:
                    toMsg = mInputMsg.getText().toString();
                    if (TextUtils.isEmpty(toMsg)) {
                        Toast.makeText(ChatShowActivity.this, "发送消息不能为空",
                                Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        //将发送出去的信息加入到listview中
                        ChatMessageInfo toMessage = new ChatMessageInfo();
                        toMessage.setDate(new Date());
                        toMessage.setMsg(toMsg);
                        toMessage.setType(ChatMessageInfo.Type.OUTCOMING);
                        mDatas.add(toMessage);
                        mAdapter.notifyDataSetChanged();
                        mInputMsg.setText("");
                        //发送数据并且把接收的数据放入listview中
                        getDataFromWeb(toMsg);

                    }
                    break;
            }
        }
    };

    private void initDatas() {
        // TODO 自动生成的方法存根
        mDatas = new ArrayList<ChatMessageInfo>();
        mDatas.add(new ChatMessageInfo("雷猴啊，我是隔壁家成绩比你好的小明", ChatMessageInfo.Type.INCOMING, new Date()));
//        mDatas.add(new ChatMessageInfo("你好啊", ChatMessageInfo.Type.OUTCOMING, new Date()));
        mAdapter = new RbMessageAdapter(this, mDatas,listView);
        listView.setAdapter(mAdapter);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lv_chat_show);
        mInputMsg = (EditText) findViewById(R.id.et_chat_input);
        mSendMsg = (Button) findViewById(R.id.bt_chat_send);
        iv_left = (ImageView) findViewById(R.id.iv_chat_left);
        iv_right = (ImageView) findViewById(R.id.iv_chat_right);
        recyclerView = (RecyclerView) findViewById(R.id.rc_chat_choice);

        mSendMsg.setOnClickListener(clickListener);
        iv_right.setOnClickListener(clickListener);
        iv_left.setOnClickListener(clickListener);
    }

//    private void initListener() {
//        // TODO 自动生成的方法存根
//        mSendMsg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                // TODO 自动生成的方法存根
//                final String toMsg = mInputMsg.getText().toString();
//                if (TextUtils.isEmpty(toMsg)) {
//                    Toast.makeText(ChatShowActivity.this, "发送消息不能为空",
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                ChatMessageInfo toMessage = new ChatMessageInfo();
//                toMessage.setDate(new Date());
//                toMessage.setMsg(toMsg);
//                toMessage.setType(ChatMessageInfo.Type.OUTCOMING);
//                mDatas.add(toMessage);
//                mAdapter.notifyDataSetChanged();
//
//                mInputMsg.setText("");
//                new Thread() {
//                    public void run() {
//
//                        ChatMessageInfo fromMessage = RbUtil.sendMessage(toMsg);
//                        Message m = Message.obtain();
//                        m.obj = fromMessage;
//                        mHandler.sendMessage(m);
//                    }
//
//                    ;
//                }.start();
//
//            }
//        });
//    }


}
