package gradieview.sabel.com.gradeview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btn_mathe;
    private ArrayList<String> faecher;
    private String fach;

    public MainActivity() {
        this.faecher = new ArrayList<>();
        fach = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        faecher = intent.getStringArrayListExtra("listeFaecher");

        buttonsHinzufuegen();
    }

    private void buttonsHinzufuegen() {
        for (String fach : faecher) {
            this.fach = fach;
        }
        switch (fach) {
            case "Mathe": {
                btn_mathe = findViewById(R.id.btn_mathe);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fachHinzufuegen:
                Intent intent = new Intent(this, FaecherAuswahl.class);
                this.startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
