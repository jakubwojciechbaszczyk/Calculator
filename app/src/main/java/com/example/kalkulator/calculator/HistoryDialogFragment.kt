package com.example.kalkulator.calculator

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kalkulator.R

class HistoryDialogFragment: DialogFragment() {
    private val keyboardViewModel: KeyboardViewModel by viewModels()
    private var list: List<String>? = null
    // Konstruktor bezargumentowy
//    constructor() : super()
////
//    // Konstruktor z argumentem
//    constructor(list: List<String>?) : super() {
//        // Tutaj możesz przekazać argumenty do fragmentu
//        arguments = Bundle().apply {
//            putStringArrayList("key", ArrayList(list ?: emptyList()))
//        }
//        this.list = list
//    }
//    private val keyboardViewModel: KeyboardViewModel by viewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        //items.add(keyboardViewModel.currentChar.value ?: "aaa")
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            // Inflate layout zawierający RecyclerView
            val view = inflater.inflate(R.layout.dialog_with_recyclerview, null)
            builder.setView(view)
            //val items =  keyboardViewModel.list //list ?: emptyList()
//                if (savedInstanceState != null) savedInstanceState.getStringArrayList("key")
//            else emptyList()

            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)
            val adapter = ResultAdapter(keyboardViewModel.list.toList())
            //println("aaaa: $list")
            recyclerView.adapter =  adapter// Załóż, że utworzysz adapter dla RecyclerView
//            keyboardViewModel.list.observe(this) {
//                items -> items?.let { adapter.submitList(items.toList()) }
//            }
            builder.setTitle("Historia działań")
                .setMessage("\n")
                .setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Tutaj możesz dodać kod, który zostanie wykonany po kliknięciu przycisku OK
                    })
//                .setNegativeButton("Anuluj",
//                    DialogInterface.OnClickListener { dialog, id ->
//                        // Tutaj możesz dodać kod, który zostanie wykonany po kliknięciu przycisku Anuluj
//                    })
            builder.create()
        } ?: throw IllegalStateException("Aktywność nie może być null")
    }
}