package etec.com.br.victor.basetcc;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import etec.com.br.victor.basetcc.R;
public class Atividade extends AppCompatActivity {

    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade);

        getSupportActionBar().hide();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.recyler_crise);

        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Query query = firebaseFirestore.collection("Crises").whereEqualTo("dono", usuarioID);

       FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<ModeloCrise>()
               .setQuery(query, ModeloCrise.class)
               .build();
        adapter = new FirestoreRecyclerAdapter<ModeloCrise, ModeloCriseViewHolder>(options) {
           @NonNull
           @Override
           public ModeloCriseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crise, parent, false);
               return new ModeloCriseViewHolder(view);
           }
           @Override
           protected void onBindViewHolder(@NonNull ModeloCriseViewHolder holder, int position, @NonNull
                   ModeloCrise model) {
            holder.List_Data.setText(model.getdataComeco());

               holder.List_Data.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       String data = model.getdataComeco();
                       FirebaseFirestore bd = FirebaseFirestore.getInstance();
                       bd.collection("Crises")
                               .whereEqualTo("dataComeco", data)
                               .get()
                               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                   @Override
                                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                       if (task.isSuccessful()) {
                                           for (QueryDocumentSnapshot document : task.getResult()) {
                                           }
                                       } else {
                                           Log.e("ERRO", "Error getting documents: ", task.getException());
                                       }
                                   }
                               });

                       Intent intent = new Intent(Atividade.this, criseAtual.class);
                       intent.putExtra("dataComeco", data);
                       startActivity(intent);
                   }
               });

           }
       };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(Atividade.this));
        mFirestoreList.setAdapter(adapter);

    }


    private class ModeloCriseViewHolder extends RecyclerView.ViewHolder {

        private TextView List_Data;

        public ModeloCriseViewHolder(@NonNull View itemView) {
            super(itemView);

            List_Data = itemView.findViewById(R.id.DataCrise);
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

    public void voltar(View view) {

        finish();

    }
}