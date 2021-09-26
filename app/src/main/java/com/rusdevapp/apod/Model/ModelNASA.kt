package com.rusdevapp.apod.Model


class ModelNASA(var title:String?=null, var url:String?=null,
                var explanation:String?=null, val date:String?=null)
{
    fun getConvertDate():String
    {
        if (date!=null)
        {
            var array: List<String> = date.split("-")
            return "Date: "+array[2]+"."+array[1]+"."+array[0]
        }
        else
        {
            return "Date: 00.00.0000"
        }
    }

}