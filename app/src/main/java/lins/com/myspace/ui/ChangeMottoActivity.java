package lins.com.myspace.ui;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import lins.com.myspace.R;
import lins.com.myspace.databinding.ActivityChangeMottoBinding;

/**
 * 修改签名
 * Created by Hmei on 17/1/9.
 */

public class ChangeMottoActivity extends AppCompatActivity {
    private ActivityChangeMottoBinding binding;
    private static final String RESULT = "result";
    private static final String EXTRA  = "extra";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_motto);

        binding.topBar.tvTopTitle.setText("修改签名");
        binding.topBar.tvTopRight.setText("完成");
        binding.etMotto.setText(getIntent().getStringExtra(EXTRA));
        binding.etMotto.setSelection(getIntent().getStringExtra(EXTRA).length());

        binding.topBar.ivTopArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.topBar.tvTopRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String afterChange = binding.etMotto.getText().toString();
                if (TextUtils.isEmpty(afterChange)||afterChange.length()>250) {
                    Toast.makeText(ChangeMottoActivity.this, "长度不合格", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra(RESULT, afterChange);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        binding.etMotto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int count = s.length();
                if (count > 250) {
                    count = 250 - count;
                    binding.tvCount.setTextColor(getResources().getColor(R.color.error_red));
                } else {
                    binding.tvCount.setTextColor(getResources().getColor(R.color.gray_text));
                }
                binding.tvCount.setText(count + "");
            }
        });
    }

    public static void start(Activity context, int requestCode, String motto) {
        Intent starter = new Intent(context, ChangeMottoActivity.class);
        starter.putExtra(EXTRA, motto);
        context.startActivityForResult(starter, requestCode);
    }
}
