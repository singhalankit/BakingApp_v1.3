package com.example.ankit_pc.bakingappudacity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ANKIT_PC on 07-03-2018.
 */

public class FullscreenPlayerActivity extends AppCompatActivity {

    private RecipeStep step;
    private Recipe recipe;
    private ArrayList<Recipe> recipes;
    private boolean destroyVideo = false;
    @BindView(R.id.videoPlayerFullscreen_1)  SimpleExoPlayerView exoPlayerView;
    Long mcurrent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.full_screen_player_activity);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            step = savedInstanceState.getParcelable(RecipeStepDetailFragment.ARG_STEP);
            recipes = savedInstanceState.getParcelableArrayList(RecipesMainFragment.TAG_RECIPES);
            recipe = savedInstanceState.getParcelable(RecipeStepListActivity.TAG_RECIPE);
        //    mcurrent = savedInstanceState.getLong("current");

        } else {
            Intent intent = getIntent();
            if (intent.hasExtra(RecipeStepListActivity.TAG_RECIPE))
            {
                recipe = intent.getParcelableExtra(RecipeStepListActivity.TAG_RECIPE);
               // mcurrent = intent.getLongExtra("current",00);
            }
            if (intent.hasExtra(RecipesMainFragment.TAG_RECIPES)) {
                //mcurrent = intent.getLongExtra("current",00);
                recipes = intent.getParcelableArrayListExtra(RecipesMainFragment.TAG_RECIPES);
            }
            if (intent.hasExtra(RecipeStepDetailFragment.ARG_STEP)) {
                //mcurrent = intent.getLongExtra("current",00);
                step = intent.getParcelableExtra(RecipeStepDetailFragment.ARG_STEP);
                setTitle(step.getShortDescription());
            }
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ){
            destroyVideo = false;
            Intent upIntent = NavUtils.getParentActivityIntent(this);
            upIntent.putExtra(RecipeStepDetailFragment.ARG_STEP, step);
            upIntent.putExtra(RecipeStepListActivity.TAG_RECIPE, recipe);
            upIntent.putParcelableArrayListExtra(RecipesMainFragment.TAG_RECIPES, recipes);
            NavUtils.navigateUpTo(this, upIntent);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RecipeStepListActivity.TAG_RECIPE, recipe);
        outState.putParcelableArrayList(RecipesMainFragment.TAG_RECIPES, recipes);
        outState.putParcelable(RecipeStepDetailFragment.ARG_STEP, step);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        step = savedInstanceState.getParcelable(RecipeStepDetailFragment.ARG_STEP);
        recipes = savedInstanceState.getParcelableArrayList(RecipesMainFragment.TAG_RECIPES);
        recipe = savedInstanceState.getParcelable(RecipeStepListActivity.TAG_RECIPE);
    }


    @Override
    protected void onResume(){
        super.onResume();
        if(ExoPlayerVideoHandler.getInstance().getPlayer() != null) {
            exoPlayerView.setPlayer(ExoPlayerVideoHandler.getInstance().getPlayer());
            ExoPlayerVideoHandler.getInstance().getPlayer().seekTo(mcurrent);
        }
        else
            ExoPlayerVideoHandler.getInstance().prepareExoPlayerForUri(getApplicationContext(), Uri.parse(step.getVideoURL()), exoPlayerView);
        ExoPlayerVideoHandler.getInstance().goToForeground();
    }

    @Override
    public void onBackPressed(){
        destroyVideo = true;
        super.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mcurrent = ExoPlayerVideoHandler.getInstance().getPlayer().getCurrentPosition();
    }

    @Override
    protected void onPause(){
        super.onPause();
        ExoPlayerVideoHandler.getInstance().goToBackground();
    }




}
