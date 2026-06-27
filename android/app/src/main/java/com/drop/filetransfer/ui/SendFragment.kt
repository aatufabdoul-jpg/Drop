package com.drop.filetransfer.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.drop.filetransfer.R
import com.drop.filetransfer.databinding.FragmentSendBinding
import com.drop.filetransfer.service.FileTransferService
import com.drop.filetransfer.viewmodel.SendViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendFragment : Fragment() {

    private val viewModel: SendViewModel by viewModels()
    private var _binding: FragmentSendBinding? = null
    private val binding get() = _binding!!

    private val pickFileLauncher = registerForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        viewModel.onFilesSelected(uris)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectFilesButton.setOnClickListener {
            pickFileLauncher.launch("*/*")
        }

        binding.sendButton.setOnClickListener {
            viewModel.startTransfer()
        }

        // Observe transfer progress
        viewModel.selectedFiles.observe(viewLifecycleOwner) { files ->
            updateFilesList(files)
        }

        viewModel.transferProgress.observe(viewLifecycleOwner) { progress ->
            updateProgress(progress)
        }
    }

    private fun updateFilesList(files: List<Uri>) {
        binding.filesCountText.text = "${files.size} fichier(s) sélectionné(s)"
    }

    private fun updateProgress(progress: Int) {
        binding.progressBar.progress = progress
        binding.progressText.text = "$progress%"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
