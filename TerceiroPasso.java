package etec.com.br.victor.basetcc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class TerceiroPasso extends AppCompatActivity {

    String horaUm, horaDois, dataUm, dataDois, intensidade, lugarEscolhido, remedioEscolhido, gatilho;
    EditText descrever;
    RadioGroup grpGatilho;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terceiro_passo);

        getSupportActionBar().hide();

        Intent telaAtual = getIntent();
        Bundle valoresRecebidos = telaAtual.getExtras();

        horaUm = valoresRecebidos.getString("horaComeco");
        horaDois = valoresRecebidos.getString("horaTermino");
        dataUm = valoresRecebidos.getString("dataComeco");
        dataDois = valoresRecebidos.getString("dataTermino");
        intensidade = valoresRecebidos.getString("intensidade");
        lugarEscolhido = valoresRecebidos.getString("lugar");
        remedioEscolhido = valoresRecebidos.getString("remedio");
        descrever = findViewById(R.id.descrevendo);
        grpGatilho = findViewById(R.id.grpGatilho);

        descrever.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                grpGatilho.clearCheck();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void btnProximo(View view) {

        switch (grpGatilho.getCheckedRadioButtonId()) {

            case R.id.familia:
                gatilho = "familia";
                break;
            case R.id.trabalho_escola:
                gatilho = "trabalho/escola";
                break;
            case R.id.preocupacao:
                gatilho = "preocupacao";
                break;
            case R.id.relacionamentos:
                gatilho = "relacionamentos";
                break;
            case R.id.pensar:
                gatilho = "pensar demais";
                break;
            default:
                gatilho = "ERRO, NULO";
                break;
        }

        if (gatilho.equals("ERRO, NULO") && descrever.getText().toString().length()<=0) {

            Toast.makeText(this, "Preencha alguma opção, ou descreva melhor", Toast.LENGTH_SHORT).show();

        } else {

            if(descrever.getText().toString().length()>0){
                gatilho = descrever.getText().toString();
            }

            finish();
            Intent quartoPasso = new Intent(TerceiroPasso.this, QuartoPasso.class);
            quartoPasso.putExtra("horaComeco", horaUm);
            quartoPasso.putExtra("horaTermino", horaDois);
            quartoPasso.putExtra("dataComeco", dataUm);
            quartoPasso.putExtra("dataTermino", dataDois);
            quartoPasso.putExtra("intensidade", intensidade);
            quartoPasso.putExtra("lugar", lugarEscolhido);
            quartoPasso.putExtra("remedio", remedioEscolhido);
            quartoPasso.putExtra("gatilho", gatilho);
            Log.e("gatilho", gatilho);
            startActivity(quartoPasso);


        }
    }

        public void btnVoltar (View view){

            finish();

        }
}