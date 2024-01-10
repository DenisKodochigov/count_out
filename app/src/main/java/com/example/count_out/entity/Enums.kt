package com.example.count_out.entity

import com.example.count_out.R

enum class SortingBy {
    BY_NAME,
    BY_SECTION
}

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
    OTHER,
    NONE
}

enum class RoundType(val strId: Int){
    UP (R.string.work_up),
    OUT (R.string.work_out ),
    DOWN ( R.string.work_down );
}