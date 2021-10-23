package com.example.choco.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Product() : Parcelable {

    @SerializedName("Id")
    @Expose
    var id: String = ""

    @SerializedName("name")
    @Expose
    var name: String = ""

    @SerializedName("Description")
    @Expose
    var description: String = ""

    @SerializedName("price")
    @Expose
    var price: String = ""

    @SerializedName("photo")
    @Expose
    var photo: String = ""

    var order: Boolean = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        name = parcel.readString().toString()
        description = parcel.readString().toString()
        price = parcel.readString().toString()
        photo = parcel.readString().toString()
        order = parcel.readByte() != 0.toByte()
    }


    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}