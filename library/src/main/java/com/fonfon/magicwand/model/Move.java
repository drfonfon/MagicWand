package com.fonfon.magicwand.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public class Move {

  public static final String NAME_RIGHT = "Right";
  public static final String NAME_LEFT = "Left";
  public static final String NAME_UP = "Up";
  public static final String NAME_DOWN = "Down";
  public static final String NAME_BACK = "Back";
  public static final String NAME_FORWARD = "Forward";

  public static final int NONE = 0;
  public static final int RIGHT = 1;
  public static final int LEFT = 2;
  public static final int UP = 3;
  public static final int DOWN = 4;
  public static final int FORWARD = 5;
  public static final int BACK = 6;

  @IntDef({NONE, UP, DOWN, LEFT, RIGHT, FORWARD, BACK})
  @Retention(RetentionPolicy.SOURCE)
  public @interface IMove {
  }

  @IMove
  private int[] moves;

  public Move(@IMove int rightLeft, @IMove int forwardBack, @IMove int upDown) {
    this.moves = new int[]{rightLeft, forwardBack, upDown};
  }

  public Move(APoint point) {
    moves = new int[]{
        point.getValues()[0] != 0 ? point.getValues()[0] > 0 ? RIGHT : LEFT : NONE,
        point.getValues()[1] != 0 ? point.getValues()[1] > 0 ? FORWARD : BACK : NONE,
        point.getValues()[2] != 0 ? point.getValues()[2] > 0 ? UP : DOWN : NONE,
    };
    if (moves[0] != NONE) {
      moves[1] = Math.abs(point.getValues()[0]) - Math.abs(point.getValues()[1]) > 3 ? NONE : moves[1];
      moves[2] = Math.abs(point.getValues()[0]) - Math.abs(point.getValues()[2]) > 3 ? NONE : moves[2];
    }
    if (moves[1] != NONE) {
      moves[0] = Math.abs(point.getValues()[1]) - Math.abs(point.getValues()[0]) > 3 ? NONE : moves[0];
      moves[2] = Math.abs(point.getValues()[1]) - Math.abs(point.getValues()[2]) > 3 ? NONE : moves[2];
    }
    if (moves[2] != NONE) {
      moves[0] = Math.abs(point.getValues()[2]) - Math.abs(point.getValues()[0]) > 3 ? NONE : moves[0];
      moves[1] = Math.abs(point.getValues()[2]) - Math.abs(point.getValues()[1]) > 3 ? NONE : moves[1];
    }
  }

  @Override
  public String toString() {
    return Arrays.toString(moves);
  }

  /**
   * Полное совпадение с другой точкой
   *
   * @param point - другая точка
   * @return - совпадают ли движения
   */
  public boolean eqMove(Move point) {
    return point != null &&
        point.moves[0] == moves[0] &&
        point.moves[1] == moves[1] &&
        point.moves[2] == moves[2];
  }

  /**
   * Частичное совпадение с другой точкой
   *
   * @param point - другая точка
   * @return - совпадают ли движения
   */
  public boolean partialEquals(Move point) {
    if(point == null) return false;
    boolean eqX = moves[0] == NONE || point.moves[0] == moves[0];
    boolean eqY = moves[1] == NONE || point.moves[1] == moves[1];
    boolean eqZ = moves[2] == NONE || point.moves[2] == moves[2];
    return eqX && eqY && eqZ;
  }

  /**
   * @return все движения пустые
   */
  public boolean isNone() {
    return moves[0] == NONE
        && moves[1] == NONE
        && moves[2] == NONE;
  }

  /**
   * @return значения активных движений через запятую
   */
  public String print() {
    String res = "";
    boolean z = false;
    if (moves[0] != NONE) {
      res += moves[0] == RIGHT ? NAME_RIGHT : NAME_LEFT;
      z = true;
    }
    if (moves[1] != NONE) {
      if (z) {
        res += ", ";
      }
      res += moves[1] == FORWARD ? NAME_FORWARD : NAME_BACK;
      z = true;
    }
    if (moves[2] != NONE) {
      if (z) {
        res += ", ";
      }
      res += moves[2] == UP ? NAME_UP : NAME_DOWN;
    }

    return res;
  }

  public int[] getMoves() {
    return moves;
  }
}
