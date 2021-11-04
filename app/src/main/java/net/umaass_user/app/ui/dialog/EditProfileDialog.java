package net.umaass_user.app.ui.dialog;

import androidx.appcompat.widget.AppCompatEditText;
import android.view.View;

import net.umaass_user.app.R;
import net.umaass_user.app.ui.base.BaseDialog;

public class EditProfileDialog extends BaseDialog {


    AppCompatEditText edt_city;

    @Override
    public int getViewLayout() {
        return R.layout.dialog_edit_profile;
    }

    @Override
    public void readView() {
        super.readView();
        edt_city = baseView.findViewById(R.id.edt_city);
    }

    @Override
    public void functionView() {
        super.functionView();
        edt_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityDialog dialog = new CityDialog();
                dialog.show(getChildFragmentManager(), "cityDialog");
            }
        });

    }

}
