package com.example.count_out.data.datastore

import androidx.datastore.core.Serializer
import com.example.count_out.entity.BleDev
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object BleDevSerializer : Serializer<BleDev>{
    override val defaultValue: BleDev
        get() = BleDev()

    override suspend fun readFrom(input: InputStream): BleDev {
        return try {
            Json.decodeFromString(
                deserializer = BleDev.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: BleDev, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = BleDev.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}