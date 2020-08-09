package glazunov.application.aplicationcost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActivityMinus extends AppCompatActivity {

    //private SharedPreferences sPref;

    private Button btnMinus;
    private EditText etMinus, edit_text_expense_category;
    private TextView text_date;

    private int balance = 0, minus = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minus);

        etMinus = (EditText)findViewById(R.id.etMinus);
        edit_text_expense_category = (EditText)findViewById(R.id.edit_expense_category);
        text_date = (TextView)findViewById(R.id.text_date_m);

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);

        text_date.setText(dateText);

        //получаем данные
        /*sPref = getSharedPreferences("MY_BALANCE", MODE_PRIVATE);
        balance = sPref.getInt("MyBalance", 0);*/

        Bundle arguments = getIntent().getExtras();
        String categoryType = arguments.getString("category");

        if(categoryType.equals("Car")){
            //etCategory.setText("Транспорт");
            edit_text_expense_category.setText("Транспорт");
        }
        else if(categoryType.equals("Home")){
            //etCategory.setText("Домашние расходы");
            edit_text_expense_category.setText("Домашние расходы");
        }
        else if(categoryType.equals("Basket")){
            //etCategory.setText("Покупки");
            edit_text_expense_category.setText("Покупки");
        }
        else if(categoryType.equals("NONE")){
            //etCategory.setText("Другое");
        }
    }

    private void saveData(){
        minus = Integer.parseInt(etMinus.getText().toString());
        balance -= minus;

        //add DB
        DBHelper db = new DBHelper(ActivityMinus.this);
        String category = edit_text_expense_category.getText().toString();

        db.addExpense(category, minus, text_date.getText().toString());
    }

    public void clickMinus(View v){
        saveData();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
