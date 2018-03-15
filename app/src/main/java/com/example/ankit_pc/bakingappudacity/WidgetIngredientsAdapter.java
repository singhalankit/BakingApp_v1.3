package com.example.ankit_pc.bakingappudacity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ANKIT_PC on 08-03-2018.
 */

public class WidgetIngredientsAdapter  extends RecyclerView.Adapter<WidgetIngredientsAdapter.IngredientsViewHolder> {

    private Recipe recipe;
    public WidgetIngredientsAdapter(Recipe recipe){
        this.recipe = recipe;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.widget_ingredients_adapter, parent, false);

        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
holder.ingredient = recipe.getIngredients()[position];
holder.IngredientsName.setText(holder.ingredient.getIngredient());
        holder.IngredientsUnit.setText(holder.ingredient.getMeasure());

        holder.IngredientsQuantity.setText(holder.ingredient.getQuantity());
    }

    @Override
    public int getItemCount() {
        return recipe.getIngredients().length;
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        TextView IngredientsName;
        TextView IngredientsQuantity;
        TextView IngredientsUnit;
        private RecipeIngredient ingredient;
        public IngredientsViewHolder(View itemView) {
            super(itemView);
            IngredientsName =itemView.findViewById(R.id.widgetNutrient);
            IngredientsQuantity = itemView.findViewById(R.id.widgetQuantity);
            IngredientsUnit = itemView.findViewById(R.id.widgetUnit);

        }
    }
}
