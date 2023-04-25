package com.sjsu.hackathon.ingredient_manager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjsu.hackathon.ingredient_manager.data.handler.RecipeHandler;
import com.sjsu.hackathon.ingredient_manager.data.model.Recipe;
import com.sjsu.hackathon.ingredient_manager.databinding.FragmentFavoritesBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {
    private FragmentFavoritesBinding binding;
    private View rootView;

    private RecipeHandler recipeHandler;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recipeHandler = new RecipeHandler(this.getActivity());
        rootView =  inflater.inflate(R.layout.fragment_favorites, container, false);

        try {
            RecyclerView recyclerView = rootView.findViewById(R.id.favorites_list);
            RecyclerView.ItemDecoration itemDecoration = new
                    DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
            List<Recipe> recipes =  recipeHandler.getAll();
            List<FavoriteItem> h = new ArrayList<>();
            recipes.stream().iterator().forEachRemaining(recipe -> {
                h.add(new FavoriteItem(recipe));
            });

            System.out.println(h);
            FavoriteListAdapter adapter = new FavoriteListAdapter(h);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        } catch (Exception e) {
            System.out.println(e);
        }

        // Inflate the layout for this fragment
        return rootView;
    }
}