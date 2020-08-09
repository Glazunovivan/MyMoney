package glazunov.application.aplicationcost;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentExpense extends Fragment implements ExpenseAdapter.IClickForItem {

    private Context context;
    private View view;
    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;

    private ArrayList<String> expense_count, expense_category, expense_id, expence_date;

    private DBHelper dbHelper;

    public FragmentExpense() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_expense, container, false);
        context = container.getContext();

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_expense_fragment);
        adapter = new ExpenseAdapter(context, expense_count, expense_category, expense_id, this, expence_date);

        LinearLayoutManager lManager = new LinearLayoutManager(context);
        lManager.setReverseLayout(true);
        lManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(lManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(getContext());

        //loadDataFromDB
        LoadDataFromDB();
    }

    private void LoadDataFromDB(){
        expense_count = new ArrayList<>();
        expense_category = new ArrayList<>();
        expense_id = new ArrayList<>();
        expence_date = new ArrayList<>();

        Cursor cursor = dbHelper.readExpenseData();
        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
                expense_count.add(cursor.getString(0));
                expense_category.add(cursor.getString(1));
                expense_id.add(cursor.getString(2));
                expence_date.add(cursor.getString(3));
            }
        }
    }

    //Удаление элемента долгим нажатием
    @Override
    public void ClickItem(int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //Toast.makeText(context, "expense id = " + expense_id.get(position), Toast.LENGTH_SHORT).show();
        db.delete("EXPENSE", "ID = " + expense_id.get(position), null );

        expense_id.remove(position);
        expense_category.remove(position);
        expense_count.remove(position);
        expence_date.remove(position);

        adapter.notifyItemRemoved(position);

    }
}