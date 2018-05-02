package gradieview.sabel.com.gradeview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Leon Arnold on 25.04.2018.
 */

public class NotenAdapter extends BaseAdapter {

    private Context context;
    private List<NotenEntry> notenEntries;



    public NotenAdapter(Context context, List<NotenEntry> notenEntries) {
        this.context = context;
        this.notenEntries = notenEntries;
    }

    @Override
    public int getCount() {
        if (notenEntries != null && notenEntries.size() > 0) {
            return notenEntries.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return notenEntries.get(i);
    }

    public void removeItem(NotenEntry notenEntry) {
        notenEntries.remove(notenEntry);
    }

    public void addItem(NotenEntry notenEntry) {
        notenEntries.add(notenEntry);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.textview, null);
        TextView textView = v.findViewById(R.id.textView);
        textView.setLayoutParams(new GridView.LayoutParams(150, 150));
        textView.setText(String.valueOf(notenEntries.get(i).getNote()));

        textView.setBackgroundResource(R.layout.gridview_border);

        return textView;
    }
}
