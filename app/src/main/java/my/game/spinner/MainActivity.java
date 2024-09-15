package my.game.spinner;

import android.os.Bundle;
import androidx.annotation.Nullable;

public class MainActivity extends FullscreenGameActivity {
    static {
        System.loadLibrary("spinner");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}