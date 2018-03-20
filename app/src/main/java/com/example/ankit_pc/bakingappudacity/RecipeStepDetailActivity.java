package com.example.ankit_pc.bakingappudacity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by ANKIT_PC on 07-03-2018.
 */

public class RecipeStepDetailActivity extends AppCompatActivity {

    private Recipe recipe;
    private RecipeStep step;
    private RecipeStep prevStep;
    private RecipeStep nextStep;
    private ArrayList<Recipe> recipes;
    private RecipeStepDetailFragment fragment;


    @Nullable
    @BindView(R.id.previousButton)  Button prevButton;
    @Nullable
    @BindView(R.id.nextButton)  Button nextButton;
   // SimpleExoPlayerView viewExoPlayer;
    long currentPosition=00;
    boolean dplayWhenReady = true;
    boolean twoPane=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("EnteredinDetail","onCreate");
        setContentView(R.layout.recipe_step_detail_activity);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
       // viewExoPlayer = (SimpleExoPlayerView) findViewById(R.id.videoPlayerFullscreen);
       /* if (savedInstanceState != null) {
           // currentPosition = savedInstanceState.getLong("current");
            //dplayWhenReady = savedInstanceState.getBoolean("ready");
            step = savedInstanceState.getParcelable(RecipeStepDetailFragment.ARG_STEP);
        }*/
        //prevButton = (Button) findViewById(R.id.previousButton);
        //nextButton = (Button) findViewById(R.id.nextButton);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (savedInstanceState == null) {
            if (intent.hasExtra(RecipeStepListActivity.TAG_RECIPE))
                recipe = intent.getParcelableExtra(RecipeStepListActivity.TAG_RECIPE);
            if (intent.hasExtra(RecipesMainFragment.TAG_RECIPES))
                recipes = intent.getParcelableArrayListExtra(RecipesMainFragment.TAG_RECIPES);
            if (intent.hasExtra(RecipeStepDetailFragment.ARG_STEP))
                step = getIntent().getParcelableExtra(RecipeStepDetailFragment.ARG_STEP);
        } else {
            Log.v("EnteredinDetail","SavedInstancehasvalue in onCreate");
            step = savedInstanceState.getParcelable(RecipeStepDetailFragment.ARG_STEP);
            recipes = savedInstanceState.getParcelableArrayList(RecipesMainFragment.TAG_RECIPES);
            recipe = savedInstanceState.getParcelable(RecipeStepListActivity.TAG_RECIPE);
           // currentPosition = savedInstanceState.getLong("current");
            //dplayWhenReady = savedInstanceState.getBoolean("state");
        }


        /*if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ) {

            prevButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
        }*/
            //     startFullscreen(currentPosition);
        //} else {
        Log.v("Detailcurrent",Long.toString(currentPosition));

        if (step != null) {
            //ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
            //ExoPlayerVideoHandler.getInstance().play();
            checkPrevNext();
            changeFragment();
        }
        //}



    }

/*
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){

            prevButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
        }

        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

            prevButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        }

    }
*/


    /* private void startFullscreen(long position){
      //  Intent intent = new Intent(this, FullscreenPlayerActivity.class);
       // intent.putExtra(RecipeStepListActivity.TAG_RECIPE, recipe);
        //intent.putExtra(RecipeStepDetailFragment.ARG_STEP, step);
        //intent.putExtra("current",currentPosition);
        //intent.putParcelableArrayListExtra(RecipesMainFragment.TAG_RECIPES, recipes);
        //startActivity(intent);
        //viewExoPlayer.setPlayer

        position = currentPosition;


        if(ExoPlayerVideoHandler.getInstance().getPlayer() != null) {
            viewExoPlayer.setVisibility(View.VISIBLE);
            viewExoPlayer.setPlayer(ExoPlayerVideoHandler.getInstance().getPlayer());
            ExoPlayerVideoHandler.getInstance().getPlayer().seekTo(position);
        }
        else
            ExoPlayerVideoHandler.getInstance().prepareExoPlayerForUri(getApplicationContext(), Uri.parse(step.getVideoURL()), viewExoPlayer);
        ExoPlayerVideoHandler.getInstance().goToForeground();



    }*/

    private void changeFragment(){
        Bundle arguments = new Bundle();
        arguments.putParcelable(RecipeStepDetailFragment.ARG_STEP, step);
        arguments.putBoolean("pane",twoPane);
        //arguments.putLong("current",currentPosition);
        //arguments.putBoolean("state",dplayWhenReady);
        fragment = new RecipeStepDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_step_detail_container, fragment)
                .commit();
    }

@Optional
    private void checkPrevNext(){
        if(recipe != null) {
            RecipeStep[] steps = recipe.getSteps();
            int length = steps.length;

            prevButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);

            if (step.getId().equals(steps[0].getId())) {
                prevButton.setVisibility(View.GONE);
                nextStep = steps[1];
            } else if (step.getId().equals(steps[length - 1].getId())) {
                nextButton.setVisibility(View.GONE);
                prevStep = steps[length - 2];
            } else {
                for (int i = 1; i < (length - 1); i++) {
                    if (step.getId().equals(steps[i].getId())) {
                        prevStep = steps[i - 1];
                        nextStep = steps[i + 1];
                    }
                }
            }
        }
        /*if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !step.getVideoURL().isEmpty()){
            startFullscreen();
        } else {
            changeFragment();
        }*/
    }
    @Optional
    @OnClick(R.id.previousButton)
    void previousStep(View view){
        step = prevStep;
        //currentPosition = 00;
        //dplayWhenReady = true;
        //ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
       // ExoPlayerVideoHandler.getInstance().play();
        //currentPosition = ExoPlayerVideoHandler.getInstance().getPlayer().getCurrentPosition();
        checkPrevNext();
        changeFragment();
    }
    @Optional
    @OnClick(R.id.nextButton)
    void nextStep(View view){
        step = nextStep;
        //currentPosition = 00;
        //dplayWhenReady = true;
        //ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
       // ExoPlayerVideoHandler.getInstance().play();
        //currentPosition = ExoPlayerVideoHandler.getInstance().getPlayer().getCurrentPosition();
        checkPrevNext();
        changeFragment();
    }

/*    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //currentPosition = ExoPlayerVideoHandler.getInstance().getPlayer().getCurrentPosition();
        //dplayWhenReady = ExoPlayerVideoHandler.getInstance().getPlayer().getPlayWhenReady();
        Log.v("EnteredinDetail","onSavedInstance");
        Log.v("DetailCurrentonSaved",Long.toString(currentPosition));
        outState.putParcelable(RecipeStepListActivity.TAG_RECIPE, recipe);
        outState.putParcelableArrayList(RecipesMainFragment.TAG_RECIPES, recipes);
        outState.putParcelable(RecipeStepDetailFragment.ARG_STEP, step);
        outState.putBoolean("state",dplayWhenReady);
        outState.putLong("current",currentPosition);
    }*/

/*    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v("EnteredinDetail","onRestoreInstanceState");
        step = savedInstanceState.getParcelable(RecipeStepDetailFragment.ARG_STEP);
        recipes = savedInstanceState.getParcelableArrayList(RecipesMainFragment.TAG_RECIPES);
        recipe = savedInstanceState.getParcelable(RecipeStepListActivity.TAG_RECIPE);
        currentPosition = savedInstanceState.getLong("current");
        dplayWhenReady = savedInstanceState.getBoolean("state");
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.putExtra(RecipeStepListActivity.TAG_RECIPE, recipe);
                upIntent.putParcelableArrayListExtra(RecipesMainFragment.TAG_RECIPES, recipes);
                NavUtils.navigateUpTo(this, upIntent);
                ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
/*
    @Override
    public void onStop() {

        Log.v("EnteredinDetail","onStop");
        if(Util.SDK_INT > 23) {
            Log.v("EnteredinDetail","SDK greater than 23");
            currentPosition = ExoPlayerVideoHandler.getInstance().getPlayer().getCurrentPosition();
            dplayWhenReady = ExoPlayerVideoHandler.getInstance().getPlayer().getPlayWhenReady();
            ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        }
        super.onStop();
    }*/

/*    @Override
    public void onPause() {
        super.onPause();
        Log.v("EnteredinDetail", "onPause");
        if(Util.SDK_INT <= 23)
        {
            Log.v("EnteredinDetail","SDK less than 23");
            currentPosition = ExoPlayerVideoHandler.getInstance().getPlayer().getCurrentPosition();
            Log.v("EnteredinDetailPause",Long.toString(currentPosition));
            dplayWhenReady = ExoPlayerVideoHandler.getInstance().getPlayer().getPlayWhenReady();
           // Log.v("On PauseCurrent",Long.toString(currentPosition));
            //ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        }
    }*/

   /* @Override
    protected void onResume() {
        super.onResume();

        if(Util.SDK_INT <= 23 ) {

            Log.v("DetailonResume", Long.toString(currentPosition));
            checkPrevNext();
            changeFragment();
        }


    }*/
}
