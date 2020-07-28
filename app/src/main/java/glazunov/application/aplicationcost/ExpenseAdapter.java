package glazunov.application.aplicationcost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private Context context;
    private ArrayList expense_count, expense_category;

    public class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView expense_count_txt, expense_category_txt;

        //конструктор
        public ExpenseViewHolder(View itemView) {
            super(itemView);
            expense_count_txt = itemView.findViewById(R.id.text_view_expence_count);
            expense_category_txt = itemView.findViewById(R.id.text_view_category_expense);
        }
    }

    ExpenseAdapter(Context context, ArrayList expense_count, ArrayList expense_category){
        this.context = context;
        this.expense_count = expense_count;
        this.expense_category = expense_category;
    }


    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expense_row, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        holder.expense_count_txt.setText(String.valueOf(expense_count.get(position)));
        holder.expense_category_txt.setText(String.valueOf(expense_category.get(position)));
    }

    @Override
    public int getItemCount() {
        return expense_count.size();
    }
}
