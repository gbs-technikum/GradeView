package gradieview.sabel.com.gradeview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philipp Schweiger on 21.02.2018.
 */

public class FachDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Noten.db";
    private SQLiteDatabase db;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " +
                    FachContract.FachEntry.TABLE_NAME
                    + " (" +
                    FachContract.FachEntry._ID + " TEXT PRIMARY KEY, " +
                    FachContract.FachEntry.COLUMN_NAME_TITLE_1 + " INTEGER, " +
                    FachContract.FachEntry.COLUMN_NAME_TITLE_2 + " INTEGER, " +
                    FachContract.FachEntry.COLUMN_NAME_TITLE_3 + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FachContract.FachEntry.TABLE_NAME;

    public FachDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void writeDatabase() {
        this.db = super.getWritableDatabase();
    }

    public void readDatabase() {
        this.db = super.getReadableDatabase();
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

    public void createTable(SQLiteDatabase db, String tabellenname) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                tabellenname
                + " (" +
                FachContract.FachEntry._ID + " TEXT PRIMARY KEY, " +
                FachContract.FachEntry.COLUMN_NAME_TITLE_1 + " INTEGER, " +
                FachContract.FachEntry.COLUMN_NAME_TITLE_2 + " INTEGER, " +
                FachContract.FachEntry.COLUMN_NAME_TITLE_3 + " INTEGER)");
    }

    public void insert(String fachname, NotenEntry notenEntry) {
        ContentValues values = new ContentValues();
        values.put(FachContract.FachEntry._ID, notenEntry.getId());
        values.put(FachContract.FachEntry.COLUMN_NAME_TITLE_1, notenEntry.getNote());
        this.db.insert(fachname, null, values);
    }

    public List<NotenEntry> readAll(String fachname) {
        List<NotenEntry> list = null;
        String[] projection = {
                FachContract.FachEntry._ID,
                FachContract.FachEntry.COLUMN_NAME_TITLE_1,
                FachContract.FachEntry.COLUMN_NAME_TITLE_2,
                FachContract.FachEntry.COLUMN_NAME_TITLE_3
        };

        Cursor cursor = db.query(fachname, projection, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            list = new ArrayList<>();
            while (cursor.moveToNext()) {
                int note = cursor.getInt(cursor.getColumnIndex(FachContract.FachEntry.COLUMN_NAME_TITLE_1));
                String id = cursor.getString(cursor.getColumnIndex(FachContract.FachEntry._ID));
                NotenEntry notenEntry = new NotenEntry(note);
                notenEntry.setId(id);
                list.add(notenEntry);
            }
        }
        cursor.close();
        return list;
    }

    public boolean deleteNote(String fachname, NotenEntry notenEntry) {
        if (fachname != null && notenEntry != null) {
            String entryId = notenEntry.getId();
            db.delete(fachname, "_ID" + " = '" + entryId + "' ", null);
            return true;
        }
        return false;
    }

    public boolean deleteFach(String fachname){
        if(fachname!= null){
            db.execSQL("DROP TABLE IF EXISTS " + fachname);
            return true;
        }
        return false;
    }

    public List<String> readFaecher(){
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table';", null);
            List<String> itemIds = new ArrayList<>();

            while (cursor.moveToNext()) {
                String s = cursor.getString(cursor.getColumnIndex("name"));
                itemIds.add(s);
            }
            cursor.close();
            return itemIds;
        }
        return null;
    }
}
