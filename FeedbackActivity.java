package com.example.wasim.compileit;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {
    protected EditText EFeedback;
    protected Button bsubmit;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        EFeedback=(EditText)findViewById(R.id.Efeedback);
        bsubmit=(Button)findViewById(R.id.btnsubmit);
        try{
            db = openOrCreateDatabase("AppDB.db", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS FeedbackInfo "+
                    "(FeedId Integer,FeedMsg varchar(100));");
        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),"Error while creating table"+e,Toast.LENGTH_SHORT).show();
        }
        Intent getfeed=getIntent();
        bsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(EFeedback.getText().toString().trim().length() == 0 ) {
                        Toast.makeText(getApplicationContext(),"Please Enter Your Feedback",Toast.LENGTH_SHORT).show();

                    }else {
                        db.execSQL("Insert into FeedbackInfo values ('" + EFeedback.getText() + "');");
                        Toast.makeText(getApplicationContext(),"Feedback Recorded",Toast.LENGTH_SHORT).show();
                    }
                }catch (SQLException e)
                {
                    Toast.makeText(getApplicationContext(),"Error While Adding Record"+e,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
