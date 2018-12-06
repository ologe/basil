package dev.olog.basil.presentation.base;

import android.content.Context;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import dev.olog.basil.R;

public class NoBackgroundBottomSheet extends BottomSheetDialog {

    public NoBackgroundBottomSheet(@NonNull Context context) {
        super(context);
    }

    public NoBackgroundBottomSheet(@NonNull Context context, int theme) {
        super(context, theme);
    }

    public NoBackgroundBottomSheet(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        getWindow().findViewById(R.id.design_bottom_sheet).setBackground(null);
    }
}
