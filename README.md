# PixelMixer
15-tile Android Game

# unSplash SDK
https://github.com/unsplash/unsplash-photopicker-android

# Game development references
https://ssaurel.medium.com/developing-a-15-puzzle-game-of-fifteen-in-java-dfe1359cc6e3

# Known Issues/Limitations
## Portrait Only
- The rotation of the screen results in a very bad looking UI. I've locked the app into portrait
  mode for now.
- There are limits on image size. The app won't load bitmaps over an arbitrary size of 86936160 bytes.
  It will display an error message if the bitmap is too large
- Only tiles directly adjacent to the empty tile can be dragged. It is not possible to move multiple
  tiles at one time
- Drag/Drop is not constrained. So the image can be dragged in a non linear fashion. It can still only be dropped on the empty tile
