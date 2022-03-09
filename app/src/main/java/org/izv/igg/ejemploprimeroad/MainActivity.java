package org.izv.igg.ejemploprimeroad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    // Campos de la clase
    private EditText etTexto;
    private AutoCompleteTextView actIdioma;
    private Button btHablar;

    private boolean ttsReady = false;
    private TextToSpeech tts;

    /**
     * Método que infla el layout principal
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        initialize();
    }

    /**
     * Método que se llama al finalizar la inicialización del objeto TextToSpeech
     *
     * @param i
     */
    @Override
    public void onInit(int i) {
        ttsReady = true;
    }

    /**
     * Método que inicializa los componentes del layout y el objeto TextToSpeech. Establece también
     * la lista de idiomas pasándo un ArrayAdapter al objeto AutoCompleteTextView
     */
    private void initialize() {

        etTexto = findViewById(R.id.etTexto);
        actIdioma = findViewById(R.id.actIdioma);
        btHablar = findViewById(R.id.btHablar);

        String[] idiomas = {"Español", "Inglés", "Francés", "Italiano", "Japonés", "Alemán", "Chino",
                "Somalí", "Ruso"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.tvList, idiomas);
        actIdioma.setAdapter(adapter);

        tts = new TextToSpeech(this, this);

        defineButtonHablarListener();
    }

    /**
     * Método que define el listener del button btHablar
     */
    private void defineButtonHablarListener() {
        btHablar.setOnClickListener(view -> {
            if (ttsReady && !etTexto.getText().toString().isEmpty() &&
                    !actIdioma.getText().toString().isEmpty() && !actIdioma.getText().equals("Idioma")) {

                String idioma = "", pais = "";

                switch (actIdioma.getText().toString()) {
                    case "Español":
                        idioma = "spa";
                        pais = "ES";
                        break;
                    case "Inglés":
                        idioma = "en";
                        pais = "GB";
                        break;
                    case "Francés":
                        idioma = "fr";
                        pais = "FR";
                        break;
                    case "Italiano":
                        idioma = "it";
                        pais = "IT";
                        break;
                    case "Japonés":
                        idioma = "ja";
                        pais = "JP";
                        break;
                    case "Alemán":
                        idioma = "de";
                        pais = "DE";
                        break;
                    case "Chino":
                        idioma = "zh";
                        pais = "CN";
                        break;
                    case "Somalí":
                        idioma = "so";
                        pais = "SO";
                        break;
                    case "Ruso":
                        idioma = "ru";
                        pais = "RU";
                        break;
                }

                tts.setLanguage(new Locale(idioma, pais));

                tts.speak(etTexto.getText().toString(), TextToSpeech.QUEUE_ADD, null, null);
            } else {
                showToast(R.string.error_toast);
            }
        });
    }

    /**
     * Método que muestra un Toast personalizado
     *
     * @param message Mensaje que queremos que aparezca en el Toast
     */
    private void showToast(int message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        View toastView = toast.getView();
        toastView.getBackground().setColorFilter(getResources().getColor(R.color.primary_color), PorterDuff.Mode.SRC_IN);
        TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
        tv.setTextColor(Color.WHITE);
        toast.show();
    }
}