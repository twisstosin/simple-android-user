package com.twisstosin.simpleandroiduserapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.twisstosin.simpleandroiduser.SimpleUserStore;

public class MainActivity extends AppCompatActivity {

    SimpleUserStore simpleUserStore;
    private EditText firstName, lastName, email, phoneNumber;
    private Button checkLoginButton,loginButton;
    private TextInputLayout phoneLayout;
    private LinearLayout nameLayout;
    private RadioGroup genderReg;

    public User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        email  = (EditText)findViewById(R.id.email_reg);
        phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        genderReg = (RadioGroup)findViewById(R.id.gender_reg);
        nameLayout = (LinearLayout)findViewById(R.id.name_box);
        phoneLayout = (TextInputLayout)findViewById(R.id.phone_box);

        checkLoginButton = (Button)findViewById(R.id.btn_check);
        loginButton = (Button)findViewById(R.id.btn_login);



        simpleUserStore = new SimpleUserStore(this,User.class);

        if(simpleUserStore.isUserLoggedIn())
        {
            nameLayout.setVisibility(View.GONE);
            phoneLayout.setVisibility(View.GONE);
            genderReg.setVisibility(View.GONE);

            String log = "LOG OUT";
            loginButton.setText(log);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MaterialDialog confirmLogoutDialog = new MaterialDialog.Builder(MainActivity.this)
                            .title("Logout")
                            .theme(Theme.LIGHT)
                            .content("Are you sure you want to logout of your account?")
                            .positiveText("Yes")
                            .negativeText("No")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    simpleUserStore.clearUser();
                                    Intent i = getBaseContext().getPackageManager()
                                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);

                                }
                            })
                            .show();
                }
            });
            checkLoginButton.setVisibility(View.VISIBLE);
            checkLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User user = (User) simpleUserStore.getLoggedInUser();

                    Toast.makeText(MainActivity.this, user.Email+"\n"+user.Name+"\n"+user.Gender+"\n"+user.Phone, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


            String name = firstName.getText().toString() +" "+ lastName.getText().toString();
            final String userEmail = email.getText().toString().trim();
            String userPhoneNumber = null;

            RadioButton activeRadioButton;
            String Sex = null;

            if(isValidEmail(userEmail)) {
                activeRadioButton = (RadioButton) findViewById(genderReg.getCheckedRadioButtonId());
                if (activeRadioButton != null) {


                    if (activeRadioButton != null)
                        Sex = activeRadioButton.getText().toString();

                    if(phoneNumber.getText().toString().trim().startsWith("0"))
                    {
                        userPhoneNumber = phoneNumber.getText().toString().trim();
                    }
                    else if(phoneNumber.getText().toString().trim().startsWith("+234"))
                    {
                        userPhoneNumber = phoneNumber.getText().toString().trim().replace("+234","0");
                    }

                    newUser = new User(null, name, userEmail, userPhoneNumber);
                    newUser.Gender = Sex;

                    //save user
                    simpleUserStore.storeUser(newUser);
                    Toast.makeText(MainActivity.this, "LoggedIn", Toast.LENGTH_SHORT).show();
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {

                Toast.makeText(MainActivity.this, "Please Input Valid Email", Toast.LENGTH_SHORT).show();
            }
                }
            });
        }
    }


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}
