package com.example.wasim.compileit;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class User_list extends AppCompatActivity {
    ListView lv;
    Context c=this;
    SQLiteDatabase db;
    ArrayList str=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Intent it=getIntent();
        lv=(ListView)findViewById(R.id.listview);
        db=openOrCreateDatabase("AppDB.db", Context.MODE_PRIVATE, null);
        Cursor cursor=db.rawQuery("Select * from LogInInfo",null);
        try {
            if(cursor.getCount()==0) {
                Toast.makeText(getApplicationContext(),"Error No Records Found",Toast.LENGTH_SHORT).show();
                return;
            }else {
                while (cursor.moveToNext()) {
                    str.add(cursor.getString(1));
                }
            }
        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),"Error"+e,Toast.LENGTH_SHORT).show();
        }
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                String selecteditem= str.get(i).toString();
                // Toast.makeText(c,""+selecteditem,Toast.LENGTH_SHORT).show();
               /* Intent it=new Intent(c,Main2Activity.class);
                //  it.putExtra("CNAME", (Bundle) lv.getSelectedItem());
                it.putExtra("UNAME",selecteditem);
                startActivity(it); */
            }
        });

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,str);
        lv.setAdapter(arrayAdapter);
    }
}
