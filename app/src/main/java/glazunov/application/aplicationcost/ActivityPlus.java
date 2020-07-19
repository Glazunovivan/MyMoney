package glazunov.application.aplicationcost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityPlus extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private  EditText edit_plus;
    private int myBalance = 0, plus = 0;
    private Button bAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        edit_plus = (EditText)findViewById(R.id.edit_plus);

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