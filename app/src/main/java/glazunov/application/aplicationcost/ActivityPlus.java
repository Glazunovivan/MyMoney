package glazunov.application.aplicationcost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityPlus extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private  EditText edit_plus, edit_incomes_category;
    private int myBalance = 0, plus = 0;
    private Button bAdd;

    //для работы с BD
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        edit_plus = (EditText)findViewById(R.id.edit_plus);
        edit_incomes_category = (EditText)findViewById(R.id.edit_incomes_category);

        loadData();
    }

    public void loadData(){
        sharedPreferences = getSharedPreferences("MY_BALANCE", MODE_PRIVATE);
        myBalance = sharedPreferences.getInt("MyBalance", 0);
    }

    private  void saveData()
    {
        plus = Integer.parseInt(edit_plus.getText().toString());
        myBalance += plus;
        String category = edit_incomes_category.getText().toString();
        //database
        DBHelper db = new DBHelper(ActivityPlus.this);
        db.addIncome(category, plus);

        //обновляем доход
        sharedPreferences = getSharedPreferences("MY_BALANCE", MODE_PRIVATE);
        plus += sharedPreferences.getInt("Plus", 0);

        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putInt("MyBalance", myBalance).apply();
        ed.putInt("Plus", plus).apply();


    }

    public void click_btnOk(View v){
        saveData();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}