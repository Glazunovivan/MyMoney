package glazunov.application.aplicationcost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SPENDING";
    private static final String TABLE_EXPENSE = "EXPENSE";
    private static final String TABLE_INCOME = "INCOME";

    private static final String INCOME_TYPE = "income_type";
    private static final String INCOME_COUNT = "income_count";
    private static final String INCOME_DATE = "income_date";

    private static final String EXPENSE_TYPE = "expense_type";
    private static final String EXPENSE_COUNT = "expense_count";
    private static final String EXPENSE_DATE = "expense_date";

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
                EXPENSE_COUNT + " integer not null, " +
                EXPENSE_DATE + " text " + ");");
        //таблица дохода
        db.execSQL("create table " + TABLE_INCOME +
                "(" + COLUMN_ID + " integer primary key autoincrement, " +
                INCOME_TYPE + " text, " +
                INCOME_COUNT + " integer not null, " +
                INCOME_DATE + " text " + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_EXPENSE);
        db.execSQL("drop table if exists " + TABLE_INCOME);

        onCreate(db);
    }

    //доходы
    void addIncome(String category, int count, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(INCOME_TYPE, category);
        cv.put(INCOME_COUNT, count);
        cv.put(INCOME_DATE, date);

        db.insert(TABLE_INCOME, null, cv);
    }

    //расходы
     void addExpense(String category, int count, String date){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv = new ContentValues();

         cv.put(EXPENSE_TYPE, category);
         cv.put(EXPENSE_COUNT, count);
         cv.put(EXPENSE_DATE, date);

         db.insert(TABLE_EXPENSE, null, cv);
    }

    Cursor readIncomeData(){
        String query = "SELECT " + INCOME_COUNT + ","+ INCOME_TYPE + "," + COLUMN_ID + /*", "+ EXPENSE_DATE + */" FROM " + TABLE_INCOME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor  = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readExpenseData(){
        String query = "SELECT " + EXPENSE_COUNT + "," + EXPENSE_TYPE + "," + COLUMN_ID +  ","+ EXPENSE_DATE +" FROM " + TABLE_EXPENSE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor  = null;
        if(db != null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //получает баланс
    public int GetMyBalance(){
        SQLiteDatabase db = this.getReadableDatabase();

        int sumExpense = 0, sumIncome = 0;
        Cursor cursorInc = db.rawQuery("SELECT SUM(" + INCOME_COUNT + ") FROM " + TABLE_INCOME, null);
        if(cursorInc.moveToFirst()){
            sumIncome = cursorInc.getInt(0);
        }
        Cursor cursorExp = db.rawQuery("SELECT SUM (" + EXPENSE_COUNT + ") FROM " + TABLE_EXPENSE, null);
        if(cursorExp.moveToFirst()){
            sumExpense = cursorExp.getInt(0);
        }
        int cost = sumIncome - sumExpense;

        return cost;
    }

    //получить сумму доходов
    public int GetMyIncome(){
        SQLiteDatabase db = this.getReadableDatabase();
        int myIncome = 0;
        Cursor cursorInc = db.rawQuery("SELECT SUM(" + INCOME_COUNT + ") FROM " + TABLE_INCOME, null);
        if(cursorInc.moveToFirst()){
            myIncome = cursorInc.getInt(0);
        }
        db.close();
        return  myIncome;
    }

    //получить сумму расходов
    public int GetMyExpense(){
        SQLiteDatabase db = this.getReadableDatabase();
        int myExpense = 0;
        Cursor cursorExp = db.rawQuery("SELECT SUM (" + EXPENSE_COUNT + ") FROM " + TABLE_EXPENSE, null);
        if(cursorExp.moveToFirst()){
            myExpense = cursorExp.getInt(0);
        }
        db.close();
        return myExpense;
    }

    //delete table Expense
    public void DeleteTableExpense(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSE, "ID=" + id, null);
        db.close();
    }

    public void DeleteAllDate(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EXPENSE);
        db.execSQL("DELETE FROM " + TABLE_INCOME);
        db.close();
    }
}
