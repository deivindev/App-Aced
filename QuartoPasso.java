package etec.com.br.victor.basetcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuartoPasso extends AppCompatActivity {

    String horaUm, horaDois, dataUm, dataDois, intensidade, lugarEscolhido, remedioEscolhido, gatilho, sentir;
    EditText outro;
    RadioGroup grpSentimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quarto_passo);

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
        gatilho = valoresRecebidos.getString("gatilho");
        outro = findViewById(R.id.outroSentimento);
        grpSentimento = findViewById(R.id.grpSentimento);

        outro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                grpSentimento.clearCheck();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void btnVoltar(View view) {

        finish();

    }

    public void btnProximo(View view) {

        switch (grpSentimento.getCheckedRadioButtonId()){

            case R.id.agitacao:
                sentir = "agitação";
                break;
            case R.id.fome_compulsiva:
                sentir = "fome compulsiva";
                break;
            case R.id.estresse:
                sentir = "estresse";
                break;
            case R.id.falta_de_ar:
                sentir = "falta de ar";
                break;
            case R.id.vontade_de_chorar:
                sentir = "vontade de chorar";
                break;
            case R.id.coracao_acelerado:
                sentir = "coração acelerado";
                break;
            default:
                sentir = "ERRO, NULO";
                break;

        }

        if(sentir.equals("ERRO, NULO") && outro.getText().toString().length()<=0){

            Toast.makeText(this, "Preench alguma opção, ou descreva melhor", Toast.LENGTH_SHORT).show();

        }
        else {

            if(outro.getText().toString().length()>0){
                sentir = outro.getText().toString();
            }

            FirebaseFirestore bd = FirebaseFirestore.getInstance();
            String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

            Map<String, Object> crise = new HashMap<>();
            crise.put("dono", usuarioID);
            crise.put("horaComeco", horaUm);
            crise.put("horaTermino", horaDois);
            crise.put("dataComeco", dataUm);
            crise.put("dataTermino", dataDois);
            crise.put("intensidade", intensidade);
            crise.put("lugar", lugarEscolhido);
            crise.put("remedio", remedioEscolhido);
            crise.put("gatilho", gatilho);
            crise.put("sentimento", sentir);

            String idCrise = UUID.randomUUID().toString();

            DocumentReference documentReference = bd.collection("Crises").document(idCrise);
            documentReference.set(crise).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    finish();
                    Intent fim = new Intent(QuartoPasso.this, CriseRegistrada.class);
                    startActivity(fim);
                    Toast.makeText(QuartoPasso.this, "Crise registrada !", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Log.e("bd_error", "ERRO AO SALVAR OS DADOS" + e.toString());

                }
            });

        }

    }
}