package com.example.kalkulator.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.kalkulator.calculator.KeyboardViewModel
import com.example.kalkulator.R
import com.example.kalkulator.databinding.FragmentKeyboardBinding

class KeyboardFragment : Fragment(), OnClickHandlerInterface {
    private lateinit var binding: FragmentKeyboardBinding
    private val keyboardViewModel: KeyboardViewModel by viewModels()
   // var historyList: List<String> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val observer = Observer<String> { newChars ->
            binding.result = newChars
        }
//        val observer2 = Observer<MutableList<String>> { newChars ->
//            keyboardViewModel.list.value = newChars
//        }
        keyboardViewModel.currentChar.observe(this, observer)
       // keyboardViewModel.list.observe(this, observer2)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_keyboard, container, false)
        binding.clickHandler = this
        binding.lifecycleOwner = viewLifecycleOwner
        binding.result = "0.0"

//        val recyclerView: RecyclerView = binding.recyclerView
//        recyclerView.layoutManager = LinearLayoutManager(this.context)
//
//        val data = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
//
//        val adapter = ResultAdapter(data)
//        recyclerView.adapter = adapter
        return binding.root
    }

    override fun onClick(view: View) {
        val result = binding.result.toString()
        val charConcat = keyboardViewModel.charConcat(view)
        val floatRegex = """[+-]?\d*\.?\d+""".toRegex()
        val letterRegex = ".*[boh].*".toRegex()
        if (floatRegex.matches(result)) {
            binding.result = when (charConcat) {
                'b' -> keyboardViewModel.toBin(result)
                'o' -> keyboardViewModel.toOkt(result)
                'h' -> keyboardViewModel.toHex(result)
                else -> { result }
            }
        }
        if (view.id == R.id.equals) {
            //keyboardViewModel.currentChar.value = "0.0"
            binding.result = keyboardViewModel.computing2()
           // keyboardViewModel.list.add(binding.result.toString())
            //println("ssssssss: ${keyboardViewModel.list[0]}")
           // historyList = keyboardViewModel.list
        } else {
            if (result.isDigitsOnly()) binding.result += charConcat.toString()
            else if (result == "0.0" || (charConcat.isDigit() && floatRegex.matches(result)))
                binding.result = charConcat.toString()
            else if ((result.length == 1 && letterRegex.matches(result)) || result.isBlank())
                binding.result = ""
            else if (letterRegex.matches(result)) binding.result = charConcat.toString()
            else binding.result += charConcat.toString()
        }
        //keyboardViewModel.addItem(binding.result.toString())
        keyboardViewModel.currentChar.value = binding.result
        //keyboardViewModel.list.value?.add(binding.result.toString())
    }


    companion object {
        fun newInstance() = KeyboardFragment()
    }
}