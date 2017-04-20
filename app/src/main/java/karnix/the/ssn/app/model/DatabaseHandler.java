package karnix.the.ssn.app.model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static String DB_PATH;
    public final Context context;
    public SQLiteDatabase sqLiteDatabase;
    ArrayList<HashMap<String, String>> mylistData1 = new ArrayList();

    public DatabaseHandler(Context context) {
        super(context, "database", null, 1);
        this.context = context;
        DB_PATH = context.getDatabasePath("database").toString();
        openDataBase();
    }

    private boolean checkDataBase() {
        try {
            sqLiteDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, 1);
        } catch (SQLException localSQLException) {
            Log.e(getClass().toString(), "Error while checking db");
        }
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        return sqLiteDatabase != null;
    }

    private void copyDataBase() throws IOException {
        InputStream localInputStream = this.context.getAssets().open("database");
        FileOutputStream localFileOutputStream = new FileOutputStream(DB_PATH);
        byte[] arrayOfByte = new byte[1024];
        for (; ; ) {
            int i = localInputStream.read(arrayOfByte);
            if (i <= 0) {
                localFileOutputStream.close();
                localInputStream.close();
                return;
            }
            localFileOutputStream.write(arrayOfByte, 0, i);
        }
    }

    public void close() {
        try {
            if (this.sqLiteDatabase != null) this.sqLiteDatabase.close();
            super.close();
        } finally {
        }
    }

    public void createDataBase() {
        if (!checkDataBase()) {
            getReadableDatabase();
            try {
                copyDataBase();
                return;
            } catch (IOException localIOException) {
                Log.e(getClass().toString(), "Copying error");
                throw new Error("Error copying sqLiteDatabase!");
            }
        }
        Log.i(getClass().toString(), "Database already exists");
    }

    public String[] getAllLabels() {
        Cursor localCursor = getReadableDatabase().rawQuery("SELECT  * FROM Bus", null);
        if (localCursor.getCount() > 0) {
            String[] arrayOfString = new String[localCursor.getCount()];
            for (int i = 0; ; i++) {
                if (!localCursor.moveToNext()) {
                    return arrayOfString;
                }
                arrayOfString[i] = localCursor.getString(1);
            }
        }
        return new String[0];
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
    }

    public void openDataBase() throws SQLException {
        if (this.sqLiteDatabase == null) {
            createDataBase();
            this.sqLiteDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, 0);
        }
    }

    public ArrayList<HashMap<String, String>> searResu(int paramInt) {
        String str = "SELECT  *from Bus where No=" + paramInt;
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery(str, null);
        localCursor.moveToFirst();
        HashMap localHashMap2;
        while (!localCursor.isAfterLast()) {
            localHashMap2 = new HashMap();
            localHashMap2.put("stop", localCursor.getString(1));
            localHashMap2.put("busno", localCursor.getString(2));
            if (localCursor.getString(3).length() != 3) {
                localHashMap2.put("time", localCursor.getString(3) + "am");
            } else {
                localHashMap2.put("time", localCursor.getString(3) + "0am");
            }
            this.mylistData1.add(localHashMap2);
            localCursor.moveToNext();
        }
        localCursor.close();
        localSQLiteDatabase.close();
        return this.mylistData1;
    }


    public ArrayList<HashMap<String, String>> searareaResu(String paramString) {
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        String str = "'%" + paramString + "%'";
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * from Bus where Stop like" + str, null);
        localCursor.moveToFirst();
        HashMap localHashMap2;
        while (!localCursor.isAfterLast()) {
            localHashMap2 = new HashMap();
            localHashMap2.put("stop", localCursor.getString(1));
            localHashMap2.put("busno", localCursor.getString(2));
            if (localCursor.getString(3).length() != 3) {
                localHashMap2.put("time", localCursor.getString(3) + "am");
            } else {
                localHashMap2.put("time", localCursor.getString(3) + "0am");
            }
            mylistData1.add(localHashMap2);
            localCursor.moveToNext();
        }

        localCursor.close();
        localSQLiteDatabase.close();
        return this.mylistData1;
    }
}
