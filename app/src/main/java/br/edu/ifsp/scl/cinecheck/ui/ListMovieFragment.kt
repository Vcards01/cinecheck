package br.edu.ifsp.scl.cinecheck.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.cinecheck.R
import br.edu.ifsp.scl.cinecheck.adapter.MovieAdapter
import br.edu.ifsp.scl.cinecheck.databinding.FragmentListMoviesBinding
import br.edu.ifsp.scl.cinecheck.viewmodel.MovieViewModel

class ListMovieFragment : Fragment(){

    private var _binding: FragmentListMoviesBinding? = null

    private val binding get() = _binding!!

    lateinit var movieAdapter: MovieAdapter

    lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListMoviesBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener { findNavController().navigate(R.id.action_listMoviesFragment_to_registerFragment) }

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.main_menu, menu)

                val searchView = menu.findItem(R.id.action_search).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        movieAdapter.filter.filter(p0)
                        return true
                    }

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }


    private fun configureRecyclerView()
    {

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        viewModel.allMovies.observe(viewLifecycleOwner) { list ->
            list?.let {
                movieAdapter.updateList(list)
            }
        }

        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        movieAdapter = MovieAdapter()
        recyclerView.adapter = movieAdapter

        val listener = object : MovieAdapter.MovieListener {
            override fun onItemClick(pos: Int) {
                val c = movieAdapter.movieListFilterable[pos]

                val bundle = Bundle()
                bundle.putInt("idMovie", c.id)

                findNavController().navigate(
                    R.id.action_listMoviesFragment_to_detailsFragment,
                    bundle
                )

            }
        }
        movieAdapter.setClickListener(listener)
           }

}


