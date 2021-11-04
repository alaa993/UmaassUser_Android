package net.umaass_user.app.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.umaass_user.app.R;


public class RoundCornerButton extends LinearLayout {
    View rootView;
    View parent;
    TextView txt_call;
    ImageView img_icon;

    public RoundCornerButton(Context context) {
        super(context);
        init(context, null);
    }

    public RoundCornerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundCornerButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RoundCornerButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void setValue(String text, int p) {

       /* Drawable img = getContext().getResources().getDrawable(p);
        img.setBounds( 0, 0, 40, 60);
        txt_call.setCompoundDrawables( null, null, img, null );*/
        img_icon.setImageResource(p);
        txt_call.setText(text);
    }

    String text;
    int icon;
    int backGroundColor;
    int textColor;
    int iconColorFilter;
    float textSize;

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerButton);
        backGroundColor = typedArray.getColor(R.styleable.RoundCornerButton_rc_backgroundColor, Color.GRAY);
        textColor = typedArray.getColor(R.styleable.RoundCornerButton_rc_text_color, Color.WHITE);
        iconColorFilter = typedArray.getColor(R.styleable.RoundCornerButton_rc_icon_color, Color.WHITE);
        textSize = typedArray.getDimensionPixelSize(R.styleable.RoundCornerButton_rc_text_size, -1);
        icon = typedArray.getResourceId(R.styleable.RoundCornerButton_rc_icon, 0);
        text = typedArray.getString(R.styleable.RoundCornerButton_rc_text);
        typedArray.recycle();

        rootView = inflate(context, R.layout.round_corner_button, this);
        txt_call = rootView.findViewById(R.id.txt_call);
        img_icon = rootView.findViewById(R.id.img_icon);
        parent = rootView.findViewById(R.id.parent);

        setIcon(icon);
        txt_call.setTextColor(textColor);
        txt_call.setText(text);
        if (textSize != -1) {
            txt_call.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        setIconColorFilter(iconColorFilter);
    }

    public void setIcon(int res) {
        icon = res;
        img_icon.setImageResource(res);
        img_icon.setVisibility(icon == 0 ? GONE : VISIBLE);
    }

    public void setIconColorFilter(int res) {
        img_icon.setColorFilter(res);
    }

    public void setText(int res) {
        txt_call.setText(res);
    }

    public void setText(String text) {
        txt_call.setText(text);
    }

    @Override
    public void setOnClickListener(@Nullable View.OnClickListener l) {
        parent.setOnClickListener(l);
    }

}
