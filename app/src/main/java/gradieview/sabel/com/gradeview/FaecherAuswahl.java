package gradieview.sabel.com.gradeview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class FaecherAuswahl extends AppCompatActivity {

    private Button btn_mathe;
    private ArrayList<String> faecher;

    public FaecherAuswahl() {
        this.faecher = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faecher_auswahl);

        btn_mathe = findViewById(R.id.btn_mathe);
        btn_mathe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faecher.add(btn_mathe.getText().toString());
                btn_mathe.setVisibility(View.GONE);
                System.out.println(faecher);
            }
        });

        Intent intent = getIntent();
    }

    public void speichern(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putStringArrayListExtra("listeFaecher", faecher);

    }






}
