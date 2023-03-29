package com.luciano.test.appgalleryluciano.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.luciano.test.appgalleryluciano.databinding.ActivityMainBinding
import com.luciano.test.appgalleryluciano.view.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel:MainActivityViewModel by viewModels()
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObservers()
        setCallbacks()

    }
    private fun setObservers(){
        viewModel.images.observe(this) { currentList ->
            //TODO: Atualizar a lista na tela
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
        lifecycleScope.launch {
            with(binding){
                searchProgressBar.visibility = View.VISIBLE
                searchButton.visibility = View.GONE
                viewModel.doSearch(value)
                searchProgressBar.visibility = View.GONE
                searchButton.visibility = View.VISIBLE
            }
        }
    }
}