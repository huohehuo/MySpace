package lins.com.myspace.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import lins.com.myspace.R;


/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class FragmentUser extends Fragment {
    private View view;
    private EditText et_find;
    private TextView tv_shownothing;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        view = inflater.inflate(R.layout.fragment_user, null);



        return view;
    }
}
