package nishant.clothpicker.ui.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import nishant.clothpicker.R;

/**
 * Created by nishant pardamwar on 18/1/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected FragmentManager fm;
    protected Toolbar toolbar;
    private ProgressDialog mProgressDialog;

    protected abstract int getLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        checkPermission();
        fm = getSupportFragmentManager();
    }

    private void checkPermission() {
        try {
            int currentapiVersion = Build.VERSION.SDK_INT;
            if (currentapiVersion >= Build.VERSION_CODES.M) {

                boolean readPhoneStatePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
                boolean locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                boolean readSmsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
                boolean receiveSmsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;

                if (!readPhoneStatePermission || !locationPermission || !readSmsPermission || !receiveSmsPermission) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS,
                                    Manifest.permission.RECEIVE_BOOT_COMPLETED},
                            10);
                }
            }
        } catch (Exception e) {
        }
    }

    public void hideToolBar() {
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    public void showFragment(Fragment frag) {
        if (frag != null) {
            fm.beginTransaction().replace(R.id.main_container, frag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commitAllowingStateLoss();
            //fm.executePendingTransactions();
        }
    }

    public void addAndShowFragment(Fragment frag) {
        if (frag != null) {
            fm.beginTransaction().addToBackStack(null)
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                    .replace(R.id.main_container, frag)
                    .commitAllowingStateLoss();
            //fm.executePendingTransactions();
        }
    }

    public void showProgressDialog(String message, boolean isCancelable) {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(this);
        if (!mProgressDialog.isShowing()) {
            if (message == null)
                message = "Please wait...";
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(isCancelable);
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
