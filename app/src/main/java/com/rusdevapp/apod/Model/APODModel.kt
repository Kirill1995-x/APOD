package com.rusdevapp.apod.Model

import android.os.Parcel
import android.os.Parcelable


class APODModel(var title:String?=null, var url:String?=null,
                var explanation:String?=null, val date:String?=null): Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    fun getConvertDate():String
    {
        if (date!=null)
        {
            var array: List<String> = date.split("-")
            return "Дата: "+array[2]+"."+array[1]+"."+array[0]
        }
        else
        {
            return "Дата: 00.00.0000"
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(explanation)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<APODModel> {
        override fun createFromParcel(parcel: Parcel): APODModel {
            return APODModel(parcel)
        }

        override fun newArray(size: Int): Array<APODModel?> {
            return arrayOfNulls(size)
        }
    }

}