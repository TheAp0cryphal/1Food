package com.project.onefood.RestaurantPage.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.onefood.R
import com.project.onefood.RestaurantPage.FragmentAdapters.MenuAdapter
import com.project.onefood.RestaurantPage.FragmentAdapters.ReviewAdapter
import com.project.onefood.RestaurantPage.FragmentAdapters.ReviewItem
import com.project.onefood.databinding.FragmentMenuBinding
import com.project.onefood.databinding.FragmentReviewsBinding

class ReviewsFragment : Fragment() {

    private lateinit var binding : FragmentReviewsBinding
    private lateinit var reviewsArr : ArrayList<ReviewItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReviewsBinding.inflate(inflater, container, false)

        reviewsArr = ArrayList()

        initListView()

        return binding.root
    }

    private fun initListView() {
        val getReviews = arguments?.getSerializable("reviewsArr") as ArrayList<ReviewItem>
        val adapter = ReviewAdapter(requireContext(), getReviews)
        binding.reviewList.adapter = adapter
    }
}