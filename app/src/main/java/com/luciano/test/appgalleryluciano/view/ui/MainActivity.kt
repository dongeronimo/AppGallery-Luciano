package com.luciano.test.appgalleryluciano.view.ui

import android.Manifest
import android.R
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.luciano.test.appgalleryluciano.databinding.ActivityMainBinding
import com.luciano.test.appgalleryluciano.datasource.ImageSource
import com.luciano.test.appgalleryluciano.view.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


//dc784d23d3fecde
//ff7773f65b740649d3bcbd88e71c958f0433a036
//https://imgur.com/account/settings/apps
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel:MainActivityViewModel by viewModels()
    lateinit var binding : ActivityMainBinding
    lateinit var imageAdapter : ImagesAdapter
    @Inject
    lateinit var imageSource:ImageSource

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted->
        if(isGranted){
            viewModel.updateStorePermission(true)
        }else{
            viewModel.updateStorePermission(false)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageAdapter = ImagesAdapter(imageSource)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerView()
        setObservers()
        setCallbacks()
    }

    override fun onResume() {
        super.onResume()
        when{
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED -> {
                Log.d("Geronimo", "ja tem a permissao")
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)->{
                Snackbar.make(findViewById(android.R.id.content),
                    "We need write permission to store the photos in your disk",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK") {
                        requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }.show()
            }
            else->{
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun setObservers(){
        viewModel.images.observe(this) { currentList ->
            imageAdapter.submitList(currentList)
        }
    }
    private fun setCallbacks() {
        binding.searchButton.setOnClickListener {
            binding.searchInput.text?.let {param->
                if(param.trim().isEmpty()){
                    Toast.makeText(this, "Informe o que deseja buscar", Toast.LENGTH_LONG).show()
                }else{
                    doSearchAsync(param.toString())
                }
            }
        }
    }

    private fun doSearchAsync(value:String){
        lifecycleScope.launch(Dispatchers.IO) {
            with(binding){
                runOnUiThread { switchSearchButtonToProgress()  }
                viewModel.doSearch(value)
                runOnUiThread { switchProgressToSearchButton() }
            }
        }
    }
    private fun switchSearchButtonToProgress(){
        with(binding) {
            searchProgressBar.visibility = View.VISIBLE
            searchButton.visibility = View.GONE
        }
    }
    private fun switchProgressToSearchButton(){
        with(binding){
            searchProgressBar.visibility = View.GONE
            searchButton.visibility = View.VISIBLE
        }
    }
    private fun setRecyclerView(){
        binding.imageList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = imageAdapter
        }
    }
}