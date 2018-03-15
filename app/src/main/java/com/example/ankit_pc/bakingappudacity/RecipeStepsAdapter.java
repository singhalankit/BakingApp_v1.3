package com.example.ankit_pc.bakingappudacity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ANKIT_PC on 06-03-2018.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.StepsViewHolder> {

    private Recipe recipe;
    private ArrayList<Recipe> recipes;
    private boolean isTwoPane;


    public RecipeStepsAdapter(Recipe recipe, ArrayList<Recipe> recipes, boolean isTwoPane) {
        this.recipe = recipe;
        this.recipes = recipes;
        this.isTwoPane = isTwoPane;
    }

    class StepsViewHolder extends RecyclerView.ViewHolder {
        private RecipeStep step;
       // @BindView(R.id.content)
        TextView mContentView;

        private StepsViewHolder(View view) {
            super(view);
            mContentView = view.findViewById(R.id.content);
        }
    }

    @Override
    public RecipeStepsAdapter.StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("Entered in StepAdapter","onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_step_list_content, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeStepsAdapter.StepsViewHolder holder, int position) {
        Log.v("Entered in StepAdapter","onBindViewHolder");
        holder.step = recipe.getSteps()[position];
        holder.mContentView.setText(holder.step.getShortDescription());

        holder.mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context =   v.getContext();
                if (isTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(RecipeStepDetailFragment.ARG_STEP, holder.step);
                    arguments.putBoolean("pane",isTwoPane);
                    RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
                    fragment.setArguments(arguments);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_step_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(context, RecipeStepDetailActivity.class);
                    intent.putExtra(RecipeStepDetailFragment.ARG_STEP, holder.step);
                    intent.putExtra(RecipeStepListActivity.TAG_RECIPE, recipe);
                    intent.putParcelableArrayListExtra(RecipesMainFragment.TAG_RECIPES,recipes);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.v("EnteredCountstepAdapter",Integer.toString(recipe.getSteps().length));
        if(recipe.getSteps() != null)
            return recipe.getSteps().length;
        else return 0;    }
}
