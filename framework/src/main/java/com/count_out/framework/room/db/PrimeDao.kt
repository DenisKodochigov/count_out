package com.count_out.framework.room.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao interface PrimeDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insert(obj: T): Long
    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insert(obj: List<T>): List<Long>
    @Update fun update(obj: T): Int
    @Update fun update(obj: List<T>): Int
    @Delete fun delete(obj: T): Int
}