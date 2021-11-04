package net.umaass_user.app.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.umaass_user.app.R;
import net.umaass_user.app.utils.Utils;
import net.umaass_user.app.data.remote.models.Category;
import net.umaass_user.app.interfac.ItemClickListener;


import java.util.ArrayList;
import java.util.List;


public class AdapterBook extends RecyclerView.Adapter<AdapterBook.MyViewHolder> {

    private Context mContext;
    private List<Category> lists = new ArrayList<>();
    private ItemClickListener<Category> listener;
    private int lastSelectedPosition;
    private boolean choosingMode;

    public AdapterBook() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_book, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        // final Category item = lists.get(position);
        holder.bind(null);
    }

    @Override
    public int getItemCount() {
        return /*lists == null ? 0 : lists.size()*/ 5;
    }

    public void clearAndPut(List<Category> items) {
        lists.clear();
        if (items != null) {
            lists.addAll(items);
        }

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
        Button btnSee;
        Button btnDelete;
        Button btnMove;
        Button btnEdit;

        LinearLayout layInfo;

        TextView txtClose;

        MyViewHolder(View view) {
            super(view);
            btnSee = view.findViewById(R.id.btnSee);
            btnDelete = view.findViewById(R.id.btnDelete);
            btnMove = view.findViewById(R.id.btnMove);
            btnEdit = view.findViewById(R.id.btnEdit);
            layInfo = view.findViewById(R.id.layInfo);
            txtClose = view.findViewById(R.id.txtClose);
        }

        void bind(Category i) {
            item = i;
            itemView.setOnClickListener(this);
            txtClose.setOnClickListener(this);
            checkVisibility();

        }

        private void checkVisibility() {
            boolean isVisible = layInfo.getVisibility() == View.VISIBLE;
            layInfo.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            isVisible = layInfo.getVisibility() == View.VISIBLE;
            int dr = isVisible ? R.drawable.ic_keyboard_arrow_up_black_18dp : R.drawable.ic_keyboard_arrow_down_black_18dp;
            txtClose.setCompoundDrawablesRelativeWithIntrinsicBounds(dr, 0, 0, 0);
            txtClose.setText(Utils.getString(isVisible ? R.string.close : R.string.open));
        }

        @Override
        public void onClick(View view) {
            if (view == itemView) {
                if (listener != null) {
                    listener.onClick(item);
                }
            } else if (view == txtClose) {
                checkVisibility();
            }


        }

    }


}