package etec.com.br.victor.basetcc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import etec.com.br.victor.basetcc.R;

public class Notas extends AppCompatActivity {

    String titulo;
    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        getSupportActionBar().hide();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.recyler_notas);

        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Query query = firebaseFirestore.collection("Notas").whereEqualTo("dono", usuarioID);

        Log.e("o que tem no query", "" +query);

        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<Modelo>()
                .setQuery(query, Modelo.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Modelo, ModeloViewHolder>(options) {
            @NonNull
            @Override
            public ModeloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anotacoes_xml, parent, false);
                return new ModeloViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull ModeloViewHolder holder, @SuppressLint("RecyclerView")
                    int position, @NonNull Modelo model) {
                holder.list_Titulo.setText(model.getTitulo());

                holder.list_Titulo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        titulo = model.getTitulo();
                        FirebaseFirestore bd = FirebaseFirestore.getInstance();
                        bd.collection("Notas")
                                .whereEqualTo("titulo", titulo)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.e("ID com titulo", "ID: " + document.getId() + " => " +
                                                        document.get("titulo"));
                                            }
                                        } else {
                                            Log.e("ERRO", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });

                        Intent intent = new Intent(Notas.this, anotacaoAtual.class);
                        intent.putExtra("tituloAtual", titulo);
                        startActivity(intent);
                    }
                });
            }

        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(Notas.this));
        mFirestoreList.setAdapter(adapter);

    }


 private class ModeloViewHolder extends RecyclerView.ViewHolder {
     private TextView list_Titulo;
     public ModeloViewHolder(@NonNull View itemView) {
         super(itemView);
         list_Titulo = itemView.findViewById(R.id.tituloNotas);
     }
 }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    protected void onStart() {
        super.onStart();

        adapter.startListening();
    }
    public void voltar(View view) {finish();}
    public void btnAdicionar(View view) {
        Intent telaAnotacao = new Intent(Notas.this, Anotacao.class);
        startActivity(telaAnotacao);
    }
}