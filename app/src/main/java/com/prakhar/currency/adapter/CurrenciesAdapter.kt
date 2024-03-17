package com.prakhar.currency.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.prakhar.currency.R
import com.prakhar.currency.model.Currency
import com.prakhar.currency.utils.getConvertedText

class CurrenciesAdapter(
) :
    RecyclerView.Adapter<CurrenciesAdapter.CurrencyViewholder>() {

    object CurrencyDiffUtil : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.currencyCode == newItem.currencyCode
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.currencyCode == newItem.currencyCode && oldItem.exchangeRate == newItem.exchangeRate
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, CurrencyDiffUtil)
    private var currentValue: Double = 0.0
    private lateinit var fromCurrency: Currency

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewholder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.currency_card, parent, false)
        return CurrencyViewholder(itemView)
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }


    override fun onBindViewHolder(holder: CurrencyViewholder, position: Int) {
        val currency = asyncListDiffer.currentList[position]
        holder.name.text = currency.currencyCode
        holder.value.text = if (this::fromCurrency.isInitialized) {
            getConvertedText(currentValue, fromCurrency.exchangeRate, currency.exchangeRate)
        } else ""
    }


    fun updateList(newData: ArrayList<Currency>) {
        asyncListDiffer.submitList(newData)
    }

    fun updateCurrentValue(value: Double) {
        currentValue = value
        notifyDataSetChanged()
    }

    fun updateFromCurrency(currency: Currency) {
        fromCurrency = currency
        notifyDataSetChanged()
    }

    class CurrencyViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.currency_label)
        val value: TextView = itemView.findViewById(R.id.currency_value)
    }
}