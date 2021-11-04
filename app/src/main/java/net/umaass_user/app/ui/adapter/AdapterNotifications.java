package net.umaass_user.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.umaass_user.app.R;
import net.umaass_user.app.data.remote.models.NotificationsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AdapterNotifications extends RecyclerView.Adapter<AdapterNotifications.MyViewHolder> {

    private List<NotificationsModel> lists = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_notifications, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        NotificationsModel item = lists.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    public void clearAndPut(List<NotificationsModel> items) {
        lists.clear();
        if (items != null) {
            lists.addAll(items);
        }

        notifyDataSetChanged();
    }

    public void removeItem(NotificationsModel staffItem) {
        int pos = lists.indexOf(staffItem);
        lists.remove(staffItem);
        notifyItemRangeRemoved(pos, lists.size());
    }

    public void put(List<NotificationsModel> items) {
        if (items != null) {
            lists.addAll(items);
        }
        notifyDataSetChanged();
    }


    public NotificationsModel getItem(int position) {
        return lists.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNotiSenderName;
        TextView tvNotiDate;
        TextView tvNotiDesc;

        MyViewHolder(View view) {
            super(view);
            tvNotiSenderName = view.findViewById(R.id.tvNotiSenderName);
            tvNotiDate = view.findViewById(R.id.tvNotiDate);
            tvNotiDesc = view.findViewById(R.id.tvNotiDesc);
        }

        NotificationsModel item;


        void bind(NotificationsModel i) {
            item = i;

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            try {
                date = inputFormat.parse(item.getCreatedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formattedDate = outputFormat.format(date);
            tvNotiSenderName.setText(item.getTitle());
            tvNotiDate.setText(formattedDate);
            tvNotiDesc.setText(item.getMessage());
        }

        @Override
        public void onClick(View view) {

        }

    }
}