package com.example.variascifras;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;


public class DisplaySelectedItemsActivity extends AppCompatActivity {

    private TextView infoTextView;
    private GestureDetector gestureDetector;
    final static float STEP = 200;
    float mRatio = 1.0f;
    int mBaseDist;
    float mBaseRatio;
    private Button scrollButton;
    private ScrollView scrollView;
    private SeekBar speedSeekBar;
    private Handler handler;
    private boolean isScrollPaused = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_selected_items);

        scrollView = findViewById(R.id.scrollView);
        infoTextView = findViewById(R.id.infoTextView);
        speedSeekBar = findViewById(R.id.speedSeekBar);
        handler = new Handler();
        infoTextView.setMovementMethod(new ScrollingMovementMethod()); // Torna o TextView rolável
        Button pauseButton = findViewById(R.id.pauseButton);

        // Ative a rolagem (scroll) e o zoom no TextView
        infoTextView.setMovementMethod(new ScrollingMovementMethod());
        infoTextView.setOnTouchListener(new ZoomTouchListener());
        scrollButton = findViewById(R.id.scrollButton);

        // Configura o Gesture Detector para permitir zoom
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                float textSize = infoTextView.getTextSize();
                float zoomFactor = 1.5f; // Fator de zoom (ajuste conforme necessário)
                infoTextView.setTextSize(textSize * zoomFactor);
                return true;
            }
        });



        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<String> selectedItems = intent.getStringArrayListExtra("selectedItems");

            if (selectedItems != null) {
                // Aqui você pode concatenar os itens selecionados em uma única String
                StringBuilder concatenatedInfo = new StringBuilder();
                for (String selectedItem : selectedItems) {
                    // Suponhamos que você queira separar cada item por uma quebra de linha
                    concatenatedInfo.append(selectedItem).append("\n");
                }

                // Exiba a informação concatenada no TextView
                infoTextView.setText("\n");
                infoTextView.setText("CIFRAS:\n" + concatenatedInfo.toString());
            }
        }


        infoTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getPointerCount() == 2) {
                    int action = event.getAction();
                    int pureaction = action & MotionEvent.ACTION_MASK;
                    if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
                        mBaseDist = getDistance(event);
                        mBaseRatio = mRatio;
                    } else {
                        float delta = (getDistance(event) - mBaseDist) / STEP;
                        float multi = (float) Math.pow(2, delta);
                        mRatio = Math.min(1024.0f, Math.max(0.1f, mBaseRatio * multi));
                        infoTextView.setTextSize(mRatio + 13);
                    }
                }

                return true;
            }
        });

        scrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int scrollSpeed = speedSeekBar.getProgress(); // Obter a velocidade do SeekBar
                smoothScrollToBottom(scrollSpeed);
            }
        });


        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isScrollPaused = !isScrollPaused; // Inverte o estado de pausa
                if (isScrollPaused) {
                    // Remove os callbacks pendentes para interromper a rolagem
                    handler.removeCallbacksAndMessages(null);
                }
            }
        });


    }//onCreate

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event); // Detecta gestos de toque, como o duplo toque para zoom
        return super.onTouchEvent(event);
    }

    private void scrollToBottom() {
        // Rola o ScrollView para a parte inferior
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private void smoothScrollToBottom(final int scrollSpeed) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollBy(0, scrollSpeed);
                handler.postDelayed(this, 50); // Ajuste o atraso conforme necessário
            }
        };

        // Iniciar a rolagem suave
        handler.postDelayed(runnable, 0); // Iniciar imediatamente
    }


    private int getDistance(MotionEvent event) {
        int dx = (int) (event.getX(0) - event.getX(1));
        int dy = (int) (event.getY(0) - event.getY(1));
        return (int) (Math.sqrt(dx * dx + dy * dy));
    }
}
