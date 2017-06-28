package com.fonfon.magicwand.sample.ui;

import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fonfon.magicwand.Wand;
import com.fonfon.magicwand.sample.R;
import com.fonfon.magicwand.sample.model.MySpell;

public final class MainActivity extends AppCompatActivity implements TapView.TapViewListener {

  private AppCompatImageView image;

  private MoveAdapter adapter;
  private Wand wand;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    wand = new Wand(this);

    ((TapView) findViewById(R.id.tapView)).setListener(this);
    image = (AppCompatImageView) findViewById(R.id.image);

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter = new MoveAdapter();
    recyclerView.setAdapter(adapter);
  }

  @Override
  protected void onResume() {
    super.onResume();
    wand.startSensorListener();
  }

  @Override
  protected void onPause() {
    super.onPause();
    wand.stopSensorListener();
  }

  @Override
  public void onStartTap() {
    wand.startMovesListen();
  }

  @Override
  public void onStopTap() {
    wand.stopMovesListen();

    adapter.clearItems();
    adapter.addItems(wand.getMoves());

    setSpell((MySpell) wand.calculateValidSpell(MySpell.getSpells()));
  }

  private void setSpell(MySpell spell) {
    if (spell != null) {
      VectorDrawableCompat drawable = VectorDrawableCompat.create(getResources(), spell.getImageId(), getTheme());
      if (drawable != null) {
        image.setImageDrawable(drawable.mutate());
        image.setVisibility(View.VISIBLE);
        image.postDelayed(new Runnable() {
          @Override
          public void run() {
            image.setVisibility(View.INVISIBLE);
          }
        }, 1000);
      }
    }
  }
}
