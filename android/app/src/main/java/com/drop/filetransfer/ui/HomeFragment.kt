package com.drop.filetransfer.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.drop.filetransfer.R
import com.drop.filetransfer.databinding.FragmentHomeBinding
import com.drop.filetransfer.service.WifiDirectService
import com.drop.filetransfer.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Start WiFi Direct Service
        val wifiDirectIntent = Intent(requireContext(), WifiDirectService::class.java)
        requireContext().startService(wifiDirectIntent)

        // Setup UI listeners
        binding.sendFileButton.setOnClickListener {
            viewModel.onSendFileClicked()
        }

        binding.receiveFileButton.setOnClickListener {
            viewModel.onReceiveFileClicked()
        }

        // Observe ViewModel states
        viewModel.wifiDirectState.observe(viewLifecycleOwner) { state ->
            updateWifiDirectStatus(state)
        }

        viewModel.discoveredDevices.observe(viewLifecycleOwner) { devices ->
            updateDevicesList(devices)
        }
    }

    private fun updateWifiDirectStatus(state: String) {
        binding.statusText.text = "WiFi Direct: $state"
    }

    private fun updateDevicesList(devices: List<String>) {
        binding.devicesCountText.text = "${devices.size} appareils trouvés"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
