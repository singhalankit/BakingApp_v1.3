package com.example.ankit_pc.bakingappudacity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ANKIT_PC on 04-03-2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>
{

    private ArrayList<Recipe> recipes;
    private Context activity_context;

    //SharedPreferences sharedPreferences =
   // SharedPreferences sharedPreferences = activity_context.getSharedPreferences("MyPref", MODE_PRIVATE);
    //SharedPreferences.Editor editor = sharedPreferences.edit();

    class RecipeViewHolder extends RecyclerView.ViewHolder{
       // @BindView(R.id.recipe_TextView)
        TextView recipeText;
        //@BindView(R.id.recipe_ImageView)
        ImageView recipeImage;
        RecipeViewHolder(View view){
            super(view);
            recipeImage = view.findViewById(R.id.recipe_ImageView);
            recipeText = view.findViewById(R.id.recipe_TextView);
            //ButterKnife.bind(this, view);
        }
    }


    public RecipeAdapter(Context applicationContext){
        this.activity_context = applicationContext;


    }

    public RecipeAdapter(ArrayList<Recipe> recipes){
        setRecipes(recipes);
    }

    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("Entered into Adapter","onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card_view, parent, false);
        return new RecipeViewHolder(v);    }

    @Override
    public void onBindViewHolder(RecipeAdapter.RecipeViewHolder holder, int position) {
        Log.v("Entered into Adapter","onBindViewHolder");
        final Recipe recipe = recipes.get(position);
        String imageUrl = recipe.getImage();
        if(imageUrl != null && !imageUrl.isEmpty()){
            Glide.with(activity_context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_load)
                    .into(holder.recipeImage);
        }

        holder.recipeText.setText(recipe.getName());

        holder.recipeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(v.getContext(), RecipeStepListActivity.class);
                intent.putExtra(RecipeStepListActivity.TAG_RECIPE, recipe);
                intent.putParcelableArrayListExtra(RecipesMainFragment.TAG_RECIPES,recipes);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        if(recipes != null)
            return recipes.size();
        else
            return 0;

    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        this.notifyDataSetChanged();
    }

}
