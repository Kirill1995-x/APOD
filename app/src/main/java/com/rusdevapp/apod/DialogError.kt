package com.rusdevapp.apod

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment

class DialogError: AppCompatDialogFragment()
{
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder:AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.title_dialog)
               .setMessage(R.string.message_dialog)
               .setPositiveButton(R.string.button_dialog,
                   DialogInterface.OnClickListener {dialog, which -> dialog.dismiss()})
        return builder.create()
    }
}