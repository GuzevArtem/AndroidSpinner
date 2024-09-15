package my.game.spinner;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;

import androidx.annotation.Nullable;

import java.util.Objects;

import my.game.spinner.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends FullscreenActivity {

    private ActivityWelcomeBinding binding;
    private Handler backInvokedOnceResetHandler;
    private boolean backInvokedOnce;

    public WelcomeActivity() {
        this.binding = null;
        this.backInvokedOnce = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Button startButton = binding.welcomeButton;

        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            view.getContext().startActivity(intent);
            //Navigation.findNavController(view).navigate(R.id.main_activity);
        });
    }

    @Nullable
    @Override
    protected OnBackInvokedCallback createBackCallback() {
        return  () -> {
            if (WelcomeActivity.this.backInvokedOnce) {
                OnBackInvokedCallback parentCallback = super.createBackCallback();
                if (parentCallback != null) {
                    parentCallback.onBackInvoked();
                } else {
                    WelcomeActivity.this.finish();
                }
                return;
            }

            WelcomeActivity.this.backInvokedOnce = true;
            Toast.makeText(WelcomeActivity.this, "Back again to exit", Toast.LENGTH_SHORT).show();

            WelcomeActivity.this.backInvokedOnceResetHandler = new Handler(Objects.requireNonNull(Looper.myLooper()));
            WelcomeActivity.this.backInvokedOnceResetHandler.postDelayed(() -> WelcomeActivity.this.backInvokedOnce=false, 2000);
        };
    }
}