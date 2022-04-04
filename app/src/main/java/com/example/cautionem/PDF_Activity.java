package com.example.cautionem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class PDF_Activity extends AppCompatActivity {

    private EditText editText;
    private TextView textView7;

    private String stringFilePath = Environment.getExternalStorageDirectory().getPath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        System.out.println(stringFilePath);
        editText = findViewById(R.id.editText);
        textView7 = findViewById(R.id.textView7);
    }

    public void buttonCreatePDF(View view){

    }

    public void buttonReadPDF(View view){

    }
}