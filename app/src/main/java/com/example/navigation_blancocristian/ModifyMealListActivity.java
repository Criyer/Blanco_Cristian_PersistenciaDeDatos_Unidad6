package com.example.navigation_blancocristian;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ModifyMealListActivity extends AppCompatActivity {

    private MealsDbHelper dbHelper;
    private MealAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_list);

        dbHelper = new MealsDbHelper(this);

        ImageView ivBack = findViewById(R.id.ivBackModifyList);
        ListView listView = findViewById(R.id.listViewModifyMeals);
        Button btnSelect = findViewById(R.id.btnSelectMealToEdit);

        ivBack.setOnClickListener(v -> finish());

        adapter = new MealAdapter(this, dbHelper.getAllMeals());
        listView.setAdapter(adapter);

        btnSelect.setOnClickListener(v -> {
            Meal selected = adapter.getSelectedMeal();
            if (selected == null) {
                Toast.makeText(this, getString(R.string.select_meal_first), Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, EditMealActivity.class);
            intent.putExtra("meal", selected);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.updateData(dbHelper.getAllMeals());
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
