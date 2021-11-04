package net.umaass_user.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import net.umaass_user.app.R;
import net.umaass_user.app.data.remote.models.GalleryItem;
import net.umaass_user.app.interfac.ItemClickListener;
import net.umaass_user.app.utils.RoundedCornersTransformation;
import net.umaass_user.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class AdapterGallery extends RecyclerView.Adapter<AdapterGallery.MyViewHolder> {

    private List<GalleryItem> lists = new ArrayList<>();
    private ItemClickListener<GalleryItem> listener;

    public AdapterGallery() {
    }


    public void setListener(ItemClickListener<GalleryItem> listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_gallery, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final GalleryItem item = lists.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }

    public void clearAndPut(List<GalleryItem> items) {
        lists.clear();
        if (items != null) {
            lists.addAll(items);
        }

        notifyDataSetChanged();
    }

    public void updateItem(GalleryItem provider) {
        if (provider != null) {
            int i = lists.indexOf(provider);
            if (i != -1) {
                lists.set(i, provider);
                notifyItemChanged(i);
            }
        }

    }

    public void removeItem(GalleryItem provider) {
        if (provider != null) {
            int i = lists.indexOf(provider);
            if (i != -1) {
                lists.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeRemoved(i, lists.size());
            }
        }

    }

    public void put(List<GalleryItem> items) {
        if (items != null) {
            lists.addAll(items);
        }
        notifyDataSetChanged();
    }

    public GalleryItem getItem(int position) {
        return lists.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        GalleryItem item;
        ImageView img;

        MyViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.img);
        }

        void bind(GalleryItem i) {
            item = i;
            itemView.setOnClickListener(this);
            Picasso.get()
                   .load(i.getUrlMd() == null ? "ddd" : i.getUrlMd())
                   .resize(400, 400)
                   .transform(new RoundedCornersTransformation(Utils.dp(8), 0))
                   .placeholder(R.drawable.holder)
                   .error(R.drawable.holder)
                   .into(img);
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