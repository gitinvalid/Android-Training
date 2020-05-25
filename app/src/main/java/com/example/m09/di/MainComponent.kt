package com.example.m09.di

import com.example.m09.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class])
interface MainComponent {
    fun inject(activity: MainActivity)
}