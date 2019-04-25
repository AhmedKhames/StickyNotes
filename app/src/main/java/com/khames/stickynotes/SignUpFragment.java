package com.khames.stickynotes;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private EditText password,username,confirmPassword;
    private Button signUpButton;
    private DatabaseHelper databaseHelper;
    private Users user;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View signUpView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        username = (EditText)signUpView.findViewById(R.id.input_username);
        password = (EditText)signUpView.findViewById(R.id.input_password);
        confirmPassword = (EditText)signUpView.findViewById(R.id.input_password_confirm);
        signUpButton = (Button)signUpView.findViewById(R.id.sign_up);
        user = new Users();
        databaseHelper = new DatabaseHelper(getContext());


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndRegister();
            }
        });

        return signUpView;
    }

    private void validateAndRegister() {
        String usernameText = username.getText().toString();
        String passText = password.getText().toString();
        String confirmText = confirmPassword.getText().toString();

        if (TextUtils.isEmpty(usernameText) || TextUtils.isEmpty(passText) || TextUtils.isEmpty(confirmText)){
            Toast.makeText(getContext(),"This Field is required",Toast.LENGTH_SHORT).show();
        }else if(!passText.equals(confirmText)) {
            Toast.makeText(getContext(), "The password dose not match confirmation", Toast.LENGTH_SHORT).show();
        }else if (databaseHelper.checkUser(usernameText)){
            Toast.makeText(getContext(), "This Username is already registered", Toast.LENGTH_SHORT).show();
        }else {

           try {

               user.setName(usernameText);
               user.setPassword(passText);
               databaseHelper.addUser(user);
               Toast.makeText(getContext(),"Welcome",Toast.LENGTH_SHORT).show();
               emptyInputEditText();
               sendUserToHomeActivity();
           }catch (Exception e){
              Log.e("Error", e.getMessage());
           }

        }

    }

    private void emptyInputEditText() {
        username.setText(null);
        password.setText(null);
        confirmPassword.setText(null);
    }

    private void sendUserToHomeActivity() {
        Intent homeIntent = new Intent(getContext(),HomeActivity.class);
        startActivity(homeIntent);
    }

}
