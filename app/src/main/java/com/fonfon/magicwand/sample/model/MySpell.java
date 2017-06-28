package com.fonfon.magicwand.sample.model;

import com.fonfon.magicwand.model.Move;
import com.fonfon.magicwand.model.Spell;
import com.fonfon.magicwand.sample.R;

import java.util.ArrayList;

public final class MySpell extends Spell {

  private int imageId;

  private MySpell(int imageId, ArrayList<Move> moves) {
    super(moves);
    this.imageId = imageId;
  }

  public int getImageId() {
    return imageId;
  }

  public static ArrayList<Spell> getSpells() {
    ArrayList<Spell> spells = new ArrayList<>();

    //----------
    ArrayList<Move> spell1 = new ArrayList<>();
    spell1.add(new Move(Move.NONE, Move.NONE, Move.UP));
    spells.add(new MySpell(R.drawable.ic_up_arrow, spell1));

    //----------
    ArrayList<Move> spell2 = new ArrayList<>();
    spell2.add(new Move(Move.RIGHT, Move.NONE, Move.NONE));
    spell2.add(new Move(Move.NONE, Move.NONE, Move.UP));
    spells.add(new MySpell(R.drawable.ic_right_up, spell2));

    //----------
    ArrayList<Move> spell3 = new ArrayList<>();
    spell3.add(new Move(Move.NONE, Move.NONE, Move.UP));
    spell3.add(new Move(Move.RIGHT, Move.NONE, Move.NONE));
    spells.add(new MySpell(R.drawable.ic_turn_right, spell3));

    //----------
    ArrayList<Move> spell4 = new ArrayList<>();
    spell4.add(new Move(Move.RIGHT, Move.NONE, Move.NONE));
    spell4.add(new Move(Move.NONE, Move.NONE, Move.DOWN));
    spell4.add(new Move(Move.LEFT, Move.NONE, Move.NONE));
    spell4.add(new Move(Move.NONE, Move.NONE, Move.UP));
    spells.add(new MySpell(R.drawable.ic_rotating, spell4));

    return spells;
  }

}
