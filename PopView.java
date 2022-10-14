package net.codebot.ragdoll;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class PopView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_view);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.widthPixels;

        getWindow().setLayout((int)(width * 0.4), (int)(height * 0.3));
    }
}
