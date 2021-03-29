package com.mateusmelodn.catimages.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mateusmelodn.catimages.databinding.ActivityMainBinding
import com.mateusmelodn.catimages.util.OnScrollFinished
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), OnScrollFinished {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            viewModel = this@MainActivity.viewModel
            lifecycleOwner = this@MainActivity
        }
        setContentView(binding.root)

        // Load images
        viewModel.downloadCatImages(currentPage)
    }

    companion object {
        var currentPage = 0
    }

    override fun onScrollFinished() {
        viewModel.downloadCatImages(++currentPage)
    }
}