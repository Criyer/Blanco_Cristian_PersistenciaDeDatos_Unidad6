package com.example.navigation_blancocristian

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ListMealsActivity : AppCompatActivity() {

    private lateinit var dbHelper: MealsDbHelper
    private lateinit var adapter: MealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ativity_list_meals)

        dbHelper = MealsDbHelper(this)

        val ivBack = findViewById<ImageView>(R.id.ivBackList)
        val listView = findViewById<ListView>(R.id.listViewAllMeals)
        val btnSeeDetails = findViewById<Button>(R.id.btnListSeeDetails)

        ivBack.setOnClickListener { finish() }

        adapter = MealAdapter(this, dbHelper.getAllMeals())
        listView.adapter = adapter

        btnSeeDetails.setOnClickListener {
            val selected = adapter.getSelectedMeal()
            if (selected == null) {
                Toast.makeText(this, getString(R.string.select_meal_first), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("meal", selected)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.updateData(dbHelper.getAllMeals())
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
