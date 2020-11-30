package danjanny.tutorial.nearme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import danjanny.tutorial.nearme.api.ApiClient
import danjanny.tutorial.nearme.data.Transaksi
import danjanny.tutorial.nearme.db.TransaksiDatabase
import danjanny.tutorial.nearme.ui.ListTransaksiFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ListTransaksiFragment.OnActionBarListener {
    private lateinit var data: ArrayList<Transaksi>
    private lateinit var listTransaksiFragment: ListTransaksiFragment
    private val TAG = javaClass.simpleName

    private var isItemCheckedAll = false
    private var checkedItems: ArrayList<Transaksi> = ArrayList()

    private val ft: FragmentTransaction by lazy {
        val fm = supportFragmentManager
        fm.beginTransaction()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // commit default fragment : ListTransaksiFragment
        val listTransaksiFragment = ListTransaksiFragment()
        ft.replace(R.id.fl_container, listTransaksiFragment)
        ft.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_check_all -> {
                // checked all item
                isItemCheckedAll = !isItemCheckedAll // set toggle item checkbox
                if (isItemCheckedAll) listTransaksiFragment.checkedAll() else listTransaksiFragment.uncheckedAll()
                true
            }
            R.id.menu_delete -> {
                // delete checked item
                Log.d(TAG, checkedItems.size.toString())
                if (!checkedItems.isEmpty()) {
                    listTransaksiFragment.removeItems(checkedItems)
                    showToast("${checkedItems.size} data dihapus!")
                    checkedItems.clear()
                    supportActionBar?.title = resources.getString(R.string.app_name)
                } else {
                    showToast("Tidak ada data yang di-ceklist!")
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showToast(message: String) {
        Toast
            .makeText(applicationContext, message, Toast.LENGTH_SHORT)
            .show()
    }

    fun fetchInsertTest() {
        lifecycleScope.launch {
            val transaksiResponse = ApiClient().getService().getTransaksi()
            Log.d(
                TAG, "${transaksiResponse.status}, " +
                        "${transaksiResponse.message}, ${transaksiResponse.data}"
            )

            val transaksiDao =
                TransaksiDatabase.getDatabase(context = applicationContext).transaksiDao()
            Log.d(TAG, transaksiDao.insertTransaksi(transaksiResponse.data).toString())
        }
    }

    override fun onAttachFragment(fragment: androidx.fragment.app.Fragment) {
        if (fragment is ListTransaksiFragment) {
            listTransaksiFragment = fragment
            fragment.setOnActionBarListener(this)
        }
    }

    override fun onItemChecked(dataItems: ArrayList<Transaksi>) {
        checkedItems.clear()
        checkedItems.addAll(dataItems)

        if (!checkedItems.isEmpty())
            supportActionBar?.title = "${checkedItems.size} item selected"
        else
            supportActionBar?.title = resources.getString(R.string.app_name)
    }

}