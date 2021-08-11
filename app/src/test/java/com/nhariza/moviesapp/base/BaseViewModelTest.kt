package com.nhariza.moviesapp.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhariza.moviesapp.view.base.BaseViewModel
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
abstract class BaseViewModelTest : BaseViewModel() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        MockKAnnotations.init(this)
        setup()
    }

    @After
    fun down() {
        unmockkAll()
        tearDown()
    }

    abstract fun setup()

    open fun tearDown() {}

}