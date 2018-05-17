package thhsu.chloe.jeeva.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import thhsu.chloe.jeeva.Jeeva;
import thhsu.chloe.jeeva.R;
import thhsu.chloe.jeeva.Utils.Constants;
import thhsu.chloe.jeeva.api.ApiJobManager;
import thhsu.chloe.jeeva.api.PostRegisterLoginCallBack;
import thhsu.chloe.jeeva.api.model.RegisterResult;

/**
 * Created by Chloe on 5/13/2018.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    TextInputLayout mRegisterEmailTextInputLayout, mRegisterPasswordTextInputLayout, mRegisterConfirmPasswordTextInputLayout;
    EditText mRegisterEmailText, mRegisterPasswordText, mRegisterConfirmPasswordText;
    Button mCreateAccountBtn, mRegisterBackBtn;
    String userToken, userEmail;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mRegisterEmailTextInputLayout = (TextInputLayout) findViewById(R.id.signup_textinputlayout_email);
        mRegisterPasswordTextInputLayout = (TextInputLayout) findViewById(R.id.signup_textinputlayout_password);
        mRegisterConfirmPasswordTextInputLayout = (TextInputLayout) findViewById(R.id.signup_textinputlayout_confirmpassword);
        mRegisterConfirmPasswordText = (EditText) findViewById(R.id.signup_textinput_confirmpassword);
        mRegisterEmailText = (EditText) findViewById(R.id.signup_textinput_email);
        mRegisterPasswordText = (EditText) findViewById(R.id.signup_textinput_password);
        mCreateAccountBtn = (Button) findViewById(R.id.signup_createaccount_btn);
        mRegisterBackBtn = (Button) findViewById(R.id.signup_back_btn);

        mCreateAccountBtn.setOnClickListener(this);
        mRegisterBackBtn.setOnClickListener(this);

        sharedPreferences = this.getSharedPreferences(Constants.USER_DATA, MODE_PRIVATE);
    }


    public boolean validateRegisterData(){
        boolean result = true;

        String email = mRegisterEmailText.getText().toString();
        if (email.equals("") || !(email.contains("@"))) {
            mRegisterEmailTextInputLayout.setError(getString(R.string.invalidEmail));
            result = false;
        } else {
            mRegisterEmailTextInputLayout.setErrorEnabled(false);
        }

        String password = mRegisterPasswordText.getText().toString();
        if(password.length() < 6){
            mRegisterPasswordTextInputLayout.setError("At least 6 characters");
            result = false;
        }else{
            mRegisterPasswordTextInputLayout.setErrorEnabled(false);
        }

        String confirmPassword = mRegisterConfirmPasswordText.getText().toString();
        if(!(confirmPassword.equals(password))){
            mRegisterConfirmPasswordTextInputLayout.setError("Password must match");
            result = false;
        }else{
            mRegisterConfirmPasswordTextInputLayout.setErrorEnabled(false);
        }

        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup_createaccount_btn:
                if(validateRegisterData()){
                    String password;
                    userEmail = mRegisterEmailText.getText().toString();
                    password = mRegisterPasswordText.getText().toString();
                    Log.d("Chloe", "Email: " + userEmail + " Password: " + password);
                    ApiJobManager.getInstance().getRegister(userEmail, password, new PostRegisterLoginCallBack() {
                        @Override
                        public void onCompleted(String token) {
                            userToken = token;
                            sharedPreferences.edit()
                                    .putString(Constants.USER_TOKEN, userToken)
                                    .putString(Constants.USER_EMAIL, userEmail)
                                    .apply();
                            Log.d("Chloe", "userToken: " + token);
                            setResult(Constants.RESULT_SUCCESS);
                            finish();}

                        @Override
                        public void onError(String errorMessage) {
                        }
                    });
                }

                break;
            case R.id.signup_back_btn:
                this.onBackPressed();
                break;
        }
    }
}
