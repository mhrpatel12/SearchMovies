package com.mihir.searchmovies.view;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mihir.searchmovies.R;
import com.mihir.searchmovies.databinding.ViewholderMovieBinding;
import com.mihir.searchmovies.model.Movie;

/**
 * Created by mihir on 26-06-2019.
 */

public class MovieAdapter extends PagedListAdapter<Movie, MovieAdapter.MyViewHolder> {

    MovieAdapter() {
        super(Movie.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewholderMovieBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.viewholder_movie, parent, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.binding.setModel(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ViewholderMovieBinding binding;

        MyViewHolder(ViewholderMovieBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

    }
}
