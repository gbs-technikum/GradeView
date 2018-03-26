package gradieview.sabel.com.gradeview;

import android.provider.BaseColumns;

/**
 * Created by Philipp Schweiger on 21.02.2018.
 */

public final class FachContract {

    private FachContract() {

    }

    public static class FachEntry implements BaseColumns {




        //Todo evtl. FachActivity anders holen


        private static String faecherliste = "Faecherliste";


        public static String TABLE_FAECHERLISTE = faecherliste;
        public static final String SCHULAUFGABE = "Schulaufgabe";
        public static final String KURZARBEIT = "Kurzarbeit";
        public static final String MÜNDLICH = "Mündlich";
        public static final String FACH = "Fach";
        public static final String AUSGEWAEHLT = "Ausgewaehlt";


    }
}
