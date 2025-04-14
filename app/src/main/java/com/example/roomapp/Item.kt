package com.example.roomapp

import androidx.room.Entity
import androidx.room.PrimaryKey

// An entity class defines a table
// Each instance of this class represents a row in the DB TABLE
// Each variable of this class represents a column
// Entity creates a table in the database
@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int
)

