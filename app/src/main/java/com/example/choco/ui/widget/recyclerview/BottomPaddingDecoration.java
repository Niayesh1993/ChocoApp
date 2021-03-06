package com.example.choco.ui.widget.recyclerview;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

class BottomPaddingDecoration extends RecyclerView.ItemDecoration {

    private final int bottomPadding;

    private BottomPaddingDecoration(int bottomOffset) {
        bottomPadding = bottomOffset;
    }


    public static BottomPaddingDecoration with(int bottomPadding) {
        return new BottomPaddingDecoration(bottomPadding);
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int dataSize = state.getItemCount();
        int position = parent.getChildAdapterPosition(view);
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager grid = (GridLayoutManager) parent.getLayoutManager();
            if ((dataSize - position) <= grid.getSpanCount()) {
                outRect.set(0, 0, 0, bottomPadding);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            if (dataSize > 0 && position == dataSize - 1) {
                outRect.set(0, 0, 0, bottomPadding);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        } else if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager grid = (StaggeredGridLayoutManager) parent.getLayoutManager();
            if ((dataSize - position) <= grid.getSpanCount()) {
                outRect.set(0, 0, 0, bottomPadding);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        }
    }
}