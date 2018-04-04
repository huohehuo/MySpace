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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import lins.com.myspace.R;
import lins.com.myspace.databinding.ActivityChangeNameBinding;


/**
 * 修改昵称
 * Created by Hmei on 17/1/9.
 */

public class ChangeNameActivity extends AppCompatActivity {
    private ActivityChangeNameBinding binding;
    private static final String RESULT = "result";
    private static final String EXTRA  = "extra";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_name);
        binding.topBar.tvTopRight.setText("完成");
        binding.topBar.tvTopTitle.setText("修改名字");
        binding.etName.setText(getIntent().getStringExtra(EXTRA));
        binding.etName.setSelection(getIntent().getStringExtra(EXTRA).length());

        binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() > 0) {
                    binding.etName.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.close), null);
                } else {
                    binding.etName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    binding.etName.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.close), null);
                } else {
                    binding.etName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            }
        });
        binding.etName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP && binding.etName.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                    if (event.getRawX() >= -20 + (binding.etName.getRight() - binding.etName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        binding.etName.setText("");
                        return true;
                    }
                }
                return false;
            }
        });
        binding.topBar.tvTopRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String afterChange = binding.etName.getText().toString();
                if (TextUtils.isEmpty(afterChange)) {
                    Toast.makeText(ChangeNameActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra(RESULT, afterChange);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        binding.topBar.ivTopArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void start(Activity context, int requestCode, String name) {
        Intent starter = new Intent(context, ChangeNameActivity.class);
        starter.putExtra(EXTRA, name);
        context.startActivityForResult(starter, requestCode);
    }
}
