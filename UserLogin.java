package com.example.wasim.compileit;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by wasim on 03-09-2019.
 */

public class UserLogin extends AppCompatActivity {
    private EditText username;
    private TextInputLayout Tpass;
    private TextInputEditText Ipass;
    private Button login;
    //private CheckBox cbPass;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user);
        db = openOrCreateDatabase("AppDB.db", Context.MODE_PRIVATE, null);
        Intent i=getIntent();
      /*  Intent it=new Intent(UserLogin.this,SplashScreen.class);
        startActivity(it); */
        username = (EditText) findViewById(R.id.ETname);
       // cbPass=(CheckBox)findViewById(R.id.cbPass);
        username.requestFocus();
        Tpass = (TextInputLayout) findViewById(R.id.Tpass);
        Ipass = (TextInputEditText)findViewById(R.id.Ipass);

       /* cbPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else
                {
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        }); */
        login = (Button) findViewById(R.id.buttonLogin);
      login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().length() == 0) {
                    username.setText("");
                    username.setError("Please Enter Username/Email.");
                    username.requestFocus();
                }
                else if (Ipass.getText().toString().trim().length() == 0) {
                    Ipass.setText("");
                    Ipass.setError("Please Enter Password.");
                    Ipass.requestFocus();
                }else
                    Tpass.setErrorEnabled(false);
                    validate(username.getText().toString(), Ipass.getText().toString());
            }
        });
    }
    private void validate(String usernm, String passwd) {
        String un=null,pw=null;
        Cursor c=db.rawQuery("Select * from LogInInfo WHERE Email='"+username.getText().toString()+"' AND Password='"+Ipass.getText().toString()+"'",null);
        try {
            if (c.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "Username or Password is incorrect", Toast.LENGTH_LONG).show();
                return;
            } else {
                String e=null,u=null;
                while (c.moveToNext()) {
                    u=(c.getString(1));
                    e=(c.getString(3));
                }
                Intent it = new Intent(UserLogin.this, Main2Activity.class);
                it.putExtra("Email",u);
                it.putExtra("UserName",e);
                startActivity(it);
                ClearEditText();
            }
        }catch (SQLException e){
        Toast.makeText(getApplicationContext(),"Error"+e,Toast.LENGTH_SHORT).show();
    }
    }
    void ClearEditText()
    {
        username.setText("");
        Ipass.setText("");
        username.requestFocus();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to go back?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                       // finish();
                        finish();
                        Intent i=new Intent(UserLogin.this, register_user.class);
                        startActivity(i);
                        //System.exit(0);
                        //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                })
                .show();

    }
}
