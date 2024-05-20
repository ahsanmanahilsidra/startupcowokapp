package Fragments

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.set
import androidx.fragment.app.DialogFragment
import com.example.starupcowokapp.R
import com.example.starupcowokapp.databinding.FragmentProfileBinding
import com.example.starupcowokapp.databinding.FragmentShowqrcodeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter


class showqrcode : DialogFragment() {
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

    }
    private lateinit var bindingFragment: FragmentShowqrcodeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingFragment = FragmentShowqrcodeBinding.inflate(layoutInflater, container, false)
        val writer = QRCodeWriter()
        try {
            val bitMatrix= writer.encode(
                Firebase.auth.currentUser!!.uid,
                BarcodeFormat.QR_CODE,512,512)
            val with=bitMatrix.width
            val high=bitMatrix.height
            val bmp= Bitmap.createBitmap(with,high, Bitmap.Config.RGB_565)
            for (x in 0 until with )
            {
                for(y in 0 until high){
                    bmp.set(x,y,if (bitMatrix[x,y])  Color.parseColor("#FF9800") else Color.WHITE)
                }
            }
            bindingFragment.showqrcode.setImageBitmap(bmp)
        }
        catch (e: WriterException){
            e.printStackTrace()
        }
        return bindingFragment.root

    }

    companion object {

    }
}