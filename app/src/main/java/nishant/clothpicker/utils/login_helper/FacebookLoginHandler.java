package nishant.clothpicker.utils.login_helper;

import android.support.v4.app.FragmentActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

/**
 * Created by serious on 1/9/17.
 */

public class FacebookLoginHandler {
    private LoginButton loginButton;
    private FragmentActivity fragmentActivity;
    private CallbackManager callbackManager;
    private LoginInterface loginInterface;

    public FacebookLoginHandler(FragmentActivity fragmentActivity, LoginButton loginButton, LoginInterface loginInterface) {
        this.fragmentActivity = fragmentActivity;
        this.loginButton = loginButton;
        this.loginInterface = loginInterface;
        FacebookSdk.sdkInitialize(fragmentActivity.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginInterface.onLoginSuccess();
            }

            @Override
            public void onCancel() {
                loginInterface.onCancel();
            }

            @Override
            public void onError(FacebookException error) {
                loginInterface.onLoginFailure();
            }
        });
    }


    public void isAlreadyLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    loginInterface.onAlreadyLoggedIn();
                }
            });
        }
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }
}
