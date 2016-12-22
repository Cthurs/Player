package com.cha.mp3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class ABsave extends AppCompatActivity {
    double ab;
    double start;
    double end;
    private String path;
    private int position;
    private String title;

    EditText et;

    Cursor cursor;
    //DB부분
    MyDB mydb;
    SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absave);


        et = (EditText)findViewById(R.id.editText);

        Intent intent = getIntent();
        ab = intent.getDoubleExtra("AB", 2020);
        start = intent.getDoubleExtra("START", 1020);
        end = intent.getDoubleExtra("END", 0020);
        title = intent.getStringExtra("TITLE");
        path = intent.getStringExtra("PATH");
        position = intent.getIntExtra("CP", 0);

        TextView tv = (TextView) findViewById(R.id.textView5);
        tv.setText(String.format("%02d : %02d.%02d",
                        TimeUnit.MILLISECONDS.toMinutes((long) start),
                        TimeUnit.MILLISECONDS.toSeconds((long) start) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) start)),
                        (TimeUnit.MILLISECONDS.toMillis((long)start)-
                                TimeUnit.MILLISECONDS.toSeconds((long) start)*1000)/10)
        );

        TextView tv2 = (TextView)findViewById(R.id.textView7);
        tv2.setText(String.format("%02d : %02d.%02d",
                        TimeUnit.MILLISECONDS.toMinutes((long) end),
                        TimeUnit.MILLISECONDS.toSeconds((long) end) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) end)),
                        (TimeUnit.MILLISECONDS.toMillis((long)end)-
                                TimeUnit.MILLISECONDS.toSeconds((long) end)*1000)/10)
        );

        TextView tv_title = (TextView)findViewById(R.id.absave_tv_title);
        tv_title.setText(title);
        tv_title.setSelected(true);

        mydb = new MyDB(getApplicationContext());

        Button btn = (Button) findViewById(R.id.button6);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = mydb.getWritableDatabase();

                String s = et.getText().toString();
                String sql = "INSERT INTO repeat VALUES(null, '"+s+"', '"+title+"', " + start +", "+end+", "+ab+", '"+path +"',"+position+");";
                db.execSQL(sql);
                db.close();

                finish();
            }
        });
        Button btn2 = (Button) findViewById(R.id.button7);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
