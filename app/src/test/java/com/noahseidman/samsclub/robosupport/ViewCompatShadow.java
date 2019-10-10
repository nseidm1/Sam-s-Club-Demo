package com.noahseidman.samsclub.robosupport;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowView;

@Implements(ViewCompat.class)
public class ViewCompatShadow extends ShadowView {
    public void __constructor__(Context context) {
        // no-op
    }

    public void __constructor__(Context context, AttributeSet attrs) {
        // no-op
    }

    public void __constructor__(Context context, AttributeSet attrs, int defStyleAttr) {
        // no-op
    }

    public static void __staticInitializer__() {

    }

    @Implementation
    public static void setOnApplyWindowInsetsListener(@NonNull View v,
                                                      final OnApplyWindowInsetsListener listener) {
    }
}

