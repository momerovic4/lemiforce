package ba.sss.lemiforce.views

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ba.sss.lemiforce.R


class AboutUsDialogFragment : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val vju = inflater.inflate(R.layout.aboutus_layout, null)
            val mail = vju.findViewById<TextView>(R.id.mailTxt)
            mail.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "softwaresolutionsarajevo@gmail.com"))
                startActivity(intent)
            }
            builder.setView(vju)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}