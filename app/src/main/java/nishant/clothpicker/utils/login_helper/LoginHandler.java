package nishant.clothpicker.utils.login_helper;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import nishant.clothpicker.model.User;

/**
 * Created by serious on 2/9/17.
 */

public class LoginHandler implements LoginInterface {
    public static final String VIA_GOOGLE = "google";
    public static final String VIA_FACEBOOK = "fb";
    private FragmentActivity fragmentActivity;
    private SignInButton signInButton;
    private LoginButton loginButton;
    private FacebookLoginHandler fbHandler;
    private GoogleLoginHandler googleHandler;
    private LoginInterface loginInterface;

    private LoginHandler(Builder builder) {
        fragmentActivity = builder.fragmentActivity;
        signInButton = builder.signInButton;
        loginButton = builder.loginButton;
        loginInterface = builder.loginInterface;
        fbHandler = new FacebookLoginHandler(fragmentActivity, loginButton, this);
        googleHandler = new GoogleLoginHandler(fragmentActivity, signInButton, this);
    }

    public void checkAlreadyLogged() {
        fbHandler.isAlreadyLoggedIn();
        googleHandler.isAlreadyLoggedIn();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GoogleLoginHandler.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            googleHandler.handleSignInResult(result);
        } else {
            fbHandler.getCallbackManager().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onAlreadyLoggedIn(String via, User user, boolean loggedIn) {
        loginInterface.onAlreadyLoggedIn(via, user, loggedIn);
    }

    @Override
    public void onLoginSuccess(String via, User user) {
        loginInterface.onLoginSuccess(via, user);
    }

    @Override
    public void onLoginFailure(String via) {
        loginInterface.onLoginFailure(via);
    }

    @Override
    public void onCancel(String via) {
        loginInterface.onCancel(via);
    }

    public static final class Builder {
        private FragmentActivity fragmentActivity;
        private SignInButton signInButton;
        private LoginButton loginButton;
        private LoginInterface loginInterface;

        public Builder(FragmentActivity fragmentActivity) {
            this.fragmentActivity = fragmentActivity;
        }

        public Builder googleLoginButton(SignInButton val) {
            signInButton = val;
            return this;
        }

        public Builder fbLoginButton(LoginButton val) {
            loginButton = val;
            return this;
        }

        public Builder loginInterface(LoginInterface val) {
            loginInterface = val;
            return this;
        }

        public LoginHandler build() {
            return new LoginHandler(this);
        }
    }
}
