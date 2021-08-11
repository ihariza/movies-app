package com.nhariza.moviesapp.view.main

import com.nhariza.moviesapp.databinding.ActivityMainBinding
import com.nhariza.moviesapp.view.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

}