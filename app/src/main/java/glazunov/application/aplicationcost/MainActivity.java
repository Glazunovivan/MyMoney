package glazunov.application.aplicationcost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardCar,cardHome,cardBasket,cardPlus;
    private TextView balance,plus,minus;
    private int i_balance, i_plus, i_minus;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balance = (TextView)findViewById(R.id.balance);
        plus = (TextView)findViewById(R.id.plus);
        minus = (TextView)findViewById(R.id.minus);

        cardPlus = (CardView)findViewById(R.id.cardPlus);
        cardCar = (CardView)findViewById(R.id.cardCar);
        cardBasket = (CardView)findViewById(R.id.cardBasket);
        cardHome = (CardView)findViewById(R.id.cardHome);

        cardPlus.setOnClickListener(this);
        cardHome.setOnClickListener(this);
        cardBasket.setOnClickListener(this);
        cardCar.setOnClickListener(this);

        //loadData
        LoadData();
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
            case R.id.cardCar:
            case R.id.cardBasket:
            case R.id.cardHome:
                intent = new Intent(MainActivity.this, ActivityMinus.class);
                startActivity(intent);
                break;
            default: break;
        }
    }
}