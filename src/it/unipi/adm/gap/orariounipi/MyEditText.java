package it.unipi.adm.gap.orariounipi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class MyEditText extends EditText {
    
    public MyEditText(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyEditText(Context context,AttributeSet attr) {
        super(context,attr);
        // TODO Auto-generated constructor stub
    }
    
    public MyEditText(Context context, AttributeSet attrs, int defStyle){
    	super(context,attrs,defStyle);
    }
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && 
            event.getAction() == KeyEvent.ACTION_UP) {
        		this.clearFocus();
                return false;
        }
        return super.dispatchKeyEvent(event);
    }

}