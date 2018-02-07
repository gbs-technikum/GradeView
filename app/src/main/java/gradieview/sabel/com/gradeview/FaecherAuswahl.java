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
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> faecher, ausgewaehlteFaecher, vonMainActivity;

    public FaecherAuswahl() {
        this.faecher = new ArrayList<>();
        this.ausgewaehlteFaecher = new ArrayList<>();
        this.vonMainActivity = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faecher_auswahl);

        listView = findViewById(R.id.lv_faecher);

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
    }

    public void speichern() {
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

    public void faecherVergleich(ArrayList<String> vonMainActivity, ArrayList<String> vonFaecherAuswahl) {
        System.out.println(vonMainActivity);
        if (vonMainActivity != null) {
            for (int i = 0; i < vonMainActivity.size(); i++) {
                for (int j = 0; j < vonFaecherAuswahl.size(); j++) {
                    if (!vonMainActivity.get(i).equals(vonFaecherAuswahl.get(j))) {
                        faecher.add("Mathe");
                    }
                }

            }
        }
        faecher.add("Deutsch");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onActivityResult FaecherAuswahl");
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                vonMainActivity = data.getStringArrayListExtra("ausgewaehlteFaecher");
                System.out.println(vonMainActivity);
                faecherVergleich(vonMainActivity, faecher);

            }
        }
    }

}
