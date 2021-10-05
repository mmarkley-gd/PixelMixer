package com.virtualtimetours.pixelmixer.data

import com.virtualtimetours.pixelmixer.ui.main.GameTile
import com.virtualtimetours.pixelmixer.ui.main.fragments.GameFragment

data class DragData(val list: List<GameTile>, val direction: GameFragment.Direction)
