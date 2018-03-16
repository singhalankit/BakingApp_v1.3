package com.example.ankit_pc.bakingappudacity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.AdapterView;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ANKIT_PC on 06-03-2018.
 */

public class RecipeStepListActivity extends AppCompatActivity implements OnClickRecyclerView {

    public final static String TAG_RECIPE = "recipe";
    private boolean isTwoPane;
    private Recipe recipe;
    private RecipeStep recipeStep;
    private ArrayList<Recipe> recipes;
    Context mContext = getApplication();


    @BindView(R.id.include_recipe_step_list) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // SharedPreferences sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();

        setContentView(R.layout.activity_recipe_step_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.recipe_step_detail_container) != null) {
            isTwoPane = true;
        }

        Intent intent = getIntent();
        if (intent.hasExtra(RecipesMainFragment.TAG_RECIPES))
            recipes = intent.getParcelableArrayListExtra(RecipesMainFragment.TAG_RECIPES);

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(TAG_RECIPE);
           // editor.putString("recipe_name",recipe.getName());
            //editor.commit();
            setupRecyclerView(recyclerView);
        } else if (intent.hasExtra(TAG_RECIPE)) {
            recipe = intent.getParcelableExtra(TAG_RECIPE);
            //editor.putString("recipe_name",recipe.getName());
            //editor.commit();
            setupRecyclerView(recyclerView);
        }




    }



    @Override
        protected void onSaveInstanceState(Bundle outState) {
            outState.putParcelable(TAG_RECIPE, recipe);
            super.onSaveInstanceState(outState);
        }

        @Override
        protected void onRestoreInstanceState(Bundle savedInstanceState) {
            recipe = savedInstanceState.getParcelable(TAG_RECIPE);
            super.onRestoreInstanceState(savedInstanceState);
        }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        Log.v("Entered in setRecycler",Integer.toString(recipe.getSteps().length));

        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(new RecipeStepsAdapter(recipe, recipes, isTwoPane,mContext));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.putParcelableArrayListExtra(RecipesMainFragment.TAG_RECIPES, recipes);
                NavUtils.navigateUpTo(this, upIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(RecipeStep step, Context clickContext) {
        recipeStep = step;
        mContext = clickContext;
        Bundle arguments = new Bundle();
        arguments.putParcelable(RecipeStepDetailFragment.ARG_STEP, step);
        arguments.putBoolean("pane",isTwoPane);
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        fragment.setArguments(arguments);
        ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_step_detail_container, fragment)
                .commit();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT &&  mContext != null) {

            Intent intent = new Intent(this,RecipeStepDetailActivity.class);
            intent.putExtra(RecipeStepDetailFragment.ARG_STEP, recipeStep);
            intent.putExtra(RecipeStepListActivity.TAG_RECIPE, recipe);
            intent.putParcelableArrayListExtra(RecipesMainFragment.TAG_RECIPES,recipes);
            startActivity(intent);

        }

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE && mContext != null)
        {
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putExtra(RecipeStepDetailFragment.ARG_STEP, recipeStep);
            intent.putExtra(RecipeStepListActivity.TAG_RECIPE, recipe);
            intent.putParcelableArrayListExtra(RecipesMainFragment.TAG_RECIPES,recipes);
            startActivity(intent);
        }


        /*if ((newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) && mContext == null)

        {
            onCreate(null);
        }*/
        }

}
