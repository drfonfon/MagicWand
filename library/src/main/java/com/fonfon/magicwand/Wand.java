package com.fonfon.magicwand;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.fonfon.magicwand.model.APoint;
import com.fonfon.magicwand.model.Spell;
import com.fonfon.magicwand.model.Move;

import java.util.LinkedList;
import java.util.List;

public class Wand implements SensorEventListener {

  private static final int threshold = 25;

  private SensorManager sensorManager;
  private Sensor accelerometer;

  private List<APoint> points = new LinkedList<>();
  private List<Move> moves = new LinkedList<>();
  private int generateTime = 0;
  private boolean isTap = false;
  private LowPassFilterSmoothing filter;

  public Wand(Context context) {
    sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    filter = new LowPassFilterSmoothing();
    //нормальное значение фильтра 0.5
    //для определения движения требуются сильно отфильтрованные движения
    filter.setTimeConstant(100f);
  }

  /**
   * Register sensor listener
   * Получаемые сенсором значения должны постоянно фильтроваться
   * Лучше всего, если значения фильтра уже сгладятся к моменту записи движения
   */
  public void startSensorListener() {
    sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
  }

  /**
   * Unregister sensor listener
   */
  public void stopSensorListener() {
    sensorManager.unregisterListener(this);
  }

  /**
   * Start listen movies
   */
  public void startMovesListen() {
    points.clear();
    moves.clear();
    generateTime = 0;
    isTap = true;
  }

  /**
   * Stop listen movies
   */
  public void stopMovesListen() {
    isTap = false;
    if (points.size() > 10) {
      calculatePoints();
      calculateMovies();
    }
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    if (event.sensor == accelerometer) {
      float[] values = filter.addSamples(event.values);
      if (isTap) {
        points.add(new APoint(values, generateTime));
        generateTime++;
      }
    }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    //TODO
  }

  /**
   * Вычисление точек
   */
  private void calculatePoints() {
    //Полученные значения обычно не близки к 0 (чаще всего -3)
    //Вычитаем из всех чисел первое значение, и приводим все значения к 0
    //Пример [3, 3.1, 3.01] -> [0, 0.1, 0.01]
    for (int i = 1; i < points.size(); i++)
      points.get(i).minus(points.get(0));
    points.get(0).minus(points.get(0));

    //Из за сглаживания фильтра, на промежутках считывания, значения в массиве постоянно растут или убывают
    //Далее идет "опускание" кривой к 0

    // Вычисление тангенсов кривых (берутся по наибольшему отклонению -> последние значения)
    //  c/ |
    //  /  |a
    //  _&)_
    //    b
    // tg& = a/b
    APoint lastPoint = points.get(points.size() - 1);
    float[] a = new float[]{
        lastPoint.getValues()[0] / lastPoint.getTime(),
        lastPoint.getValues()[1] / lastPoint.getTime(),
        lastPoint.getValues()[2] / lastPoint.getTime()
    };

    //сглаживание отклонения знчения
    // a = b*tg&
    for (int i = 1; i < points.size(); i++) {
      points.get(i).getValues()[0] -= points.get(i).getTime() * a[0];
      points.get(i).getValues()[1] -= points.get(i).getTime() * a[1];
      points.get(i).getValues()[2] -= points.get(i).getTime() * a[2];

      //Фильтрация значений
      //до 15 могут попадаться случайные значения-помехи

      points.get(i).threshold(threshold);
    }
  }

  /**
   * Вычисление движений
   */
  private void calculateMovies() {
    //преобразование точки в движения
    for (int i = 0; i < points.size(); i++)
      moves.add(new Move(points.get(i)));

    //удаление одинаковых движений
    int index = 1;
    do {
      if (moves.get(index).eqMove(moves.get(index - 1))) {
        moves.remove(index - 1);
      } else {
        if (moves.get(index).isNone()) {
          moves.remove(index);
        } else {
          index++;
        }
      }
    } while (index < moves.size());

    if (moves.get(0).isNone())
      moves.remove(0);
  }

  /**
   * @return calculated accelerometer points
   */
  public List<APoint> getPoints() {
    return points;
  }

  /**
   * @return calculated movies
   */
  public List<Move> getMoves() {
    return moves;
  }

  /**
   * @param spells list of all Spells
   * @return most valid spell
   */
  public Spell calculateValidSpell(List<Spell> spells) {
    return Spell.calculateValidSpell(moves, spells);
  }

  /**
   * @param spells list of all Spells
   * @return most valid spell index
   */
  public int calculateValidSpellIndex(List<Spell> spells) {
    return Spell.calculateValidSpellIndex(moves, spells);
  }
}
