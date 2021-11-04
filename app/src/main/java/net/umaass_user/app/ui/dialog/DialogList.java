package net.umaass_user.app.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import net.umaass_user.app.R;
import net.umaass_user.app.interfac.ItemClickListener;
import net.umaass_user.app.interfac.ListItem;

import java.util.ArrayList;
import java.util.List;

public class DialogList<T extends ListItem> extends BottomSheetDialog {

    SearchView searchView;
    private RecyclerView recyclerView;
    private AdapterDialogList adapter;
    private List<ListItem> itemes = new ArrayList<>();
    private ItemClickListener<ListItem> listener;
    private ItemClickListener<List<ListItem>> multiSelectListener;
    private TextView txtTitle;
    private TextView btnDone;
    private boolean multiSelect = false;
    List<ListItem> searchItem = new ArrayList<>();

    public DialogList(@NonNull Context context, Boolean serachVisiblity) {
        super(context);
        init(context, serachVisiblity);
    }

    public void clearAndPut(@Nullable List<ListItem> items) {
        itemes.clear();
        if (items != null) {
            itemes.addAll(items);
        }
        adapter.clearAndPut(itemes);
    }

    public void setMultiSelectListener(ItemClickListener<List<ListItem>> multiSelectListener) {
        this.multiSelectListener = multiSelectListener;
    }

    public void setMultiSelect(boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    public void setListener(ItemClickListener<ListItem> listener) {
        this.listener = listener;
    }

    public void put(@Nullable List<ListItem> items) {
        if (items != null) {
            itemes.addAll(items);
        }
        adapter.put(items);
    }

    private void init(Context context, Boolean searchVisibility) {
        View contentView = View.inflate(getContext(), R.layout.dialoglist, null);
        setContentView(contentView);
        searchView = contentView.findViewById(R.id.searchView);
        txtTitle = contentView.findViewById(R.id.txtTitle);
        recyclerView = contentView.findViewById(R.id.recyclerView);
        btnDone = contentView.findViewById(R.id.btnDone);
        if (searchVisibility == true){
            searchView.setVisibility(View.VISIBLE);
        }else {
            searchView.setVisibility(View.GONE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        adapter = new AdapterDialogList();
        adapter.setListener(new ItemClickListener<ListItem>() {
            @Override
            public void onClick(ListItem item) {
                if (multiSelect) {
                    adapter.changeSelected(item);
                } else {
                    if (listener != null) {
                        listener.onClick(item);
                    }
                    dismiss();
                }
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (multiSelectListener != null) {
                    multiSelectListener.onClick(adapter.getSelectedList());
                }
                dismiss();
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.equals("")){

                    adapter.clearAndPut(itemes);

                }else {

                    searchItem.clear();
                    for (ListItem row : itemes) {

                        if (row.getItemName().toLowerCase().contains(newText.toLowerCase())) {
                            searchItem.add(row);
                        }
                    }
                    adapter.clearAndPut(searchItem);
                }

                return false;
            }
        });
    }


    public void setTitle(String title) {
        if (txtTitle != null) {
            txtTitle.setText(title);
        }
    }

    @Override
    public void show() {
        btnDone.setVisibility(multiSelect ? View.VISIBLE : View.GONE);
        super.show();
    }

}