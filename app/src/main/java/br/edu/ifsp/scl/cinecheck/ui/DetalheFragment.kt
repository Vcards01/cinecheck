package br.edu.ifsp.scl.cinecheck.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.scl.cinecheck.R
import br.edu.ifsp.scl.cinecheck.data.Movie
import br.edu.ifsp.scl.cinecheck.databinding.FragmentDetailsBinding
import br.edu.ifsp.scl.cinecheck.viewmodel.MovieViewModel
import com.google.android.material.snackbar.Snackbar


class DetalheFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    lateinit var movie: Movie

    lateinit  var nameEditText: EditText
    lateinit var genreEditText: Spinner
    lateinit var scoreEditText: EditText
    lateinit var coverEditText: EditText

    lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun getPosition(genre: String): Int {
        val genresArray = resources.getStringArray(R.array.genres)
        for ((index, value) in genresArray.withIndex()){
            if (value.equals(genre)){
                return index
            }
        }
        return -1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEditText = binding.commonLayout.editTextName
        scoreEditText = binding.commonLayout.editTextScore
        genreEditText = binding.commonLayout.editSpinnerGenre
        coverEditText = binding.commonLayout.editTextCover

        val idMovie = requireArguments().getInt("idMovie")

        viewModel.getMovieById(idMovie)

        viewModel.movie.observe(viewLifecycleOwner) { result ->
            result?.let {
                movie = result
                nameEditText.setText(movie.name)
                scoreEditText.setText(movie.score.toString())
                genreEditText.setSelection(getPosition(movie.genre))
                coverEditText.setText(movie.cover)
            }
        }

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.detalhe_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_updateMovie -> {

                        movie.name= nameEditText.text.toString()
                        movie.score=scoreEditText.text.toString().toInt()
                        movie.genre= (genreEditText.selectedView as TextView).text.toString()
                        movie.cover= coverEditText.text.toString()

                        viewModel.update(movie)

                        Snackbar.make(binding.root, "Filme alterado", Snackbar.LENGTH_SHORT).show()

                        findNavController().popBackStack()
                        true
                    }
                    R.id.action_deleteMovie ->{
                        viewModel.delete(movie)

                        Snackbar.make(binding.root, "Filme apagado", Snackbar.LENGTH_SHORT).show()

                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

}