package net.umaass_user.app.ui.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import net.umaass_user.app.R;
import net.umaass_user.app.application.G;


public abstract class BaseDialog extends BottomSheetDialogFragment {


    private boolean isCancelable = false;
    protected Context context;
    public AppCompatButton btnPoss, btnNavi;
    public AppCompatTextView txtDesc, txtTitle;
    public AppCompatImageView imgIcon, img_close;

    private String mTxtDesc = "", mTxtTitle = "", mTxtBtnPoss = "", mTxtBtnNav = "";
    private int imageRes = 0;

    public View.OnClickListener btnPossClickListener;
    public View.OnClickListener btnNaviClickListener;

    public int baseViewId = getViewLayout();
    public View baseView;


    public abstract int getViewLayout();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        baseView = inflater.inflate(baseViewId, container, false);
        return baseView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //  mViewModel = ViewModelProviders.of(this).get(FilterViewModel.class);
        readView();
        functionView();
        initViewModel();
        // TODO: Use the ViewModel
    }

    public void initViewModel() {

    }

    public void readView() {

        btnPoss = baseView.findViewById(R.id.btnPoss);
        img_close = baseView.findViewById(R.id.img_close);
        btnNavi = baseView.findViewById(R.id.btnNavi);
        txtDesc = baseView.findViewById(R.id.txtDesc);
        txtTitle = baseView.findViewById(R.id.txtTitle);
        imgIcon = baseView.findViewById(R.id.imgIcon);
    }


    public void functionView() {

        if (btnPossClickListener != null) {
            btnPoss.setOnClickListener(btnPossClickListener);
        } else {
            if (btnPoss != null)
                btnPoss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseDialog.this.dismiss();
                    }
                });
        }

        if (btnNaviClickListener != null) {
            btnNavi.setOnClickListener(btnNaviClickListener);
        } else {
            if (btnNavi != null)
                btnNavi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseDialog.this.dismiss();
                    }
                });
        }
        if (img_close != null) {
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        if (!mTxtBtnNav.isEmpty() && btnNavi != null) {
            btnNavi.setText(mTxtBtnNav);
        }
        if (!mTxtBtnPoss.isEmpty() && btnPoss != null) {
            btnPoss.setText(mTxtBtnPoss);
        }
        if (!mTxtTitle.isEmpty() && txtTitle != null) {
            txtTitle.setText(mTxtTitle);
        }
        if (!mTxtDesc.isEmpty() && txtDesc != null) {
            txtDesc.setText(mTxtDesc);
        }
        if (imageRes != 0 && imgIcon != null) {
            imgIcon.setImageResource(imageRes);
        }

    }


    public void setTxtDesc(int mTxtDesc) {
        this.mTxtDesc = G.getInstance().getString(mTxtDesc);
    }

    public void setTxtTitle(int mTxtTitle) {
        this.mTxtTitle = G.getInstance().getString(mTxtTitle);
    }

    public void setTxtBtnPoss(int mTxtBtnPoss) {
        this.mTxtBtnPoss = G.getInstance().getString(mTxtBtnPoss);
    }

    public void setTxtBtnNav(int mTxtBtnNav) {
        this.mTxtBtnNav = G.getInstance().getString(mTxtBtnNav);
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public void setTxtDesc(String mTxtDesc) {
        this.mTxtDesc = mTxtDesc;
    }

    public void setTxtTitle(String mTxtTitle) {
        this.mTxtTitle = mTxtTitle;
    }

    public void setTxtBtnPoss(String mTxtBtnPoss) {
        this.mTxtBtnPoss = mTxtBtnPoss;
    }

    public void setTxtBtnNav(String mTxtBtnNav) {
        this.mTxtBtnNav = mTxtBtnNav;
    }

    public AppCompatButton getBtnPoss() {
        return btnPoss;
    }

    public AppCompatButton getBtnNavi() {
        return btnNavi;
    }

    public void setBtnPossListener(View.OnClickListener btnPossClickListener) {
        this.btnPossClickListener = btnPossClickListener;
    }


    public void setBtnNaviClickListener(View.OnClickListener btnNaviClickListener) {
        this.btnNaviClickListener = btnNaviClickListener;
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }


    public void setBtnPossClickListener(View.OnClickListener onClickListener) {
        if (btnPoss != null)
            btnPoss.setOnClickListener(onClickListener);
    }
}

