package net.codebot.ragdoll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MyCanvas myCanvas;
    private ArrayList<MyDoll> dolls = new ArrayList<>();
    private int currDoll;
    private ArrayList<ImageButton> dollBtns = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        myCanvas = findViewById(R.id.canvas);
        Human human = new Human(); // idx 0
        dolls.add(human);
        Tree tree = new Tree(); // idx 1
        dolls.add(tree);
        currDoll = 0;
        myCanvas.setDoll(dolls.get(currDoll));

        Button aboutBtn = findViewById(R.id.aboutBtn);
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PopView.class));
            }
        });

        Button resetBtn = findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dolls.get(currDoll).reset();
                myCanvas.invalidate();
            }
        });

        ImageButton dollBtn1 = findViewById(R.id.dollBtn1);
        dollBtns.add(dollBtn1);
        ImageButton dollBtn2 = findViewById(R.id.dollBtn2);
        dollBtns.add(dollBtn2);
        dollBtns.get(currDoll).setImageResource(R.drawable.btn_border);

        dollBtn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currDoll = 0;
                for (ImageButton btn : dollBtns) {
                    btn.setImageResource(android.R.color.transparent);
                }
                dollBtns.get(currDoll).setImageResource(R.drawable.btn_border);
                myCanvas.setDoll(dolls.get(currDoll));
            }
        });
        dollBtn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currDoll = 1;
                for (ImageButton btn : dollBtns) {
                    btn.setImageResource(android.R.color.transparent);
                }
                dollBtns.get(currDoll).setImageResource(R.drawable.btn_border);
                myCanvas.setDoll(dolls.get(currDoll));
            }
        });
    }
}
