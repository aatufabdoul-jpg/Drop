package com.drop.filetransfer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.drop.filetransfer.R
import com.drop.filetransfer.databinding.FragmentReceiveBinding
import com.drop.filetransfer.viewmodel.ReceiveViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReceiveFragment : Fragment() {

    private val viewModel: ReceiveViewModel by viewModels()
    private var _binding: FragmentReceiveBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startListeningButton.setOnClickListener {
            viewModel.startListening()
        }

        binding.stopListeningButton.setOnClickListener {
            viewModel.stopListening()
        }

        // Observe incoming transfers
        viewModel.incomingTransfers.observe(viewLifecycleOwner) { transfers ->
            updateTransfersList(transfers)
        }

        viewModel.listeningStatus.observe(viewLifecycleOwner) { isListening ->
            updateListeningStatus(isListening)
        }
    }

    private fun updateTransfersList(transfers: List<String>) {
        binding.transfersCountText.text = "${transfers.size} transfert(s) en attente"
    }

    private fun updateListeningStatus(isListening: Boolean) {
        binding.statusText.text = if (isListening) {
            "À l'écoute des transferts entrants..."
        } else {
            "Arrêt de l'écoute"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
