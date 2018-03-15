package com.example.ankit_pc.bakingappudacity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by ANKIT_PC on 08-03-2018.
 */

public class RecipeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Intent intent = new Intent(context, RecipeWidgetService.class);

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        rv.setRemoteAdapter(R.id.recipe_widget_ListView, intent);
        rv.setEmptyView(R.id.recipe_widget_ListView, R.id.recipes_empty_TextView);


       // Intent ingredientsIntent = new Intent(context, IngredientsList.class);
        //Intent mainIntent = new Intent();
        // Set the action for the intent.
        // When the user touches a particular view, it will have the effect of
        // broadcasting TOAST_ACTION.
        //toastIntent.setAction(StackWidgetProvider.TOAST_ACTION);
        //ingredientsIntent.putExtra("recipe", mainIntent.getExtras());
       // intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        //PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, ingredientsIntent,
          //      PendingIntent.FLAG_UPDATE_CURRENT);
      //  PendingIntent toastPendingIntent = PendingIntent.getActivity(context,0,ingredientsIntent,PendingIntent.FLAG_UPDATE_CURRENT);
       // rv.setPendingIntentTemplate(R.id.recipe_widget_GridView, toastPendingIntent);



        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    static public void onUpdateRecipes(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


}
