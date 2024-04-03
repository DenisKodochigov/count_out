package com.example.count_out.entity

import com.example.count_out.R

enum class TypeText {
    NAME_SCREEN,
    NAME_SECTION,
    TEXT_IN_LIST,
    TEXT_IN_LIST_SMALL,
    EDIT_TEXT,
    EDIT_TEXT_TITLE,
    TEXT_IN_LIST_SETTING,
    NAME_SLIDER,
}
enum class SizeElement{
    HEIGHT_BOTTOM_BAR,
    SIZE_FAB,
    PADDING_FAB,
    OFFSET_FAB,
    HEIGHT_FAB_BOX
}
enum class Rainfall{
    SMALL,
    MEDIUM,
    LARGE
}
enum class UPDOWN {
    UP,
    DOWN,
    START,
    END
}
enum class TypeKeyboard{
    DIGIT,
    TEXT,
    PASS,
    OTHER,
    NONE
}
enum class Zone(val id: Int, var maxPulse: Int){
    EXTRASLOW( id = 1, maxPulse = 100),
    SLOW(id = 2, maxPulse = 120),
    MEDIUM(id = 3, maxPulse = 135),
    HIGH(id = 4, maxPulse = 160),
    MAX(id = 5, maxPulse = 180)
}

enum class RoundType(val strId: Int){
    UP (R.string.work_up),
    OUT (R.string.work_out),
    DOWN (R.string.work_down);
}
enum class GoalSet(val id: Int){
    DISTANCE(1),
    DURATION(2),
    COUNT(3),
    COUNT_GROUP(4),
}
enum class StopwatchState {
    Idle, Started, onPause, onStop
}

enum class StateRunning {
    Created, Started, Pause, Stopped
}