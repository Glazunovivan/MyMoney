package glazunov.application.aplicationcost;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentExpense extends Fragment {

    private Context context;
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<String> expense_count, expense_category;
    private DBHelper dbHelper;

    public FragmentExpense() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_expense, container, false);
        context = container.getContext();

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_expense_fragment);
        ExpenseAdapter adapter = new ExpenseAdapter(context, expense_count, expense_category);

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

        expense_count = new ArrayList<>();
        expense_category = new ArrayList<>();
        dbHelper = new DBHelper(getContext());

        //loadDataFromDB
        LoadDataFromDB();
    }

    private void LoadDataFromDB(){
        Cursor cursor = dbHelper.readExpenseData();
        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
                expense_count.add(cursor.getString(0));
                expense_category.add(cursor.getString(1));
            }
        }
    }
}