package com.cha.mp3.musicList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cha.mp3.MainActivity;
import com.cha.mp3.R;


public class MusicList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private int CP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.re_music_list);

        //인텐트로 넘겨 받은 값 처리
        Intent intent = getIntent();
        CP = intent.getIntExtra("CP", 0);

        //뮤직 리스트 커서
        ContentResolver contentResolver = getContentResolver();
        String[] ColumnsList = {MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID};
        final Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, ColumnsList, null, null, null);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        //use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //specify an adapter
        mAdapter = new ML_Adapter(cursor, getApplicationContext(), this, CP);
        mRecyclerView.setAdapter(mAdapter);
    }
}
