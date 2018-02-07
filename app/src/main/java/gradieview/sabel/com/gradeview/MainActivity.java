package gradieview.sabel.com.gradeview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    private Button btn_mathe;
    private ArrayList<String> ausgewaehlteFaecher;
    private String fach;
    private Intent intent;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    public MainActivity() {
        this.ausgewaehlteFaecher = new ArrayList<>();
        fach = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lv_faecher);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ausgewaehlteFaecher);
        listView.setAdapter(arrayAdapter);
    }

//    private String getFach() {
//        System.out.println("getFach");
//        for (String fach : ausgewaehlteFaecher) {
//            this.fach = fach;
//        }
//        System.out.println(fach);
//        return this.fach;
//    }

    private void faecherHinzufuegen() {

        for (String fach:ausgewaehlteFaecher
             ) {

            arrayAdapter.add(fach);
        }


//        System.out.println("ButtonsHinzufuegen");
//        switch (getFach()) {
//            case "Mathe": {
//                btn_mathe = findViewById(R.id.btn_mathe);
//                btn_mathe.setVisibility(View.VISIBLE);
//            }
//        }
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
                intent = new Intent(this, FaecherAuswahl.class);
                this.startActivityForResult(intent, 1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                ausgewaehlteFaecher = data.getStringArrayListExtra("listeFaecher");
                System.out.println(ausgewaehlteFaecher);
                System.out.println("onActivityResult");
                faecherHinzufuegen();
            }
        }
    }


}


