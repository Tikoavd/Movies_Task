package com.task.movies.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.view.Gravity
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.task.movies.appbase.utils.viewBinding
import com.task.movies.databinding.DialogErrorBinding

class ErrorDialog(
    private val onDismiss: (() -> Unit)? = null
) : DialogFragment() {

    private val binding: DialogErrorBinding by viewBinding()

    private var message: String? = null

    @SuppressWarnings("MagicNumber")
    private val dialogWithPercent: Float = 0.8f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        message = arguments?.getString(EXTRA_MESSAGE)
    }

    override fun onResume() {
        super.onResume()
        val metrics: DisplayMetrics = this.resources.displayMetrics
        val width = metrics.widthPixels
        dialog?.window?.setLayout(
            (width * dialogWithPercent).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setGravity(Gravity.CENTER)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setOnDismissListener {
            onDismiss?.invoke()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            massage.text = message
            ok.setOnClickListener {
                dismiss()
            }
        }
    }

    companion object {

        const val TAG = "ErrorDialog"

        private const val EXTRA_MESSAGE = "message"

        fun newInstance(message: String? = null, onDismiss: (() -> Unit)? = null): ErrorDialog {
            val dialog = ErrorDialog(onDismiss)
            val args = Bundle().apply {
                message?.let { putString(EXTRA_MESSAGE, it) }
            }
            dialog.arguments = args
            return dialog
        }
    }
}
