package com.example.wasim.compileit;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class About extends AppCompatActivity {
    TextView t,mymail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Intent i=getIntent();
        t=(TextView)findViewById(R.id.textView9);
        mymail=(TextView)findViewById(R.id.textView14);
        mymail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                Uri uri=Uri.parse("mailto:wasimbagwan55@gmail.com");
                intent.setData(uri);
                startActivity(Intent.createChooser(intent,""));
              /*  intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"wasimbagwan55@gmail.com"});
                //intent.putExtra(Intent.EXTRA_TEXT,"Main body");
                startActivity(Intent.createChooser(intent,"")); */

            }
        });
    }
}
