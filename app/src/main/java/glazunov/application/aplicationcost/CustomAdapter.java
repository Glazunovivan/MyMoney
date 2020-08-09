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
    private ArrayList income_count, income_category, income_id;
    private IClickForItem iClickForItem;

    //и это конструктор, но для адаптера
    CustomAdapter(Context context, ArrayList income_count, ArrayList income_category,ArrayList income_id, IClickForItem iClickForItem) {
        this.context = context;
        this.income_count = income_count;
        this.income_category = income_category;
        this.income_id = income_id;
        this.iClickForItem = iClickForItem;
    }

    public class CostViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView incomeDB_txt, incomeType_txt;

        //интерфейс
        IClickForItem clickForItem;

        //конструктор
        public CostViewHolder(View itemView, IClickForItem clickForItem) {
            super(itemView);
            incomeDB_txt = itemView.findViewById(R.id.tvCountIncomes);
            incomeType_txt = itemView.findViewById(R.id.txt_income_type);
            this.clickForItem = clickForItem;

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            clickForItem.ClickItem(getAdapterPosition());
            return true;
        }
    }

    @NonNull
    @Override
    public CostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.incomes_row, parent, false);
        return new CostViewHolder(view, iClickForItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CostViewHolder holder, int position) {
        holder.incomeDB_txt.setText(String.valueOf(income_count.get(position)));
        holder.incomeType_txt.setText(String.valueOf(income_category.get(position)));
    }

    @Override
    public int getItemCount() {
        return income_count.size();
    }

    public interface IClickForItem {
        void ClickItem(int position);
    }

}
