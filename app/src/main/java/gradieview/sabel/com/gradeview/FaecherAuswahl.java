package gradieview.sabel.com.gradeview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class FaecherAuswahl extends AppCompatActivity {

    private ListView listView;

//    private Button btn_mathe;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> faecher, ausgewaehlteFaecher;

    public FaecherAuswahl() {
        this.faecher = new ArrayList<>();
        this.ausgewaehlteFaecher = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faecher_auswahl);

        listView = findViewById(R.id.lv_faecher);
        faecher.add("Mathe");
        faecher.add("Deutsch");
        faecher.add("Englisch");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, faecher);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fach = faecher.get(position);
                arrayAdapter.remove(fach);
                ausgewaehlteFaecher.add(fach);
            }
        });
//        btn_mathe = findViewById(R.id.btn_mathe);
//        btn_mathe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                faecher.add(btn_mathe.getText().toString());
//                btn_mathe.setVisibility(View.GONE);
//                System.out.println(faecher);
//            }
//        });
    }

    public void speichern(){
        Intent intent = new Intent();
        intent.putStringArrayListExtra("listeFaecher", ausgewaehlteFaecher);
        setResult(RESULT_OK, intent);


        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menufaecher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fertig:
                speichern();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }






}
