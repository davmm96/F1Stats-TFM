package com.david.f1stats.ui.ranking.drivers.driverDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.david.f1stats.databinding.FragmentDriverDetailBinding
import com.david.f1stats.utils.Constants
import com.david.f1stats.utils.DialogHelper
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DriverDetailFragment : Fragment() {

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var dialogHelper: DialogHelper

    private var _binding: FragmentDriverDetailBinding? = null
    private val binding get() = _binding!!
    private val driverDetailViewModel: DriverDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriverDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { driverDetailViewModel.fetchDriverDetail(it) }

        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers(){
        driverDetailViewModel.driverInfo.observe(viewLifecycleOwner) { driver ->
            binding.apply {
                if(driver != null) {
                    tvDriverName.text = driver.name
                    tvDriverNumber.text = driver.number
                    tvDriverCountry.text = driver.country
                    tvWC.text = driver.worldChampionships
                    tvPodiums.text = driver.podiums
                    tvRaces.text = driver.gpEntered
                    tvWins.text = driver.wins
                    tvPoints.text = driver.points

                    loadImage(ivDriverImage, driver.image)
                    loadImage(ivActualTeam, driver.teamImage)
                }
            }
        }

        driverDetailViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                driverDetailViewModel.clearErrorMessage()
            }
        }
    }

    private fun loadImage(view: ImageView, imageUrl: String?) {
        picasso.load(imageUrl).into(view)
        setupImageClickListener(view, imageUrl)
    }

    private fun setupImageClickListener(view: View, imageUrl: String?) {
        view.setOnClickListener {
            if (imageUrl != null && imageUrl != Constants.IMAGE_NOT_FOUND) {
                dialogHelper.showImageDialog(requireActivity(), picasso, imageUrl)
            }
        }
    }
}
