package my.game.spinner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.window.OnBackInvokedCallback;

import androidx.annotation.Nullable;

import my.game.spinner.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends FullscreenActivity {

    private ActivityWelcomeBinding binding;

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
        return () -> {
            new AlertDialog.Builder(WelcomeActivity.this)
                    .setTitle("Confirm")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", (DialogInterface dialog, int which) -> {
                        WelcomeActivity.this.finish();
                        System.exit(0); // TODO: better way to exit?
                    })
                    .setNegativeButton("No", null)
                    .show();
        };
    }
}