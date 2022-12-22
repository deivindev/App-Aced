package etec.com.br.victor.basetcc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import etec.com.br.victor.basetcc.R;

public class Configuracao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        getSupportActionBar().hide();

    }

    public void voltar(View view) {

        finish();

    }

    public void idioma(View view) {
        Toast.makeText(this, "Função ainda não disponível", Toast.LENGTH_SHORT).show();
    }

    public void tema(View view) {
        Toast.makeText(this, "Função ainda não disponível", Toast.LENGTH_SHORT).show();
    }

    public void excluir(View view) {
        Toast.makeText(this, "Função ainda não disponível", Toast.LENGTH_SHORT).show();
    }

    public void alterar_dados(View view) {
        Toast.makeText(this, "Função ainda não disponível", Toast.LENGTH_SHORT).show();
    }

    public void termos(View view) {
        Toast.makeText(this, "Função ainda não disponível", Toast.LENGTH_SHORT).show();
    }
}