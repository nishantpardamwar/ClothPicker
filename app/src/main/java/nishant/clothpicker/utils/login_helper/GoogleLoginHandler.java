package nishant.clothpicker.utils.login_helper;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

import nishant.clothpicker.model.User;

/**
 * Created by serious on 2/9/17.
 */

public class GoogleLoginHandler {
    public static final int RC_SIGN_IN = 50;
    private FragmentActivity fragmentActivity;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;
    private LoginInterface loginInterface;

    public GoogleLoginHandler(FragmentActivity fragmentActivity, SignInButton signInButton, LoginInterface loginInterface) {
        this.fragmentActivity = fragmentActivity;
        this.signInButton = signInButton;
        this.loginInterface = loginInterface;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(fragmentActivity)
                .enableAutoManage(fragmentActivity, connectionResult -> {
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton.setOnClickListener(v -> {
            signIn();
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        fragmentActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            User user = new User.Builder()
                    .name(acct.getDisplayName())
                    .email(acct.getEmail()).build();
            loginInterface.onLoginSuccess(LoginHandler.VIA_GOOGLE, user);
        } else {
            loginInterface.onLoginFailure(LoginHandler.VIA_GOOGLE);
        }
    }

    public void isAlreadyLoggedIn() {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            GoogleSignInResult result = opr.get();
            //handleSignInResult(result);
            GoogleSignInAccount acct = result.getSignInAccount();
            User user = new User.Builder()
                    .name(acct.getDisplayName())
                    .email(acct.getEmail()).build();
            loginInterface.onAlreadyLoggedIn(LoginHandler.VIA_GOOGLE, user, true);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            opr.setResultCallback(googleSignInResult -> {
                //handleSignInResult(googleSignInResult);
                if (googleSignInResult.isSuccess()) {
                    GoogleSignInAccount acct = googleSignInResult.getSignInAccount();
                    User user = new User.Builder()
                            .name(acct.getDisplayName())
                            .email(acct.getEmail()).build();
                    loginInterface.onAlreadyLoggedIn(LoginHandler.VIA_GOOGLE, user, true);
                } else {
                    loginInterface.onAlreadyLoggedIn(LoginHandler.VIA_GOOGLE, null, false);
                }
            });
        }
    }
}
