package com.paricio.livestreamhighlights.ClipList.ClipListRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.paricio.livestreamhighlights.utils.CircleTransform;
import com.paricio.livestreamhighlights.utils.ClipUtils;
import com.paricio.livestreamhighlights.Model.Clip;
import com.paricio.livestreamhighlights.databinding.ClipItemBinding;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class ClipListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private View view;
    private final WeakReference<ClipItemListener> listener;
    private final ClipItemBinding clipBinding;

    ClipListViewHolder(ClipItemListener clipItemListener, ClipItemBinding binding) {
        super(binding.getRoot());
        this.view = itemView;
        this.listener = new WeakReference<>(clipItemListener);
        this.clipBinding = binding;
        view.setOnClickListener(this);

    }

    void bindClip(Clip clip) {
        Picasso.with(view.getContext()).load(clip.getThumbnails().getMedium()).transform(new RoundedCornersTransformation(10,1)).into(clipBinding.clipItemThumbnail);
        clipBinding.clipItemTitle.setText(clip.getTitle());
        clipBinding.clipItemBroadcaster.setText(clip.getBroadcaster().getDisplay_name());
        clipBinding.clipItemGame.setText(clip.getGame());
        clipBinding.clipItemDuration.setText(ClipUtils.clipDurationFormat(clip.getDuration()));
        clipBinding.executePendingBindings();
    }

    @Override
    public void onClick(View view) {
        listener.get().onClipItemClicked(getAdapterPosition());
    }
}
