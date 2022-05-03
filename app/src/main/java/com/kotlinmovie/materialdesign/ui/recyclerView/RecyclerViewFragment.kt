package com.kotlinmovie.materialdesign.ui.recyclerView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kotlinmovie.materialdesign.R
import com.kotlinmovie.materialdesign.databinding.FragmentRecyclerViewBinding


class RecyclerViewFragment : Fragment() {

    lateinit var itemTouchHelper: ItemTouchHelper
    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding: FragmentRecyclerViewBinding
        get() = _binding!!

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    val lat = 20
    val lon = 23
    val time = 23


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initPair()
    }

    private fun initPair() {
        val myCoordinate1 = lat to lon
        myCoordinate1.first
        myCoordinate1.second
        val myCoordinate2 = Pair(lat, lon)
        myCoordinate2.first
        myCoordinate2.second
        val myCoordinate3 = Triple(lat, lon, time)
        myCoordinate3.first// то же самое что и lat
        myCoordinate3.second// то же самое что и lon
        myCoordinate3.third// то же самое что и time
    }

    private fun initView() {
        val listData = arrayListOf(
            Pair(RecyclerData(getString(R.string.earth), ""), false),
            Pair(RecyclerData(getString(R.string.earth), ""), false),
            Pair(RecyclerData(getString(R.string.mars), type = TYPE_MARS), false),
            Pair(RecyclerData(getString(R.string.mars), type = TYPE_MARS), false),
            Pair(RecyclerData(getString(R.string.mars), type = TYPE_MARS), false)
        )
        listData.shuffle()
        listData.add(0, Pair(RecyclerData(getString(R.string.title), type = TYPE_HEADER),false))
        val adapter = RecyclerViewFragmentAdapter(object : OnClickItemListener {
            override fun onItemClick(data: RecyclerData) {
                Toast.makeText(requireContext(), data.name, Toast.LENGTH_SHORT).show()
            }
        }, object: OnStartDragListener{
            override fun OnStartDrag(view: RecyclerView.ViewHolder) {
                itemTouchHelper.startDrag(view)
            }

        })

        adapter.setData(listData)
        binding.recycler.adapter = adapter

        binding.recyclerFAB.setOnClickListener {
            adapter.addMars()
            binding.recycler.smoothScrollToPosition(adapter.itemCount)
        }
        binding.recyclerFabEarth.setOnClickListener {
            adapter.addEarth()
            binding.recycler.smoothScrollToPosition(adapter.itemCount)
        }
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recycler)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}