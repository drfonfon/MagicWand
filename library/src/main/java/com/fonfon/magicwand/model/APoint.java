package com.fonfon.magicwand.model;

import android.hardware.SensorManager;

import java.util.Arrays;

/**
 * Accelerometer point
 */
public class APoint {

  private float[] values;
  private long time;

  public APoint(float[] values, long time) {

    //Устройство держится экраном в верх, в таком положении нормальное ускорение по оси z - 9.8
    //Вычитаем g из z значения, чтобы все значения были приближенны к 0 - для удобства вычислений

    this.values = values;
    this.time = time;

    //TODO
    this.values[2] = this.values[2] - SensorManager.GRAVITY_EARTH;

    this.values[0] = this.values[0] * 10000;
    this.values[1] = this.values[1] * 10000;
    this.values[2] = this.values[2] * 10000;
  }

  /**
   * @return point time
   */
  public long getTime() {
    return time;
  }

  /**
   * @return point accelerometer values
   */
  public float[] getValues() {
    return values;
  }

  /**
   * Вычитание из значений текущей точки значений другой точки
   *
   * @param point - другая точка
   */
  public void minus(APoint point) {
    if(point == null)
      return;
    values[0] -= point.values[0];
    values[1] -= point.values[1];
    values[2] -= point.values[2];
  }

  /**
   * Приведение значений ниже порогового к 0
   * Reduction of values below threshold to 0
   *
   * @param value - значение порога
   */
  public void threshold(int value) {
    values[0] = Math.abs(values[0]) > value ? values[0] : 0;
    values[1] = Math.abs(values[1]) > value ? values[1] : 0;
    values[2] = Math.abs(values[2]) > value ? values[2] : 0;
  }

  @Override
  public String toString() {
    return time + ": " + Arrays.toString(values);
  }

}
