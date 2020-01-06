package com.rrithvik.idrive;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Created by rrithvik on 2/17/18.
 */

public class CustomDialogClass extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public CheckBox check1, check2;
    public Button b;

    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.terms_and_conditions);
        check1 = (CheckBox) findViewById(R.id.checkBox);
        check2 = (CheckBox) findViewById(R.id.checkBox2);

        b = (Button) findViewById(R.id.buttonTC);
        b.setOnClickListener(this);
        b.setEnabled(false);
        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if ( isChecked )
                {

                    check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                        {
                            if ( isChecked )
                            {
                                b.setEnabled(true);

                            }else{
                                b.setEnabled(false);
                            }

                        }
                    });

                }else{
                    b.setEnabled(false);
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonTC:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();

    }
}