package etec.com.br.victor.basetcc;

 import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import java.text.SimpleDateFormat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
 import android.graphics.drawable.BitmapDrawable;
 import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
 import com.google.firebase.firestore.QueryDocumentSnapshot;
 import com.google.firebase.firestore.QuerySnapshot;
 import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
 import java.util.concurrent.atomic.AtomicMarkableReference;

public class TelaInicial extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInAccount acct;
    LinearLayout meu_perfil, minha_atividade, trocar_foto;
    RelativeLayout calendario, musica, notas, sair, sobre_nos, contate_nos ,configuracoes;
    TextView nomeUser;
    private FirebaseStorage storage;

    ImageView imagem_perfil;
    //String imagem_perfil_string;
    View cabecaUm, cabecaDois, cabecaTres, cabecaQuatro, cabecaCinco, cabecaSeis;

    /*private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    StorageReference storageRef;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        getSupportActionBar().hide();

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        sair = findViewById(R.id.sairMenu);
        meu_perfil = findViewById(R.id.meu_perfil);
        minha_atividade = findViewById(R.id.minha_atividade);
        sobre_nos = findViewById(R.id.sobre_nos);
        contate_nos = findViewById(R.id.contate_nos);
        configuracoes = findViewById(R.id.configuracoes);
        calendario = findViewById(R.id.btnCalendario);
        musica = findViewById(R.id.menuMusica);
        notas = findViewById(R.id.menuNotas);
        nomeUser = findViewById(R.id.textoNome);
        trocar_foto = findViewById(R.id.trocarFoto);
        imagem_perfil = findViewById(R.id.imageProfile);

        trocar_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder selecionarFoto = new AlertDialog.Builder(TelaInicial.this);
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

        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent calendario = new Intent(TelaInicial.this, Calendario.class);
                startActivity(calendario);

            }
        });

        musica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent musicas = new Intent(TelaInicial.this, Musica.class);
                startActivity(musicas);

            }
        });

        notas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent notas = new Intent(TelaInicial.this, Notas.class);
                startActivity(notas);

            }
        });

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Sair");
                builder.setMessage("Tem certeza de que deseja sair ?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {

                                finish();
                                startActivity(new Intent(TelaInicial.this, MainActivity.class));

                            }
                        });
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();

            }
        });

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        Intent telaAtual = getIntent();
        Bundle valoresRecebidos = telaAtual.getExtras();

        String nome = valoresRecebidos.getString("nome");
        String email = valoresRecebidos.getString("email");

        nomeUser.setText(valoresRecebidos.getString("nome"));

        meu_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent perfil = new Intent(TelaInicial.this, SecondActivity.class);
                perfil.putExtra("nome", nome);
                perfil.putExtra("email", email);
                startActivity(perfil);

            }
        });

        minha_atividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent atividade = new Intent(TelaInicial.this, Atividade.class);
                startActivity(atividade);

            }
        });

        sobre_nos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sobre = new Intent(TelaInicial.this, Sobre.class);
                startActivity(sobre);

            }
        });

        contate_nos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent contate_nos = new Intent(TelaInicial.this, Contato.class);
                startActivity(contate_nos);

            }
        });

        configuracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent configuracoes = new Intent(TelaInicial.this, Configuracao.class);
                startActivity(configuracoes);

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
                    //salvarFoto();

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
                    //salvarFoto();

                    //imagem_perfil_string = Base64.getEncoder().encodeToString(Base64.,);
                } catch (Exception e) {
                }
            } else{
                imagem_perfil.setImageResource(R.drawable.fotomenu);
            }
        }
    }
    public void btnCrise(View view) {

        //BOTÃO PARA REGSTRAR A CRISE
        Intent primeiroPasso = new Intent(TelaInicial.this, PrimeiroPasso.class);
        startActivity(primeiroPasso);

    }

    public void btnRelat(View view) {

        Intent intent = new Intent(TelaInicial.this, Atividade.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Saia pelo botão de sair!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    public void dataa(){

        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Date datinha = new Date();
        String dataFormatada = formataData.format(datinha);

        FirebaseFirestore bd = FirebaseFirestore.getInstance();
        bd.collection("Reacao")
                .whereEqualTo("dia", dataFormatada)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Toast.makeText(TelaInicial.this, "Você já escolheu a sua reação de hoje!", Toast.LENGTH_SHORT).show();

                                String reaction = String.valueOf(document.get("reacao"));

                                switch (reaction) {
                                    case "1":
                                        cabecaDois.setVisibility(View.INVISIBLE);
                                        cabecaTres.setVisibility(View.INVISIBLE);
                                        cabecaQuatro.setVisibility(View.INVISIBLE);
                                        cabecaCinco.setVisibility(View.INVISIBLE);
                                        cabecaSeis.setVisibility(View.INVISIBLE);
                                        break;

                                    case "2":
                                        cabecaUm.setVisibility(View.INVISIBLE);
                                        cabecaTres.setVisibility(View.INVISIBLE);
                                        cabecaQuatro.setVisibility(View.INVISIBLE);
                                        cabecaCinco.setVisibility(View.INVISIBLE);
                                        cabecaSeis.setVisibility(View.INVISIBLE);
                                        break;

                                    case "3":
                                        cabecaUm.setVisibility(View.INVISIBLE);
                                        cabecaDois.setVisibility(View.INVISIBLE);
                                        cabecaQuatro.setVisibility(View.INVISIBLE);
                                        cabecaCinco.setVisibility(View.INVISIBLE);
                                        cabecaSeis.setVisibility(View.INVISIBLE);
                                        break;

                                    case "4":
                                        cabecaUm.setVisibility(View.INVISIBLE);
                                        cabecaDois.setVisibility(View.INVISIBLE);
                                        cabecaTres.setVisibility(View.INVISIBLE);
                                        cabecaCinco.setVisibility(View.INVISIBLE);
                                        cabecaSeis.setVisibility(View.INVISIBLE);
                                        break;

                                    case "5":
                                        cabecaUm.setVisibility(View.INVISIBLE);
                                        cabecaDois.setVisibility(View.INVISIBLE);
                                        cabecaTres.setVisibility(View.INVISIBLE);
                                        cabecaUm.setVisibility(View.INVISIBLE);
                                        cabecaSeis.setVisibility(View.INVISIBLE);
                                        break;

                                    case "6":
                                        cabecaUm.setVisibility(View.INVISIBLE);
                                        cabecaDois.setVisibility(View.INVISIBLE);
                                        cabecaTres.setVisibility(View.INVISIBLE);
                                        cabecaCinco.setVisibility(View.INVISIBLE);
                                        cabecaUm.setVisibility(View.INVISIBLE);
                                        break;

                                }

                            }
                        } else {
                            Log.e("ERRO", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void muitoFeliz(View view) {

        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Date datinha = new Date();
        String dataFormatada = formataData.format(datinha);

        String reacao = "1";

        String idReacao = UUID.randomUUID().toString();//ID ALEATÓRIO PARA UMA MENSAGEM

        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> notas = new HashMap<>();
        notas.put("reacao", reacao);
        notas.put("dia", dataFormatada);
        notas.put("dono", usuarioID); //ID DO USUÁRIO, PRA INTERLIGAR
        Log.e("inseriu no map", "passou do map");
        FirebaseFirestore bd = FirebaseFirestore.getInstance();

        DocumentReference documentReference = bd.collection("Reacao").document(idReacao);
        documentReference.set(notas).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                inicializarBotoes();
                cabecaDois.setVisibility(View.INVISIBLE);
                cabecaTres.setVisibility(View.INVISIBLE);
                cabecaQuatro.setVisibility(View.INVISIBLE);
                cabecaCinco.setVisibility(View.INVISIBLE);
                cabecaSeis.setVisibility(View.INVISIBLE);
                Toast.makeText(TelaInicial.this, "Reação diária salva!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.e("bd_error", "ERRO AO SALVAR OS DADOS" + e.toString());

            }
        });

    }

    public void feliz(View view) {

        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Date datinha = new Date();
        String dataFormatada = formataData.format(datinha);

        String reacao = "2";

        String idReacao = UUID.randomUUID().toString();//ID ALEATÓRIO PARA UMA MENSAGEM

        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> notas = new HashMap<>();
        notas.put("reacao", reacao);
        notas.put("dia", dataFormatada);
        notas.put("dono", usuarioID); //ID DO USUÁRIO, PRA INTERLIGAR
        Log.e("inseriu no map", "passou do map");
        FirebaseFirestore bd = FirebaseFirestore.getInstance();

        DocumentReference documentReference = bd.collection("Reacao").document(idReacao);
        documentReference.set(notas).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                inicializarBotoes();
                cabecaUm.setVisibility(View.INVISIBLE);
                cabecaTres.setVisibility(View.INVISIBLE);
                cabecaQuatro.setVisibility(View.INVISIBLE);
                cabecaCinco.setVisibility(View.INVISIBLE);
                cabecaSeis.setVisibility(View.INVISIBLE);
                Toast.makeText(TelaInicial.this, "Reação diária salva!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.e("bd_error", "ERRO AO SALVAR OS DADOS" + e.toString());

            }
        });
    }

    public void normal(View view) {

        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Date datinha = new Date();
        String dataFormatada = formataData.format(datinha);

        String reacao = "3";

        String idReacao = UUID.randomUUID().toString();//ID ALEATÓRIO PARA UMA MENSAGEM

        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> notas = new HashMap<>();
        notas.put("reacao", reacao);
        notas.put("dia", dataFormatada);
        notas.put("dono", usuarioID); //ID DO USUÁRIO, PRA INTERLIGAR
        Log.e("inseriu no map", "passou do map");
        FirebaseFirestore bd = FirebaseFirestore.getInstance();

        DocumentReference documentReference = bd.collection("Reacao").document(idReacao);
        documentReference.set(notas).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                inicializarBotoes();
                cabecaDois.setVisibility(View.INVISIBLE);
                cabecaUm.setVisibility(View.INVISIBLE);
                cabecaQuatro.setVisibility(View.INVISIBLE);
                cabecaCinco.setVisibility(View.INVISIBLE);
                cabecaSeis.setVisibility(View.INVISIBLE);
                Toast.makeText(TelaInicial.this, "Reação diária salva!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.e("bd_error", "ERRO AO SALVAR OS DADOS" + e.toString());

            }
        });
    }

    public void triste(View view) {

        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Date datinha = new Date();
        String dataFormatada = formataData.format(datinha);

        String reacao = "4";

        String idReacao = UUID.randomUUID().toString();//ID ALEATÓRIO PARA UMA MENSAGEM

        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> notas = new HashMap<>();
        notas.put("reacao", reacao);
        notas.put("dia", dataFormatada);
        notas.put("dono", usuarioID); //ID DO USUÁRIO, PRA INTERLIGAR
        Log.e("inseriu no map", "passou do map");
        FirebaseFirestore bd = FirebaseFirestore.getInstance();

        DocumentReference documentReference = bd.collection("Reacao").document(idReacao);
        documentReference.set(notas).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                inicializarBotoes();
                cabecaDois.setVisibility(View.INVISIBLE);
                cabecaTres.setVisibility(View.INVISIBLE);
                cabecaUm.setVisibility(View.INVISIBLE);
                cabecaCinco.setVisibility(View.INVISIBLE);
                cabecaSeis.setVisibility(View.INVISIBLE);
                Toast.makeText(TelaInicial.this, "Reação diária salva!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.e("bd_error", "ERRO AO SALVAR OS DADOS" + e.toString());

            }
        });
    }

    public void muitoTriste(View view) {

        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Date datinha = new Date();
        String dataFormatada = formataData.format(datinha);

        String reacao = "5";

        String idReacao = UUID.randomUUID().toString();//ID ALEATÓRIO PARA UMA MENSAGEM

        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> notas = new HashMap<>();
        notas.put("reacao", reacao);
        notas.put("dia", dataFormatada);
        notas.put("dono", usuarioID); //ID DO USUÁRIO, PRA INTERLIGAR
        Log.e("inseriu no map", "passou do map");
        FirebaseFirestore bd = FirebaseFirestore.getInstance();

        DocumentReference documentReference = bd.collection("Reacao").document(idReacao);
        documentReference.set(notas).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                inicializarBotoes();
                cabecaDois.setVisibility(View.INVISIBLE);
                cabecaTres.setVisibility(View.INVISIBLE);
                cabecaQuatro.setVisibility(View.INVISIBLE);
                cabecaUm.setVisibility(View.INVISIBLE);
                cabecaSeis.setVisibility(View.INVISIBLE);
                Toast.makeText(TelaInicial.this, "Reação diária salva!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.e("bd_error", "ERRO AO SALVAR OS DADOS" + e.toString());

            }
        });
    }

    public void inicializarBotoes(){

        cabecaUm = findViewById(R.id.muitoFeliz);
        cabecaDois = findViewById(R.id.feliz);
        cabecaTres = findViewById(R.id.normal);
        cabecaQuatro = findViewById(R.id.triste);
        cabecaCinco = findViewById(R.id.muitoTriste);
        cabecaSeis = findViewById(R.id.raiva);

    }

    public void raiva(View view) {

        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Date datinha = new Date();
        String dataFormatada = formataData.format(datinha);

        String reacao = "6";

        String idReacao = UUID.randomUUID().toString();//ID ALEATÓRIO PARA UMA MENSAGEM

        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> notas = new HashMap<>();
        notas.put("reacao", reacao);
        notas.put("dia", dataFormatada);
        notas.put("dono", usuarioID); //ID DO USUÁRIO, PRA INTERLIGAR
        Log.e("inseriu no map", "passou do map");
        FirebaseFirestore bd = FirebaseFirestore.getInstance();

        DocumentReference documentReference = bd.collection("Reacao").document(idReacao);
        documentReference.set(notas).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                inicializarBotoes();
                cabecaDois.setVisibility(View.INVISIBLE);
                cabecaTres.setVisibility(View.INVISIBLE);
                cabecaQuatro.setVisibility(View.INVISIBLE);
                cabecaCinco.setVisibility(View.INVISIBLE);
                cabecaUm.setVisibility(View.INVISIBLE);
                cabecaSeis.setClickable(false);
                Toast.makeText(TelaInicial.this, "Reação diária salva!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.e("bd_error", "ERRO AO SALVAR OS DADOS" + e.toString());

            }
        });
    }
}