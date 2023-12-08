package br.edu.ifsp.scl.cinecheck.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface MovieDAO {
    @Insert
    suspend fun insert(movie: Movie)

    @Update
    suspend fun update (movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * FROM movie ORDER BY name")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE id=:id")
    fun getMovieById(id: Int): LiveData<Movie>


}