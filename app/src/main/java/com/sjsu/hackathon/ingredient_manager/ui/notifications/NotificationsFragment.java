package com.sjsu.hackathon.ingredient_manager.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sjsu.hackathon.ingredient_manager.CheckListAdapter;
import com.sjsu.hackathon.ingredient_manager.IngredientListAdapter;
import com.sjsu.hackathon.ingredient_manager.R;
import com.sjsu.hackathon.ingredient_manager.controller.RecipeController;
import com.sjsu.hackathon.ingredient_manager.data.listener.RecipeListener;
import com.sjsu.hackathon.ingredient_manager.data.model.Ingredient;
import com.sjsu.hackathon.ingredient_manager.data.model.Recipe;
import com.sjsu.hackathon.ingredient_manager.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.Date;

public class NotificationsFragment extends Fragment implements RecipeListener {

    private FragmentNotificationsBinding binding;
    private String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DatabaseReference mDatabase;

    private Button submit;

    private RecipeController recipeController;

    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //NotificationsViewModel notificationsViewModel =
        //        new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recipeController = new RecipeController(getContext(), this);

        mDatabase = FirebaseDatabase.getInstance().getReference("dev").child(user).child("ingredients");
        submit = binding.button2;
        addIngredientEventListener(mDatabase);

        final TextView textView = binding.textNotifications;
        textView.setText("Select Ingredients: ");
        //notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    private void addIngredientEventListener(DatabaseReference mPostReference) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Use adapter to update the scroll list with the new data
                try {
                    RecyclerView recyclerView = binding.checkListIng;
                    CheckListAdapter adapter = new CheckListAdapter(dataSnapshot);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ArrayList<String> selected = adapter.getCheckedItems();
                            ArrayList<Ingredient> ingredients = new ArrayList<>();
                            if(selected.size() == 0){
                                Toast.makeText(getContext(), "Please select at least one ingredient", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("Selected", selected.toString());
                                //Make a call to ChatGPT with the ingredients
                                for(int i = 0; i < selected.size(); i++){
                                    //TODO very bad, fix this, string to ingredient
                                    ingredients.add(new Ingredient(selected.get(i), 1.0, "none", "none", new Date(), new Date(), "none", "none", "none"));
                                }
                                binding.getRoot().findViewById(R.id.notifications_loading).setVisibility(View.VISIBLE);
//                                Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                                recipeController.getRecipe(ingredients, 2);
                            }
                        }
                    });

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                System.out.println(databaseError.toException());
            }
        };
        mPostReference.addValueEventListener(postListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public void onDataSuccess(String reason) {
        Log.d("Recipe good?", reason);
    }

    @Override
    public void onDataFail(String reason) {
        Log.d("Recipe Failed!", reason);
    }

    @Override
    public void onGetSuccess(ArrayList<Recipe> recipeList) {
        Log.d("Recipe success?", "Yes");
        Log.d("Recipe", recipeList.toString());
        //Navigate to the recipe list fragment


        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("recipes", recipeList);

        NavOptions navOptions = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setPopUpTo(navController.getCurrentDestination().getId(), false)
                .build();
        navController.navigate(R.id.action_navigation_notifications_to_fragment_recipe_list, bundle, navOptions);
        binding.getRoot().findViewById(R.id.notifications_loading).setVisibility(View.INVISIBLE);
    }
}