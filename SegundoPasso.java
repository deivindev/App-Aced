package etec.com.br.victor.basetcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class SegundoPasso extends AppCompatActivity {

    String horaUm, horaDois, dataUm, dataDois, intensidade, lugarEscolhido, remedioEscolhido;
    RadioGroup grpLugar, grpRemedio;
    RadioButton casa, trabalhop, festa, escola, casa_outro, outro;
    Button btnProximo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundo_passo);

        getSupportActionBar().hide();

        Intent telaAtual = getIntent();
        Bundle valoresRecebidos = telaAtual.getExtras();

        horaUm = valoresRecebidos.getString("horaComeco");
        horaDois = valoresRecebidos.getString("horaTermino");
        dataUm = valoresRecebidos.getString("dataComeco");
        dataDois = valoresRecebidos.getString("dataTermino");
        intensidade = valoresRecebidos.getString("intensidade");

        casa = findViewById(R.id.casa);
        trabalhop = findViewById(R.id.trabalho);
        festa = findViewById(R.id.festa);
        escola = findViewById(R.id.escola);
        casa_outro = findViewById(R.id.outra_pessoa);
        outro = findViewById(R.id.outro);
        grpLugar = findViewById(R.id.grpLugar);
        grpRemedio = findViewById(R.id.grpRemedio);
        btnProximo = findViewById(R.id.btnProximo);

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int op = grpLugar.getCheckedRadioButtonId();

                if (op == R.id.casa){
                    lugarEscolhido = "casa";
                }
                else if(op == R.id.trabalho){
                    lugarEscolhido = "trabalho";
                }
                else if(op == R.id.escola){
                    lugarEscolhido = "escola";
                }
                else if(op == R.id.festa){
                    lugarEscolhido = "festa";
                }
                else if(op == R.id.outra_pessoa){
                    lugarEscolhido = "casa de outra pessoa";
                }
                else if(op == R.id.outro){
                    lugarEscolhido = "outro";
                }
                else{
                    lugarEscolhido = "ERRO, NULO";
                }

                int op2 = grpRemedio.getCheckedRadioButtonId();

                if (op2 == R.id.com_remedio){
                    remedioEscolhido = "sim";
                }
                else if (op2 == R.id.sem_remedio){
                    remedioEscolhido = "não";
                }
                else{
                    remedioEscolhido = "ERRO, NULO";
                }

                if (lugarEscolhido.equals("ERRO, NULO") || remedioEscolhido.equals("ERRO, NULO")) {

                    Toast.makeText(SegundoPasso.this, "Preencha todos os campos", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    finish();
                    Intent terceiroPasso = new Intent(SegundoPasso.this, TerceiroPasso.class);
                    terceiroPasso.putExtra("horaComeco", horaUm);
                    terceiroPasso.putExtra("horaTermino", horaDois);
                    terceiroPasso.putExtra("dataComeco", dataUm);
                    terceiroPasso.putExtra("dataTermino", dataDois);
                    terceiroPasso.putExtra("intensidade", intensidade);
                    terceiroPasso.putExtra("lugar", lugarEscolhido);
                    Log.e("lugar", lugarEscolhido);
                    terceiroPasso.putExtra("remedio", remedioEscolhido);
                    Log.e("remedio", remedioEscolhido);
                    startActivity(terceiroPasso);
                }

                }
            });
    }

    public void btnVoltar(View view) {

        finish();

    }
}
