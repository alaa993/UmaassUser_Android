package net.umaass_user.app.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.umaass_user.app.R;
import net.umaass_user.app.ui.adapter.AdapterBook;
import net.umaass_user.app.ui.base.BaseFragment;

public class FragmentBooksFuture extends BaseFragment {

    AdapterBook adapterBook;
    RecyclerView recyclerView;


    @Override
    public int getViewLayout() {
        return R.layout.fragment_books_future;
    }

    @Override
    public void readView() {
        super.readView();
        recyclerView = baseView.findViewById(R.id.recyclerView);
    }

    @Override
    public void functionView() {
        super.functionView();
        adapterBook = new AdapterBook();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterBook);

    }

    private void getData() {

    }
}
