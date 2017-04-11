package com.andyfriends.showcase.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.andyfriends.showcase.R;
import com.andyfriends.showcase.activities.MainActivity;

import java.util.Map;

/**
 * Created by davi on 20/03/17.
 * Abertura de telas, tratamento de menu, info do usuário no menu
 */

public class NavigationUtils {

    private static NavigationView navHeaderView;

    private NavigationUtils() {
    }

    public static void setNavHeaderView(NavigationView v) {
        navHeaderView = v;
    }

    /*public static void displayUserData(Context context) {
        User user = AppPreferences.getInstance(context).getUser();

//        ImageView userPicture = (ImageView) navHeaderView.findViewById(R.id.ivUserPicture);
        TextView userName = (TextView) navHeaderView.getHeaderView(0).findViewById(R.id.tvUserName);
        TextView userEmail = (TextView) navHeaderView.getHeaderView(0).findViewById(R.id.tvUserEmail);
        ImageView imageView = (ImageView) navHeaderView.getHeaderView(0).findViewById(R.id.ivUserPicture);

        userName.setText(user.getName());
        userEmail.setText(user.getEmail());

//        String url = user.getPhoto().getUrl();
//        Picasso.with(context).load(url).fit().centerCrop().into(imageView);
    }*/

    public static void toggleInternetActivity(MainActivity mainActivity, boolean active) {
        int v = active ? View.VISIBLE : View.GONE;

        if (null != mainActivity) mainActivity.findViewById(R.id.pgMain).setVisibility(v);
    }

    public static void openFragment(
            Context context,
            Class fragmentClass,
            Bundle bundle,
            boolean addToBackStack) {
        openFragment(context, fragmentClass, bundle, addToBackStack, null, null);
    }

    public static void openFragment(
            Context context,
            Class fragmentClass,
            boolean addToBackStack) {
        openFragment(context, fragmentClass, null, addToBackStack, null, null);
    }

    public static void openFragment(
            Context context,
            Class fragmentClass,
            Bundle bundle,
            boolean addToBackStack,
            Map<String, View> transitionNameViewPairs,
            Fragment exitingFragment) {

        try {
            Fragment fragment;
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();

            if (null != fragmentClass) {
                fragment = (Fragment) fragmentClass.newInstance();
                if (null != bundle) fragment.setArguments(bundle);

                FragmentTransaction ft = fragmentManager.beginTransaction();

                if (addToBackStack) {
                    ft.addToBackStack(fragmentClass.getName());
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                        && null != transitionNameViewPairs) {

                    Transition changeTransform = TransitionInflater.from(context).
                            inflateTransition(android.R.transition.move);
                    Transition explodeTransform = TransitionInflater.from(context).
                            inflateTransition(android.R.transition.move);

                    // Setup exit transition on first fragment
                    exitingFragment.setSharedElementReturnTransition(changeTransform);
                    exitingFragment.setExitTransition(explodeTransform);

                    // Setup enter transition on second fragment
                    fragment.setSharedElementEnterTransition(changeTransform);
                    fragment.setEnterTransition(explodeTransform);

                    for (Object o : transitionNameViewPairs.entrySet()) {
                        Map.Entry pair = (Map.Entry) o;

                        ft.addSharedElement((View) pair.getValue(), (String) pair.getKey());
                    }
                } else {
                    ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                }

                ft.replace(R.id.flMain, fragment);
                ft.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openActivity(Context context, Class newActivity, Bundle bundle, Uri data) {
        Intent intent = new Intent(context, newActivity);
        if (null != data) intent.setData(data);
        if (null != bundle) intent.putExtras(bundle);

        context.startActivity(intent);
    }

    public static void openActivity(Context context, Class newActivity, Bundle bundle, Uri data, int REQ_TAG) {
        Intent intent = new Intent(context, newActivity);
        if (null != data) intent.setData(data);
        if (null != bundle) intent.putExtras(bundle);

        ((AppCompatActivity) context).startActivityForResult(intent, REQ_TAG);
    }

    public static void openActivity(Context context, Class newActivity) {
        openActivity(context, newActivity, null, null);
    }

    public static void openActivity(Context context, Class newActivity, Bundle bundle) {
        openActivity(context, newActivity, bundle, null);
    }

    public static void setMenuSelected(int menuOptionId) {
        int size = navHeaderView.getMenu().size();

        for (int i = 0; i < size; i++) {
            MenuItem item = navHeaderView.getMenu().getItem(i);
            if (item.getItemId() == menuOptionId) {
                item.setChecked(true);
            }
            else {
                item.setChecked(false);
            }
        }
    }
}
