package com.nhariza.moviesapp.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<BINDING : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: BINDING

    abstract fun getViewBinding(): BINDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
    }
}