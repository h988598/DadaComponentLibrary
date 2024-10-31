package com.dada.dadacomponentlibrary.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dada.dadacomponentlibrary.R
import com.dada.dadacomponentlibrary.databinding.FragmentBottomDialogBinding
import com.dada.dadacomponentlibrary.databinding.LayoutBottomDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomDialogFragment : BaseNormalFragment() {
    lateinit var binding: FragmentBottomDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            activity?.let { it1 ->
                val bottomSheetDialog = BottomSheetDialog(it1, R.style.BottomSheetDialog)
                val bottomDialogBinding =
                    LayoutBottomDialogBinding.inflate(LayoutInflater.from(context))
                bottomSheetDialog.setContentView(bottomDialogBinding.getRoot())
                bottomSheetDialog.window!!.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                    .setBackgroundColor(
                        Color.TRANSPARENT
                    )
                bottomSheetDialog.show()
            }
        }
    }

    override fun getRootViewResId(): Int {
        return R.layout.layout_bottom_dialog
    }
}