# Magic Wand

#####Turn your phone into a magic wand!
The library is based on the accelerometer readings. 
To work correctly need to keep the phone screen up.

## Example

Create custom spell class
```java
  class MySpell extends Spell {
    ...
  }
```


Add wand to project
```java

  interface TapViewListener {
    void onStartTap();
    void onStopTap();
  }
  
  class MainActivity extends AppCompatActivity implements TapView.TapViewListener {
    
    ...
  
    private Wand wand;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      ...
      wand = new Wand(this);
      ...
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
      Spell spell = wand.calculateValidSpell(MySpell.getSpells());
    }
  }

```

