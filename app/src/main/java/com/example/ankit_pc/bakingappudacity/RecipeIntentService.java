package com.example.ankit_pc.bakingappudacity;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by ANKIT_PC on 08-03-2018.
 */

public class RecipeIntentService extends IntentService {

    private static final String UPDATE_RECIPES = "com.example.ankit_pc.bakingappudacity.widget.action.UPDATE_RECIPES";
    public RecipeIntentService() {
        super("RecipeIntentService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();
            if (UPDATE_RECIPES.equals(action)) {
                handleActionUpdateRecipes();
            }
        }
    }

    private void handleActionUpdateRecipes() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidget.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.layout.recipe_widget);
        RecipeWidget.onUpdateRecipes(this, appWidgetManager, appWidgetIds);
    }

    }

