package nishant.clothpicker.utils.login_helper;

/**
 * Created by serious on 1/9/17.
 */

public interface LoginInterface {
    void onAlreadyLoggedIn();

    void onLoginSuccess();

    void onLoginFailure();

    void onCancel();
}
