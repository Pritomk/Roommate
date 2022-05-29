package com.techmihirnaik.mergeroommate.ui.myTrips

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techmihirnaik.mergeroommate.R
import com.techmihirnaik.mergeroommate.dao.MyTripsDao
import com.techmihirnaik.mergeroommate.databinding.FragmentMyTripsBinding
import com.techmihirnaik.mergeroommate.ui.HomeFragment
import com.techmihirnaik.roommate.ui.myTrips.MyTripsViewModel

class MyTripsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentMyTripsBinding? = null

    private lateinit var dao: MyTripsDao
    private lateinit var adapter: MyTripsAdapter
    private lateinit var trips_rv: RecyclerView
    private lateinit var spinner: Spinner
    private val TAG = "com.techmihirnaik.mergeroommate.ui.myTrips.MyTripsFragment"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(MyTripsViewModel::class.java)

        _binding = FragmentMyTripsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        binding.fab.setOnClickListener {
//            startActivity(Intent(requireContext(), AddActivity::class.java))
//        }

        dao = MyTripsDao()
        trips_rv = binding.rvMyTrips
        spinner = binding.spinnerCategory
        adapter = MyTripsAdapter()

        trips_rv.adapter = adapter
        trips_rv.layoutManager = LinearLayoutManager(requireContext())


        dao.extractOrderedList()


        setupSpinner()

        setHasOptionsMenu(true)

        return root
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.d(TAG,"position is $p2")
        when (p2) {
            0 -> {
                dao.orderedTrips.observe(viewLifecycleOwner) {
                    adapter.updateTrips(it)
                }
            }
            1 -> {
                dao.extractHistoryList()
                dao.historyTrips.observe(viewLifecycleOwner) {
                    adapter.updateTrips(it)
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        dao.orderedTrips.observe(viewLifecycleOwner) {
            adapter.updateTrips(it)
        }

    }

    //Implement menu inflater

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.action_search)
        val searchView : SearchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(searchListener)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private val searchListener: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(p0: String?): Boolean {
            adapter.filter.filter(p0)
            return false
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MyTripsFragment().apply {
                arguments = Bundle().apply {}
            }
    }



}
