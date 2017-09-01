package nishant.clothpicker.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import nishant.clothpicker.ui.activities.BaseActivity;

/**
 * Created by nishant pardamwar on 18/1/17.
 */

public class BaseFragment extends DialogFragment {
    protected FragmentActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    protected void backPressed() {
        if (mActivity != null)
            ((BaseActivity) mActivity).onBackPressed();
    }

    protected void hideToolBar() {
        if (mActivity != null)
            ((BaseActivity) mActivity).hideToolBar();
    }

    protected void showFragment(Fragment fragment) {
        if (mActivity != null) {
            ((BaseActivity) mActivity).showFragment(fragment);
        }
    }

    protected void addAndShowFragment(Fragment fragment) {
        if (mActivity != null) {
            ((BaseActivity) mActivity).addAndShowFragment(fragment);
        }
    }

    protected void showProgressDialog(String message, boolean isCancellable) {
        if (mActivity != null) {
            ((BaseActivity) mActivity).showProgressDialog(message, isCancellable);
        }
    }

    protected void dismissProgressDialog() {
        try {
            ((BaseActivity) mActivity).dismissProgressDialog();
        } catch (Exception e) {
        }
    }
}
