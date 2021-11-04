package net.umaass_user.app.ui.dialog;

import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;

import net.umaass_user.app.R;
import net.umaass_user.app.ui.base.BaseDialog;

public class OrderByDialog extends BaseDialog {

    AppCompatTextView txt_default, txt_a2zSort, txt_newest, txt_mostVisited, txt_oldest;

    @Override
    public int getViewLayout() {
        return R.layout.dialog_order_by;
    }

    @Override
    public void readView() {
        super.readView();
        txt_default = baseView.findViewById(R.id.txt_default);
        txt_a2zSort = baseView.findViewById(R.id.txt_a2zSort);
        txt_newest = baseView.findViewById(R.id.txt_newest);
        txt_mostVisited = baseView.findViewById(R.id.txt_mostVisited);
        txt_oldest = baseView.findViewById(R.id.txt_oldest);
        img_close = baseView.findViewById(R.id.img_close);
    }

    @Override
    public void functionView() {
        super.functionView();

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkChange(v);
            }
        };
        txt_default.setOnClickListener(clickListener);
        txt_a2zSort.setOnClickListener(clickListener);
        txt_newest.setOnClickListener(clickListener);
        txt_mostVisited.setOnClickListener(clickListener);
        txt_oldest.setOnClickListener(clickListener);

    }

    private void checkChange(View v) {
        removeCheck(txt_default);
        removeCheck(txt_a2zSort);
        removeCheck(txt_newest);
        removeCheck(txt_mostVisited);
        removeCheck(txt_oldest);
        if ((v.getId() == txt_default.getId())) {
            setCheck(txt_default);
        }
        if ((v.getId() == txt_a2zSort.getId())) {
            setCheck(txt_a2zSort);
        }
        if ((v.getId() == txt_newest.getId())) {
            setCheck(txt_newest);
        }
        if ((v.getId() == txt_mostVisited.getId())) {
            setCheck(txt_mostVisited);
        }
        if ((v.getId() == txt_oldest.getId())) {
            setCheck(txt_oldest);
        }
    }

    private void removeCheck(AppCompatTextView txt) {
        txt.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
    }

    private void setCheck(AppCompatTextView txt) {
        txt.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_green, 0);
    }

}
