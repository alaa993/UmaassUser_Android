package net.umaass_user.app.ui.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.recyclerview.widget.RecyclerView;

import net.umaass_user.app.R;
import net.umaass_user.app.interfac.ItemClickListener;
import net.umaass_user.app.interfac.ListItem;

import java.util.ArrayList;
import java.util.List;


public class AdapterDialogList extends RecyclerView.Adapter<AdapterDialogList.MyViewHolder> {

    @NonNull
    private List<ListItem> list = new ArrayList<>();
    private List<ListItem> selectedList = new ArrayList<>();
    private ItemClickListener<ListItem> listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatCheckedTextView txtName;

        MyViewHolder(@NonNull View view) {
            super(view);
            txtName = itemView.findViewById(R.id.txtName);
        }


        ListItem item;

        public void bind(@NonNull final ListItem item) {
            this.item = item;
            txtName.setText(item.getItemName());
            if (selectedList.contains(item)) {
                txtName.setCheckMarkDrawable(R.drawable.ic_check_black_24dp);
            } else {
                txtName.setCheckMarkDrawable(null);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }

    }


    @NonNull
    @Override
    public AdapterDialogList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterDialogList.MyViewHolder holder, int position) {

        final ListItem item = list.get(position);
        holder.bind(item);
    }

    public void clearAndPut(@Nullable List<ListItem> items) {
        list.clear();
        if (items != null) {
            list.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void put(@Nullable List<ListItem> items) {
        if (items != null) {
            list.addAll(items);
        }
        notifyDataSetChanged();
    }

    public List<ListItem> getSelectedList() {
        return selectedList;
    }

    public void changeSelected(ListItem listItem) {
        if (selectedList.contains(listItem)) {
            selectedList.remove(listItem);
        } else {
            selectedList.add(listItem);
        }
        int i = list.indexOf(listItem);
        notifyItemChanged(i);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setListener(ItemClickListener<ListItem> listener) {
        this.listener = listener;
    }

    public ListItem getItem(int position) {
        return list.get(position);
    }


}