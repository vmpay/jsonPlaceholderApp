package eu.vmpay.jsonplaceholder.repository.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import eu.vmpay.jsonplaceholder.repository.Company

class RoomTypeConverters {

    @TypeConverter
    fun companyToJson(value: Company?): String? {
        return if (value == null) null
        else Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToCompany(value: String): Company = Gson().fromJson(value, Company::class.java)
}