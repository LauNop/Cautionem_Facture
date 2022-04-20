package com.example.cautionem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.io.FileOutputStream;


public class PDF_Activity extends AppCompatActivity {

    private EditText editText;
    private TextView textView7;

    private String stringFilePath = Environment.getExternalStorageDirectory().getPath()+"Download/TD.pdf";
    private File file = new File(stringFilePath);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        System.out.println(stringFilePath);
        editText = findViewById(R.id.editText);
        textView7 = findViewById(R.id.textView7);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
    }


    public void buttonReadPDF(View view){
        try {
            PdfReader pdfReader = new PdfReader(file.getPath());
            String stringParse = PdfTextExtractor.getTextFromPage(pdfReader,1).trim();
            pdfReader.close();
            textView7.setText(stringParse);
        }
        catch (Exception e){
            e.printStackTrace();
            textView7.setText("Error in Reading");
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

}