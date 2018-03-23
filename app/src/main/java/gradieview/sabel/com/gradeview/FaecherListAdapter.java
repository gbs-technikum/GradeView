package gradieview.sabel.com.gradeview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon Arnold on 23.03.2018.
 */

public class FaecherListAdapter extends BaseAdapter {

    private Context context;
    private List<String> faecher;
    private FachPlusNote fachPlusNote;




    public FaecherListAdapter(Context context, List<String> faecher) {
        this.context = context;
        this.faecher= faecher;

    }


    public void deletItem(String i){
        faecher.remove(i);
    }

    @Override
    public int getCount() {
        return faecher.size();
    }

    @Override
    public Object getItem(int i) {
        return faecher.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.textview_noten, null);
        TextView tvFach = v.findViewById(R.id.tv_fach);
//        TextView tvNote = v.findViewById(R.id.tv_gesamtnote);



        tvFach.setText(faecher.get(i));
//        tvNote.setText();
        return v;
    }
}
