package gradieview.sabel.com.gradeview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon Arnold on 11.04.2018.
 */

public class FachActivityAdapter extends BaseAdapter {

    private Context context;
    //private String fachname;
//    private List<NotenEntry> notenEntries;
    ArrayAdapter<NotenEntry> arrayAdapter;
    String notenart;


    public FachActivityAdapter(Context context, String notenart) {
        this.context = context;
        this.notenart = notenart;
//        this.notenEntries = notenEntries;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return notenart;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.noteneintrag_adapter, null);
        TextView tv_notenart = v.findViewById(R.id.tv_notenart);
        Button btn_plus = v.findViewById(R.id.btn_plus);
//        final EditText editText = v.findViewById(R.id.editText);
//        ListView lv_noten = v.findViewById(R.id.lv_Noten);
//        FachDBHelper fachDBHelper = new FachDBHelper(context);
//        fachDBHelper.leseRechtDatenbank();
//
//        //ArrayList<NotenEntry> notenEntries = (ArrayList) fachDBHelper.readAllFromFachSA(fachname);
        tv_notenart.setText(notenart);

//        btn_plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editText.setVisibility(View.VISIBLE);
//                //eigabefeld öffnen
//                editText.requestFocus();
//                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.showSoftInput(editText, 0);
//            }
//        });

//        arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, notenEntries);
//        lv_noten.setAdapter(arrayAdapter);
        return v;
    }


}
