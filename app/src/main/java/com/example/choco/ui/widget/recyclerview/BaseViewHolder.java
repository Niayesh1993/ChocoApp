package com.example.choco.ui.widget.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("unchecked")
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    @Nullable protected final BaseRecyclerAdapter adapter;

    public static View getView(@NonNull ViewGroup parent, @LayoutRes int layoutRes) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
    }

    protected BaseViewHolder(@NonNull View itemView) {
        this(itemView, null);
    }

    public BaseViewHolder(@NonNull View itemView, @Nullable BaseRecyclerAdapter adapter) {
        super(itemView);
        this.adapter = adapter;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (adapter != null && adapter.getListener() != null) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && position < adapter.getItemCount()) {
                onViewClicked(position, v, getItem(position));
            }
        }
    }


    @Override
    public boolean onLongClick(View v) {
        if (adapter != null && adapter.getListener() != null) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && position < adapter.getItemCount()) {
                onViewLongClick(position, v, getItem(position));
            }
        }
        return true;
    }

    public <V> V getView() {
        return (V) itemView;
    }

    public T getItem(int position) {
        return (T) adapter.getItem(position);
    }

    public abstract void bind(@NonNull T t);

    protected void onViewIsDetaching() {
    }

    protected void onViewClicked(int position, View v, T item) {
        adapter.getListener().onItemClick(position, item);
    }

    protected void onViewLongClick(int position, View v, T item) {
        adapter.getListener().onItemLongClick(position, v, item);
    }

    public interface OnItemClickListener<T> {

        void onItemClick(int position, T item);

        default void onItemLongClick(int position, View v, T item) {
        }
    }
}
