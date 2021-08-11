package com.nhariza.moviesapp.view.base

import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.LargeTest
import com.nhariza.moviesapp.rule.OkHttp3IdlingResourceRule
import io.mockk.MockKAnnotations
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.test.KoinTest

@LargeTest
abstract class BaseAndroidTest : KoinTest {

    private lateinit var navController: TestNavHostController
    protected val mockServer = MockWebServer()

    abstract fun setup()

    @get:Rule
    val okHttpIdlingResourceRule = OkHttp3IdlingResourceRule()

    @Before
    fun init() {
        MockKAnnotations.init(this)
        setupNavigation()
        setupMockWebServer()
        setup()
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    private fun setupNavigation() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    private fun setupMockWebServer() {
        with(mockServer) {
            start(7878)
            dispatcher = MockServerDispatcher.RequestDispatcher()
        }
    }
}