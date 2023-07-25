package com.bladoaepokedex.databasemanager

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.bladoaepokedex.databasemanager.entities.AbilityEntity
import com.bladoaepokedex.databasemanager.entities.SpritesEntity
import com.bladoaepokedex.databasemanager.entities.TypeEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

@ProvidedTypeConverter
class RoomConverters(val moshi: Moshi) {

    @TypeConverter
    fun toStringList(value: String?): List<String?>? = fromJson(value)

    @TypeConverter
    fun fromStringList(value: List<String?>?): String = toJson(value)

    private inline fun <reified T> fromJson(value: String?): List<T?>? {
        if (value == null) return null
        val listMyData: Type = Types.newParameterizedType(
            List::class.java,
            T::class.java
        )
        val jsonAdapter: JsonAdapter<List<T?>> = moshi.adapter(listMyData)
        return jsonAdapter.fromJson(value)
    }

    private inline fun <reified T> toJson(value: List<T?>?): String {
        val listMyData: Type = Types.newParameterizedType(
            List::class.java,
            T::class.java
        )
        val jsonAdapter: JsonAdapter<List<T?>?> = moshi.adapter(listMyData)
        return jsonAdapter.toJson(value)
    }

    private val abilityEntityType =
        Types.newParameterizedType(List::class.java, AbilityEntity::class.java)
    private val abilityEntityAdapter = moshi.adapter<List<AbilityEntity>>(abilityEntityType)

    @TypeConverter
    fun toAbilityEntityList(string: String?): List<AbilityEntity> {
        if (string.isNullOrEmpty())
            return listOf()
        return abilityEntityAdapter.fromJson(string).orEmpty()
    }

    @TypeConverter
    fun fromAbilityEntityList(members: List<AbilityEntity>?): String {
        return abilityEntityAdapter.toJson(members)
    }

    private val spritesEntityAdapter = moshi.adapter(SpritesEntity::class.java)

    @TypeConverter
    fun toSpritesEntityList(string: String?): SpritesEntity? {
        if (string.isNullOrEmpty())
            return null
        return spritesEntityAdapter.fromJson(string)
    }

    @TypeConverter
    fun fromSpritesEntityList(members: SpritesEntity?): String {
        return spritesEntityAdapter.toJson(members)
    }

    private val typeEntityType =
        Types.newParameterizedType(List::class.java, TypeEntity::class.java)
    private val typeEntityAdapter = moshi.adapter<List<TypeEntity>>(typeEntityType)

    @TypeConverter
    fun toTypeEntityList(string: String?): List<TypeEntity> {
        if (string.isNullOrEmpty())
            return listOf()
        return typeEntityAdapter.fromJson(string).orEmpty()
    }

    @TypeConverter
    fun fromTypeEntityList(members: List<TypeEntity>?): String {
        return typeEntityAdapter.toJson(members)
    }

}