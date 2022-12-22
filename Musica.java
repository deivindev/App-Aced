package etec.com.br.victor.basetcc;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import etec.com.br.victor.basetcc.R;

public class Musica extends AppCompatActivity {

    ImageView somNatureza, somCalmo, somEstudar, somDormir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musica);

        getSupportActionBar().hide();

        somNatureza = findViewById(R.id.somNatureza);
        somCalmo = findViewById(R.id.somCalmo);
        somEstudar = findViewById(R.id.somEstudar);
        somDormir = findViewById(R.id.somDormir);

        MediaPlayer natureza = MediaPlayer.create(this, R.raw.natureza);
        MediaPlayer calmo = MediaPlayer.create(this, R.raw.calmo);
        MediaPlayer estudar = MediaPlayer.create(this, R.raw.estudar);
        MediaPlayer dormir = MediaPlayer.create(this, R.raw.dormir);

        somNatureza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(natureza.isPlaying()){
                    natureza.pause();
                    somNatureza.setImageResource(R.drawable.ic_play);
                } else{
                    if(calmo.isPlaying()){
                        calmo.stop();
                        somCalmo.setImageResource(R.drawable.ic_play);
                        natureza.start();
                        somNatureza.setImageResource(R.drawable.ic_pause);
                    }else if(estudar.isPlaying()){
                        estudar.stop();
                        somEstudar.setImageResource(R.drawable.ic_play);
                        natureza.start();
                        somNatureza.setImageResource(R.drawable.ic_pause);
                    }else if(dormir.isPlaying()){
                        dormir.stop();
                        somDormir.setImageResource(R.drawable.ic_play);
                        natureza.start();
                        somNatureza.setImageResource(R.drawable.ic_pause);
                    }else{
                        natureza.start();
                        somNatureza.setImageResource(R.drawable.ic_pause);
                    }
                }
            }
        });

        somCalmo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(calmo.isPlaying()){
                    calmo.pause();
                    somCalmo.setImageResource(R.drawable.ic_play);
                } else{
                    if(natureza.isPlaying()){
                        natureza.stop();
                        somNatureza.setImageResource(R.drawable.ic_play);
                        calmo.start();
                        somCalmo.setImageResource(R.drawable.ic_pause);
                    }else if(estudar.isPlaying()){
                        estudar.stop();
                        somEstudar.setImageResource(R.drawable.ic_play);
                        calmo.start();
                        somCalmo.setImageResource(R.drawable.ic_pause);
                    }else if(dormir.isPlaying()){
                        dormir.stop();
                        somDormir.setImageResource(R.drawable.ic_play);
                        calmo.start();
                        somCalmo.setImageResource(R.drawable.ic_pause);
                    }else{
                        calmo.start();
                        somCalmo.setImageResource(R.drawable.ic_pause);
                    }
                }
            }
        });

        somEstudar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(estudar.isPlaying()){
                    estudar.pause();
                    somEstudar.setImageResource(R.drawable.ic_play);
                } else{
                    if(natureza.isPlaying()){
                        natureza.stop();
                        somNatureza.setImageResource(R.drawable.ic_play);
                        estudar.start();
                        somEstudar.setImageResource(R.drawable.ic_pause);
                    }else if(calmo.isPlaying()){
                        calmo.stop();
                        somCalmo.setImageResource(R.drawable.ic_play);
                        estudar.start();
                        somEstudar.setImageResource(R.drawable.ic_pause);
                    }else if(dormir.isPlaying()){
                        dormir.stop();
                        somDormir.setImageResource(R.drawable.ic_play);
                        estudar.start();
                        somEstudar.setImageResource(R.drawable.ic_pause);
                    }else{
                        estudar.start();
                        somEstudar.setImageResource(R.drawable.ic_pause);
                    }
                }
            }
        });

        somDormir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dormir.isPlaying()){
                    dormir.pause();
                    somDormir.setImageResource(R.drawable.ic_play);
                } else{
                    if(natureza.isPlaying()){
                        natureza.stop();
                        somNatureza.setImageResource(R.drawable.ic_play);
                        dormir.start();
                        somDormir.setImageResource(R.drawable.ic_pause);
                    }else if(calmo.isPlaying()){
                        calmo.stop();
                        somCalmo.setImageResource(R.drawable.ic_play);
                        dormir.start();
                        somDormir.setImageResource(R.drawable.ic_pause);
                    }else if(estudar.isPlaying()){
                        estudar.stop();
                        somEstudar.setImageResource(R.drawable.ic_play);
                        dormir.start();
                        somDormir.setImageResource(R.drawable.ic_pause);
                    }else{
                        dormir.start();
                        somDormir.setImageResource(R.drawable.ic_pause);
                    }
                }
            }
        });

    }

    public void voltar(View view) {

        finish();

    }
}