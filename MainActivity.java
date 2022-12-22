package etec.com.br.victor.basetcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }
    public void btnCad(View view) {

        Intent cadastrar = new Intent(MainActivity.this, TelaCadastro.class);
        startActivity(cadastrar);

    }

    public void btnEnt(View view) {

        Intent entrar = new Intent(MainActivity.this, TelaLogin.class);
        startActivity(entrar);
    }

}