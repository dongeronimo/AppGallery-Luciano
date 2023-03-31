package com.luciano.test.appgalleryluciano.view.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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
import com.luciano.test.appgalleryluciano.view.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel:MainActivityViewModel by viewModels()
    lateinit var binding : ActivityMainBinding
    lateinit var imageAdapter : ImagesAdapter

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted->
        if(isGranted){
            viewModel.updateStorePermission(true)
        }else{
            viewModel.updateStorePermission(false)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageAdapter = ImagesAdapter{img->
            with(binding) {
                imageDetails.visibility = View.VISIBLE
                exibitionName.text = img.exibitionName
                followers.text = "${img.followers}"
                description.text = img.description
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.imageDetails.visibility = View.GONE
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
            submitSearch()
        }
        binding.closeImageDetails.setOnClickListener {
            binding.imageDetails.visibility = View.GONE
        }
        binding.searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                submitSearch()
                return@setOnEditorActionListener true
            }
            false
        }
    }
    private fun submitSearch(){
        binding.searchInput.text?.let {param->
            if(param.trim().isEmpty()){
                Toast.makeText(this, "Informe o que deseja buscar", Toast.LENGTH_LONG).show()
            }else{
                closeKeyboard()
                doSearchAsync(param.toString())
            }
        }
    }
    private fun closeKeyboard() {
        // this will give us the view
        // which is currently focus
        // in this layout
        val view = this.currentFocus

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            val manager: InputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            manager
                .hideSoftInputFromWindow(
                    view.windowToken, 0
                )
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