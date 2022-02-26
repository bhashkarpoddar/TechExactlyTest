package com.example.techexactlytest.ui.home.application

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.techexactlytest.R
import com.example.techexactlytest.data.model.Applications
import com.example.techexactlytest.databinding.AppliactionsItemviewBinding
import java.util.*

class ApplicationAdapter(private var items:MutableList<Applications>, private val interaction: Interaction) : RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder>(), Filterable {

    private var filteredList = items

    init {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationViewHolder {
        val binding: AppliactionsItemviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.appliactions_itemview, parent, false)
        return ApplicationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApplicationViewHolder, position: Int) {
        holder.bind(filteredList!![holder.adapterPosition], interaction, holder.adapterPosition)
        /*holder.itemView.setOnClickListener {
            interaction.onItemSelected(holder.adapterPosition, items[holder.adapterPosition])
        }*/
    }


    override fun getItemCount(): Int {
        return filteredList?.size
    }

    class ApplicationViewHolder(private val binding: AppliactionsItemviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Applications, interaction: Interaction, adapterPosition: Int){
            binding.application = item

            binding.switchToggle.setOnCheckedChangeListener { switch, isChecked ->
                // Handle switch checked/unchecked
                Log.d("TAG", "onCreate: $switch and $isChecked")
                interaction.onSwitchSelected(adapterPosition, item, isChecked)
            }

            binding.executePendingBindings()
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Applications)
        fun onSwitchSelected(position: Int, item: Applications, isCheck: Boolean)
    }

    fun setData(list: MutableList<Applications>){
        items = list
        filteredList = items
        notifyDataSetChanged()
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                filteredList = filterResults.values as MutableList<Applications>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()
                Log.d("TAG", "performFiltering: $queryString ${filteredList?.size}")
                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    items
                else
                    items?.filter {
                        it.app_name.lowercase(Locale.getDefault()).contains(queryString)
                    }
                return filterResults
            }
        }
    }
}