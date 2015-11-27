package com.neusoft.eenie.order.controller.main.fragments.introducevp;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.neusoft.eenie.order.R;
import com.neusoft.eenie.order.controller.main.MainActivity;
import com.neusoft.eenie.order.utiles.SharePreferencesTool;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroduceThreeFragment extends IntroduceBaseFragment {

    Button btn_comein;
    View contentView;
    AnimatorSet animationSet;

    SharePreferencesTool sptool;


    public IntroduceThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.fragment_introduce_three, container, false);
        sptool = new SharePreferencesTool(getContext());
        initView(contentView);
        initAnimation();

        return contentView;
    }

    private void initView(View rootView) {
        btn_comein = (Button) rootView.findViewById(R.id.btn_comein);
        btn_comein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFirst(sptool.KEY_FIRST);
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });

    }

    public void setFirst(String key) {
        sptool.setBooleanValue(key, false);
    }




    public void initAnimation() {
        animationSet = new AnimatorSet();
        animationSet.setDuration(500);
        animationSet.playTogether(
                ObjectAnimator.ofFloat(btn_comein, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(btn_comein, "translationY", 100, 0)
        );
    }

    public void showAnimation() {
        animationSet.start();
    }


}
