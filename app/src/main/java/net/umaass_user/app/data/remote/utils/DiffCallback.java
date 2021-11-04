package net.umaass_user.app.data.remote.utils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;


public class DiffCallback<T> extends DiffUtil.Callback {

    private List<T>                  mOldList;
    private List<T>                  mNewList;
    private DiffUtil.ItemCallback<T> itemCallback;


    public DiffCallback(List<T> oldList, List<T> newList, DiffUtil.ItemCallback<T> itemCallback) {
        super();
        this.mOldList = oldList;
        this.mNewList = newList;
        this.itemCallback = itemCallback;

    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return itemCallback.areItemsTheSame(mOldList.get(oldItemPosition), mNewList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return itemCallback.areContentsTheSame(mOldList.get(oldItemPosition), mNewList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return newItemPosition;
    }

    public DiffUtil.DiffResult getDiffResult() {
        return DiffUtil.calculateDiff(this);
    }

}
