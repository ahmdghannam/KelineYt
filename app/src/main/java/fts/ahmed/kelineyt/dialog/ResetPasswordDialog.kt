package fts.ahmed.kelineyt.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

import fts.ahmed.kelineyt.R

fun Fragment.setupBottomSheetDialog(
    onSendClick: (String) -> Unit
){
    val dialog= BottomSheetDialog(requireContext(),R.style.DialogStyle)
    val view=layoutInflater.inflate(R.layout.reset_password_dialog,null)
    dialog.setContentView(view)
    dialog.behavior.state= BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val edEmail =view.findViewById<EditText>(R.id.edResetPassword)
    val buttonSend =view.findViewById<Button>(R.id.buttonSendResetPassword)
    val buttonCancel =view.findViewById<Button>(R.id.buttonCancelResetPassword)

    buttonSend.setOnClickListener{
        val email=edEmail.text.toString().trim()
        onSendClick(email)
        dialog.dismiss()
    }
    buttonCancel.setOnClickListener {
        dialog.dismiss()
    }
//    dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//    dialog.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
//    dialog.window?.setGravity(Gravity.BOTTOM);
}

