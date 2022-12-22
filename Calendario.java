package etec.com.br.victor.basetcc;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
public class Calendario extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    TextView datinha;
    FrameLayout dia;
    View um,dois,tres,quatro,cinco,seis;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        getSupportActionBar().hide();
        dia = findViewById(R.id.diaEspecifico);
        datinha = findViewById(R.id.dataatual);
        um = findViewById(R.id.um);
        dois = findViewById(R.id.dois);
        tres = findViewById(R.id.tres);
        quatro = findViewById(R.id.quatro);
        cinco = findViewById(R.id.cinco);
        seis = findViewById(R.id.seis);
        inicializarComponentes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {selectedDate = LocalDate.now();}
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {setMonthView();}
    }

    private void inicializarComponentes() {

        calendarRecyclerView = findViewById(R.id.calendarRecycleView);
        monthYearText = findViewById(R.id.mesAno);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {

        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonth(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonth(LocalDate date) {

        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i<=42; i++){

            if(i <= dayOfWeek || i >= daysInMonth + dayOfWeek){

                daysInMonthArray.add("");

            }
            else{

                daysInMonthArray.add(String.valueOf(i - dayOfWeek));

            }

        }

        return daysInMonthArray;


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void mesAnterior(View view) {

        selectedDate = selectedDate.minusMonths(1);
        setMonthView();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void mesProximo(View view) {

        selectedDate = selectedDate.plusMonths(1);
        setMonthView();

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText) {

        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Date datinhaa = new Date();
        String dataFormatada = formataData.format(datinhaa);

        String msg = "Data selecionada " + dayText + " " + monthYearFromDate(selectedDate);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        dia.setVisibility(View.VISIBLE);
        datinha.setText("Dia selecionado: " + dataFormatada);

        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore bd = FirebaseFirestore.getInstance();
        bd.collection("Reacao")
                .whereEqualTo("dono", usuarioID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String reaction = String.valueOf(document.get("reacao"));

                                switch (reaction) {
                                    case "1":
                                        um.setVisibility(View.VISIBLE);
                                        break;

                                    case "2":
                                        dois.setVisibility(View.VISIBLE);
                                        break;
                                    case "3":
                                        tres.setVisibility(View.VISIBLE);
                                        break;
                                    case "4":
                                        quatro.setVisibility(View.VISIBLE);
                                        break;
                                    case "5":
                                        cinco.setVisibility(View.VISIBLE);
                                        break;
                                    case "6":
                                        seis.setVisibility(View.VISIBLE);
                                        break;
                                }
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
}