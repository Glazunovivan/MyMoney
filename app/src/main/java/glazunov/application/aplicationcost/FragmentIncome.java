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

public class FragmentIncome extends Fragment {

    private Context context;
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<String> incomes_count, incomes_category;
    private DBHelper dbHelper;

    public FragmentIncome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_income, container, false);
        context = container.getContext();

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_incomes_fragment);
        CustomAdapter adapter = new CustomAdapter(context, incomes_count, incomes_category);

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

        incomes_count = new ArrayList<>();
        incomes_category = new ArrayList<>();
        dbHelper = new DBHelper(getContext());

        //load data from DB
        LoadDataFromDB();
    }

    private void LoadDataFromDB(){
        Cursor cursor = dbHelper.readIncomeData();
        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
                incomes_count.add(cursor.getString(0));
                incomes_category.add(cursor.getString(1));
            }
        }
    }
}