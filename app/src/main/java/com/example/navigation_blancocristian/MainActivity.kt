package com.example.navigation_blancocristian

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnAddMeals).setOnClickListener {
            startActivity(Intent(this, AddMealsActivity::class.java))
        }

        findViewById<Button>(R.id.btnDeleteMeals).setOnClickListener {
            startActivity(Intent(this, DeleteMealActivity::class.java))
        }

        findViewById<Button>(R.id.btnListAllMeals).setOnClickListener {
            startActivity(Intent(this, ListMealsActivity::class.java))
        }

        findViewById<Button>(R.id.btnListMealsByTitle).setOnClickListener {
            startActivity(Intent(this, SearchByTitleActivity::class.java))
        }

        findViewById<Button>(R.id.btnModifyMeal).setOnClickListener {
            startActivity(Intent(this, ModifyMealListActivity::class.java))
        }
    }
}
