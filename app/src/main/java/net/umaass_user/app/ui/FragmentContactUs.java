package net.umaass_user.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import net.umaass_user.app.R;
import net.umaass_user.app.ui.base.BaseFragment;
import net.umaass_user.app.ui.components.RoundCornerButton;

public class FragmentContactUs extends BaseFragment {

    private RoundCornerButton txtEmail;
    private RoundCornerButton txtPhone;

    @Override
    public int getViewLayout() {
        return R.layout.fragment_contact_us;
    }

    @Override
    public void readView() {
        super.readView();
        txtEmail = baseView.findViewById(R.id.txtEmail);
        txtPhone = baseView.findViewById(R.id.txtPhone);

    }


    @Override
    public void functionView() {
        super.functionView();

        txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{txtEmail.getTag().toString()});
                    email.putExtra(Intent.EXTRA_SUBJECT, "");
                    email.putExtra(Intent.EXTRA_TEXT, "");
                    //need this to prompts email client only
                    email.setType("message/rfc822");
                    startActivity(Intent.createChooser(email, "Choose an Email client :"));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = txtPhone.getTag().toString();
                String url = "https://api.whatsapp.com/send?phone="+phone;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }



}
