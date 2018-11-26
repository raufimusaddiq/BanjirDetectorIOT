package pre.com.loraiot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FloodMonitor.db";
    private static final String TABLE_NAME= "FloodData";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "timestamp";
    private static final String KEY_DATA = "distance";
    private SQLiteDatabase sqLiteDatabase;

    public List<DataSensor> getAllData() {
        List<DataSensor> data = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " +
                KEY_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataSensor dataSensor = new DataSensor();
                dataSensor.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                dataSensor.setTimeStamp(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                dataSensor.setDistance(cursor.getInt(cursor.getColumnIndex(KEY_DATA)));

                data.add(dataSensor);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        return data;
    }

    public DataSensor getData(){
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " +
                KEY_ID + " DESC LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        DataSensor data = new DataSensor(
                cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                cursor.getInt(cursor.getColumnIndex(KEY_DATA))
        );
        return data;
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME+"("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DATE + " DATETIME,"
                + KEY_DATA + " INTEGER)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String timestamp, int distance) {
        boolean createSuccessful = false;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_DATE, timestamp);
        values.put(KEY_DATA, String.valueOf(distance));

        createSuccessful = db.insert(TABLE_NAME, null, values) > 0;
        db.close();

        return createSuccessful;
    }
}
