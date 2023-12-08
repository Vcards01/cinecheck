package br.edu.ifsp.scl.cinecheck.repository

import androidx.lifecycle.LiveData
import br.edu.ifsp.scl.cinecheck.data.Movie
import br.edu.ifsp.scl.cinecheck.data.MovieDAO

class MovieRepository (private val movieDAO: MovieDAO) {

    suspend fun insert(movie: Movie){
        movieDAO.insert(movie)
    }

    suspend fun update(movie: Movie){
        movieDAO.update(movie)
    }

    suspend fun delete(movie: Movie){
        movieDAO.delete(movie)
    }

    fun getAllMovies(): LiveData<List<Movie>> {
        return movieDAO.getAllMovies()
    }

    fun getMovieById(id: Int): LiveData<Movie>{
        return movieDAO.getMovieById(id)
    }

}