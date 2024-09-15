package my.game.spinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.androidgamesdk.GameActivity;

public abstract class FullscreenGameActivity extends GameActivity {

    private OnBackInvokedCallback backCallback;

    private void enableImmersiveFullscreen() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false); // allow overlap with status & nav bars

        WindowInsetsControllerCompat windowInsetsController =  WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars()); // hide navigation and status bars

        // actual cutout will be processed later, but black bar on top is ugly as hell, so forcing layout in display cutout.
        getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS;

        // hide bar with action name
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Nullable
    protected OnBackInvokedCallback createBackCallback() {
        return () -> {
            Intent parentIntent = getParentActivityIntent();
            if (parentIntent == null || !navigateUpTo(parentIntent)) {
                finish();
            }
        };
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);

        enableImmersiveFullscreen();

        backCallback = createBackCallback();

        if (backCallback != null) {
            getOnBackInvokedDispatcher().registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT, backCallback);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (backCallback != null) {
            getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(backCallback);
            backCallback = null;
        }
    }
}
