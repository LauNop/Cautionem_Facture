package com.example.cautionem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Photo_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
    }

    public void onAlignmentSelected(View view) {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.profil1:
                Toast.makeText(this,"Vous avez choisis la photo 1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.profil2:
                Toast.makeText(this,"Vous avez choisis la photo 2",Toast.LENGTH_SHORT).show();
                break;
            case R.id.profil3:
                Toast.makeText(this,"Vous avez choisis la photo 3",Toast.LENGTH_SHORT).show();
                break;
            case R.id.profil4:
                Toast.makeText(this,"Vous avez choisis la photo 4",Toast.LENGTH_SHORT).show();
                break;
            case R.id.profil5:
                Toast.makeText(this,"Vous avez choisis la photo 5",Toast.LENGTH_SHORT).show();
                break;
            case R.id.profil6:
                Toast.makeText(this,"Vous avez choisis la photo 6",Toast.LENGTH_SHORT).show();
                break;
            case R.id.profil7:
                Toast.makeText(this,"Vous avez choisis la photo 7",Toast.LENGTH_SHORT).show();
                break;
            case R.id.profil8:
                Toast.makeText(this,"Vous avez choisis la photo 8",Toast.LENGTH_SHORT).show();
                break;

        }
    }
}