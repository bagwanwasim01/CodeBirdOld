package com.example.wasim.compileit;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class OutputActivity extends AppCompatActivity {
    TextView output;
    WebView w;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
       /* Intent i=getIntent();
       Bundle bundle=i.getExtras();
        String weboutput=bundle.getString("webdata");
        w=(WebView)findViewById(R.id.webview);
        WebSettings webSettings=w.getSettings();
       // webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        w.setWebChromeClient(new WebChromeClient());
       // w.Settings.DomStorageEnabled(true);
       w.loadDataWithBaseURL("file:///android_asset/","text/html","UTF-8",null,null);
       // webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        w.loadData(weboutput,null,null); */
        output=(TextView)findViewById(R.id.textView);
        Intent it=getIntent();
        Bundle b=it.getExtras();
        if(b!=null)
        {
            String str=b.getString("Output");
            output.setText(str);
            //Toast.makeText(getApplicationContext(),""+str,Toast.LENGTH_LONG).show();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(OutputActivity.this);
                builder.setTitle("Help");
                LayoutInflater inflater = OutputActivity.this.getLayoutInflater();
                final View dialogview = inflater.inflate(R.layout.custom_dialogtextview, null);
                final TextView help = (TextView) dialogview.findViewById(R.id.texthelp1);
                final TextView gcchelp = (TextView) dialogview.findViewById(R.id.gcchelp1);
                final TextView jdkhelp = (TextView) dialogview.findViewById(R.id.jdkhelp1);
                builder.setView(dialogview);
               // builder.setMessage(help.getText());
                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                Dialog a = builder.create();
                a.show();
                gcchelp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "https://www3.ntu.edu.sg/home/ehchua/programming/cpp/gcc_make.html";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });
                jdkhelp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "https://www.programming.guide/java/list-of-java-exceptions.html";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); */
            }
        });

    }
}
