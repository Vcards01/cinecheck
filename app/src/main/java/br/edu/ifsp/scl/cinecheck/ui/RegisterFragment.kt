package br.edu.ifsp.scl.cinecheck.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.scl.cinecheck.R
import br.edu.ifsp.scl.cinecheck.data.Movie
import br.edu.ifsp.scl.cinecheck.databinding.FragmentRegisterBinding
import br.edu.ifsp.scl.cinecheck.viewmodel.MovieViewModel
import com.google.android.material.snackbar.Snackbar


class RegisterFragment : Fragment(){
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.cadastro_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_saveMovie -> {
                        if (binding.commonLayout.editTextName.text.isEmpty()||(binding.commonLayout.editSpinnerGenre.selectedView as TextView).text.isEmpty()||binding.commonLayout.editTextScore.text.isEmpty()){
                            Toast.makeText(binding.commonLayout.editTextScore.context, "Preencha os campos", Toast.LENGTH_SHORT).show()
                        }else{
                            val name = binding.commonLayout.editTextName.text.toString()
                            val genre = (binding.commonLayout.editSpinnerGenre.selectedView as TextView).text.toString()
                            val score = binding.commonLayout.editTextScore.text.toString().toInt()
                            var cover = binding.commonLayout.editTextCover.text.toString()

                            val movie = Movie(0,name, genre, score, cover)

                            viewModel.insert(movie)

                            Snackbar.make(binding.root, "Filme inserido", Snackbar.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                            true
                        }

                        false
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

}
