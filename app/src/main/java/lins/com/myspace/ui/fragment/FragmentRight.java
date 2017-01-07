package lins.com.myspace.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lins.com.myspace.R;


/**
 * Created by LINS on 2016/12/20.
 * Please Try Hard
 */
public class FragmentRight extends Fragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_right, container,false);
        // TODO 自动生成的方法存根
        return view;
    }

}
