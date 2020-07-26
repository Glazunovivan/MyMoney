package glazunov.application.aplicationcost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardCar,cardHome,cardBasket,cardPlus, cardMinus;
    private TextView balance,plus,minus;
    private int i_balance, i_plus, i_minus;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor ed;

    RecyclerView recyclerView;
    DBHelper dbHelper;
    ArrayList<String> dbIncome, dbIncomeType, dbExpense;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balance = (TextView)findViewById(R.id.balance);
        plus = (TextView)findViewById(R.id.plus);
        minus = (TextView)findViewById(R.id.minus);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_incomes);

        cardPlus = (CardView)findViewById(R.id.cardPlus);
        cardMinus = (CardView)findViewById(R.id.cardMinus);
        cardCar = (CardView)findViewById(R.id.cardCar);
        cardBasket = (CardView)findViewById(R.id.cardBasket);
        cardHome = (CardView)findViewById(R.id.cardHome);

        cardPlus.setOnClickListener(this);
        cardMinus.setOnClickListener(this);
        cardHome.setOnClickListener(this);
        cardBasket.setOnClickListener(this);
        cardCar.setOnClickListener(this);

        //loadData
        LoadData();

        dbHelper = new DBHelper(MainActivity.this);
        dbIncome = new ArrayList<>();
        dbIncomeType = new ArrayList<>();
        dbExpense = new ArrayList<>();

        displayDataFromDB();

        customAdapter = new CustomAdapter(MainActivity.this, dbIncome, dbIncomeType);
        recyclerView.setAdapter(customAdapter);

        LinearLayoutManager lManager = new LinearLayoutManager(this);
        lManager.setReverseLayout(true);
        lManager.setStackFromEnd(true);

        //recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setLayoutManager(lManager);

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        saveData();
    }

    //загрузка данных
    private void LoadData()
    {
        sharedPreferences = getSharedPreferences("MY_BALANCE", MODE_PRIVATE);

        i_balance = sharedPreferences.getInt("MyBalance", 0);
        i_plus = sharedPreferences.getInt("Plus", 0);
        i_minus = sharedPreferences.getInt("Minus", 0);

        balance.setText(Integer.toString(i_balance));
        plus.setText(Integer.toString(i_plus));
        minus.setText(Integer.toString(i_minus));
    }

    private  void saveData()
    {
        sharedPreferences = getSharedPreferences("MY_BALANCE", MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();

        ed.putInt("MyBalance", i_balance).apply();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        saveData();
        switch (v.getId()){
            case R.id.cardPlus:
                intent = new Intent(MainActivity.this, ActivityPlus.class);
                startActivity(intent);
                break;
            case R.id.cardMinus:
            case R.id.cardCar:
            case R.id.cardBasket:
            case R.id.cardHome:
                intent = new Intent(MainActivity.this, ActivityMinus.class);
                //передать имя карточки, чтобы определить категорию
                if(v.getId() == R.id.cardCar){
                    intent.putExtra("CategoryExpense", "Car");
                }
                if(v.getId() == R.id.cardBasket){
                    intent.putExtra("CategoryExpense", "Basket");
                }
                if(v.getId() == R.id.cardHome){
                    intent.putExtra("CategoryExpense", "Home");
                }
                else{
                    intent.putExtra("CategoryExpense", "NONE");
                }
                startActivity(intent);
                break;
            default: break;
        }
    }

    void displayDataFromDB(){
        Cursor cursor = dbHelper.readIncomeData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                dbIncome.add(cursor.getString(0));
                dbIncomeType.add(cursor.getString(1));
            }
        }
        /*Cursor cursor1 = dbHelper.readExpenseData();
        if(cursor1.getCount() != 0){
            while(cursor1.moveToNext()) dbExpense.add(cursor1.getString(0));
        }*/
    }
}