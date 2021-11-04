package net.umaass_user.app.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import net.umaass_user.app.R;


public class AlertDialog extends Dialog implements DialogInterface {

    Context context;
    Button btnPossi;
    Button btnNavi;
    TextView txtTitle;
    TextView txtDesc;
    String title;
    String Message;

    public AlertDialog(Context context) {
        super(context);
        this.context = context;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        init(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void init(final Context context) {
        View view = View.inflate(context, R.layout.dialog_alert, null);
        setContentView(view);
        btnPossi = view.findViewById(R.id.btnPoss);
        btnNavi = view.findViewById(R.id.btnNavi);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtDesc = view.findViewById(R.id.txtDesc);

        txtTitle.setText(title);
        btnNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnPossi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public AlertDialog setInVisibleBtnNavi() {
        btnNavi.setVisibility(View.INVISIBLE);
        return this;
    }


    public AlertDialog onPossiClick(View.OnClickListener click) {
        btnPossi.setOnClickListener(click);
        return this;
    }

    public AlertDialog onNaviOnClick(View.OnClickListener click) {
        btnNavi.setOnClickListener(click);
        return this;

    }

    public AlertDialog withTitle(CharSequence title) {
        txtTitle.setText(title);
        return this;
    }


    public AlertDialog withTitleColor(int color) {
        txtDesc.setTextColor(color);
        return this;
    }

    public AlertDialog withMessage(int textResId) {
        txtDesc.setText(textResId);
        return this;
    }

    public AlertDialog withMessage(CharSequence msg) {
        txtDesc.setText(msg);
        txtDesc.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);
        return this;
    }

    public AlertDialog withMessageColor(int color) {
        txtDesc.setTextColor(color);
        return this;
    }


    public AlertDialog withPossiText(CharSequence text) {
        btnPossi.setText(text);
        return this;
    }

    public AlertDialog withNaviText(CharSequence text) {
        btnNavi.setText(text);
        return this;
    }


    public AlertDialog setCustomView(int resId, Context context) {
        View customView = View.inflate(context, resId, null);
        return this;
    }

    public AlertDialog setCustomView(View view, Context context) {

        return this;
    }

    public AlertDialog isCancelable(boolean cancelable) {
        this.setCancelable(cancelable);
        return this;
    }

    private void toggleView(View view, Object obj) {
        if (obj == null) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show() {
        super.show();
    }


    @Override
    public void dismiss() {
        super.dismiss();

    }
}

