package etec.com.br.victor.basetcc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    EditText telefonetxt, nascimentotxt, nometxt, emailtxt;
    GoogleSignInAccount acct;
    String usuarioID;
    FirebaseFirestore bd = FirebaseFirestore.getInstance();
    LinearLayout trocar_foto;
    ImageView imagem_perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getSupportActionBar().hide();

        //reacaoBD = findViewById(R.id.reacaoBD);

        nometxt = findViewById(R.id.name);
        emailtxt = findViewById(R.id.email);
        telefonetxt = findViewById(R.id.telefone);
        nascimentotxt = findViewById(R.id.nascimento);
        trocar_foto = findViewById(R.id.trocarFoto);
        imagem_perfil = findViewById(R.id.imageProfile);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);



        trocar_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TROCAR A FOTO DA PORRA DO USUÁRIO

                AlertDialog.Builder selecionarFoto = new AlertDialog.Builder(SecondActivity.this);
                selecionarFoto.setTitle("Origem da foto");
                selecionarFoto.setMessage("Por favor, selecione a origem da foto");
                selecionarFoto.setPositiveButton("Câmera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);

                    }
                });
                selecionarFoto.setNegativeButton("Galeria", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent,2);

                    }
                });
                selecionarFoto.setNeutralButton("Icone padrão", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        imagem_perfil.setImageResource(R.drawable.fotopadrao);

                    }
                });
                selecionarFoto.create().show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK) {
                try {

                    Bitmap fotoRegistrada = (Bitmap) data.getExtras().get("data");

                    imagem_perfil.setImageBitmap(fotoRegistrada);
                    byte[] fotoEmBytes;

                    ByteArrayOutputStream streamDaFotoEmBytes = new ByteArrayOutputStream();

                    fotoRegistrada.compress(Bitmap.CompressFormat.PNG, 70, streamDaFotoEmBytes);
                    fotoEmBytes = streamDaFotoEmBytes.toByteArray();

                    //imagem_perfil_string = Base64.getEncoder().encodeToString(Base64.,);
                } catch (Exception e) {

                }
            } else{

                imagem_perfil.setImageResource(R.drawable.fotomenu);
                //imagem_perfil_string = "null";

            }

        } else if(requestCode == 2){
            if(resultCode == RESULT_OK) {
                try {

                    Uri imageUri = data.getData();

                    Bitmap fotoBuscada = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                    imagem_perfil.setImageBitmap(fotoBuscada);
                    byte[] fotoEmBytes;
                    ByteArrayOutputStream streamDaFotoEmBytes = new ByteArrayOutputStream();

                    fotoBuscada.compress(Bitmap.CompressFormat.PNG, 70, streamDaFotoEmBytes);
                    fotoEmBytes = streamDaFotoEmBytes.toByteArray();

                    //imagem_perfil_string = Base64.getEncoder().encodeToString(Base64.,);
                } catch (Exception e) {

                }
            } else{

                imagem_perfil.setImageResource(R.drawable.fotomenu);
                //imagem_perfil_string = "null";

            }

        }

    }

    public void voltar(View view) {

        finish();

    }

    public void btnSair(View view) {

        signOut();

    }

    void signOut() {


        if (gsc != null) {
            gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    finish();
                    startActivity(new Intent(SecondActivity.this, MainActivity.class));

                }
            });

        }

        else{

            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }

    }

    public void btnSalvar(View view) {

        acct = GoogleSignIn.getLastSignedInAccount(SecondActivity.this);

        if(acct==null) {
            String nome = nometxt.getText().toString();
            String email = emailtxt.getText().toString();
            String telefone = telefonetxt.getText().toString();
            String nascimento = nascimentotxt.getText().toString();

            FirebaseFirestore bd = FirebaseFirestore.getInstance();

            Map<String, Object> usuarios = new HashMap<>();
            usuarios.put("nome", nome);
            usuarios.put("email", email);

            //ESTA LINHA A SEGUIR É RESPONSAVEL POR CRIAR OUTRO ATRIBUTO NO MESMO ID
            usuarios.put("telefone", telefone);

            usuarios.put("data de nascimento", nascimento);

            usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

            DocumentReference documentReference = bd.collection("Usuarios").document(usuarioID);
            documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Toast.makeText(SecondActivity.this, "Dados atualizados com sucesso",
                            Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Log.e("bd_error", "ERRO AO SALVAR OS DADOS" + e.toString());

                }
            });
        }else{

            Toast.makeText(this, "Impossível mudar seus dados do google", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        colocarDados();
    }

    public void colocarDados(){

        acct = GoogleSignIn.getLastSignedInAccount(SecondActivity.this);

        if(acct==null){

            usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

            DocumentReference documentReference = bd.collection("Usuarios").document(usuarioID);
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentoSnapshot, @Nullable
                        FirebaseFirestoreException error) {

                    String nomeID = documentoSnapshot.getString("nome");
                    String telefoneID = documentoSnapshot.getString("telefone");
                    String nascimentoID = documentoSnapshot.getString("data de nascimento");
                    String emailID = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                    nometxt.setText(nomeID);
                    emailtxt.setText(emailID);
                    telefonetxt.setText(telefoneID);
                    nascimentotxt.setText(nascimentoID);
                }
            });
        }else{
            Intent telaAtual = getIntent();
            Bundle valoresRecebidos = telaAtual.getExtras();
            nometxt.setText(valoresRecebidos.getString("nome"));
            emailtxt.setText(valoresRecebidos.getString("email"));
            telefonetxt.setHint("TELEFONE DO USUÁRIO");
            nascimentotxt.setHint("DATA DE NASCIMENTO DO USUÁRIO");
        }
    }
}