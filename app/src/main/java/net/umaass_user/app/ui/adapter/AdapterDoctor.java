package net.umaass_user.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import net.umaass_user.app.R;
import net.umaass_user.app.data.remote.models.Provider;
import net.umaass_user.app.interfac.ItemClickListener;
import net.umaass_user.app.ui.components.RoundCornerButton;
import net.umaass_user.app.utils.CircleImageView;
import net.umaass_user.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class AdapterDoctor extends RecyclerView.Adapter<AdapterDoctor.MyViewHolder> {

    private List<Provider> lists = new ArrayList<>();
    private ItemClickListener<Provider> listener;
    private ItemClickListener<Provider> bookListener;
    private ItemClickListener<Provider> favListener;

    public AdapterDoctor() {
    }

    public void setFavListener(ItemClickListener<Provider> favListener) {
        this.favListener = favListener;
    }

    public void setListener(ItemClickListener<Provider> listener) {
        this.listener = listener;
    }

    public void setBookListener(ItemClickListener<Provider> bookListener) {
        this.bookListener = bookListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_doctor, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Provider item = lists.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    public void clearAndPut(List<Provider> items) {
        lists.clear();
        if (items != null) {
            lists.addAll(items);
        }

        notifyDataSetChanged();
    }

    public void updateItem(Provider provider) {
        if (provider != null) {
            int i = lists.indexOf(provider);
            if (i != -1) {
                lists.set(i, provider);
                notifyItemChanged(i);
            }
        }

    }

    public void removeItem(Provider provider) {
        if (provider != null) {
            int i = lists.indexOf(provider);
            if (i != -1) {
                lists.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeRemoved(i, lists.size());
            }
        }

    }

    public void put(List<Provider> items) {
        if (items != null) {
            lists.addAll(items);
        }
        notifyDataSetChanged();
    }

    public Provider getItem(int position) {
        return lists.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Provider item;
        RoundCornerButton btnBook;
        TextView txtName;
        TextView txtTakhasos;
        TextView txtKilometer;
        TextView txtClosest;
        ImageView btnBookmark;
        TextView txtCountView;
        CircleImageView imgProfile;

        MyViewHolder(View view) {
            super(view);
            btnBook = view.findViewById(R.id.btnBook);
            txtName = view.findViewById(R.id.txtName);
            txtTakhasos = view.findViewById(R.id.txtTakhasos);
            txtKilometer = view.findViewById(R.id.txtKilometer);
            txtClosest = view.findViewById(R.id.txtClosest);
            btnBookmark = view.findViewById(R.id.btnBookmark);
            txtCountView = view.findViewById(R.id.txtCountView);
            imgProfile = view.findViewById(R.id.imgProfile);
        }

        void bind(Provider i) {
            item = i;
            btnBookmark.setOnClickListener(this);
            btnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bookListener != null) {
                        bookListener.onClick(item);
                    }
                }
            });
            itemView.setBackgroundColor(Utils.getColor(getAdapterPosition() % 2 != 0 ? R.color.colorAccentVeryLight : R.color.card_color));
            itemView.setOnClickListener(this);
            txtName.setText(item.getName());
            txtTakhasos.setText(item.getIndustryTitle());
            txtKilometer.setText(item.getDistance());
            txtClosest.setText(item.getFirstExistsAppt());
            btnBookmark.setImageResource(item.getIsFavorited() == 0 ?
                                         R.drawable.ic_bookmark_border_black :
                                         R.drawable.ic_bookmark_black);
            txtCountView.setText(String.valueOf(item.getVisits()));
            Picasso.get()
                   .load(i.getAvatar() == null ? "ddd" : i.getAvatar().getUrlMd())
                   .placeholder(R.drawable.profile)
                   .error(R.drawable.profile)
                   .into(imgProfile);
        }

        @Override
        public void onClick(View view) {
            if (view == itemView) {
                if (listener != null) {
                    listener.onClick(item);
                }
            } else if (view == btnBook) {
                if (bookListener != null) {
                    bookListener.onClick(item);
                }
            } else if (view == btnBookmark) {
                if (favListener != null) {
                    favListener.onClick(item);
                }
            }
        }
    }


}