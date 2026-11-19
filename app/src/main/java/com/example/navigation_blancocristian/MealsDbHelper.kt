package com.example.navigation_blancocristian

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MealsDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "meals.db"
        const val DATABASE_VERSION = 1

        const val TABLE_MEALS = "meals"
        const val COLUMN_ID = "id"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_MEALS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CATEGORY TEXT NOT NULL,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)

        // Insert initial sample meals so the database is not empty on fresh install
        insertSampleMeal(db, "Pasta", "Espaguetis a la carbonara", "Pasta italiana tradicional elaborada con huevos, queso pecorino, panceta y pimienta negra.")
        insertSampleMeal(db, "Carnes", "Hamburguesa gourmet", "Hamburguesa de ternera con queso cheddar, cebolla caramelizada, lechuga fresca y salsa especial.")
        insertSampleMeal(db, "Ensaladas", "Ensalada César", "Ensalada fresca con lechuga romana, pechuga de pollo a la plancha, picatostes, parmesano y salsa César.")
        insertSampleMeal(db, "Postres", "Tarta de queso casera", "Deliciosa tarta de queso horneada al estilo tradicional sobre base de galleta y sirope de frutos rojos.")
    }

    private fun insertSampleMeal(db: SQLiteDatabase, category: String, title: String, description: String) {
        val values = ContentValues().apply {
            put(COLUMN_CATEGORY, category)
            put(COLUMN_TITLE, title)
            put(COLUMN_DESCRIPTION, description)
        }
        db.insert(TABLE_MEALS, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MEALS")
        onCreate(db)
    }

    fun insertMeal(category: String, title: String, description: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CATEGORY, category)
            put(COLUMN_TITLE, title)
            put(COLUMN_DESCRIPTION, description)
        }
        return db.insert(TABLE_MEALS, null, values)
    }

    fun updateMeal(id: Long, category: String, title: String, description: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CATEGORY, category)
            put(COLUMN_TITLE, title)
            put(COLUMN_DESCRIPTION, description)
        }
        return db.update(TABLE_MEALS, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun deleteMeal(id: Long): Int {
        val db = writableDatabase
        return db.delete(TABLE_MEALS, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun getAllMeals(): List<Meal> {
        val meals = mutableListOf<Meal>()
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_MEALS,
            arrayOf(COLUMN_ID, COLUMN_CATEGORY, COLUMN_TITLE, COLUMN_DESCRIPTION),
            null,
            null,
            null,
            null,
            "$COLUMN_ID ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(COLUMN_ID))
                val category = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY))
                val title = it.getString(it.getColumnIndexOrThrow(COLUMN_TITLE))
                val description = it.getString(it.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                meals.add(Meal(id, category, title, description))
            }
        }
        return meals
    }

    fun getMealsByTitle(query: String): List<Meal> {
        val meals = mutableListOf<Meal>()
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_MEALS,
            arrayOf(COLUMN_ID, COLUMN_CATEGORY, COLUMN_TITLE, COLUMN_DESCRIPTION),
            "$COLUMN_TITLE LIKE ?",
            arrayOf("%$query%"),
            null,
            null,
            "$COLUMN_ID ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(COLUMN_ID))
                val category = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY))
                val title = it.getString(it.getColumnIndexOrThrow(COLUMN_TITLE))
                val description = it.getString(it.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                meals.add(Meal(id, category, title, description))
            }
        }
        return meals
    }

    fun getMealById(id: Long): Meal? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_MEALS,
            arrayOf(COLUMN_ID, COLUMN_CATEGORY, COLUMN_TITLE, COLUMN_DESCRIPTION),
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        cursor.use {
            if (it.moveToFirst()) {
                val category = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY))
                val title = it.getString(it.getColumnIndexOrThrow(COLUMN_TITLE))
                val description = it.getString(it.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                return Meal(id, category, title, description)
            }
        }
        return null
    }
}
