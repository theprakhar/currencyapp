package com.prakhar.currency.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prakhar.currency.R
import com.prakhar.currency.adapter.CurrenciesAdapter
import com.prakhar.currency.adapter.SelectorAdapter
import com.prakhar.currency.model.Currency
import com.prakhar.currency.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: CurrenciesAdapter
    private lateinit var selectorAdapter: SelectorAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var textInput: EditText
    private lateinit var dropDown: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)

        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        initViews(view)
        observeCurrencies()
        return view
    }

    override fun onStart() {
        super.onStart()
        fetchData()
    }

    private fun initViews(view:View) {
        initRecyclerView(view)
        initTextInput(view)
        initDropDown(view)
    }

    private fun initDropDown(view:View) {
        //dropdown
        dropDown = view.findViewById(R.id.currencySelector)
        selectorAdapter = SelectorAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            ArrayList()
        )
        dropDown.adapter = selectorAdapter
        dropDown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                val currency: Currency = selectorAdapter.getItem(position)
                adapter.updateFromCurrency(currency)
            }

            override fun onNothingSelected(adapter: AdapterView<*>?) {}
        }
    }

    private fun initTextInput(view:View) {
        //text input
        textInput = view.findViewById(R.id.amountInput)
        textInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //pass
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val string = s?.toString()?.trim()
                val value = if (!string.isNullOrEmpty()) string.toDouble() else 0.00
                adapter.updateCurrentValue(value)
            }

            override fun afterTextChanged(s: Editable?) {
                //pass
            }
        })
    }

    private fun initRecyclerView(view:View) {
        //recycler view
        recyclerView = view.findViewById(R.id.currencies_list)
        adapter = CurrenciesAdapter()
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = adapter
    }


    private fun observeCurrencies() {
        val currenciesObserver = Observer<List<Currency>> {
            val newList = ArrayList(it)
            adapter.updateList(newList)
            selectorAdapter.updateValues(newList)
        }
        homeViewModel.currencies.observe(viewLifecycleOwner, currenciesObserver)
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            homeViewModel.startFetchingCurrencies()
        }
    }
}