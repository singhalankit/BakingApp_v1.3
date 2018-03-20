package com.example.ankit_pc.bakingappudacity;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.util.Util;

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
    long currentPosition=00;
    boolean mTwoPane;
    boolean playWhenReady = true;


    public RecipeStepDetailFragment() {
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        Log.v("EnteredinDetailFragment","onSavedInstance");
        //currentPosition = ExoPlayerVideoHandler.getInstance().getPlayer().getCurrentPosition();
        //playWhenReady = ExoPlayerVideoHandler.getInstance().getPlayer().getPlayWhenReady();
        outState.putLong("current",currentPosition);
        outState.putParcelable(RecipeStepDetailFragment.ARG_STEP, step);
        super.onSaveInstanceState(outState);
        Log.v("On SavedInstanceCurrent",Long.toString(currentPosition));
        outState.putBoolean("ready",playWhenReady);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
       // Log.v("On BeforeCreateCurrent",Long.toString(currentPosition));
        super.onCreate(savedInstanceState);
        Log.v("EnteredinDetailFragment","onCreate");
        Log.v("On JustCreateCurrent",Long.toString(currentPosition));
        if (savedInstanceState != null){
        currentPosition = savedInstanceState.getLong("current");
        step = savedInstanceState.getParcelable(RecipeStepDetailFragment.ARG_STEP);
        playWhenReady = savedInstanceState.getBoolean("ready");
        }


        if (getArguments().containsKey(ARG_STEP)) {
            step = getArguments().getParcelable(ARG_STEP);
            mTwoPane = getArguments().getBoolean("pane");
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(step.getShortDescription());
            }
        }



       /* if (getArguments().containsKey("current"))
        {
            currentPosition = getArguments().getLong("current");
        }



        if(getArguments().containsKey("state"))
        {
            playWhenReady = getArguments().getBoolean("state");
        }*/

        ExoPlayerVideoHandler.getInstance().play();
        Log.v("On CreateCurrent",Long.toString(currentPosition));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_step_detail_fragment, container, false);
        ButterKnife.bind(this,rootView);
        Log.v("EnteredinDetailFragment","onCreateView");
        /*if (savedInstanceState != null){
            currentPosition = savedInstanceState.getLong("current");
            playWhenReady = savedInstanceState.getBoolean("ready");
        }*/

        if (mTwoPane ) {
            ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE  && ! mTwoPane)
        {
            stepDesc.setVisibility(View.GONE);

        }


        fillUI(rootView,currentPosition);
        Log.v("On CreateViewCurrent",Long.toString(currentPosition));
        return rootView;
    }

    private void fillUI(View view, long position){
        if (step != null) {
            stepDesc.setText(step.getDescription());
            if(step.getVideoURL() != null && stepVideoPlayer != null){
                if(step.getVideoURL().isEmpty())
                {
                    stepVideoPlayer.setVisibility(View.GONE);
                    stepThumbnail.setVisibility(View.GONE);
                    stepDesc.setVisibility(View.VISIBLE);
                }
                else {
                    stepVideoPlayer.setVisibility(View.VISIBLE);
                    if (ExoPlayerVideoHandler.getInstance().getPlayer() != null) {
                        stepVideoPlayer.setPlayer(ExoPlayerVideoHandler.getInstance().getPlayer());

                    } else {
                        ExoPlayerVideoHandler.getInstance()
                                .prepareExoPlayerForUri(view.getContext(),
                                        Uri.parse(step.getVideoURL()), stepVideoPlayer);


                        ExoPlayerVideoHandler.getInstance().getPlayer().setPlayWhenReady(playWhenReady);

                        ExoPlayerVideoHandler.getInstance().getPlayer().seekTo(position);
                    }
                }

            } else {
                if(stepVideoPlayer != null) {
                    stepVideoPlayer.setVisibility(View.GONE);

                }
                String thumbnail = step.getThumbnailURL();
                if(!thumbnail.isEmpty() ){
                    stepThumbnail.setVisibility(View.GONE);
                    stepDesc.setVisibility(View.VISIBLE);
                    stepDesc.setText(step.getDescription());
                    Glide.with(getActivity())
                            .load(thumbnail)
                            .placeholder(R.drawable.ic_load)
                            .into(stepThumbnail);
                }else if(thumbnail == null) {
                    stepThumbnail.setVisibility(View.GONE);
                    stepDesc.setVisibility(View.VISIBLE);
                }
            }
        }
    }

  /*  @Override
    public void onPause() {
        super.onPause();
        if (ExoPlayerVideoHandler.getInstance().getPlayer() != null)
        {
            //playWhenReady = ExoPlayerVideoHandler.getInstance().getPlayer().getPlayWhenReady();
        //ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        ExoPlayerVideoHandler.getInstance().goToBackground();
       // ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        }
    }*/

    @Override
    public void onStop() {

        Log.v("EnteredinDetailFragment","onStop");
        if(Util.SDK_INT > 23) {
            Log.v("EnteredinDetailFragment","SDK greater than 23");
            currentPosition = ExoPlayerVideoHandler.getInstance().getPlayer().getCurrentPosition();
            playWhenReady = ExoPlayerVideoHandler.getInstance().getPlayer().getPlayWhenReady();
            ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        }
        super.onStop();
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.v("EnteredinDetailFragment", "onPause");
        if(Util.SDK_INT <= 23)
        {
            Log.v("EnteredinDetailFragment","SDK less than 23");
            currentPosition = ExoPlayerVideoHandler.getInstance().getPlayer().getCurrentPosition();
            playWhenReady = ExoPlayerVideoHandler.getInstance().getPlayer().getPlayWhenReady();
            Log.v("On PauseCurrent",Long.toString(currentPosition));
            ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("EnteredinDetailFragment", "onStart");
        if(Util.SDK_INT > 23) {
            stepVideoPlayer.setVisibility(View.VISIBLE);
            Log.v("EnteredinDetailFragment", Long.toString(currentPosition));
            if (ExoPlayerVideoHandler.getInstance().getPlayer() != null) {
                //  ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
                Log.v("EnteredinDetailFragment", "player not null");
                stepVideoPlayer.setPlayer(ExoPlayerVideoHandler.getInstance().getPlayer());

            } else {
                Log.v("EnteredinDetailFragment", "player is null");
                ExoPlayerVideoHandler.getInstance()
                        .prepareExoPlayerForUri(getActivity(),
                                Uri.parse(step.getVideoURL()), stepVideoPlayer);
            }

            ExoPlayerVideoHandler.getInstance().getPlayer().seekTo(currentPosition);
            ExoPlayerVideoHandler.getInstance().getPlayer().setPlayWhenReady(playWhenReady);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("EnteredinDetailFragment", "onResume");
        if(Util.SDK_INT <= 23 ) {
            stepVideoPlayer.setVisibility(View.VISIBLE);
            //Log.v("EnteredinDetailFragment", "onResume");
            if (ExoPlayerVideoHandler.getInstance().getPlayer() != null) {
                //  ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
                Log.v("EnteredinDetailFragment", "player not null");
                stepVideoPlayer.setPlayer(ExoPlayerVideoHandler.getInstance().getPlayer());

            } else {
                Log.v("EnteredinDetailFragment", "player is null");
                ExoPlayerVideoHandler.getInstance()
                        .prepareExoPlayerForUri(getActivity(),
                                Uri.parse(step.getVideoURL()), stepVideoPlayer);
            }
            Log.v("On ResumeCurrent",Long.toString(currentPosition));
            ExoPlayerVideoHandler.getInstance().getPlayer().seekTo(currentPosition);
            ExoPlayerVideoHandler.getInstance().getPlayer().setPlayWhenReady(playWhenReady);
        }
    }

/*    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.v("EnteredinDetailFragment","onViewStateRestored");
        if (savedInstanceState != null) {
            step = savedInstanceState.getParcelable(ARG_STEP);
            //currentPosition = savedInstanceState.getLong("current");
            //playWhenReady = savedInstanceState.getBoolean("ready");
        }
    }*/

    /* @Override
    public void onResume() {
        super.onResume();
        if (ExoPlayerVideoHandler.getInstance().getPlayer() != null)
        {
        //ExoPlayerVideoHandler.getInstance().getPlayer().setPlayWhenReady(playWhenReady);
        ExoPlayerVideoHandler.getInstance().getPlayer().seekTo(currentPosition);
        }
    }*/
}
