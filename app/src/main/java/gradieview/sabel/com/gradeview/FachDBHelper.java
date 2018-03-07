package gradieview.sabel.com.gradeview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Philipp Schweiger on 21.02.2018.
 */

public class FachDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Noten.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " +
                    FachContract.FachEntry.TABLE_NAME
                    + " (" +
                    FachContract.FachEntry._ID + " INTEGER PRIMARY KEY, " +
                    FachContract.FachEntry.COLUMN_NAME_TITLE_1 + " INTEGER, " +
                    FachContract.FachEntry.COLUMN_NAME_TITLE_2 + " INTEGER, "+
                    FachContract.FachEntry.COLUMN_NAME_TITLE_3 + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FachContract.FachEntry.TABLE_NAME;

    public FachDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void createTable(SQLiteDatabase db, String tabellenname){
        db.execSQL( "CREATE TABLE IF NOT EXISTS " +
               tabellenname
                + " (" +
                FachContract.FachEntry._ID + " INTEGER PRIMARY KEY, " +
                FachContract.FachEntry.COLUMN_NAME_TITLE_1 + " INTEGER, " +
                FachContract.FachEntry.COLUMN_NAME_TITLE_2 + " INTEGER, "+
                FachContract.FachEntry.COLUMN_NAME_TITLE_3 + " INTEGER)");

    }
}
