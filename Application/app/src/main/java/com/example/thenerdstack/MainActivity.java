package com.example.thenerdstack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    int x=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MediaPlayer player=MediaPlayer.create(this,R.raw.backgroundsound); //to play music in background
        player.setLooping(true);
        player.start();
        Button b;
        final ImageButton mute;
        b=findViewById(R.id.bot);
         mute=findViewById(R.id.mute); //to mute background music
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,nirvana.class); //to call new activity
                startActivity(intent);
            }
        });
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(x==0) {
                    player.pause();

                    mute.setImageResource(R.drawable.play); //to set background image

                    x=1;
                }
                else
                {
                    player.start();

                    x=0;
                    mute.setImageResource(R.drawable.mute);
                }

            }
        });


    }
}
