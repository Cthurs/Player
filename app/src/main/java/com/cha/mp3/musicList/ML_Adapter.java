package com.cha.mp3.musicList;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cha.mp3.MainActivity;
import com.cha.mp3.R;

/**
 * Created by Cha on 2016-10-20.
 */
public class ML_Adapter extends RecyclerView.Adapter<ML_Adapter.ViewHolder> {
    private Cursor cursor;
    private Context context;
    private int mCP;
    private Activity mActivity;

    public ML_Adapter(Cursor cursor, Context contexts, Activity mActivity, int CP) {
        this.mCP = CP;
        this.cursor = cursor;
        this.mActivity = mActivity;
        this.context = contexts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView mTitle;
        public final TextView mArtist;
        public final ImageView mAlbum;
        public final View mView;

        //adapter item layout에 있는 아이템들을 생성자로 초기화 함.
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTitle = (TextView) itemView.findViewById(R.id.music_title);
            mArtist = (TextView) itemView.findViewById(R.id.music_artist);
            mAlbum = (ImageView) itemView.findViewById(R.id.music_album);
        }
    }


    //Create new views(invoked by the layout manager)
    //여기서 뷰홀더의 생성자에 뷰(아이템레이아웃을 인플레이트 시킨)를 넣어서 아이템들을 초기화 해주는 역할을 함.
    @Override
    public ML_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.re_music_list_item, parent, false);
        return new ViewHolder(v);
    }

    //Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        cursor.moveToPosition(position);

        //albumart Uri 가져오기
        long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
        Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
        Uri myAlbumURI = ContentUris.withAppendedId(sArtworkUri, albumId);

        holder.mAlbum.setImageURI(myAlbumURI);
        holder.mTitle.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.TITLE)));
        holder.mArtist.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCP==position){
                    mActivity.finish();
                }

                cursor.moveToPosition(position);
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                Log.d("패쓰 ", path);

                //--------------------------------------------------------------------------------
                Intent intent = new Intent();
                intent.putExtra("PATH", path);
                intent.putExtra("TITLE", cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                intent.putExtra("ALBUM", cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                intent.putExtra("POSITION", position);

                mActivity.setResult(mActivity.RESULT_OK, intent);
                mActivity.finish();
                //--------------------------------------------------------------------------------
            }
        });
    }

    //리스트의 길이를 정해주는 것.
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

}
