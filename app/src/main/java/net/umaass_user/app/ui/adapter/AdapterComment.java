package net.umaass_user.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import net.umaass_user.app.R;
import net.umaass_user.app.data.remote.models.Comment;
import net.umaass_user.app.utils.CircleImageView;
import net.umaass_user.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class AdapterComment extends RecyclerView.Adapter<AdapterComment.MyViewHolder> {

    private List<Comment> lists = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_comment, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Comment item = lists.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    public void clearAndPut(List<Comment> items) {
        lists.clear();
        if (items != null) {
            lists.addAll(items);
        }

        notifyDataSetChanged();
    }

    public void removeItem(Comment staffItem) {
        int pos = lists.indexOf(staffItem);
        lists.remove(staffItem);
        notifyItemRangeRemoved(pos, lists.size());
    }

    public void put(List<Comment> items) {
        if (items != null) {
            lists.addAll(items);
        }
        notifyDataSetChanged();
    }


    public Comment getItem(int position) {
        return lists.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtUsername;
        TextView txtComment;
        AppCompatRatingBar ratingBar;
        CircleImageView imgProfile;


        MyViewHolder(View view) {
            super(view);
            txtUsername = view.findViewById(R.id.txtUsername);
            txtComment = view.findViewById(R.id.txtComment);
            ratingBar = view.findViewById(R.id.ratingBar);
            imgProfile = view.findViewById(R.id.imgProfile);
        }

        Comment item;

        void bind(Comment i) {
            item = i;
            txtUsername.setText(i.getName());
            txtComment.setText(i.getContent());
            ratingBar.setRating(i.getRate());
            itemView.setBackgroundColor(Utils.getColor(getAdapterPosition() % 2 != 0 ? R.color.colorAccentVeryLight : R.color.card_color));

            Picasso.get()
                   .load(i.getAvatar() == null ? "ddd" : i.getAvatar().getUrlMd())
                   .placeholder(R.drawable.profile)
                   .error(R.drawable.profile)
                   .into(imgProfile);
        }


        @Override
        public void onClick(View view) {


        }

    }


}