package com.example.choco.ui.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.choco.ui.widget.StateLayout;

public class DynamicRecyclerView extends RecyclerView {

    private BottomPaddingDecoration bottomPaddingDecoration;
    @Nullable
    private View parentView;
    private StateLayout emptyView;

    @NonNull
    private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            showEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            showEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            showEmptyView();
        }
    };

    public DynamicRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public DynamicRecyclerView(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicRecyclerView(@NonNull Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(@Nullable RecyclerView.Adapter adapter) {
        super.setAdapter(adapter);
        if (isInEditMode()) return;
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
            observer.onChanged();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        try {// rare case
            if (getAdapter() != null) {
                getAdapter().unregisterAdapterDataObserver(observer);
            }
        } catch (Exception e) {
        }
        super.onDetachedFromWindow();
    }

    public void removeBottomDecoration() {
        if (bottomPaddingDecoration != null) {
            removeItemDecoration(bottomPaddingDecoration);
            bottomPaddingDecoration = null;
        }
    }

//    public void addDecoration() {
//        bottomPaddingDecoration = BottomPaddingDecoration.with(getContext());
//        addItemDecoration(bottomPaddingDecoration);
//    }

    private void showEmptyView() {
        Adapter<?> adapter = getAdapter();
        if (adapter != null) {
            if (adapter.getItemCount() == 0) {
                showParentOrSelf(false);
            } else {
                showParentOrSelf(true);
            }
        } else {
            showParentOrSelf(false);
        }
    }

    private void showParentOrSelf(boolean showRecyclerView) {
        if (parentView != null) {
            parentView.setVisibility(VISIBLE);
        }
        setVisibility(showRecyclerView ? VISIBLE : GONE);
        if (!showRecyclerView) {
            if (emptyView != null) {
                emptyView.showEmptyState();
            }
        } else {
            if (emptyView != null) {
                emptyView.setVisibility(GONE);
            }
        }
    }

    public void setEmptyView(@NonNull StateLayout emptyView, @Nullable View parentView) {
        this.emptyView = emptyView;
        this.parentView = parentView;
        showEmptyView();
    }

    public void setEmptyView(@NonNull StateLayout emptyView) {
        setEmptyView(emptyView, null);
    }

    public void hideProgress(@NonNull StateLayout view) {
        view.hideProgress();
    }

    public void showProgress(@NonNull StateLayout view) {
        view.showProgress();
    }


    private boolean canAddDivider() {
        if (getLayoutManager() != null) {
            if (getLayoutManager() instanceof GridLayoutManager) {
                return ((GridLayoutManager) getLayoutManager()).getSpanCount() == 1;
            } else if (getLayoutManager() instanceof LinearLayoutManager) {
                return true;
            } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                return ((StaggeredGridLayoutManager) getLayoutManager()).getSpanCount() == 1;
            }
        }
        return false;
    }
}