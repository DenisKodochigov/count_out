package com.example.count_out.data.datastore

import androidx.datastore.core.Serializer
import com.example.count_out.entity.bluetooth.BleDevSerializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object BleDevSerializer : Serializer<BleDevSerializable>{
    override val defaultValue: BleDevSerializable
        get() = BleDevSerializable()

    override suspend fun readFrom(input: InputStream): BleDevSerializable {
        return try {
            Json.decodeFromString(
                deserializer = BleDevSerializable.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: BleDevSerializable, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = BleDevSerializable.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}