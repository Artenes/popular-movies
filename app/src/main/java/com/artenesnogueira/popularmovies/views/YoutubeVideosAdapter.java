package com.artenesnogueira.popularmovies.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artenesnogueira.popularmovies.R;
import com.artenesnogueira.popularmovies.models.YoutubeVideo;

import java.util.ArrayList;
import java.util.List;

/**
 * The adapter that displays a list of videos from youtube
 */
public class YoutubeVideosAdapter extends RecyclerView.Adapter<YoutubeVideosAdapter.YoutubeVideoViewHolder> {

    private List<YoutubeVideo> videos = new ArrayList<>(0);
    private final OnVideoClicked videoClickedCallback;
    private final Context context;

    public YoutubeVideosAdapter(OnVideoClicked videoClickedCallback, Context context) {
        this.videoClickedCallback = videoClickedCallback;
        this.context = context;
    }

    /**
     * Set the new data set and notify the adapter of the changes
     *
     * @param videos the new list of videos
     */
    public void setData(@NonNull List<YoutubeVideo> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public YoutubeVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item, parent, false);
        return new YoutubeVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YoutubeVideoViewHolder holder, int position) {
        holder.countTextView.setText(context.getString(R.string.trailer_count, (position + 1)));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    /**
     * Interface for when an item is clicked
     */
    public interface OnVideoClicked {

        void onVideoClicked(YoutubeVideo video);

    }

    /**
     * The view holder for this adapter
     */
    class YoutubeVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView countTextView;

        YoutubeVideoViewHolder(View itemView) {
            super(itemView);
            countTextView = itemView.findViewById(R.id.tv_trailer_count);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            videoClickedCallback.onVideoClicked(videos.get(getAdapterPosition()));
        }

    }

}
