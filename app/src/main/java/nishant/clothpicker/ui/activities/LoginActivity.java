package nishant.clothpicker.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

import nishant.clothpicker.R;
import nishant.clothpicker.model.User;
import nishant.clothpicker.utils.login_helper.LoginHandler;
import nishant.clothpicker.utils.login_helper.LoginInterface;

/**
 * Created by serious on 1/9/17.
 */

public class LoginActivity extends BaseActivity implements LoginInterface {
    private LoginHandler loginHandler;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginHandler = new LoginHandler.Builder(this)
                .loginInterface(this)
                .fbLoginButton((LoginButton) findViewById(R.id.fb_login_button))
                .googleLoginButton((SignInButton) findViewById(R.id.google_login_button)).build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginHandler.onActivityResult(requestCode, resultCode, data);
    }

    private void completeLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog(null, false);
        loginHandler.checkAlreadyLogged();
    }

    @Override
    public void onAlreadyLoggedIn(String via, User user, boolean loggedIn) {
        dismissProgressDialog();
        if (loggedIn)
            completeLogin();
    }

    @Override
    public void onLoginSuccess(String via, User user) {
        dismissProgressDialog();
        completeLogin();
    }

    @Override
    public void onLoginFailure(String via) {
        dismissProgressDialog();
    }

    @Override
    public void onCancel(String via) {
        dismissProgressDialog();
    }
}
