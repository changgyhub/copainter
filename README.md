# Multiplayer-Painter

A Java CoPainter using SWING.

### Notes
* This CoPainter looks better in Mac OS, and other Unix based OS. The GUI looks a bit stranged in windows.
* I finish this CoPainter in my sophomore year, it is just for fun.
* I am considering using Javascript or GacUI(C++) to rewrite it.

###Set Up Guide:
####I. Compile using
```Bash
  cd src
  javac CoPainter.java
```
####II. Run with
```Bash
  java CoPainter
```

####III. Set up IP and port<br>
* For the host, it will automatically detect your IP, what you need to do is to select a port.<br>
* For the clients, you need to provide the host IP and port you want to connect to.<br>
<img src="https://github.com/irsisyphus/pictures/raw/master/Multiplayer-Painter/Port_1.png" width = "350" height = "180" alt="Host and Port" align=center />

####IV: Paint!
* For both host and clinets, you can customize color and brush size. The `Color` button allows you to choose whatever color you like, and the `More` buttom enables you to input the exact size and RGB color.
* Both host and clinets can save their work, but only host can load.
* Only host can clear the screen. When host leaves, the clients will be forced to exit. A pop up window will remind the host and the clients to save their work. 
<img src="https://github.com/irsisyphus/pictures/raw/master/Multiplayer-Painter/Painter_1.png" alt="CoPainter" align=center />
