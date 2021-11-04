package net.umaass_user.app.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.umaass_user.app.R;
import net.umaass_user.app.data.remote.models.Category;
import net.umaass_user.app.interfac.ItemClickListener;
import net.umaass_user.app.utils.permission.ActivityUtils;

import java.util.ArrayList;
import java.util.List;


public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.MyViewHolder> {

    private Activity activity;
    private Context mContext;
    private List<Category> lists = new ArrayList<>();
    private ItemClickListener<Category> listener;
    private int lastSelectedPosition;
    private boolean choosingMode;

    public AdapterCategory() {
    }

    public void setListener(ItemClickListener<Category> listener) {
        this.listener = listener;
    }

    public AdapterCategory(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Category item = lists.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void setLastSelectedPosition(int lastSelectedPosition) {
        this.lastSelectedPosition = lastSelectedPosition;
    }

    public void clearAndPut(List<Category> items,boolean b) {
        lists.clear();
        lists.add(new Category(ActivityUtils.getTopActivity().getString(R.string.all), "", b));
        if (items != null) {
            lists.addAll(items);
        }
        notifyDataSetChanged();
    }
    public void clearAndPut(List<Category> items) {
        lists.clear();
        lists.add(new Category(ActivityUtils.getTopActivity().getString(R.string.all), "", true));
        if (items != null) {
            lists.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void set(List<Category> items) {
        lists = items;
        notifyDataSetChanged();
    }

    public void put(List<Category> items) {
        if (items != null) {
            lists.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void enableChoosingMode() {
        choosingMode = true;
    }

    public Category getItem(int position) {
        return lists.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Category item;
        TextView txtTitle;
        View ll_main;
        ImageView imgIcon;

        MyViewHolder(View view) {
            super(view);
            txtTitle = view.findViewById(R.id.txtTitle);
            ll_main = view.findViewById(R.id.ll_main);
            imgIcon = view.findViewById(R.id.imgIcon);
        }

        void bind(Category i) {
            item = i;
            txtTitle.setText(i.getName());
            itemView.setOnClickListener(this);
            ll_main.setBackgroundResource(i.isSelected() ? R.drawable.round_item_selected : R.drawable.round_item);
        }

        @Override
        public void onClick(View view) {
            if (listener != null)
                listener.onClick(item);
            if (choosingMode) {
                lists.get(lastSelectedPosition).setSelected(false);
                notifyItemChanged(lastSelectedPosition);

                lastSelectedPosition = lists.indexOf(item);
                lists.get(lastSelectedPosition).setSelected(true);
                notifyItemChanged(lastSelectedPosition);
            }
        }
    }


}