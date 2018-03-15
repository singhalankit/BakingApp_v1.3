package com.example.ankit_pc.bakingappudacity;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipesMainFragment extends Fragment {

    public final static String TAG_RECIPES = "recipes";
     Context mContext;
    RecipeAdapter mRecipeAdapter;
    RecyclerView mRecycler;
    ArrayList<Recipe>  mRecipes = new ArrayList<Recipe>();


   // RecyclerView mRecipesRecyclerView;


    public RecipesMainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v("Entered in OnActivity","ActivityCreated");
        FetchRecipeData fetchRecipeData = new FetchRecipeData();
        fetchRecipeData.execute();
    }

 /*   @Override
    public void onStart() {
        super.onStart();
        Log.v("Entered in OnStart","OnStart");
        FetchRecipeData fetchRecipeData = new FetchRecipeData();
        fetchRecipeData.execute();
    }
*/
/*    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.v("Entered in OnCreate","OnCreate");
        //setContentView(R.layout.fragment_main);
        //ButterKnife.bind(mRecipesRecyclerView);
        mRecipeAdapter = new RecipeAdapter();
        FetchRecipeData fetchRecipeData = new FetchRecipeData();
        fetchRecipeData.execute();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

Log.v("Entered in OnCreateView","OnCreateView");
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecycler =  (RecyclerView) (rootView.findViewById(R.id.recipe_RecyclerView));
       mRecipeAdapter = new RecipeAdapter(mContext);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new GridLayoutManager(mContext, 2);
        mRecycler.setLayoutManager(mLayoutManager);
       return rootView;
    }


    public class FetchRecipeData extends AsyncTask<Void,Void,ArrayList<Recipe>>
    {


        @Override
        protected ArrayList<Recipe> doInBackground(Void... voids) {
           // ArrayList<Recipe> RecipeList = new ArrayList<Recipe>();
            try {
                String json = NetworkUtils.getResponseFromHttpUrl();
                mRecipes = RecipeJsonUtils.getRecipesFromJson(json);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }

            return mRecipes;
        }


        @Override
        protected void onPostExecute(ArrayList<Recipe> results) {
            //super.onPostExecute(movieParses);
            Log.v("Entered into execute ",Integer.toString(results.size()));

            if (results != null) {

                mRecipeAdapter.setRecipes(results);
                mRecycler.setAdapter(mRecipeAdapter);

            }
        }

    }




}
