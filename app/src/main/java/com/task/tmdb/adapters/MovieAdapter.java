package com.task.tmdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rdo.endlessrecyclerviewadapter.EndlessRecyclerViewAdapter;
import com.squareup.picasso.Picasso;
import com.task.tmdb.R;
import com.task.tmdb.beans.Movie;
import com.task.tmdb.interfaces.ItemClickListener;
import com.task.tmdb.services.MovieService;

import java.util.List;

/**
 * Created by HSM Roshan on 02/05/2018.
 */

public class MovieAdapter extends EndlessRecyclerViewAdapter<Movie, MovieAdapter.ViewHolder, MovieAdapter.FooterViewHolder> {

    Context mContext;

    ItemClickListener itemClickListener;

    public MovieAdapter(List<Movie> movies, Context context,  ItemClickListener itemClickListener) {
        super(movies, context);
        this.mContext = context;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.list_item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindDataViewHolder(ViewHolder holder, int position) {
        Movie movie = this.data.get(position);
        holder.tv_title.setText(movie.getTitle());
        holder.tv_date.setText(movie.getReleaseDate());

        Picasso.with(mContext).load(MovieService.BASE_IMAGE_URL_THUMB + movie.getBackdropPath()).into(holder.iv_thumb);

    }

    @Override
    public MovieAdapter.FooterViewHolder onCreateFooterViewHolder(ViewGroup parent, MovieAdapter.FooterViewHolder reusableFooterHolder) {
        if (reusableFooterHolder == null) {
            View view = inflater.inflate(R.layout.loading_footer_item, parent, false);
            return new FooterViewHolder(view);
        }
        return reusableFooterHolder;
    }

    @Override
    public void onBindFooterViewHolder(MovieAdapter.FooterViewHolder holder, int position) {
        holder.text.setText("Loading more movies...");
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final LinearLayout ll_list_item_movie;
        private final TextView tv_title, tv_date;
        private final ImageView iv_thumb;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_list_item_movie = (LinearLayout) itemView.findViewById(R.id.ll_list_item_movie);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            iv_thumb = (ImageView) itemView.findViewById(R.id.iv_thumb);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClickItem(data.get(getAdapterPosition()));
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        private final TextView text;

        public FooterViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.footer_text);
        }
    }

}
