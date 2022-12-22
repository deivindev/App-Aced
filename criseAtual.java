package etec.com.br.victor.basetcc;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileOutputStream;

public class criseAtual extends AppCompatActivity {

    String dataComeco;
    TextView data, nome, dataa, horaTerminoo, dataTerminoo, horaComecoo, intensidadeo, ondeo, gatilhoo,
            remedioo, sentimentoo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crise_atual);

        getSupportActionBar().hide();

        data = findViewById(R.id.datinha);
        nome = findViewById(R.id.nome);
        dataa = findViewById(R.id.datinhaa);
        horaComecoo = findViewById(R.id.horaComeco);
        horaTerminoo = findViewById(R.id.horatermino);
        dataTerminoo = findViewById(R.id.dataTermino);
        intensidadeo = findViewById(R.id.intensidade);
        ondeo = findViewById(R.id.onde);
        gatilhoo = findViewById(R.id.gatilho);
        remedioo = findViewById(R.id.remedio);
        sentimentoo = findViewById(R.id.sentimentoo);

        Intent telaAtual = getIntent();
        Bundle valoresRecebidos = telaAtual.getExtras();

        data.setText(valoresRecebidos.getString("dataComeco"));
        String datinhaaaa = data.getText().toString();
        dataa.setText("Data: " + datinhaaaa);

        dataComeco = valoresRecebidos.getString("dataComeco");

        FirebaseFirestore bd = FirebaseFirestore.getInstance();
        bd.collection("Crises")
                .whereEqualTo("dataComeco", dataComeco)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("ID com tudo", "ID: " + document.getId() + " => " +
                                        document.get("intensidade"));
                                String horaComeco = String.valueOf(document.get("horaComeco"));
                                String horaTermino = String.valueOf(document.get("horaTermino"));
                                String dataTermino = String.valueOf(document.get("dataTermino"));
                                String onde = String.valueOf(document.get("lugar"));
                                String gatilho = String.valueOf(document.get("gatilho"));
                                String intensidade = String.valueOf(document.get("intensidade"));
                                String sentimento = String.valueOf(document.get("sentimento"));
                                String remedio = String.valueOf(document.get("remedio"));

                                horaComecoo.setText(horaComeco);
                                horaTerminoo.setText(horaTermino);
                                dataTerminoo.setText(dataTermino);
                                ondeo.setText(onde);
                                gatilhoo.setText(gatilho);
                                intensidadeo.setText(intensidade);
                                sentimentoo.setText(sentimento);
                                remedioo.setText(remedio);
                            }
                        } else {
                            Log.e("ERRO", "Error getting documents: ", task.getException());
                        }
                    }
                });
        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = bd.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentoSnapshot, @Nullable
                    FirebaseFirestoreException error) {
                String nomeID = documentoSnapshot.getString("nome");
                nome.setText("Nome: " + nomeID);
            }
        });

    }

    public void voltar(View view) {
        finish();
    }

    public void btnPDF(View view) {
        Toast.makeText(this, "Exportação ainda não disponível!", Toast.LENGTH_SHORT).show();
    }
}