package com.khames.stickynotes;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import javax.microedition.khronos.egl.EGLDisplay;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private Button loginButton;
    private EditText usernameEditText,passwordEditText;
    private DatabaseHelper databaseHelper;
    private Users user;
    private CheckBox rememberMe;
    private SharedPreferences loginPreferences;
    private  SharedPreferences.Editor loginEditor;
    private String usernameValue,PasswordValue;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = (Button)view.findViewById(R.id.login_button);
        usernameEditText = (EditText)view.findViewById(R.id.login_input_username);
        passwordEditText = (EditText)view.findViewById(R.id.login_input_password);
        rememberMe = (CheckBox)view.findViewById(R.id.remember_me);
        user = new Users();
        databaseHelper = new DatabaseHelper(getContext());

        loginPreferences = getContext().getSharedPreferences("login",Context.MODE_PRIVATE);
        if (loginPreferences.getBoolean("logged",false)){
            sendUserToHomeActivity();
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndLogin();

            }
        });
        return view;
    }



    private void validateAndLogin() {
        String usernameText = usernameEditText.getText().toString();
        String passText = passwordEditText.getText().toString();
        if (TextUtils.isEmpty(usernameText) || TextUtils.isEmpty(passText)){
            Toast.makeText(getContext(),"This Field is required",Toast.LENGTH_SHORT).show();
        }else if (databaseHelper.checkUser(usernameText)){
            Toast.makeText(getContext(),"Welcome",Toast.LENGTH_SHORT).show();
            emptyInputEditText();
            sendUserToHomeActivity();
            if (rememberMe.isChecked()){
                loginPreferences.edit().putBoolean("logged",true).commit();
            }
        } else if (!databaseHelper.checkUser(usernameText)){
            Toast.makeText(getContext(),"Please Register first",Toast.LENGTH_SHORT).show();
        }
    }
    private void emptyInputEditText() {
        usernameEditText.setText(null);
        passwordEditText.setText(null);
    }

    private void sendUserToHomeActivity() {
        Intent homeIntent = new Intent(getContext(),HomeActivity.class);
        startActivity(homeIntent);
    }

}
