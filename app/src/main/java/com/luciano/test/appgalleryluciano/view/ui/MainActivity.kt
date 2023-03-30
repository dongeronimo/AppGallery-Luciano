package com.luciano.test.appgalleryluciano.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.luciano.test.appgalleryluciano.databinding.ActivityMainBinding
import com.luciano.test.appgalleryluciano.view.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

//dc784d23d3fecde
//ff7773f65b740649d3bcbd88e71c958f0433a036
//https://imgur.com/account/settings/apps
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel:MainActivityViewModel by viewModels()
    lateinit var binding : ActivityMainBinding
    private val imageAdapter = ImagesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerView()
        setObservers()
        setCallbacks()
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