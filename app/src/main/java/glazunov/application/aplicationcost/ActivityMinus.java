package glazunov.application.aplicationcost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityMinus extends AppCompatActivity {

    private SharedPreferences sPref;

    private Button btnMinus;
    private EditText etSum;
    private TextView etCategory;
    private int balance = 0, minus = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minus);

        etSum = (EditText)findViewById(R.id.etMinus);
        etCategory = (TextView)findViewById(R.id.etCategory);

        //получаем данные
        sPref = getSharedPreferences("MY_BALANCE", MODE_PRIVATE);
        balance = sPref.getInt("MyBalance", 0);

        Bundle arguments = getIntent().getExtras();
        String categoryType = arguments.getString("category");

        if(categoryType.equals("Car")){
            etCategory.setText("Транспортное средство");
        }
        else if(categoryType.equals("Home")){
            etCategory.setText("Домашние расходы");
        }
        else if(categoryType.equals("Basket")){
            etCategory.setText("Покупки");
        }
        else if(categoryType.equals("NONE")){
            etCategory.setText("Другое");
        }
    }

    private void saveData(){
        minus = Integer.parseInt(etSum.getText().toString());
        balance -= minus;

        //add DB
        DBHelper db = new DBHelper(ActivityMinus.this);
        db.addExpense(etCategory.getText().toString(), minus);

        //обновляем расход
        minus += sPref.getInt("Minus", 0);

        SharedPreferences.Editor editor = sPref.edit();
        editor.putInt("Minus", minus).apply();
        editor.putInt("MyBalance", balance).apply();
    }

    public void clickMinus(View v){
        saveData();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
