package com.count_out.framework

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.count_out.framework.datastore.SettingsSourceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestDataStore {
    private val dataStore = mock<DataStore<Preferences>>()
    private val dataSource = SettingsSourceImpl(dataStore)
    private val preferences = mock<Preferences>()

//    @Test @Order(1)
//    fun testGetSettingSpeechDescr() = runTest {
//        whenever(preferences[keySpeechDescr]).thenReturn(false)
//        whenever(dataStore.data).thenReturn(flowOf(preferences))
//        Assertions.assertEquals(false, dataSource.getSettingSpeechDescr().first())
//    }
//    @ExperimentalCoroutinesApi
//    @Test @Order(2)
//    fun testSaveSettingSpeechDescr() = runTest {
//        val newValue = true
//        whenever(preferences[keySpeechDescr]).thenReturn(newValue)
//        dataSource.saveSettingSpeechDescr(newValue)
//        whenever(dataStore.data).thenReturn(flowOf(preferences))
//        Assertions.assertEquals(newValue, dataSource.getSettingSpeechDescr().first())
//    }
//    @Test @Order(3)
//    fun testGetSettingBleAddress() = runTest {
//        whenever(preferences[keyAddress]).thenReturn("")
//        whenever(dataStore.data).thenReturn(flowOf(preferences))
//        Assertions.assertEquals("", dataSource.getBleAddress().first())
//    }
//    @Test
//    fun testSaveSettingBleAddress() = runTest {
//        val newValue = "address"
//        whenever(preferences[keyAddress]).thenReturn(newValue)
//        dataSource.saveBleAddress(newValue)
//        whenever(dataStore.data).thenReturn(flowOf(preferences))
//        Assertions.assertEquals(newValue, dataSource.getBleAddress().first())
//    }
//    @Test
//    fun testGetSettingBleName() = runTest {
//        whenever(preferences[keyName]).thenReturn("")
//        whenever(dataStore.data).thenReturn(flowOf(preferences))
//        Assertions.assertEquals("", dataSource.getBleName().first())
//    }
//    @ExperimentalCoroutinesApi
//    @Test
//    fun testSaveSettingBleName() = runTest {
//        val newValue = "name"
//        whenever(preferences[keyName]).thenReturn(newValue)
//        dataSource.saveBleName(newValue)
//        whenever(dataStore.data).thenReturn(flowOf(preferences))
//        Assertions.assertEquals(newValue, dataSource.getBleName().first())
//    }
}