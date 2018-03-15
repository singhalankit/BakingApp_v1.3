package com.example.ankit_pc.bakingappudacity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ANKIT_PC on 08-03-2018.
 */

public class IngredientsList extends AppCompatActivity {
    Context mContext;
    private Recipe recipe = new Recipe();
    int position;

    @BindView(R.id.widget_ingredients_RecyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable("recipe");
            setupRecyclerView(recyclerView);
        } else if (intent.hasExtra("recipe")) {
            recipe = intent.getParcelableExtra("recipe");
            setupRecyclerView(recyclerView);
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("recipe", recipe);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        recipe = savedInstanceState.getParcelable("recipe");
        super.onRestoreInstanceState(savedInstanceState);
    }



    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        Log.v("Entered in setRecycler",Integer.toString(recipe.getSteps().length));
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager (mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(new WidgetIngredientsAdapter(recipe));
    }

}