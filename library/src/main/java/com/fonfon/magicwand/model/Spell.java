package com.fonfon.magicwand.model;

import java.util.ArrayList;
import java.util.List;

public class Spell {

  private List<Move> moves;

  public Spell(List<Move> moves) {
    this.moves = moves;
  }

  /**
   * @return current movies
   */
  public List<Move> getMoves() {
    return moves;
  }

  /**
   * @param moves new movies
   */
  public void setMoves(List<Move> moves) {
    this.moves = moves;
  }

  /**
   * Вычисление подходящего спелла
   * @param moves current movies
   * @param spells list of all spells
   * @return most valid spell
   */
  public static Spell calculateValidSpell(List<Move> moves, List<Spell> spells) {
    if (moves == null || spells == null)
      return null;

    ArrayList<Float> spellsPercents = new ArrayList<>();
    for (Spell spell : spells)
      spellsPercents.add(getSpellHitPercent(spell, moves));

    int index = 0;
    for (int i = 1; i < spellsPercents.size(); i++) {
      if (spellsPercents.get(i) > spellsPercents.get(index))
        index = i;
    }

    if (spellsPercents.get(index) > 0.0f)
      return spells.get(index);

    return null;
  }

  /**
   * Вычисление индекса подходящего спелла
   * @param moves current movies
   * @param spells list of all spells
   * @return most valid spell
   */
  public static int calculateValidSpellIndex(List<Move> moves, List<Spell> spells) {
    if (moves == null || spells == null)
      return -1;

    List<Float> spellsPercents = new ArrayList<>();
    for (Spell spell : spells)
      spellsPercents.add(getSpellHitPercent(spell, moves));

    int index = 0;
    for (int i = 1; i < spellsPercents.size(); i++) {
      if (spellsPercents.get(i) > spellsPercents.get(index))
        index = i;
    }

    if (spellsPercents.get(index) > 0.0f)
      return index;

    return -1;
  }

  /**
   * Вычисление % вхождения текущего спелла в список движений
   *
   * @param spell  спелл
   * @param points список движений
   * @return процент
   */
  private static float getSpellHitPercent(Spell spell, List<Move> points) {
    int item = 0;
    boolean eq;
    int count = 0;
    for (Move move : spell.moves) {
      eq = false;
      for (int i = item; i < points.size(); i++) {
        if (move.partialEquals(points.get(i))) {
          eq = true;
          item = i + 1;
          count++;
          break;
        }
      }
      if (!eq) {
        break;
      }
    }

    float res = 0.00f;
    if (count == spell.moves.size()) {
      res = (float) count / (float) points.size() * 100f;
    }

    return res;
  }
}
