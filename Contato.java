package etec.com.br.victor.basetcc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import etec.com.br.victor.basetcc.R;

public class Contato extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);
        getSupportActionBar().hide();
    }
    public void voltar(View view) {finish();}
    public void facebook(View view) {
        Toast.makeText(this, "Função ainda não disponível", Toast.LENGTH_SHORT).show();
    }
    public void twitter(View view) {
        Toast.makeText(this, "Função ainda não disponível", Toast.LENGTH_SHORT).show();
    }
    public void instagram(View view) {
        Toast.makeText(this, "Função ainda não disponível", Toast.LENGTH_SHORT).show();
    }
    public void whatsapp(View view) {
        Toast.makeText(this, "Função ainda não disponível", Toast.LENGTH_SHORT).show();
    }
}