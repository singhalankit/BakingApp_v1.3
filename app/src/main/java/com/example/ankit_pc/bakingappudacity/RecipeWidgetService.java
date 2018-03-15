package com.example.ankit_pc.bakingappudacity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ANKIT_PC on 08-03-2018.
 */

public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(this.getApplicationContext());
    }
}


class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private Context mContext;
    ArrayList<Recipe> mRecipes = new ArrayList<Recipe>();
    RecipeIngredient recipeIngredient;
    Recipe mrecipe;
    Recipe selectedRecipe;
    RecipeRemoteViewsFactory(Context context){
        this.mContext = context;
    }







    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {


        SharedPreferences sp = mContext.getSharedPreferences("MyPref",Context.MODE_PRIVATE);

        FetchWidgetRecipeData recipeData = new FetchWidgetRecipeData();
        try {
            recipeData.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        for (int i=0;i<mRecipes.size();i++)
        {

            mrecipe = mRecipes.get(i);
            if(mrecipe.getName().equals(sp.getString("recipe_name","Nutella Pie")))
            {

                recipeIngredient = mRecipes.get(i).getIngredients()[i];
                selectedRecipe = mRecipes.get(i);
            }
        }


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (recipeIngredient != null)
            return selectedRecipe.getIngredients().length;
        else
            return 0;
    }

    @Override
    public RemoteViews getViewAt(int j) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredients_adapter);
        RecipeIngredient ri = selectedRecipe.getIngredients()[j];
        //Recipe recipe = mRecipes.get(i);
        rv.setTextViewText(R.id.widgetNutrient,ri.getIngredient());
        rv.setTextViewText(R.id.widgetQuantity,ri.getQuantity());
        rv.setTextViewText(R.id.widgetUnit,ri.getMeasure());
        //Intent intent = new Intent(mContext, IngredientsList.class);

       // PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        //rv.setOnClickPendingIntent(R.id.recipe_TextView, pendingIntent);
        //return rv;


      //  Intent fillInIntent = new Intent();
       // fillInIntent.putExtra("recipe", recipe);
        //fillInIntent.putExtra("position",i);
       // fillInIntent.putExtras(extras);
        // Make it possible to distinguish the individual on-click
        // action of a given item
        //rv.setOnClickFillInIntent(R.id.recipe_TextView, fillInIntent);
        return  rv;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int j) {
        return j;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }



    public class FetchWidgetRecipeData extends AsyncTask<Void,Void,ArrayList<Recipe>>
    {


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




}
