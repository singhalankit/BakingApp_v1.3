package com.example.ankit_pc.bakingappudacity;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ANKIT_PC on 07-03-2018.
 */

public class RecipeStepDetailFragment extends android.support.v4.app.Fragment {

    public static final String ARG_STEP = "step";
    private boolean destroyVideo = false;
    private RecipeStep step;

    @BindView(R.id.step_description)
    TextView stepDesc;
    @BindView(R.id.step_thumbnail)
    ImageView stepThumbnail;
    @BindView(R.id.step_video)
    SimpleExoPlayerView stepVideoPlayer;
    Long currentPosition;
    boolean mTwoPane;


    public RecipeStepDetailFragment() {
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        currentPosition = ExoPlayerVideoHandler.getInstance().getPlayer().getCurrentPosition();
        outState.putLong("current",currentPosition);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
        currentPosition = savedInstanceState.getLong("current");}

        if (getArguments().containsKey(ARG_STEP)) {
            step = getArguments().getParcelable(ARG_STEP);
            mTwoPane = getArguments().getBoolean("pane");
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(step.getShortDescription());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_step_detail_fragment, container, false);
        ButterKnife.bind(this,rootView);

        if (mTwoPane ) {
            ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        }


        if (savedInstanceState != null){
        currentPosition = savedInstanceState.getLong("current");}
        fillUI(rootView);
        return rootView;
    }

    private void fillUI(View view){
        if (step != null) {
            stepDesc.setText(step.getDescription());
            if(step.getVideoURL() != null && stepVideoPlayer != null){
                if(step.getVideoURL().isEmpty())
                    stepVideoPlayer.setVisibility(View.GONE);
                else {
                    stepVideoPlayer.setVisibility(View.VISIBLE);
                    if(ExoPlayerVideoHandler.getInstance().getPlayer() != null)
                        stepVideoPlayer.setPlayer(ExoPlayerVideoHandler.getInstance().getPlayer());
                    else
                        ExoPlayerVideoHandler.getInstance()
                                .prepareExoPlayerForUri(view.getContext(),
                                        Uri.parse(step.getVideoURL()), stepVideoPlayer);
                    ExoPlayerVideoHandler.getInstance().goToForeground();
                }
            } else {
                if(stepVideoPlayer != null)
                    stepVideoPlayer.setVisibility(View.GONE);
                String thumbnail = step.getThumbnailURL();
                if(!thumbnail.isEmpty()){
                    stepThumbnail.setVisibility(View.VISIBLE);
                    Glide.with(getActivity())
                            .load(thumbnail)
                            .placeholder(R.drawable.ic_load)
                            .into(stepThumbnail);
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ExoPlayerVideoHandler.getInstance().goToBackground();
    }

  /*  @Override
    public void onResume() {
        super.onResume();

        ExoPlayerVideoHandler.getInstance().getPlayer().seekTo(currentPosition);
    }*/
}
