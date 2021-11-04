package net.umaass_user.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.umaass_user.app.R;
import net.umaass_user.app.data.remote.models.StaffItem;
import net.umaass_user.app.interfac.ItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class AdapterStaff extends RecyclerView.Adapter<AdapterStaff.MyViewHolder> {

    private Context mContext;
    private List<StaffItem> lists = new ArrayList<>();
    private ItemClickListener<StaffItem> listener;
    private ItemClickListener<StaffItem> deleteListener;
    private ItemClickListener<StaffItem> editListener;
    private int lastSelectedPosition;
    private boolean choosingMode;

    public void setListener(ItemClickListener<StaffItem> listener) {
        this.listener = listener;
    }

    public void setDeleteListener(ItemClickListener<StaffItem> deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setEditListener(ItemClickListener<StaffItem> editListener) {
        this.editListener = editListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_staff, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        StaffItem item = lists.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    public void clearAndPut(List<StaffItem> items) {
        lists.clear();
        if (items != null) {
            lists.addAll(items);
        }

        notifyDataSetChanged();
    }

    public void removeItem(StaffItem staffItem) {
        int pos = lists.indexOf(staffItem);
        lists.remove(staffItem);
        notifyItemRangeRemoved(pos, lists.size());
    }

    public void put(List<StaffItem> items) {
        if (items != null) {
            lists.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void enableChoosingMode() {
        choosingMode = true;
    }

    public StaffItem getItem(int position) {
        return lists.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtUsername;
        TextView txtRoll;
        TextView txtPhone;
        TextView txtEmail;
        TextView btnEdit;
        TextView btnDelete;


        MyViewHolder(View view) {
            super(view);
            txtUsername = view.findViewById(R.id.txtUsername);
            txtRoll = view.findViewById(R.id.txtRoll);
            txtPhone = view.findViewById(R.id.txtPhone);
            txtEmail = view.findViewById(R.id.txtEmail);
            btnEdit = view.findViewById(R.id.btnEdit);
            btnDelete = view.findViewById(R.id.btnDelete);
        }

        StaffItem item;

        void bind(StaffItem i) {
            item = i;
            txtUsername.setText(i.getName());
            txtRoll.setText(i.getRole());
            txtPhone.setText(i.getPhone());
            txtEmail.setText(i.getEmail());
            itemView.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
            btnEdit.setOnClickListener(this);
            btnEdit.setVisibility(getAdapterPosition() == 0 ? View.INVISIBLE : View.VISIBLE);
            btnDelete.setVisibility(getAdapterPosition() == 0 ? View.INVISIBLE : View.VISIBLE);
        }


        @Override
        public void onClick(View view) {
            if (view == itemView) {
                if (listener != null) {
                    listener.onClick(item);
                }
            } else if (view == btnDelete) {
                if (deleteListener != null) {
                    deleteListener.onClick(item);
                }
            } else if (view == btnEdit) {
                if (editListener != null) {
                    editListener.onClick(item);
                }
            }


        }

    }


}