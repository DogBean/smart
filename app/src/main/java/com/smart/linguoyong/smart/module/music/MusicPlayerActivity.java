package com.smart.linguoyong.smart.module.music;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.smart.linguoyong.smart.R;

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
}
