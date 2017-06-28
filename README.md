# Magic Wand

[![](https://jitpack.io/v/drfonfon/MagicWand.svg)](https://jitpack.io/#drfonfon/MagicWand)

###Turn your phone into a magic wand!
The library is based on the accelerometer readings. 
To work correctly need to keep the phone screen up.

Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Add the dependency
```groovy
dependencies {
        compile 'com.github.drfonfon:MagicWand:1.0'
}
```

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

