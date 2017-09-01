package nishant.clothpicker.utils.login_helper;

import android.support.v4.app.FragmentActivity;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

/**
 * Created by serious on 2/9/17.
 */

public class LoginHandler implements LoginInterface {
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

    @Override
    public void onAlreadyLoggedIn() {
        loginInterface.onAlreadyLoggedIn();
    }

    @Override
    public void onLoginSuccess() {
        loginInterface.onLoginSuccess();
    }

    @Override
    public void onLoginFailure() {
        loginInterface.onLoginFailure();
    }

    @Override
    public void onCancel() {
        loginInterface.onCancel();
    }

    public CallbackManager getFBCallBackManager() {
        return fbHandler.getCallbackManager();
    }

    public static final class Builder {
        private FragmentActivity fragmentActivity;
        private SignInButton signInButton;
        private LoginButton loginButton;
        private LoginInterface loginInterface;

        public Builder(FragmentActivity fragmentActivity) {
            this.fragmentActivity = fragmentActivity;
        }

        public Builder signInButton(SignInButton val) {
            signInButton = val;
            return this;
        }

        public Builder loginButton(LoginButton val) {
            loginButton = val;
            return this;
        }

        private Builder loginInterface(LoginInterface val) {
            loginInterface = val;
            return this;
        }

        public LoginHandler build() {
            return new LoginHandler(this);
        }
    }
}
