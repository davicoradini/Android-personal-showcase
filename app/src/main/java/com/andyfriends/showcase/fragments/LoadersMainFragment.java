package com.andyfriends.showcase.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andyfriends.showcase.R;
import com.skyfishjy.library.RippleBackground;

/**
 * Main fragment class for demonstrating loaders
 */
public class LoadersMainFragment extends Fragment {

    public LoadersMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null != container) container.removeAllViews();

        return inflater.inflate(R.layout.fragment_loaders_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initiateLoaders();
    }

    private void initiateLoaders() {
        final RippleBackground rb1 = (RippleBackground) getView().findViewById(R.id.rippleBackground1);
        rb1.startRippleAnimation();
        final RippleBackground rb2 = (RippleBackground) getView().findViewById(R.id.rippleBackground2);
        rb2.startRippleAnimation();

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb1.isRippleAnimationRunning()) rb1.stopRippleAnimation();
                else rb1.startRippleAnimation();
            }
        });

        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb2.isRippleAnimationRunning()) rb2.stopRippleAnimation();
                else rb2.startRippleAnimation();
            }
        });
    }
}
