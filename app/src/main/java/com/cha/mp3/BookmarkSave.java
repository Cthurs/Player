package com.cha.mp3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class BookmarkSave extends AppCompatActivity {
    String title;
    double start;
    EditText et;
    private String path;
    private int position;

    //DB부분
    MyDB mydb;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_save);


        et = (EditText)findViewById(R.id.bm_save_et_name);

        Intent intent = getIntent();

        title = intent.getStringExtra("TITLE");
        Log.d("타이틀 ", title + "");
        start = intent.getDoubleExtra("START", 1020);
        Log.d("시작시간 ", start+"");
        path = intent.getStringExtra("PATH");

        position = intent.getIntExtra("CP", 0);
        Log.d("패쓰 ", path+"");

        TextView tv = (TextView)findViewById(R.id.bm_save_tv_title);
        tv.setText(title);

        TextView tv2 = (TextView)findViewById(R.id.bm_save_tv_start);
        tv2.setText(String.format("%02d : %02d.%02d",
                        TimeUnit.MILLISECONDS.toMinutes((long) start),
                        TimeUnit.MILLISECONDS.toSeconds((long) start) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) start)),
                        (TimeUnit.MILLISECONDS.toMillis((long)start)-
                                TimeUnit.MILLISECONDS.toSeconds((long) start)*1000)/10)
        );

        mydb = new MyDB(getApplicationContext());
        Button btn = (Button) findViewById(R.id.bm_save_btn_confirm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = mydb.getWritableDatabase();
                String name = et.getText().toString();
                String sql = "INSERT INTO bookmark VALUES(null" +
                        ", '"+name+"', '" + title +"', "+start+", '"+path+"', "+position+");";
                db.execSQL(sql);
                db.close();
                finish();
            }
        });

        Button btn2 = (Button) findViewById(R.id.bm_save_btn_cancle);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
