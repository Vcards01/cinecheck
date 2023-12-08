package br.edu.ifsp.scl.cinecheck.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var genre: String,
    var score: Int,
    var cover: String,
)