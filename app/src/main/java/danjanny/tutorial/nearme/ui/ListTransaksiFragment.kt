package danjanny.tutorial.nearme.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import danjanny.tutorial.nearme.MyApplication
import danjanny.tutorial.nearme.R
import danjanny.tutorial.nearme.adapter.TransaksiAdapter
import danjanny.tutorial.nearme.data.Transaksi
import danjanny.tutorial.nearme.utils.Status
import danjanny.tutorial.nearme.viewmodel.TransaksiViewModel
import danjanny.tutorial.nearme.viewmodel.TransaksiViewModelFactory

/**
 * UI List Transaksi
 */
class ListTransaksiFragment : Fragment(), TransaksiAdapter.ItemTransaksiListener,
    AddTransaksiFragment.submitTransaksiListener {

    private lateinit var callback: OnActionBarListener

    private lateinit var swipeRefreshTransaksi: SwipeRefreshLayout
    private lateinit var loadDataDialog: MaterialDialog
    private lateinit var transaksiAdapter: TransaksiAdapter
    private lateinit var dataTransaksi: ArrayList<Transaksi>
    private var TAG = javaClass.simpleName
    private lateinit var transaksiViewModel: TransaksiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // init ViewModel untuk interaksi dengan Repository
        transaksiViewModel =
            ViewModelProvider(this, TransaksiViewModelFactory(application = MyApplication.instance))
                .get(TransaksiViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the xml layout to play with logic
        return inflater.inflate(R.layout.fragment_list_transaksi, container, false)
    }

    /**
     * Setelah View selesai dibuat
     * init semua UI yg ditampilkan di XML layout
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transaksiLayoutManager = LinearLayoutManager(activity) // init rv layout manager
        dataTransaksi = ArrayList() // init empty data list source transaksi

        // transaksi adapter : yg berisi data transaksi, context dari fragment, dan instance listener dari adapter
        transaksiAdapter = TransaksiAdapter(dataTransaksi, context, this)

        // init RecyclerView
        view.findViewById<RecyclerView>(R.id.transaksi_rv).apply {
            setHasFixedSize(true)
            layoutManager = transaksiLayoutManager // set layout manager to rv
            adapter = transaksiAdapter // set transaksi adapter
        }

        // init FAB untuk munculkan 'add transaksi dialog'
        view.findViewById<FloatingActionButton>(R.id.transaksi_add).apply {
            setOnClickListener {
                showAddTransaksiDialog() // tampilkan 'add transaksi' dialog
            }
        }

        // init data loading dialog
        loadDataDialog = MaterialDialog(requireContext())
            .message(R.string.please_wait)

        // swipe refresh layout
        swipeRefreshTransaksi =
            view.findViewById<SwipeRefreshLayout>(R.id.transaksi_swipeRefresh).apply {
                setOnRefreshListener {
                    fetchTransaksiObserver() // fetch transaksi observer
                }
            }

        fetchTransaksiObserver() // fetch transaksi observer

    }

    /**
     * Tampilkan Dialog 'Tambah Transaksi'
     */
    private fun showAddTransaksiDialog() {
        val fm = activity?.supportFragmentManager
        AddTransaksiFragment(this).show(fm!!, addTransaksiDialog)
    }


    fun fetchTransaksiObserver() {
        transaksiViewModel.getTransaksi().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    // saat loading, tampilkan loading dialog
                    loadDataDialog.show()
                }
                Status.SUCCESS -> {
                    loadDataDialog.cancel() // cancel loading dialog
                    swipeRefreshTransaksi.isRefreshing = false // if success, hide refresh icon
                    transaksiAdapter.apply {
                        if (it.data?.status.equals("ok")) {
                            // if data is not empty, load data to adapter
                            loadData(it.data?.data!!)
                        } else {
                            // success, but no data found
                            Toast.makeText(context, it.data?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
                Status.ERROR -> {
                    loadDataDialog.cancel() // cancel dialog
                    swipeRefreshTransaksi.isRefreshing = false // hide refresh icon
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show() // show error message
                }
            }
        })
    }

    companion object {
        val addTransaksiDialog = "ADD_TRANSAKSI_DIALOG"
    }

    override fun onItemClick(id: String?) {
        Log.d(TAG, id.toString())
        Toast
            .makeText(context, "id $id has been clicked", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onItemChecked(dataItems: ArrayList<Transaksi>) {
        this.callback.onItemChecked(dataItems)
    }

    private fun showToast(dataTransaksi: String?) {
        Toast
            .makeText(
                context,
                "${dataTransaksi.toString()} is back to parent fragment",
                Toast.LENGTH_SHORT
            )
            .show()
    }


    override fun onSubmitDialog(dataTransaksi: MutableMap<String, String>) {
        showToast(dataTransaksi.toString())
    }

    fun setOnActionBarListener(callback: OnActionBarListener) {
        this.callback = callback
    }

    fun checkedAll() {
        transaksiAdapter.selectAll()
    }

    fun uncheckedAll() {
        transaksiAdapter.deselectAll()
    }

    fun removeItems(dataItems : ArrayList<Transaksi>) {
        transaksiAdapter.removeItems(dataItems)
    }

    interface OnActionBarListener {
        fun onItemChecked(dataItems: ArrayList<Transaksi>)
    }
}