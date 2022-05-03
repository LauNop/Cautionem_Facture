package com.example.cautionem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Photo_Activity extends AppCompatActivity {
    private Button valider;
    private int page;
    private int idNumPhoto;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        this.valider = (Button) findViewById(R.id.valid_buton);

        bundle = getIntent().getExtras();
        page = bundle.getInt("key1");

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page==1) {
                    Intent intent = new Intent(getApplicationContext(), Inscription2Activity.class);
                    bundle = new Bundle();
                    bundle.putInt("key1", idNumPhoto);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
                else if(page==2){
                    Intent intent = new Intent(getApplicationContext(), Modif_Compte_Activity.class);
                    bundle = new Bundle();
                    bundle.putInt("key1", idNumPhoto);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void onAlignmentSelected(View view) {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.profil1:
                Toast.makeText(this,"Vous avez choisis la photo 1",Toast.LENGTH_SHORT).show();
                idNumPhoto=1;
                break;
            case R.id.profil2:
                Toast.makeText(this,"Vous avez choisis la photo 2",Toast.LENGTH_SHORT).show();
                idNumPhoto=2;
                break;
            case R.id.profil3:
                Toast.makeText(this,"Vous avez choisis la photo 3",Toast.LENGTH_SHORT).show();
                idNumPhoto=3;
                break;
            case R.id.profil4:
                Toast.makeText(this,"Vous avez choisis la photo 4",Toast.LENGTH_SHORT).show();
                idNumPhoto=4;
                break;
            case R.id.profil5:
                Toast.makeText(this,"Vous avez choisis la photo 5",Toast.LENGTH_SHORT).show();
                idNumPhoto=5;
                break;
            case R.id.profil6:
                Toast.makeText(this,"Vous avez choisis la photo 6",Toast.LENGTH_SHORT).show();
                idNumPhoto=6;
                break;
            case R.id.profil7:
                Toast.makeText(this,"Vous avez choisis la photo 7",Toast.LENGTH_SHORT).show();
                idNumPhoto=7;
                break;
            case R.id.profil8:
                Toast.makeText(this,"Vous avez choisis la photo 8",Toast.LENGTH_SHORT).show();
                idNumPhoto=8;
                break;

        }
    }


}