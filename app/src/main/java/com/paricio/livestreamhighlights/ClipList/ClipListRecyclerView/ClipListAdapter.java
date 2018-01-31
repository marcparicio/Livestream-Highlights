package com.paricio.livestreamhighlights.ClipList.ClipListRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paricio.livestreamhighlights.Model.Clip;
import com.paricio.livestreamhighlights.R;
import com.paricio.livestreamhighlights.databinding.ClipItemBinding;

import java.util.List;


public class ClipListAdapter extends RecyclerView.Adapter<ClipListViewHolder> {

    private List<Clip> clips;
    private ClipItemListener clipItemListener;

    public ClipListAdapter(List<Clip> clipList, ClipItemListener clipItemListener) {
        this.clips = clipList;
        this.clipItemListener = clipItemListener;
    }

    @Override
    public ClipListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ClipItemBinding clipItemBinding = ClipItemBinding.inflate(inflater,parent,false);
        return new ClipListViewHolder(clipItemListener,clipItemBinding);
    }

    @Override
    public void onBindViewHolder(ClipListViewHolder holder, int position) {
        Clip clip = clips.get(position);
        holder.bindClip(clip);
    }

    @Override
    public int getItemCount() {
        return clips.size();
    }

    public Clip getClipAtPosition(int position) {
        return clips.get(position);
    }


    public void refreshClipList(List<Clip> clips) {
        this.clips = clips;
        notifyDataSetChanged();
    }

    public void addClipsToList(List<Clip> clips) {
        this.clips.addAll(clips);
        notifyDataSetChanged();
    }

}
