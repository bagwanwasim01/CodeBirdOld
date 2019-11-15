package com.example.wasim.compileit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class register_user extends AppCompatActivity {
    TextView clickhere,Tname,Tmobile,Temail,Tpass;
    final String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final String passPattern="[a-zA-Z0-9@?]";
    EditText name,mobile,email,password;
    private CheckBox cbPass;
    Button signup,userlist;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        db = openOrCreateDatabase("AppDB.db", Context.MODE_PRIVATE, null);
        cbPass=(CheckBox)findViewById(R.id.cbPassR);
        name=(EditText)findViewById(R.id.editText5);
        mobile=(EditText)findViewById(R.id.editText8);
        int emailen=35,passlen=15;
        email=(EditText)findViewById(R.id.editText9);
        email.setFilters(new InputFilter[]{new InputFilter.LengthFilter(emailen) });
        final String emailV=email.getEditableText().toString().trim();
        password=(EditText)findViewById(R.id.editText10);
        password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(passlen) });
        final String passV=password.getEditableText().toString().trim();
        clickhere=(TextView)findViewById(R.id.textView5);
        Tname=(TextView)findViewById(R.id.textView8);
        Tmobile=(TextView)findViewById(R.id.textView10);
        Temail=(TextView)findViewById(R.id.textView11);
        Tpass=(TextView)findViewById(R.id.textView12);
        signup=(Button)findViewById(R.id.button2);
        userlist=(Button)findViewById(R.id.button);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        cbPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else
                {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        clickhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=getIntent();
                Intent it=new Intent(register_user.this,UserLogin.class);
                startActivity(it);
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               if (emailV.contains(emailPattern) && s.length() > 0)
               {
                   email.setError("Invalid Email");
               }
               else {
                   return;
               }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (passV.contains(passPattern) && s.length() > 0)
                {
                    password.setError("Your password should contain\na-z,A-Z,0-9,@,?");

                }
                else {
                    return;
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(name.getText().toString().trim().length() == 0 ) {
                        name.setText("");
                        name.setError("Please Enter Your Name");
                        name.requestFocus();
                    }else if (mobile.getText().toString().trim().length() == 0) {
                        mobile.setText("");
                        mobile.setError("Please Enter Your Mobile Number");
                        mobile.requestFocus();
                    }else if (email.getText().toString().trim().length() == 0) {
                        email.setText("");
                        email.setError("Please Enter Your Email");
                        email.requestFocus();
                    }
                    else if (password.getText().toString().trim().length() == 0) {
                        password.setText("");
                        password.setError("Please Enter Password");
                        password.requestFocus();
                }else {
                        String id=getNewId();
                        db.execSQL("Insert into LogInInfo values ('"+id+"','" + name.getText() + "','" + mobile.getText() + "','" + email.getText() + "','" + password.getText() + "');");
                        Toast.makeText(getApplicationContext(),""+id,Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"Registred",Toast.LENGTH_SHORT).show();
                        Cursor c=db.rawQuery("Select * from LogInInfo WHERE Email='"+email.getText().toString()+"' AND Password='"+password.getText().toString()+"'",null);
                        try {
                            if (c.getCount() == 0) {
                                Toast.makeText(getApplicationContext(), "No User found with Username", Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                String e=null,u=null;
                                while (c.moveToNext()) {
                                    u=(c.getString(1));
                                    e=(c.getString(3));
                                }
                                Intent it = new Intent(register_user.this, Main2Activity.class);
                                it.putExtra("Email",u);
                                it.putExtra("UserName",e);
                                startActivity(it);
                                ClearEditText();
                            }
                        }catch (SQLException e){
                            Toast.makeText(getApplicationContext(),"Error"+e,Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (SQLException e)
                {
                    Toast.makeText(getApplicationContext(),"Error While Adding Record"+e,Toast.LENGTH_SHORT).show();
                }
            }
        });
        userlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(register_user.this,User_list.class);
                startActivity(it);
               /* Cursor c=db.rawQuery("Select * from LogInInfo",null);
                if(c.getCount()==0) {
                    ShowRecords("Error","No Records Found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(c.moveToNext())
                {
                    buffer.append("\nUser ID :"+c.getString(0));
                    buffer.append("\nUser Name :"+c.getString(1));
                    buffer.append("\nMobile :"+c.getString(2));
                    buffer.append("\nEmail :"+c.getString(3));
                    buffer.append("\nPassword:"+c.getString(4));
                }
                ShowRecords("User Information",buffer.toString()); */
            }
           /*     Intent it=new Intent(register_user.this,User_list.class);
                startActivity(it); */
        });
    }
    private void ShowRecords(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public String getNewId()
    {
        String max=null;
        Cursor c=db.rawQuery("Select max(LoginId) from LogInInfo",null);
        if(c.moveToNext())
        {
            max=String.valueOf(c.getInt(0));
            if(max==null)
            {
                max="1001";
            }
            else
                max=String.valueOf(Integer.parseInt(max)+1);
        }
        return max;
    }
    private void ClearEditText()
    {
        name.setText("");
        mobile.setText("");
        email.setText("");
        password.setText("");
        name.requestFocus();
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
                .setMessage("Close application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        // finish();
                      //  finish();
                        finishAffinity();
                        System.exit(0);
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

  /*  public  static boolean isValidEmail(CharSequence target)
    {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());

    } */
}
