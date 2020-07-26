package glazunov.application.aplicationcost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CostViewHolder>{

    private Context context;
    private ArrayList incomeDB, incomeDBType;


    public class CostViewHolder extends RecyclerView.ViewHolder {
        TextView incomeDB_txt, incomeType_txt;

        //конструктор
        public CostViewHolder(View itemView) {
            super(itemView);
            incomeDB_txt = itemView.findViewById(R.id.tvCountIncomes);
            incomeType_txt = itemView.findViewById(R.id.txt_income_type);
        }
    }

    //и это конструктор, но для адаптера
    CustomAdapter(Context context, ArrayList incomeDB, ArrayList incomeDBType) {
        this.context = context;
        this.incomeDB = incomeDB;
        this.incomeDBType = incomeDBType;
    }


    @NonNull
    @Override
    public CostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.incomes_row, parent, false);
        return new CostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CostViewHolder holder, int position) {
        holder.incomeDB_txt.setText(String.valueOf(incomeDB.get(position)));
        holder.incomeType_txt.setText(String.valueOf(incomeDBType.get(position)));
    }

    /*
     public void bindView(View view, Context context, Cursor cursor){
       textView name = (TextView)view.findViewById(R.id.name);
       name.setText(cursor.getString(cursor.getColumnIndex("name")));

     }*/

    @Override
    public int getItemCount() {
        return incomeDB.size();
    }
}
