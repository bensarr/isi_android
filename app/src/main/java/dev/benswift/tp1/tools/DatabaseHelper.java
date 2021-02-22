package dev.benswift.tp1.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import dev.benswift.tp1.model.Utilisateur;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Mes_Utilisateurs";
    // Table name: Technicien.
    private static final String TABLE_USER = "USER";
    // Columns for Table Technicien.
    private static final String COLUMN_USER_ID ="User_Id";
    private static final String COLUMN_USER_LOGIN ="User_Login";
    private static final String COLUMN_USER_PASSWORD ="User_Password";

    // Script CREATE TABLE User
    String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_USER_LOGIN+ " TEXT,"+ COLUMN_USER_PASSWORD + " TEXT" + ")";

    /**
     * Constructeur
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    // creating required tables
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
    }

    /**
     * Close Database
     */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
    /**
     * Creating a utilisateur
     * @param utilisateur
     * @return
     */
    public int userCreate(Utilisateur utilisateur) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_LOGIN, utilisateur.getLogin());
        values.put(COLUMN_USER_PASSWORD, utilisateur.getPassword());

        // insert row
        int utilisateur_id = (int) db.insert(TABLE_USER, null, values);

        return utilisateur_id;
    }

    /**
     * Connexion
     * @param user
     * @return
     */
    public Utilisateur userGetOne(Utilisateur user) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USER+ " WHERE "
                + COLUMN_USER_LOGIN + " = '" + user.getLogin()+"'"
                + " AND " + COLUMN_USER_PASSWORD + " = '" + user.getPassword()+"'";

        Log.e(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Utilisateur u = new Utilisateur();
        u.setId(c.getInt(c.getColumnIndex(COLUMN_USER_ID)));
        u.setLogin((c.getString(c.getColumnIndex(COLUMN_USER_LOGIN))));
        u.setPassword(c.getString(c.getColumnIndex(COLUMN_USER_PASSWORD)));

        return u;
    }

    /**
     * getting all utilisateurs
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Utilisateur> userGetAll() {
        List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Utilisateur u = new Utilisateur();
                u.setId(c.getInt(c.getColumnIndex(COLUMN_USER_ID)));
                u.setLogin((c.getString(c.getColumnIndex(COLUMN_USER_LOGIN))));
                u.setPassword((c.getString(c.getColumnIndex(COLUMN_USER_PASSWORD))));

                // adding to todo list
                utilisateurs.add(u);
            } while (c.moveToNext());
        }

        return utilisateurs;
    }

    /**
     * Updating a utilisateur
     * @param utilisateur
     * @return
     */
    public int userUpdate(Utilisateur utilisateur) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_LOGIN, utilisateur.getLogin());
        values.put(COLUMN_USER_PASSWORD, utilisateur.getPassword());

        // updating row
        return db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[] { String.valueOf(utilisateur.getId()) });
    }

    /**
     * deleting user
     * @param utilisateur
     * @return
     */
    public int userDelete(Utilisateur utilisateur) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + utilisateur.getLogin() );

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[] { String.valueOf(utilisateur.getId()) });
    }
}
