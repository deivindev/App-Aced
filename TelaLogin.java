package etec.com.br.victor.basetcc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class TelaLogin extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView google_btn;
    EditText edit_email, edit_senha;
    String[] mensagens = {"Preencha todos os campos"};
    GoogleSignInAccount acct;
    FirebaseFirestore bd = FirebaseFirestore.getInstance();
    String usuarioID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        getSupportActionBar().hide();

        google_btn = findViewById(R.id.google_btn);
        edit_email = findViewById(R.id.email);
        edit_senha = findViewById(R.id.senha);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){

            navigateToSecondActivity();

        }

        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();

            }
        });

    }

    void signIn(){

        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(this, "Algo deu errado !", Toast.LENGTH_SHORT).show();
            }

        }

    }

    void navigateToSecondActivity(){
        finish();
        acct = GoogleSignIn.getLastSignedInAccount(this);
        Intent intent = new Intent(TelaLogin.this, TelaInicial.class);
        String personName = acct.getDisplayName();
        String personEmail = acct.getEmail();
        intent.putExtra("nome", personName);
        intent.putExtra("email", personEmail);
        startActivity(intent);

    }

    void telaID(){

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = bd.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentoSnapshot, @Nullable FirebaseFirestoreException error) {

                Intent intent = new Intent(TelaLogin.this, TelaInicial.class);
                String nomeID = documentoSnapshot.getString("nome");
                String emailID = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String reacaoID = documentoSnapshot.getString("reacao");
                intent.putExtra("nome", nomeID);
                intent.putExtra("email", emailID);
                intent.putExtra("reacao", reacaoID);
                startActivity(intent);
            }
        });
    }

    public void entrar(View view) {

        //BOTÃO ENTRAR COM EMAIL/SENHA

        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        if (email.isEmpty() || senha.isEmpty()) {

            Snackbar snackbar = Snackbar.make(view, mensagens[0], Snackbar.LENGTH_SHORT);
            snackbar.setBackgroundTint(Color.WHITE);
            snackbar.setTextColor(Color.BLACK);
            snackbar.show();

        }else{
            AutenticarUsuario(view);
        }

    }

    private void AutenticarUsuario(View view){

        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(new
            OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            telaID();
                        }
                    },0);
                }else{
                    String erro;

                    try {
                        throw task.getException();
                    }catch (Exception e){
                        erro = "Erro ao logar usuário";
                    }Snackbar snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }

            }
        });

    }

    public void btnVoltar(View view) {

        finish();

    }
}