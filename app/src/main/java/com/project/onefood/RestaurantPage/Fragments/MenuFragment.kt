package com.project.onefood.RestaurantPage.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.onefood.R
import com.project.onefood.RestaurantPage.FragmentAdapters.MenuAdapter
import com.project.onefood.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding : FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = FragmentMenuBinding.inflate(inflater, container, false)

        initListView()

        return binding.root

       // return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    private fun initListView() {
        val adapter = MenuAdapter(requireContext())
        binding.menuList.adapter = adapter
    }
}