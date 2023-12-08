package br.edu.ifsp.scl.cinecheck.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.cinecheck.data.Movie
import br.edu.ifsp.scl.cinecheck.databinding.TileMovieBinding
import com.bumptech.glide.Glide;

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(),
    Filterable {

    var listener: MovieListener?=null

    var movieList = ArrayList<Movie>()
    var movieListFilterable = ArrayList<Movie>()

    private lateinit var binding: TileMovieBinding

    fun updateList(newList: List<Movie> ){
        movieList = newList as ArrayList<Movie>
        movieListFilterable = movieList
        notifyDataSetChanged()
    }

    fun setClickListener(listener: MovieListener)
    {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {

        binding = TileMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return  MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.nameVH.text = movieListFilterable[position].name
        holder.genreVH.text = movieListFilterable[position].genre
        holder.scoreVH.text = movieListFilterable[position].score.toString() + " ⭐"
        var context = holder.imghVH.context
        var url = "https://blog.springshare.com/wp-content/uploads/2010/02/nc-md.gif"
        if(movieListFilterable[position].cover.isNotEmpty()){
            url = movieListFilterable[position].cover
        }
        Glide.with(context).load(url).into(holder.imghVH);
    }

    override fun getItemCount(): Int {
        return movieListFilterable.size
    }

    inner class MovieViewHolder(view: TileMovieBinding): RecyclerView.ViewHolder(view.root)
    {
        val nameVH = view.nameTv
        val genreVH = view.genreTv
        val scoreVH = view.scoreTv
        val imghVH = view.imgIv

        init {
            view.root.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

    }

    interface MovieListener
    {
        fun onItemClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                movieListFilterable = if(p0.toString().isEmpty())
                    movieList
                else {
                    val resultList = ArrayList<Movie>()
                    for (row in movieList)
                        if (row.name.lowercase().contains(p0.toString().lowercase()))
                            resultList.add(row)
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = movieListFilterable
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                movieListFilterable = p1?.values as ArrayList<Movie>
                notifyDataSetChanged()
            }

        }
    }

}