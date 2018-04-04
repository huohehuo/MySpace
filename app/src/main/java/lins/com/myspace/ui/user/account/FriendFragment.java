package lins.com.myspace.ui.user.account;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lins.com.myspace.R;
import lins.com.myspace.databinding.FragmentFriendBinding;


/**
 * Created by LINS on 2017/2/22.
 */

public class FriendFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentFriendBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_friend,container,false);
        return binding.getRoot();
    }
}
