package etec.com.br.victor.basetcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CriseRegistrada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crise_registrada);
        getSupportActionBar().hide();
    }
    public void feed(View view) {

        Intent intent = new Intent(CriseRegistrada.this, TelaInicial.class);
        startActivity(intent);
    }
    public void relatorio(View view) {
        finish();
        Intent intent = new Intent(CriseRegistrada.this, Atividade.class);
        startActivity(intent);

    }

    public void voltar(View view) {
        finish();
    }
}