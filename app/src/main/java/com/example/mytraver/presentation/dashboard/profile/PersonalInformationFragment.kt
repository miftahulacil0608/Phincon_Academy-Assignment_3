package com.example.mytraver.presentation.dashboard.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.mytraver.R
import com.example.mytraver.databinding.FragmentPersonalInformationBinding
import com.example.mytraver.databinding.FragmentProfileBinding

class PersonalInformationFragment : Fragment() {
    private var _binding: FragmentPersonalInformationBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel.getPersonalInformation()

        setupDataPersonal()
    }

    private fun setupDataPersonal() {
        profileViewModel.userPersonalInformation.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.avatar)
                .centerCrop()
                .circleCrop()
                .into(binding.ivAvatar)

            binding.tieName.setText(it.username)
            binding.tieEmail.setText(it.email)
            binding.tiePassword.setText(it.password)
            binding.tiePhone.setText(it.phone)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}