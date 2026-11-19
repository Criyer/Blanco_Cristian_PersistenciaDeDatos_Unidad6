package com.example.navigation_blancocristian

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SearchByTitleActivity : AppCompatActivity() {

    private lateinit var dbHelper: MealsDbHelper
    private lateinit var adapter: MealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_by_title)

        dbHelper = MealsDbHelper(this)

        val ivBack = findViewById<ImageView>(R.id.ivBackSearch)
        val etQuery = findViewById<EditText>(R.id.etSearchQuery)
        val listView = findViewById<ListView>(R.id.listViewSearchResults)
        val btnSearch = findViewById<Button>(R.id.btnSearchAction)
        val btnSeeDetails = findViewById<Button>(R.id.btnSeeDetails)

        ivBack.setOnClickListener { finish() }

        adapter = MealAdapter(this, emptyList())
        listView.adapter = adapter

        btnSearch.setOnClickListener {
            val query = etQuery.text.toString().trim()
            if (query.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val results = dbHelper.getMealsByTitle(query)
            if (results.isEmpty()) {
                Toast.makeText(this, getString(R.string.no_meals_found), Toast.LENGTH_SHORT).show()
            }
            adapter.updateData(results)
        }

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

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
