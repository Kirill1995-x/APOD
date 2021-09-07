package com.rusdevapp.apod.Additional

import android.widget.EditText

class CheckFields(var etDay:EditText, var etMonth:EditText, var etYear:EditText)
{
    fun CheckDayField():Boolean
    {
         if(etDay.text.toString().isEmpty())
         {
             etDay.setError("Введите день")
             return true
         }
         else
         {
             etDay.setError("")
             return false
         }
    }

    fun CheckMonthField():Boolean
    {
        if(etMonth.text.toString().isEmpty())
        {
            etMonth.setError("Введите день")
            return true
        }
        else
        {
            etMonth.setError("")
            return false
        }
    }

    fun CheckYearField():Boolean
    {
        if(etYear.text.toString().isEmpty())
        {
            etYear.setError("Введите день")
            return true
        }
        else
        {
            etYear.setError("")
            return false
        }
    }

    open fun CheckResult(): Boolean
    {
        return CheckDayField() || CheckMonthField() || CheckYearField()
    }

}