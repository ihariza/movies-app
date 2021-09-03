object Versions {
    const val GRADLE_TOOLS_VERSION = "7.0.2"

    const val KOTLIN_VERSION = "1.5.20"

    const val ANDROIDX_APPCOMPAT_VERSION = "1.3.0"
    const val ANDROIDX_CORE_VERSION = "1.6.0"
    const val ANDROIDX_MATERIAL_VERSION = "1.4.0"
    const val ANDROIDX_CONSTRAINTLAYOUT_VERSION = "2.0.4"

    const val NAVIGATION_VERSION = "2.3.5"

    const val LIFECYCLE_VIEWMODEL_VERSION = "2.3.1"

    const val RETROFIT_CORE_VERSION = "2.9.0"
    const val RETROFIT_INTERCEPTOR_VERSION = "4.9.0"

    const val KOIN_VERSION = "2.2.2"

    const val GLIDE_VERSION = "4.12.0"

    const val TEST_ARCH_CORE_VERSION = "2.1.0"
    const val TEST_COROUTINES_VERSION = "1.5.0"
    const val TEST_RUNNER_VERSION = "1.4.0"
    const val TEST_ORCHESTRATOR_VERSION = "1.4.0"
    const val TEST_FRAGMENT_VERSION = "1.3.5"
    const val TEST_MOCKK_VERSION = "1.11.0"
    const val TEST_TURBINE_VERSION = "0.5.2"
    const val TEST_TRUTH_VERSION = "1.4.0"
    const val TEST_ESPRESSO_VERSION = "3.4.0"
    const val TEST_BARISTA_VERSION = "4.1.0"
    const val TEST_MOCKWEBSERVER_VERSION = "4.9.0"
}

object Libs {
    const val gradleTools = "com.android.tools.build:gradle:${Versions.GRADLE_TOOLS_VERSION}"
}

object Kotlin {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.KOTLIN_VERSION}"
    const val gradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_VERSION}"
}

object AndroidX {
    const val coreKtx = "androidx.core:core-ktx:${Versions.ANDROIDX_CORE_VERSION}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.ANDROIDX_APPCOMPAT_VERSION}"
    const val material =
        "com.google.android.material:material:${Versions.ANDROIDX_MATERIAL_VERSION}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.ANDROIDX_CONSTRAINTLAYOUT_VERSION}"
}

object Navigation {
    const val fragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION_VERSION}"
    const val ui = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION_VERSION}"
    const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION_VERSION}"
}

object Lifecycle {
    const val viewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_VIEWMODEL_VERSION}"
}

object Retrofit {
    const val core = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT_CORE_VERSION}"
    const val gson =
        "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT_CORE_VERSION}"
    const val interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.RETROFIT_INTERCEPTOR_VERSION}"
}

object Koin {
    const val core = "org.koin:koin-androidx-viewmodel:${Versions.KOIN_VERSION}"
}

object Glide {
    const val core = "com.github.bumptech.glide:glide:${Versions.GLIDE_VERSION}"
    const val compiler = "com.github.bumptech.glide:compiler:${Versions.GLIDE_VERSION}"
}

object TestLibs {
    const val arch_core = "androidx.arch.core:core-testing:${Versions.TEST_ARCH_CORE_VERSION}"
    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.TEST_COROUTINES_VERSION}"
    const val runner = "androidx.test:runner:${Versions.TEST_RUNNER_VERSION}"
    const val orchestrator = "androidx.test:orchestrator:${Versions.TEST_ORCHESTRATOR_VERSION}"
    const val mockk = "io.mockk:mockk:${Versions.TEST_MOCKK_VERSION}"
    const val turbine = "app.cash.turbine:turbine:${Versions.TEST_TURBINE_VERSION}"

    const val truth = "androidx.test.ext:truth:${Versions.TEST_TRUTH_VERSION}"
    const val espresso_intents =
        "androidx.test.espresso:espresso-intents:${Versions.TEST_ESPRESSO_VERSION}"

    const val mockk_android = "io.mockk:mockk-android:${Versions.TEST_MOCKK_VERSION}"
    const val koin = "org.koin:koin-test:${Versions.KOIN_VERSION}"
    const val barista = "com.adevinta.android:barista:${Versions.TEST_BARISTA_VERSION}"
    const val navigation = "androidx.navigation:navigation-testing:${Versions.NAVIGATION_VERSION}"
    const val fragment = "androidx.fragment:fragment-testing:${Versions.TEST_FRAGMENT_VERSION}"
    const val mockWebServer =
        "com.squareup.okhttp3:mockwebserver:${Versions.TEST_MOCKWEBSERVER_VERSION}"
}