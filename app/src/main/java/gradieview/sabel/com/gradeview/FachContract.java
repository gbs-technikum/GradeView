package gradieview.sabel.com.gradeview;

import android.provider.BaseColumns;

/**
 * Created by Philipp Schweiger on 21.02.2018.
 */

public final class FachContract {

    private FachContract(){

    }

    public static class FachEntry implements BaseColumns{


        //Todo evtl. FachActivity anders holen
        private static FachActivity fachActivity = new FachActivity();

        private static String tabellenName =fachActivity.getFachname();


        public static final String TABLE_NAME = tabellenName;
        public static final String COLUMN_NAME_TITLE_1 = "Schulaufgabe";
        public static final String COLUMN_NAME_TITLE_2 = "Kurzarbeit";
        public static final String COLUMN_NAME_TITLE_3 = "Mündlich";

    }
}
