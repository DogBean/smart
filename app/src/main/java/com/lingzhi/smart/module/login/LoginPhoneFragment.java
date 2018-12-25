package com.lingzhi.smart.module.login;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.lingzhi.smart.R;


public class LoginPhoneFragment extends BaseFragment {
    private ImageView ivBack;
    private EditText etPhone;
    private ImageButton ibSendMsg;

    @Override
    protected int bindLayout() {
        return R.layout.fragment_login_phone;
    }

    @Override
    protected void initView() {
        ivBack = getContentView().findViewById(R.id.login_iv_back);
        etPhone = getContentView().findViewById(R.id.login_et_phone);
        ibSendMsg = getContentView().findViewById(R.id.login_ib_sendmsg);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        ivBack.setOnClickListener(ivBackListener);
        etPhone.addTextChangedListener(phoneWatcher);
        ibSendMsg.setOnClickListener(ibSendMsgListener);
    }

    private View.OnClickListener ivBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mContext instanceof LoginActivity) {
                LoginActivity loginActivity = (LoginActivity) mContext;
                loginActivity.backLoginFirst();
            }
        }
    };

    private View.OnClickListener ibSendMsgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mContext instanceof LoginActivity) {
                LoginActivity loginActivity = (LoginActivity) mContext;
                loginActivity.toMsgCodeEdit();
            }
        }
    };

    private TextWatcher phoneWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() == 11) {
                LoginActivity.phone = s.toString();
                ibSendMsg.setBackground(getResources().getDrawable(R.drawable.next_ok));
                ibSendMsg.setClickable(true);
            } else {
                ibSendMsg.setBackground(getResources().getDrawable(R.drawable.next_fail));
                ibSendMsg.setClickable(false);
            }
        }
    };
}
