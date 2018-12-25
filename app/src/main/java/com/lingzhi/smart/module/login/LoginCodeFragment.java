package com.lingzhi.smart.module.login;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ebensz.shop.net.utils.Constants;
import com.ebensz.shop.net.utils.SPUtils;
import com.lingzhi.smart.R;
import com.lingzhi.smart.app.SmartApplication;
import com.lingzhi.smart.data.bean.LoginResult;
import com.lingzhi.smart.data.request.AccountRequest;
import com.lingzhi.smart.data.source.remote.ApiHelper;
import com.lingzhi.smart.data.source.remote.Resp;
import com.lingzhi.smart.module.esp.EsptouchDemoActivity;
import com.lingzhi.smart.view.widget.PhoneCode;
import com.lingzhi.smart.view.widget.TimerText;

import io.reactivex.functions.Consumer;


public class LoginCodeFragment extends BaseFragment {
    private ImageView ivBackPhone;
    private PhoneCode pcMsgCode;
    private ImageView ivVerifyCode;
    private TimerText ttSendMsg;
    private static final String TAG = "LoginCodeFragment";

    @Override
    protected int bindLayout() {
        return R.layout.fragment_login_code;
    }

    @Override
    protected void initView() {
        ivBackPhone = getContentView().findViewById(R.id.login_iv_backphone);
        pcMsgCode = getContentView().findViewById(R.id.login_pc_code);
        ivVerifyCode = getContentView().findViewById(R.id.login_iv_verify);
        ttSendMsg = getContentView().findViewById(R.id.login_tt_sendmsg);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        ivBackPhone.setOnClickListener(ivBackListener);
        pcMsgCode.setOnInputListener(pcMsgListener);
        ivVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(SmartApplication.TAG, "onClick  被电击");
                ApiHelper.login(new AccountRequest(LoginActivity.phone, pcMsgCode.getPhoneCode())).subscribe(new Consumer<Resp<LoginResult>>() {
                    @Override
                    public void accept(Resp<LoginResult> loginResultResp) throws Exception {
                        if (loginResultResp != null && loginResultResp.isSuccess()) {
                            LoginResult data = loginResultResp.getData();
                            String token = data.getToken();
                            SPUtils.getInstance().put(Constants.TOKEN, token);
                            startActivity(new Intent(getContext(), EsptouchDemoActivity.class));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: " + throwable);
                    }
                });
            }
        });
    }

    private View.OnClickListener ivBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mContext instanceof LoginActivity) {
                LoginActivity loginActivity = (LoginActivity) mContext;
                loginActivity.toPhoneEdit();
            }
        }
    };

    private PhoneCode.OnInputListener pcMsgListener = new PhoneCode.OnInputListener() {
        @Override
        public void onSucess(String code) {
            ivVerifyCode.setBackground(getResources().getDrawable(R.drawable.next_ok));
            ivVerifyCode.setClickable(true);
        }

        @Override
        public void onInput() {
            ivVerifyCode.setBackground(getResources().getDrawable(R.drawable.next_fail));
            ivVerifyCode.setClickable(false);
        }
    };

    private View.OnClickListener ivVerifyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        }
    };

    @Override
    public void onDetach() {
        ttSendMsg.clearTimer();
        super.onDetach();
    }
}
