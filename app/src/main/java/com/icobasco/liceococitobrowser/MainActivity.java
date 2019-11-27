package com.icobasco.liceococitobrowser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

//librerie per scanning qr code
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btOpenBrowser = null;
    private Button btScan = null;
    public static final String EXTRA_MESSAGE_OPEN_BROWSER = "OPEN_BROWSER";
    public static final String URL_WIKIPEDIA = "https://www.wikipedia.com";
    public static final String URL_HOME = "https://icobasco.github.io/cocito_android/fakesite/";

    private String url = "";


    private IntentIntegrator mioIntent; //dichiarazione di oggetto di tipo IntentIntegrator

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mioIntent = new IntentIntegrator(this); //creazione dell'oggetto IntentIntegrator

        btScan = findViewById(R.id.btScan);
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mioIntent.initiateScan();
            }
        });

        btOpenBrowser = findViewById(R.id.btOpenBrowser);
        btOpenBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (url == "") {
                    url = URL_WIKIPEDIA;
                }
                startBrowser();
            }
        });
    }

    private void startBrowser() {
        Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
        intent.putExtra(EXTRA_MESSAGE_OPEN_BROWSER, url);
        String outputMex = "[" + EXTRA_MESSAGE_OPEN_BROWSER + "][" + url + "]";
        Log.d(TAG, outputMex);
        startActivity(intent);
    }


    //generate methods -> onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //creo un oggetto di tipo IntentResult
        IntentResult mioResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (mioResult != null) {
            if (mioResult.getContents() == null) { //se è nullo visualizzo un toast
                Log.d(TAG, "QRCODE senza contenuto");
                Toast.makeText(this, "Non riesco a risolvere questo QR!", Toast.LENGTH_LONG).show();
            }
            else { //se il qr code ha dei dati
                String contenuto = mioResult.getContents();
                if (contenuto.startsWith(URL_HOME)) {
                    url = contenuto;
                    Log.i(TAG, "QRCODE (url): " + url);
                    startBrowser();
                }
                else {
                    Log.i(TAG, "QRCODE: " + contenuto);
                    Log.e(TAG, "Contenuto non gestito da questa app");
                }
            }
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}
