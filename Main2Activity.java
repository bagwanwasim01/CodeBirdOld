package com.example.wasim.compileit;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import com.itextpdf.text.DocumentException;


public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "PdfCreatorActivity";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private File pdfFile;
    private String codeFile;
    Context context;
    private  static final int PER_REQ_STORAGE = 1000;
    private  static final int READ_REQ_CODE = 42;
   // final TextView tname=null, tmail = null;
    private static final int FILE_SELECT_CODE = 0;
    ImageButton iSave, iOpen, iAdd, iRotate;
    //final Uri fileUri = null;
    Spinner s, s1;
    Button TAB, CURLY, DQ, SEMI, LS, RS, SQ, iRun, iCompile, Tag,CLR;
    String[] items =new String[] {"-SELECT-","JAVA","C","C++","FORTRAN","PYTHON"};
    String[] items2 =new String[] {"-SELECT-","HTML", "JS"};
    com.example.wasim.compileit.MyEditText editText;
    EditText inputbox;
    RadioButton dark, light, default1, Rd16dp, Rd18dp, Rd20dp;
    static private String apiPath;
    // private String apiPath = "http://192.168.43.107/Php_Android/C.php";
    private int success = 0;
    String result1 = null;
    static String code, input1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TAB = (Button) findViewById(R.id.buttonTab);
        CURLY = (Button) findViewById(R.id.buttonCurly);
        DQ = (Button) findViewById(R.id.buttonDoubleQoutes);
        SEMI = (Button) findViewById(R.id.buttonSemi);
        LS = (Button) findViewById(R.id.buttonLeftShift);
        RS = (Button) findViewById(R.id.buttonRightShift);
        SQ = (Button) findViewById(R.id.buttonSingleQoute);
        Tag = (Button) findViewById(R.id.btnTag);
        CLR= (Button)findViewById(R.id.btnClear);
        DQ.setText("\" \"");
        LS.setText("<<");
        RS.setText(">>");
        Tag.setText("< >");
        TAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getSelectionStart();
                editText.getText().insert(editText.getSelectionStart(), "     ");
            }
        });
        CURLY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getSelectionStart();
                editText.getText().insert(editText.getSelectionStart(), "{\n\n\n\n}");
            }
        });
        DQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getSelectionStart();
                editText.getText().insert(editText.getSelectionStart(), "\" \"");
            }
        });
        SEMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getSelectionStart();
                editText.getText().insert(editText.getSelectionStart(), ";");
            }
        });
        LS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getSelectionStart();
                editText.getText().insert(editText.getSelectionStart(), "<<");
            }
        });
        RS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getSelectionStart();
                editText.getText().insert(editText.getSelectionStart(), ">>");
            }
        });
        SQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getSelectionStart();
                editText.getText().insert(editText.getSelectionStart(), "'  '");
            }
        });
        Tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getSelectionStart();
                editText.getText().insert(editText.getSelectionStart(), "< >");
            }
        });
        CLR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        s = (Spinner) findViewById(R.id.spinner);
        s1 = (Spinner) findViewById(R.id.spinner3);
        iSave = (ImageButton) findViewById(R.id.imageSave);
        iOpen = (ImageButton) findViewById(R.id.imageOpen);
        iAdd = (ImageButton) findViewById(R.id.imageAdd);
        iRotate = (ImageButton) findViewById(R.id.imageRotate);
        editText = (MyEditText) findViewById(R.id.editor_area);
        inputbox = (EditText) findViewById(R.id.input1);
        LayoutInflater inflater = Main2Activity.this.getLayoutInflater();
        final View dialogview = inflater.inflate(R.layout.custom_dialograting, null);
        final EditText input = (EditText) dialogview.findViewById(R.id.edit1);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        s.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items2);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        s1.setAdapter(arrayAdapter1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feed = new Intent(Main2Activity.this, FeedbackActivity.class);
                startActivity(feed);
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        Intent it = getIntent();
        Bundle b = it.getExtras();
        final View navview;
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navview=navigationView.getHeaderView(0);
        final TextView tname = (TextView) navview.findViewById(R.id.tname);
        final TextView tmail = (TextView) navview.findViewById(R.id.tmail);
        final TextView teditprofile = (TextView) navview.findViewById(R.id.tEditProfile);
        if(b!=null)
        {
            String name=b.getString("Email");
            String mail=b.getString("UserName");
            tname.setText(name);
            tmail.setText(mail);
        }
        /*teditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teditprofile.setText("Changed");

                 /  Intent it = new Intent(getBaseContext(), RegisterUser.class);
                    startActivity(it);
            }
        }); */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},PER_REQ_STORAGE);
        }
        navigationView.setNavigationItemSelectedListener(this);
        iAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
                String getLang = s.getSelectedItem().toString();
                String getLangSpinner1 = s1.getSelectedItem().toString();
                StringBuilder stringBuilder = new StringBuilder();
                switch (getLangSpinner1) {
                    case "HTML":
                        editText.requestFocus();
                        editText.setText("");
                        break;
                    case "JS":
                        editText.requestFocus();
                        editText.setText("");
                        break;
                    default:
                        editText.setText("");
                        break;
                }
                switch (getLang) {
                    case "JAVA":
                        apiPath = "http://192.168.43.107/Php_Android/java.php";
                        stringBuilder.append("public class Main\n ");
                        stringBuilder.append("{ \n");
                        stringBuilder.append("     public static void main(String[]args) \n");
                        stringBuilder.append("     { \n\n\n\n");
                        stringBuilder.append("     } \n");
                        stringBuilder.append("} \n");
                        editText.setText(stringBuilder.toString());
                        editText.requestFocus();
                        //editText.setSelection(editText.getText().length());
                        editText.setSelection(70);
                        break;
                    case "C":
                        apiPath = "http://192.168.43.107/Php_Android/C.php";
                        stringBuilder.append("#include<stdio.h> \n");
                        //  stringBuilder.append("#include<conio.h> \n");
                        stringBuilder.append("void main() \n");
                        stringBuilder.append("{ \n\n\n\n");
                        stringBuilder.append("} \n");
                        editText.setText(stringBuilder.toString());
                        editText.requestFocus();
                        editText.setSelection(54);
                        break;
                    case "C++":
                        apiPath = "http://192.168.43.107/Php_Android/Cpp.php";
                        stringBuilder.append("#include<iostream> \n");
                        stringBuilder.append("using namespace std;\n");
                        // stringBuilder.append("#include<conio.h> \n");
                        stringBuilder.append("int main() \n");
                        stringBuilder.append("{ \n\n\n\n");
                        stringBuilder.append("  return 0;\n} \n");
                        editText.setText(stringBuilder.toString());
                        editText.requestFocus();
                        editText.setSelection(57);
                        break;
                    case "FORTRAN":
                        apiPath = "http://192.168.43.107/Php_Android/Fortran.php";
                        stringBuilder.append("program helloworld \n\n");
                        stringBuilder.append("  print *, 'Hello World'\n\n");
                        stringBuilder.append("end program helloworld \n\n");
                        editText.setText(stringBuilder.toString());
                        editText.requestFocus();
                        editText.setSelection(57);
                        break;
                    case "PYTHON":
                        apiPath = "http://192.168.43.107/Php_Android/Py.php";
                        stringBuilder.append("print(\"welcome to python programming\")\n");
                        editText.setText(stringBuilder.toString());
                        editText.requestFocus();
                        break;
                    default:
                        editText.setText("");
                        break;
                }

            }
        });
        iSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(Main2Activity.this);
                builder.setTitle("Save File");
                LayoutInflater inflater = Main2Activity.this.getLayoutInflater();
                final View dialogview = inflater.inflate(R.layout.custom_dialogedit, null);
                final EditText save = (EditText) dialogview.findViewById(R.id.edit1);
                builder.setView(dialogview);
                builder.setCancelable(false);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Wasim");
                        if (!docsFolder.exists()) {
                            docsFolder.mkdir();
                            Log.i(TAG, "Created a new directory for Your File System");
                        }
                        String Codename = save.getText().toString();
                        codeFile = new File(docsFolder.getAbsolutePath(), Codename).toString();
                        try {
                            FileOutputStream fileOutputStream=new FileOutputStream(codeFile);
                            String str=editText.getText().toString();
                            byte buf[]=str.getBytes();
                            fileOutputStream.write(buf);
                            Toast.makeText(getApplicationContext(), "File saved successfully"+docsFolder, Toast.LENGTH_LONG).show();
                            fileOutputStream.close();
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "Something went wrong" + e, Toast.LENGTH_LONG).show();
                        }
                    }
                });
                Dialog a = builder.create();
                a.show();
            }
        });
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                String selectedLang = parent.getSelectedItem().toString();
                StringBuilder stringBuilder = new StringBuilder();
                switch (selectedLang) {
                    case "HTML":
                        iCompile.setEnabled(true);
                        iRun.setEnabled(false);
                        editText.requestFocus();
                        stringBuilder.append("<html>\n");
                        stringBuilder.append("   <title> </title>\n");
                        stringBuilder.append("     <head> </head>\n");
                        stringBuilder.append("        <body> \n\n\n\n");
                        stringBuilder.append("        </body> \n");
                        stringBuilder.append("</html> \n");
                        editText.setText(stringBuilder.toString());
                        break;
                    case "JS":
                        iCompile.setEnabled(true);
                        iRun.setEnabled(false);
                        stringBuilder.append("<!DOCTYPE html");
                        stringBuilder.append("<html>\n");
                        stringBuilder.append("     <head> </head>\n");
                        stringBuilder.append("<script>\n\n\n\n");
                        stringBuilder.append("function myFun(){");
                        stringBuilder.append("document.getElementById(\"demo\").innerHTML=\"Para changed\";}");
                        stringBuilder.append("</script> \n");
                        stringBuilder.append("     <body> \n\n\n\n");
                        stringBuilder.append("<h2>JAVASCRIPT in HEAD </h2> \n");
                        stringBuilder.append("<p id=\"demo\"> A paragraph </p>");
                        stringBuilder.append("<button type=\"button\" \n onclick=\"myFun()\"> Try it </button>");
                        stringBuilder.append("     </body> \n");
                        stringBuilder.append("</html> \n");
                        editText.setText(stringBuilder.toString());
                        editText.requestFocus();
                        break;
                    default:
                        editText.setText("");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                 String selectedLang = parent.getSelectedItem().toString();
                StringBuilder stringBuilder = new StringBuilder();
                switch (selectedLang) {
                    case "JAVA":
                        iRun.setEnabled(true);
                        iCompile.setEnabled(false);
                        apiPath = "http://192.168.43.107/Php_Android/java.php";
                        stringBuilder.append("public class Main\n ");
                        stringBuilder.append("{ \n");
                        stringBuilder.append("     public static void main(String[]args) \n");
                        stringBuilder.append("     { \n\n\n\n");
                        stringBuilder.append("     } \n");
                        stringBuilder.append("} \n");
                        editText.setText(stringBuilder.toString());
                        editText.requestFocus();
                        editText.setSelection(70);
                        break;
                    case "C":
                        iRun.setEnabled(true);
                        iCompile.setEnabled(false);
                        apiPath = "http://192.168.43.107/Php_Android/C.php";
                        stringBuilder.append("#include<stdio.h> \n");
                        //stringBuilder.append("#include<conio.h> \n");
                        stringBuilder.append("void main() \n");
                        stringBuilder.append("{ \n\n\n\n");
                        stringBuilder.append("} \n");
                        editText.setText(stringBuilder.toString());
                        editText.requestFocus();
                        // editText.setSelection(54);
                        break;
                    case "C++":
                        iRun.setEnabled(true);
                        iCompile.setEnabled(false);
                        apiPath = "http://192.168.43.107/Php_Android/Cpp.php";
                        stringBuilder.append("#include<iostream> \n");
                        stringBuilder.append("using namespace std;\n");
                        stringBuilder.append("int main() \n");
                        stringBuilder.append("{ \n\n\n\n");
                        stringBuilder.append("return 0;\n} \n");
                        editText.setText(stringBuilder.toString());
                        editText.requestFocus();
                        // editText.setSelection(57);
                        break;
                    case "FORTRAN":
                        iRun.setEnabled(true);
                        iCompile.setEnabled(false);
                        apiPath = "http://192.168.43.107/Php_Android/Fortran.php";
                        stringBuilder.append("program helloworld \n\n");
                        stringBuilder.append("  print *, 'Hello World'\n\n");
                        stringBuilder.append("end program helloworld \n\n");
                        editText.setText(stringBuilder.toString());
                        editText.requestFocus();
                        editText.setSelection(57);
                        break;
                    case "PYTHON":
                        iRun.setEnabled(true);
                        iCompile.setEnabled(false);
                        apiPath = "http://192.168.43.107/Php_Android/Py.php";
                        stringBuilder.append("print(\"welcome to python programming\")\n");
                        editText.setText(stringBuilder.toString());
                        editText.requestFocus();
                        break;
                    default:
                        editText.setText("");
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        iRun = (Button) findViewById(R.id.imageRun);
        iRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String selectedLang = items[0];
                code = editText.getText().toString();
                input1 = inputbox.getText().toString();
                new RegisterUser().execute();
            }
        });
        iCompile = (Button) findViewById(R.id.imageCompile);
        iCompile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = editText.getText().toString();
                Intent it = new Intent(Main2Activity.this, WebviewOutput.class);
                it.putExtra("webdata", code);
                startActivity(it);
            }
        });
        iOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent it = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    it.addCategory(Intent.CATEGORY_OPENABLE);
                    it.setType("text/*");
                    startActivityForResult(it,READ_REQ_CODE);
                    //it.setType("*/*")
                    // Intent i = Intent.createChooser(it, "Select File");
                    // startActivityForResult(Intent.getIntent(Intent.ACTION_GET_CONTENT), FILE_SELECT_CODE);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Please install File Manager", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data) {
        if ( requestCode == READ_REQ_CODE && resultCode == Activity.RESULT_OK)
        {
            if(Data != null)
            {
                Uri uri=Data.getData();
                String path= uri.getPath();
                path = path.substring(path.indexOf(":")+1);
                Toast.makeText(this,""+path,Toast.LENGTH_LONG).show();
                try {
                    editText.setText(ReadFile(path));
                } catch (IOException e) {
                    Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, Data);
       /* switch (requestCode) {
            case RESULT_OK:
                Toast.makeText(getApplicationContext(), "" + RESULT_OK, Toast.LENGTH_SHORT).show();
                break;
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, FILE_SELECT_CODE);     //for starting runtime permissions for app
                    }
                }
                break;
        } */
    }

    @Override
    public void onRequestPermissionsResult(int reqCode, String[] permissions, int[] grantResults) {
        if(reqCode == PER_REQ_STORAGE)
        {
            if(grantResults[0] ==  PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permission granted",Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        super.onRequestPermissionsResult(reqCode, permissions, grantResults);
      /*  switch (reqCode) {
            case FILE_SELECT_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
            }

        } */

    }
    public String ReadFile(String input) throws IOException {
       File f = new File(input);
        StringBuilder filecontents = new StringBuilder();
        Log.d("jason", "fileName: " +f );
        try {

       // FileInputStream fIS = new FileInputStream(f);
       // InputStreamReader isr = new InputStreamReader(fIS, "UTF-8");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
            filecontents.append(line + '\n');
        }
        br.close();
        } catch (IOException e) {
            filecontents.append("IOException: " + e.getMessage() + "\n");
            Log.e("Error!", "Error occured while reading text file from Internal Storage!");
        }
       return filecontents.toString();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_impfile) {
            // Handles importing of file
            Intent it = new Intent(Intent.ACTION_GET_CONTENT);
            it.addCategory(Intent.CATEGORY_OPENABLE);
            it.setType("text/*");
            startActivityForResult(it,FILE_SELECT_CODE);
            //startActivity(Intent.createChooser(it,""));
        } else if (id == R.id.nav_changeTheme) {
            final AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(Main2Activity.this);
            builder.setTitle("Change Theme");
            LayoutInflater inflater = Main2Activity.this.getLayoutInflater();
            final View dialogRadio = inflater.inflate(R.layout.radiobutton_dialog, null);
            final RadioGroup radioGroup = (RadioGroup) dialogRadio.findViewById(R.id.radio);
            builder.setView(dialogRadio);
            builder.setCancelable(true);
            Dialog a = builder.create();
            a.show();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int Width = displayMetrics.widthPixels;
            int Height = displayMetrics.heightPixels;
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(a.getWindow().getAttributes());
            int DWidth = (int) (Width * 0.60f);
            int DHeight = (int) (Height * 0.27f);
            layoutParams.width = DWidth;
            layoutParams.height = DHeight;
            a.getWindow().setAttributes(layoutParams);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    dark = (RadioButton) group.findViewById(R.id.radio_dark);
                    light = (RadioButton) group.findViewById(R.id.radio_light);
                    default1 = (RadioButton) group.findViewById(R.id.radio_default);
                    switch (checkedId) {
                        case R.id.radio_dark:
                            s.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                            s.setPopupBackgroundResource(R.color.colorBlack);
                            editText.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                            ((TextView) s.getChildAt(0)).setTextColor(Color.WHITE);
                            editText.setTextColor(Color.WHITE);
                            break;
                        case R.id.radio_light:
                            s.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                            s.setPopupBackgroundResource(R.color.colorDarkGray);
                            editText.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                            ((TextView) s.getChildAt(0)).setTextColor(Color.BLACK);
                            editText.setTextColor(Color.BLACK);
                            break;
                        case R.id.radio_default:

                            s.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorDefaultGreen)));
                            //  s.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                            s.setPopupBackgroundResource(R.color.colorDefaultGreen);
                            editText.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorDefaultGreen)));
                            ((TextView) s.getChildAt(0)).setTextColor(Color.WHITE);
                            editText.setTextColor(Color.WHITE);
                            break;
                        default:

                    }
                }
            });
        } else if (id == R.id.nav_changeFont) {
            final AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(Main2Activity.this);
            builder.setTitle("Change Font");
            LayoutInflater inflater = Main2Activity.this.getLayoutInflater();
            final View dialogRadio = inflater.inflate(R.layout.radiofont_dialog, null);
            final RadioGroup radioGroup = (RadioGroup) dialogRadio.findViewById(R.id.radio);
            builder.setView(dialogRadio);
            builder.setCancelable(true);
            Dialog a = builder.create();
            a.show();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int Width = displayMetrics.widthPixels;
            int Height = displayMetrics.heightPixels;
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(a.getWindow().getAttributes());
            int DWidth = (int) (Width * 0.60f);
            int DHeight = (int) (Height * 0.25f);
            layoutParams.width = DWidth;
            layoutParams.height = DHeight;
            a.getWindow().setAttributes(layoutParams);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    Rd16dp = (RadioButton) group.findViewById(R.id.radio_font16);
                    Rd18dp = (RadioButton) group.findViewById(R.id.radio_font18);
                    Rd20dp = (RadioButton) group.findViewById(R.id.radio_font20);
                 /*   int status=radioGroup.getCheckedRadioButtonId();
                    Toast.makeText(getApplicationContext(),"Selected ID"+status,Toast.LENGTH_LONG).show(); */
                    switch (checkedId) {
                        case R.id.radio_font16:
                            editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                            break;
                        case R.id.radio_font18:
                            editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                            break;
                        case R.id.radio_font20:
                            editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                        default:
                    }
                }
            });
        } else if (id == R.id.nav_savepdf) {
            // @RequiresApi(api = Build.VERSION_CODES.KITKAT)  not allowed here should mentioned on top of function def
            try {
                if(editText.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(),"No code found in editor area.",Toast.LENGTH_LONG).show();
                }else{
                    createPdfWrapper();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_sharefile) {

        } else if (id == R.id.nav_shareApp) {
            String shareBody = "https://play.google.com/store/apps/details?id=************************";
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "APP NAME (Open it in Google Play Store to Download the Application)");

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (id == R.id.nav_Feedback) {
            Intent feed = new Intent(Main2Activity.this, FeedbackActivity.class);
            startActivity(feed);
        } else if (id == R.id.nav_Rate) {
            final AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(Main2Activity.this);
            builder.setTitle("Rate App");
            LayoutInflater inflater = Main2Activity.this.getLayoutInflater();
            final View dialogview = inflater.inflate(R.layout.custom_dialograting, null);
            final RatingBar rt = (RatingBar) dialogview.findViewById(R.id.ratingBar);
            builder.setView(dialogview);
            builder.setCancelable(true);
            Dialog a = builder.create();
            a.show();
            rt.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    String rate = "Ratings:" + String.valueOf(rt.getRating());
                    Toast.makeText(getApplicationContext(), rate, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Thanks for Ratings", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (id == R.id.nav_About) {
            Intent itabt = new Intent(this, About.class);
            startActivity(itabt);
        } else if (id == R.id.nav_logout) {
            Intent itlogout = new Intent(Main2Activity.this, UserLogin.class);
            startActivity(itlogout);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void CreatePDF(String EdtData) throws IOException {
        PdfDocument document = new PdfDocument();
        // new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(550, 800, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.LEFT);
        int x = 10, y = 60;
        for (String line : EdtData.split("\n")) {
            canvas.drawText(line, x, y, paint);
            y += paint.descent() - paint.ascent();
        }
        document.finishPage(page);
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        String pdfname = "YourCode.pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        try {
            document.writeTo(new FileOutputStream(pdfFile));
        } catch (IOException e) {
            Toast.makeText(this, "Something went wrong" + e, Toast.LENGTH_LONG).show();
        }
        document.close();
        Toast.makeText(this, "PDF SAVED SUCCESSFULLY TO\n" + docsFolder, Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdfWrapper() throws FileNotFoundException, DocumentException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            try {
                CreatePDF(editText.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
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
                .setMessage("Do you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        finish();
                        Intent i=new Intent(Main2Activity.this, register_user.class);
                        startActivity(i);

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
  //inner/helper class for handling UI Thread.
  // This class allows you to perform BG operations and publish results on UI thread without having
    //to manipulate threads and/or handlers.
    //used for short operations(a few seconds at the most
    //If you want need to keep thread running for longer periods od time
    //it is highly recommded to useAPI - java.util.Concurrent,Executor,ThreadPoolExecutor,FutureTask
    //has 3 methods onPreexcute,doInBackground,onProgressUpdate,onPostExecute
    class RegisterUser extends AsyncTask<Void, Void, String> {
       // private ProgressBar progressBar;
        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();
            //creating request parameters
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("code1", code);
           /* params.put("code1", " import java.util.Scanner; class Main\n" +
                    "{\n" +
                    "public static void main(String arg[])\n" +
                    "{ \n " +
                    "System.out.println(\"hello=\");\n " +
                    "}\n" +
                    "}\n"); */
            params.put("input1", input1);
            result1 = "" + requestHandler.sendPostRequest(apiPath, params);
            return result1;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //displaying the progress bar while user registers on the server
            //  progressBar = (ProgressBar) findViewById(R.id.progressBar);
            //   progressBar.setVisibility(View.VISIBLE);
        }
       /* @Override
        protected void onProgressUpdate(Integer...progress)
        {
            //setProgress(progress[0]);
        } */

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent it = new Intent(Main2Activity.this, OutputActivity.class);
            it.putExtra("Output", result1);
            startActivity(it);

            //  t1.setText(result1);
            //hiding the progressbar after completion
            // progressBar.setVisibility(View.GONE);
            //   t1.setText(result1);
                /*
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        User user = new User(
                                userJson.getInt("id"),
                                userJson.getString("username"),
                                userJson.getString("email"),
                                userJson.getString("gender")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
        }
    }
}




































































   /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            } */
   /* private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        String pdfname = "YourCode.pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{3});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
       for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
            System.out.println("Done");

        PdfWriter.getInstance(document, output);
        document.open();
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.UNDERLINE, BaseColor.BLUE);
        Font g = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.BLUE);
        document.add(new Paragraph("Pdf Data \n\n", f));
        document.add(new Paragraph("Pdf File Through Itext", g));
        document.add(table);

//        for (int i = 0; i < MyList1.size(); i++) {
//            document.add(new Paragraph(String.valueOf(MyList1.get(i))));
//        }
        document.close();
        previewPdf();
    }
    private void previewPdf() {

        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
        }
    } */