package danjanny.tutorial.nearme.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import danjanny.tutorial.nearme.R
import danjanny.tutorial.nearme.data.Transaksi

class TransaksiAdapter(
    var data: ArrayList<Transaksi>,
    val context: Context?,
    val itemTransaksiListener: ItemTransaksiListener
) :
    RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder>() {


    private var TAG = javaClass.simpleName
    private var checkedItemList = ArrayList<Transaksi>()

    class TransaksiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // inflate widget
        val itemTransaksiLayout: RelativeLayout = itemView.findViewById(R.id.transaksi_layout)
        val namaTransaksi: TextView = itemView.findViewById(R.id.transaksi_nama)
        val nominalTransaksi: TextView = itemView.findViewById(R.id.transaksi_nominal)
        val checkBoxTransaksi: CheckBox = itemView.findViewById(R.id.transaksi_checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiViewHolder {
        val itemTransaksiVH = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_transaksi, parent, false)

        return TransaksiViewHolder(itemTransaksiVH)
    }

    override fun onBindViewHolder(holder: TransaksiViewHolder, position: Int) {
        val dataItem: Transaksi = data[position]
        holder.namaTransaksi.text = dataItem.namaTransaksi
        holder.itemTransaksiLayout.setOnClickListener {
            itemTransaksiListener.onItemClick(dataItem.id)
        }

        holder.nominalTransaksi.text = dataItem.nominal
        holder.checkBoxTransaksi.isChecked = dataItem.isChecked

        holder.checkBoxTransaksi.setOnClickListener {
            dataItem.adapterPosition = position
            if (holder.checkBoxTransaksi.isChecked) checkedItemList.add(dataItem) else checkedItemList.remove(dataItem)
            itemTransaksiListener.onItemChecked(checkedItemList)
        }
    }

    /**
     * Load data to adapter from API
     */
    fun loadData(dataTransaksi: ArrayList<Transaksi>) {
        data.clear()
        checkedItemList.apply {
            clear()
            itemTransaksiListener.onItemChecked(checkedItemList) // passing data to Main Activity
        }

        data = dataTransaksi
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun selectAll() {
        val dataNew = ArrayList<Transaksi>()
        var count = 0
        for (transaksi in data) {
            transaksi.isChecked = true
            transaksi.adapterPosition = count
            dataNew.add(transaksi)
            count++
        }

        data = dataNew

        // clear and add new checked data to ArrayList<Transaksi>
        checkedItemList.apply {
            clear()
            addAll(data)
        }

        itemTransaksiListener.onItemChecked(checkedItemList) // passing data to parent fragment
        notifyDataSetChanged()
    }

    fun deselectAll() {
        val dataNew = ArrayList<Transaksi>()
        var count = 0
        for (transaksi in data) {
            transaksi.isChecked = false
            transaksi.adapterPosition = count
            dataNew.add(transaksi)
            count++
        }

        data = dataNew

        // clear and add new checked data to ArrayList<Transaksi>
        checkedItemList.apply {
            clear()
            removeAll(data)
        }

        itemTransaksiListener.onItemChecked(checkedItemList) // passing data to parent fragment

        notifyDataSetChanged()
    }

    fun removeItems(dataItems : ArrayList<Transaksi>) {
        checkedItemList.clear()
        for (dataItem in dataItems) {
            data.remove(dataItem)
            notifyItemRemoved(dataItem.adapterPosition!!)
            notifyDataSetChanged()
        }

        // create data item remaining (unchecked remaining item)
        val dataItemNew = ArrayList<Transaksi>()
        for(dataItemRemaining in data) {
            dataItemRemaining.isChecked = false // unchecked remaining item list
            dataItemNew.add(dataItemRemaining)
        }

        data = dataItemNew
        notifyDataSetChanged()
    }

    interface ItemTransaksiListener {
        fun onItemClick(id: String?)
        fun onItemChecked(dataItems: ArrayList<Transaksi>)
    }

}