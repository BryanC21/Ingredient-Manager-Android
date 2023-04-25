package com.sjsu.hackathon.ingredient_manager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjsu.hackathon.ingredient_manager.data.model.Recipe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeList extends Fragment {

    private View rootView;
    public RecipeList() {
        // Required empty public constructor
    }
    public static RecipeList newInstance() {
        RecipeList fragment = new RecipeList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView =  inflater.inflate(R.layout.fragment_recipe_list, container, false);

        if (getArguments() != null) {

            ArrayList<Recipe> recipeList = getArguments().getParcelableArrayList("recipes");
            Log.d("returned from GPT-3", String.valueOf(recipeList.size()));
            RecyclerView recyclerView = rootView.findViewById(R.id.recipe_list_recycler_view);
            RecipeListAdapter adapter = new RecipeListAdapter(recipeList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
        }

        return rootView;
    }
}