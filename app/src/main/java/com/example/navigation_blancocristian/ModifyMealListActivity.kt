package com.example.navigation_blancocristian

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ModifyMealListActivity : AppCompatActivity() {

    private lateinit var dbHelper: MealsDbHelper
    private lateinit var adapter: MealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_list)

        dbHelper = MealsDbHelper(this)

        val ivBack = findViewById<ImageView>(R.id.ivBackModifyList)
        val listView = findViewById<ListView>(R.id.listViewModifyMeals)
        val btnSelect = findViewById<Button>(R.id.btnSelectMealToEdit)

        ivBack.setOnClickListener { finish() }

        adapter = MealAdapter(this, dbHelper.getAllMeals())
        listView.adapter = adapter

        btnSelect.setOnClickListener {
            val selected = adapter.getSelectedMeal()
            if (selected == null) {
                Toast.makeText(this, getString(R.string.select_meal_first), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, EditMealActivity::class.java)
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
