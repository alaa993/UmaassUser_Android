package net.umaass_user.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.umaass_user.app.R;
import net.umaass_user.app.data.remote.models.ServicesItem;
import net.umaass_user.app.interfac.ItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class AdapterService extends RecyclerView.Adapter<AdapterService.MyViewHolder> {

    private List<ServicesItem> lists = new ArrayList<>();
    private ItemClickListener<ServicesItem> listener;
    private ItemClickListener<ServicesItem> deleteListener;
    private ItemClickListener<ServicesItem> editListener;

    public void setListener(ItemClickListener<ServicesItem> listener) {
        this.listener = listener;
    }

    public void setDeleteListener(ItemClickListener<ServicesItem> deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setEditListener(ItemClickListener<ServicesItem> editListener) {
        this.editListener = editListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_service, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ServicesItem item = lists.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    public void clearAndPut(List<ServicesItem> items) {
        lists.clear();
        if (items != null) {
            lists.addAll(items);
        }

        notifyDataSetChanged();
    }

    public void removeItem(ServicesItem item) {
        int pos = lists.indexOf(item);
        lists.remove(item);
        notifyItemRangeRemoved(pos, lists.size());
    }

    public void put(List<ServicesItem> items) {
        if (items != null) {
            lists.addAll(items);
        }
        notifyDataSetChanged();
    }


    public ServicesItem getItem(int position) {
        return lists.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ServicesItem item;
        TextView txtName;
        TextView txtPrice;
        TextView txtDuration;
        TextView txtDesc;

        MyViewHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.txtName);
            txtPrice = view.findViewById(R.id.txtPrice);
            txtDuration = view.findViewById(R.id.txtDuration);
            txtDesc = view.findViewById(R.id.txtDesc);
        }

        void bind(ServicesItem i) {
            item = i;
            txtName.setText(i.getTitle());
            txtPrice.setText(R.string.price_);
            txtPrice.append(String.valueOf(i.getPrice()));
            txtPrice.append(" IQD");
            txtDesc.setText(i.getNotesForTheCustomer());
            txtDuration.setText(R.string.duration_);
            txtDuration.append(String.valueOf(i.getDuration()));
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            if (view == itemView) {
                if (listener != null) {
                    listener.onClick(item);
                }
            }


        }

    }


}