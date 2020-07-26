package glazunov.application.aplicationcost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SPENDING";
    private static final String TABLE_EXPENSE = "SPENDING";
    private static final String TABLE_INCOME = "INCOME";

    private static final String INCOME = "income";
    private static final String INCOME_COUNT = "income_count";
    private static final String EXPENSE_TYPE = "expense_type";
    private static final String EXPENSE_COUNT = "expense_count";
    private static final String COLUMN_ID = "ID";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //таблица дохода
        db.execSQL("create table " + TABLE_EXPENSE +
                "(" + COLUMN_ID + " integer primary key autoincrement, " +
                EXPENSE_TYPE + " text, " +
                EXPENSE_COUNT + " integer not null" + ");");
        //таблица дохода
        db.execSQL("create table " + TABLE_INCOME +
                "(" + COLUMN_ID + " integer primary key autoincrement, " +
                INCOME + " text, " +
                INCOME_COUNT + " integer not null" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_EXPENSE);
        db.execSQL("drop table if exists " + TABLE_INCOME);

        onCreate(db);
    }

    //доходы
    void addIncome(int count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(INCOME, "Зарплата");
        cv.put(INCOME_COUNT, count);
        //добавить дату

        db.insert(TABLE_INCOME, null, cv);
    }

    //расходы
     void addExpense(String category, int count){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv = new ContentValues();

         cv.put(EXPENSE_TYPE, category);
         cv.put(EXPENSE_COUNT, count);
         //добавить дату

         db.insert(TABLE_EXPENSE, null, cv);
    }

    Cursor readIncomeData(){
        String query = "SELECT " + INCOME_COUNT + ","+ INCOME + " FROM " + TABLE_INCOME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor  = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readExpenseData(){
        String query = "SELECT " + EXPENSE_TYPE + "," + EXPENSE_COUNT + " FROM " + TABLE_EXPENSE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor  = null;
        if(db != null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


}
