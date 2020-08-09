package glazunov.application.aplicationcost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private Context context;
    private ArrayList expense_count, expense_category, expense_id, expence_date;
    private IClickForItem iClickForItem;

    ExpenseAdapter(Context context, ArrayList expense_count, ArrayList expense_category, ArrayList expense_id, IClickForItem iClickForItem, ArrayList expence_date){
        this.context = context;
        this.expense_count = expense_count;
        this.expense_category = expense_category;
        this.expense_id = expense_id;
        this.expence_date = expence_date;
        this.iClickForItem = iClickForItem;
    }


    public class ExpenseViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        private TextView expense_count_txt, expense_category_txt, expence_date_txt;
        IClickForItem clickForItem;  //интерфейс

        //конструктор
        public ExpenseViewHolder(View itemView, IClickForItem clickForItem) {
            super(itemView);
            expense_count_txt = itemView.findViewById(R.id.text_view_expence_count);
            expense_category_txt = itemView.findViewById(R.id.text_view_category_expense);
            expence_date_txt = itemView.findViewById(R.id.text_date_expense_recycler_view);
            this.clickForItem = clickForItem;

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            clickForItem.ClickItem(getAdapterPosition());
            return false;
        }
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expense_row, parent, false);
        final ExpenseViewHolder viewHolder = new ExpenseViewHolder(view, iClickForItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        holder.expense_count_txt.setText(String.valueOf(expense_count.get(position)));
        holder.expense_category_txt.setText(String.valueOf(expense_category.get(position)));
        holder.expence_date_txt.setText(String.valueOf(expence_date.get(position)));
    }

    @Override
    public int getItemCount() {
        return expense_count.size();
    }

    public interface IClickForItem{
        void ClickItem(int position);
    }
}
