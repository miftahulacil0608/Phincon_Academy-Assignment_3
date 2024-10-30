package com.example.mytraver.presentation.dashboard.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mytraver.R
import com.example.mytraver.databinding.BottomSheetLogoutBinding
import com.example.mytraver.databinding.FragmentProfileBinding
import com.example.mytraver.presentation.authentication.AuthenticationActivity
import com.example.mytraver.presentation.typepreference.ChooseTypeReferenceActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel.getPersonalInformation()
        setupDataUserToolbar()
        personalInformation()

        binding.btnEditTypeReference.setOnClickListener {
            startActivity(Intent(requireContext(), ChooseTypeReferenceActivity::class.java))
        }
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun setupDataUserToolbar() {
        profileViewModel.userPersonalInformation.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.avatar)
                .centerCrop()
                .circleCrop()
                .into(binding.ivAvatar)

            binding.tvUsername.text = "Hello, ${it.username}"
            binding.tvEmail.text = it.email
        }
    }


    private fun personalInformation() {
        binding.btnPersonalInformation.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_personalInformationFragment)
        }
    }

    private fun logout() {
        val bottomSheetBinding = BottomSheetLogoutBinding.inflate(layoutInflater)
        val bottomSheet = BottomSheetDialog(requireContext())
        bottomSheet.setCancelable(false)
        bottomSheet.setContentView(bottomSheetBinding.root)
        bottomSheet.show()

        bottomSheetBinding.btnCancel.setOnClickListener {
            bottomSheet.dismiss()
        }

        bottomSheetBinding.btnLogout.setOnClickListener {
            profileViewModel.clearLogout()
            bottomSheet.dismiss()
            startActivity(Intent(requireContext(), AuthenticationActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}