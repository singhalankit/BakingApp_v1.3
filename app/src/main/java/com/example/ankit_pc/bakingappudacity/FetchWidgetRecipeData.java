package com.example.ankit_pc.bakingappudacity;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ANKIT_PC on 08-03-2018.
 */

public class FetchWidgetRecipeData extends AsyncTask<Void,Void,ArrayList<Recipe>>
{
ArrayList<Recipe> mRecipes = new ArrayList<Recipe>();

    @Override

    protected ArrayList<Recipe> doInBackground(Void... voids) {
         ArrayList<Recipe> RecipeList = new ArrayList<Recipe>();
        try {
            String json = NetworkUtils.getResponseFromHttpUrl();
            RecipeList = RecipeJsonUtils.getRecipesFromJson(json);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return RecipeList;
    }


    @Override
    protected void onPostExecute(ArrayList<Recipe> recipes) {
        super.onPostExecute(recipes);
        mRecipes=recipes;
    }
}
