package nishant.clothpicker.utils.login_helper;

import nishant.clothpicker.model.User;

/**
 * Created by serious on 1/9/17.
 */

public interface LoginInterface {
    void onAlreadyLoggedIn(String via, User user, boolean loggedIn);

    void onLoginSuccess(String via, User user);

    void onLoginFailure(String via);

    void onCancel(String via);
}
