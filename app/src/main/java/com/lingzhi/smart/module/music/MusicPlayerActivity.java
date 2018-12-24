package com.lingzhi.smart.module.music;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lingzhi.smart.R;
import com.lingzhi.smart.module.music.event.PlaySongEvent;
import com.lingzhi.smart.module.music.model.Song;

public class MusicPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        MusicPlayerFragment musicPlayerFragment = new MusicPlayerFragment();


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.music_play_fragment_container, musicPlayerFragment)
                .show(musicPlayerFragment).commit();


    }

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, MusicPlayerActivity.class);
        return intent;
    }
}
