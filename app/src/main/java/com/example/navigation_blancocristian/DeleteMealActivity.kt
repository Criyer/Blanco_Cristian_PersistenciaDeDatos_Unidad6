package com.example.navigation_blancocristian

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DeleteMealActivity : AppCompatActivity() {

    private lateinit var dbHelper: MealsDbHelper
    private lateinit var adapter: MealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_meal)

        dbHelper = MealsDbHelper(this)

        val ivBack = findViewById<ImageView>(R.id.ivBackDelete)
        val listView = findViewById<ListView>(R.id.listViewMeals)
        val btnDelete = findViewById<Button>(R.id.btnDeleteAction)

        ivBack.setOnClickListener { finish() }

        adapter = MealAdapter(this, dbHelper.getAllMeals())
        listView.adapter = adapter

        btnDelete.setOnClickListener {
            val selected = adapter.getSelectedMeal()
            if (selected == null) {
                Toast.makeText(this, getString(R.string.select_meal_first), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val rows = dbHelper.deleteMeal(selected.id)
            if (rows > 0) {
                Toast.makeText(this, getString(R.string.meal_deleted_successfully), Toast.LENGTH_SHORT).show()
                adapter.updateData(dbHelper.getAllMeals())
            } else {
                Toast.makeText(this, getString(R.string.error_saving), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
