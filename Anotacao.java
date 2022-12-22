package etec.com.br.victor.basetcc;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public class Anotacao extends AppCompatActivity {

    EditText anotacao, txttitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacao);

        getSupportActionBar().hide();

        anotacao = findViewById(R.id.anotacao_agora);
        txttitulo = findViewById(R.id.titulo);
    }
    public void voltar(View view) {
        finish();
    }
    public void btnSalvar(View view) {
        String note = anotacao.getText().toString();
        String titulo = txttitulo.getText().toString();

        String idMensagem = UUID.randomUUID().toString();//ID ALEATÓRIO PARA UMA MENSAGEM

        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> notas = new HashMap<>();
        notas.put("titulo", titulo);
        notas.put("mensagem", note); //ANOTAÇÃO SALVA PELO USUÁRIO
        notas.put("dono", usuarioID); //ID DO USUÁRIO, PRA INTERLIGAR
        Log.e("inseriu no map", "passou do map");
        FirebaseFirestore bd = FirebaseFirestore.getInstance();

        DocumentReference documentReference = bd.collection("Notas").document(idMensagem);
        documentReference.set(notas).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                //Toast.makeText(Anotacao.this,"Anotação salva com sucesso", Toast.LENGTH_SHORT);
                finish();
                Log.e("bd", "Anotação salva com sucesso");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.e("bd_error", "ERRO AO SALVAR OS DADOS" + e.toString());

            }
        });

    }
}