package etec.com.br.victor.basetcc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;

public class PrimeiroPasso extends AppCompatActivity {

    TextView dataUm, dataDois, horaUm, horaDois, rearranjo, rearranjoDois;
    Button fraco, moderado, forte, extremo;
    int op;
    String intensidade = "nulo";
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeiro_passo);

        getSupportActionBar().hide();
        iniciarComponentes();

        fraco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intensidade = "fraca";
            }
        });
        moderado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intensidade = "moderado";
            }
        });
        forte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intensidade = "forte";
            }
        });
        extremo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intensidade = "extremo";
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dataUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PrimeiroPasso.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                rearranjo.setVisibility(View.INVISIBLE);
                rearranjoDois.setVisibility(View.INVISIBLE);
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;
                String date = dayOfMonth+"/"+month+"/"+year;
                dataUm.setText(date);

            }
        };

        dataDois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(PrimeiroPasso.this,new
                        DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        dataDois.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        rearranjo.setVisibility(View.INVISIBLE);
                        rearranjoDois.setVisibility(View.INVISIBLE);

                    }
                }, year, month,day);
                datePickerDialog.show();
            }
        });

        horaUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                final int minutes = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(PrimeiroPasso.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                horaUm.setText(hourOfDay + ":" + minute);

                            }
                        },hour,minutes,false);
                timePickerDialog.show();

            }
        });

        horaDois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                final int minutes = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(PrimeiroPasso.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                horaDois.setText(hourOfDay + ":" + minute);

                            }
                        },hour,minutes,false);
                timePickerDialog.show();

            }
        });

    }

    public void iniciarComponentes(){

        horaUm = findViewById(R.id.hora_comeco);
        horaDois = findViewById(R.id.hora_termino);
        dataUm = findViewById(R.id.data_comeco);
        dataDois = findViewById(R.id.data_termino);
        fraco = findViewById(R.id.intensidade_fraca);
        moderado = findViewById(R.id.intensidade_moderada);
        forte = findViewById(R.id.intensidade_forte);
        extremo = findViewById(R.id.intensidade_extrema);
        rearranjo = findViewById(R.id.rearranjo);
        rearranjoDois = findViewById(R.id.rearranjoDois);

    }

    public void btnVoltar(View view) {

        finish();

    }

    public void btnProximo(View view) {

        if(horaUm.getText().toString().equals("") || dataUm.getText().toString().equals("") ||
                horaDois.getText().toString().equals("") || dataDois.getText().toString().equals("") ||
                intensidade.equals("nulo")){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
        else {
            finish();
            Intent segundoPasso = new Intent(PrimeiroPasso.this, SegundoPasso.class);

            String horaTermino, dataTermino;
            horaTermino = horaDois.getText().toString();
            dataTermino = dataDois.getText().toString();

            //USAR edit horaComeco = dataUm.getText().toString();
            String horaComeco = horaUm.getText().toString();
            String dataComeco = dataUm.getText().toString();

            segundoPasso.putExtra("horaComeco", horaComeco);
            Log.e("horaComeco", horaComeco);
            segundoPasso.putExtra("dataComeco", dataComeco);
            Log.e("dataComeco", dataComeco);
            segundoPasso.putExtra("horaTermino", horaTermino);
            Log.e("horaTermino", horaTermino);
            segundoPasso.putExtra("dataTermino", dataTermino);
            Log.e("dataTermino", dataTermino);
            segundoPasso.putExtra("intensidade", intensidade);
            Log.e("intensidade", intensidade);
            startActivity(segundoPasso);

        }
    }
}