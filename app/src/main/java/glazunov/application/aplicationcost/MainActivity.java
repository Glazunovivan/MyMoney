package glazunov.application.aplicationcost;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardCar,cardHome,cardBasket,cardPlus, cardMinus, cardBalance;
    private TextView balance,plus,minus;
    private int i_balance, i_plus, i_minus;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    //SharedPreferences sharedPreferences;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balance = (TextView)findViewById(R.id.balance);
        plus = (TextView)findViewById(R.id.plus);
        minus = (TextView)findViewById(R.id.minus);

        cardBalance = (CardView)findViewById(R.id.cardBalance);
        cardPlus = (CardView)findViewById(R.id.cardPlus);
        cardMinus = (CardView)findViewById(R.id.cardMinus);
        cardCar = (CardView)findViewById(R.id.cardCar);
        cardBasket = (CardView)findViewById(R.id.cardBasket);
        cardHome = (CardView)findViewById(R.id.cardHome);

        cardPlus.setOnClickListener(this);
        cardMinus.setOnClickListener(this);

        cardBalance.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ConfirmDialogDeleteAll();
            }
        });

        //удаляем таблицу с расходами
        cardMinus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //dbHelper.
                ConfirmDialog();
                return true;
            }
        });
        cardPlus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Удалить все данные о доходах?");
                builder.setMessage("Вы удалите все данные о доходах в Вашем приложении без возможности восстановления. Вы действительно хотите это сделать?");
                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.execSQL("DELETE FROM INCOME");
                        //перезапуск активити
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                builder.create().show();
                return true;
            }
        });

        cardHome.setOnClickListener(this);
        cardBasket.setOnClickListener(this);
        cardCar.setOnClickListener(this);

        //loadData
        dbHelper = new DBHelper(MainActivity.this);
        LoadData();

        LinearLayoutManager lManager = new LinearLayoutManager(this);
        lManager.setReverseLayout(true);
        lManager.setStackFromEnd(true);

        tabLayout = (TabLayout)findViewById(R.id.tablayout_id);
        viewPager = (ViewPager)findViewById(R.id.viepager_id);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.AddFragment(new FragmentIncome(), "доходы");
        viewPagerAdapter.AddFragment(new FragmentExpense(), "расходы");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    //загрузка данных
    private void LoadData()
    {
        int myBalanceFromDB = dbHelper.GetMyBalance();
        int myIncomeFromDB = dbHelper.GetMyIncome();
        int myExpenseFromDB = dbHelper.GetMyExpense();

        balance.setText(Integer.toString(myBalanceFromDB));
        plus.setText(Integer.toString(myIncomeFromDB));
        minus.setText(Integer.toString(myExpenseFromDB));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        String str;
        //saveData();
        switch (v.getId()){
            case R.id.cardPlus:
                intent = new Intent(MainActivity.this, ActivityPlus.class);
                startActivity(intent);
                break;
            case R.id.cardMinus:
                intent = new Intent(MainActivity.this, ActivityMinus.class);
                intent.putExtra("category", "NONE");
                startActivity(intent);
                break;
            case R.id.cardCar:
                intent = new Intent(MainActivity.this, ActivityMinus.class);
                str = "Car";
                intent.putExtra("category", str);
                startActivity(intent);
                break;
            case R.id.cardBasket:
                intent = new Intent(MainActivity.this, ActivityMinus.class);
                str = "Basket";
                intent.putExtra("category", str);
                startActivity(intent);
                break;
            case R.id.cardHome:
                intent = new Intent(MainActivity.this, ActivityMinus.class);
                str = "Home";
                intent.putExtra("category", str);
                startActivity(intent);
                break;
            default: break;
        }
    }

    void  ConfirmDialogDeleteAll(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить все данные о счете?");
        builder.setMessage("Вы удалите все данные об этом счете в Вашем приложении без возможности восстановления. Вы действительно хотите это сделать?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper db = new DBHelper(MainActivity.this);
                db.DeleteAllDate();
                //перезапуск активити
                recreate();
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        builder.create().show();
    }

    void ConfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить все данные о расходах?");
        builder.setMessage("Вы удалите все данные о расходах в Вашем приложении без возможности восстановления. Вы действительно хотите это сделать?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("DELETE FROM EXPENSE");

                //перезапуск активити
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.update){
            //перезапуск активити
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}