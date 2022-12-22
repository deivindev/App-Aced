package etec.com.br.victor.basetcc;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public class anotacaoAtual extends AppCompatActivity {

    EditText titulo, anotacao;
    String tituloo, note;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacao_atual);
        getSupportActionBar().hide();

        titulo = findViewById(R.id.titulo);
        anotacao = findViewById(R.id.anotacao_agora);

        Intent telaAtual = getIntent();
        Bundle valoresRecebidos = telaAtual.getExtras();

        titulo.setText(valoresRecebidos.getString("tituloAtual"));
        String tituloQuery = titulo.getText().toString();
        FirebaseFirestore bd = FirebaseFirestore.getInstance();
        bd.collection("Notas")
                .whereEqualTo("titulo", tituloQuery)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.e("ID com titulo", "ID: " + document.getId() + " => " + document.get("titulo"));
                                String idAnotacao = document.getId();
                                String nota = String.valueOf(document.get("mensagem"));
                                anotacao.setText(nota);

                            }
                        } else {
                            Log.e("ERRO", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void voltar(View view) {
        finish();
    }

    public void btnSalvar(View view) {

        tituloo = titulo.getText().toString();
        note = anotacao.getText().toString();
        FirebaseFirestore bd = FirebaseFirestore.getInstance();

        bd.collection("Notas")
                .whereEqualTo("titulo", tituloo)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                Map<String, Object> notas = new HashMap<>();
                                notas.put("titulo", tituloo);
                                notas.put("mensagem", note); //ANOTAÇÃO SALVA PELO USUÁRIO
                                notas.put("dono", usuarioID); //ID DO USUÁRIO, PRA INTERLIGAR
                                Log.e("inseriu no map", "passou do map");
                                String idNota = document.getId();
                                DocumentReference documentReference = bd.collection("Notas")
                                        .document(idNota);
                                documentReference.set(notas).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
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
                        } else {
                            Log.e("ERRO", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}