package danjanny.tutorial.nearme.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import danjanny.tutorial.nearme.R

class AddTransaksiFragment(val listener: submitTransaksiListener) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_transaksi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageView>(R.id.transaksi_dialog_close).apply {
            setOnClickListener {
                dismiss()
            }
        }

        val namaTransaksiET = view.findViewById<TextInputEditText>(R.id.transaksi_nama)
        val nominalTransaksiET = view.findViewById<TextInputEditText>(R.id.transaksi_nominal)

        // onSubmit
        view.findViewById<MaterialButton>(R.id.transaksi_add_submit).apply {
            setOnClickListener {
                listener.onSubmitDialog(
                    mutableMapOf(
                        namaTransaksiET.text.toString() to "nama_transaksi",
                        nominalTransaksiET.text.toString() to "nominal_transaksi",
                    )
                )
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            isCancelable = true
            val layoutParams = window?.attributes
            layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT
            window?.attributes = layoutParams
        }
    }

    interface submitTransaksiListener {
        fun onSubmitDialog(dataTransaksi: MutableMap<String, String>)
    }

}