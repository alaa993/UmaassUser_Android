package net.umaass_user.app.utils;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;

/**
 * Created on : December 09, 2015
 * User     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {


    private int     previousTotal    = 0;
    private boolean loading          = false;
    private int     visibleThreshold = 1;
    private int firstVisibleItem;
    private int visibleItemCount;
    private int totalItemCount = 0;
    private int currentPage    = 1;


    private int lastPostion;

    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    public EndlessRecyclerOnScrollListener(GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }

    public EndlessRecyclerOnScrollListener(StaggeredGridLayoutManager staggeredGridLayoutManager) {
        this.staggeredGridLayoutManager = staggeredGridLayoutManager;
    }

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager, int visibleThreshold) {
        this.linearLayoutManager = linearLayoutManager;
        this.visibleThreshold = visibleThreshold;
    }

    public EndlessRecyclerOnScrollListener(GridLayoutManager gridLayoutManager, int visibleThreshold) {
        this.gridLayoutManager = gridLayoutManager;
        this.visibleThreshold = visibleThreshold;
    }


    public EndlessRecyclerOnScrollListener(StaggeredGridLayoutManager staggeredGridLayoutManager, int visibleThreshold) {
        this.staggeredGridLayoutManager = staggeredGridLayoutManager;
        this.visibleThreshold = visibleThreshold;

    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        Log.d("recycler.visibleICount", recyclerView.getChildCount() + "");
        currentPage = recyclerView.getChildCount() == 0 ? 1 : currentPage;
        previousTotal = recyclerView.getChildCount() == 0 ? 0 : previousTotal;
        if (linearLayoutManager != null) {
            totalItemCount = linearLayoutManager.getItemCount();
            firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

        } else if (gridLayoutManager != null) {
            visibleItemCount = gridLayoutManager.getChildCount();
            totalItemCount = gridLayoutManager.getItemCount();
            firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

            Log.d("grid.getItemCount", gridLayoutManager.getItemCount() + "");
            Log.d("grid.findFirstItem", gridLayoutManager.findFirstVisibleItemPosition() + "");

        } else if (staggeredGridLayoutManager != null) {

            totalItemCount = staggeredGridLayoutManager.getItemCount();
            int[] tmp = null;
            tmp = staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(tmp);
            if (tmp != null && tmp.length > 0) {
                firstVisibleItem = tmp[0];
            }

        }


        if (loading && totalItemCount > previousTotal) {
            loading = false;
            previousTotal = totalItemCount;

        }


        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {

            Log.d("totalItemCount", totalItemCount + "");
            Log.d("visibleItemCount", visibleItemCount + "");
            Log.d("firstVisibleItem", firstVisibleItem + "");

            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }


    }

    public abstract void onLoadMore(int currentPage);
}