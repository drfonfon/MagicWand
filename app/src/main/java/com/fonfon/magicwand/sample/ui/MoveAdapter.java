package com.fonfon.magicwand.sample.ui;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fonfon.magicwand.model.Move;
import com.fonfon.magicwand.sample.R;

import java.util.ArrayList;
import java.util.List;

final class MoveAdapter extends RecyclerView.Adapter<MoveAdapter.Holder> {

  private ArrayList<Move> items = new ArrayList<>();

  @Override
  public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new Holder((AppCompatTextView) LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_text, parent, false));
  }

  @Override
  public void onBindViewHolder(Holder holder, int position) {
    ((AppCompatTextView) holder.itemView).setText(items.get(position).print());
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  void addItems(List<Move> items) {
    if (items != null) {
      this.items.addAll(items);
      notifyDataSetChanged();
    }
  }

  void clearItems() {
    this.items.clear();
  }

  class Holder extends RecyclerView.ViewHolder {
    Holder(AppCompatTextView v) {
      super(v);
    }
  }
}
