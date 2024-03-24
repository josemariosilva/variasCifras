package com.example.variascifras;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class ZoomTouchListener implements OnTouchListener {
    private float scaleFactor = 1f;
    private float initialScaleFactor = 1f;
    private TextView textView;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getPointerCount() == 2) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_POINTER_DOWN:
                    initialScaleFactor = scaleFactor;
                    break;
                case MotionEvent.ACTION_MOVE:
                    scaleFactor = initialScaleFactor * (event.getX(0) - event.getX(1));
                    if (textView != null) {
                        textView.setTextSize(scaleFactor * 20); // Ajuste o fator de escala conforme necessário
                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
            }
        }
        return true;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}

