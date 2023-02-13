package com.example.waterdrink;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.drinknow.waterdrink.R;
import com.example.waterdrink.Model.Calcular;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText txtPeso;
    private EditText txtIdade;
    private Button btCalc;
    private TextView txtResultado;
    private Button btLembrete;
    private Button btAlarme;
    private TextView txtHora;
    private TextView txtMinutos;
    private ImageView imgRefresh;
    private Calcular calcularDiario;
    private double resultadoML = 0.0;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int horaAtual = 0;
    private int minutos = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        calcularDiario = new Calcular();

        txtPeso = findViewById(R.id.textPeso);
        txtIdade = findViewById(R.id.textIdade);
        btCalc = findViewById(R.id.btCalcular);
        txtResultado = findViewById(R.id.txt_resultado_ml);
        btLembrete = findViewById(R.id.bt_lembrete);
        btAlarme = (Button) findViewById(R.id.bt_alarme);
        txtHora = findViewById(R.id.txt_hora);
        txtMinutos = findViewById(R.id.txt_minutos);
        imgRefresh = findViewById(R.id.imgRefresh);

        btCalc.setOnClickListener(view -> {


            String valorPeso = txtPeso.getText().toString();
            String idadeUser = txtIdade.getText().toString();


            if (valorPeso.isEmpty() && idadeUser.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Preencha os campos Peso e Idade", Toast.LENGTH_LONG)
                        .show();
            } else {

                double peso = Double.parseDouble(valorPeso);
                int idade = new Integer(idadeUser);

                calcularDiario.calcularTotalMl(peso, idade);
                resultadoML = calcularDiario.ResultadoML();
                String res = Double.toString(resultadoML);
                txtResultado.setText(res + "ML");


            }
        });

        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Limpar Dados");
                alert.setMessage("Gostaria de Limpar todos os dados?");
                alert.setCancelable(false);

                alert.setPositiveButton("Sim", (dialog, which) -> limparCampos());

                alert.setNegativeButton("Não", (dialog, which) -> alert.setCancelable(true));

                alert.create();
                alert.show();
            }
        });

        btLembrete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                horaAtual = calendar.get(Calendar.HOUR_OF_DAY);
                minutos = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(MainActivity.this, (view, hourOfDay, minute) -> {

                    txtHora.setText(""+ hourOfDay);
                    txtMinutos.setText(""+minute);
                },
                        horaAtual, minutos, true);
                timePickerDialog.setTitle("Selecione a hora do Lembrete");
                timePickerDialog.show();
            }
        });

        btAlarme.setOnClickListener(v -> {
           int hora = Integer.parseInt(txtHora.getText().toString());
           int min = Integer.parseInt(txtMinutos.getText().toString());


               Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
               intent.putExtra(AlarmClock.EXTRA_HOUR, hora);
               intent.putExtra(AlarmClock.EXTRA_MINUTES, min);
               intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Hora de beber água !");
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


               startActivity(intent);

        });


    }


    private void limparCampos () {
            txtPeso.setText("");
            txtIdade.setText("");
            txtResultado.setText("");
            txtHora.setText("00");
            txtMinutos.setText("00");

        }

}
