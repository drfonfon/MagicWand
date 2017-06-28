package com.fonfon.magicwand.sample.ui;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public final class TapView extends AppCompatImageView {

  private TapViewListener listener;

  public TapView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        ViewCompat.animate(this).scaleX(1.2f).scaleY(1.2f).setDuration(200).start();
        if (listener != null) listener.onStartTap();
        break;
      case MotionEvent.ACTION_UP:
        ViewCompat.animate(this).scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
        if (listener != null) listener.onStopTap();
        break;
      case MotionEvent.ACTION_CANCEL:
        ViewCompat.animate(this).scaleX(1.0f).scaleY(1.0f).setDuration(200).start();
        if (listener != null) listener.onStopTap();
        break;
      default:
        break;
    }
    return true;
  }

  interface TapViewListener {
    void onStartTap();

    void onStopTap();
  }

  public void setListener(TapViewListener listener) {
    this.listener = listener;
  }
}
