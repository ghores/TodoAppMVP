package com.example.todoappmvp.utils.di

import androidx.fragment.app.Fragment
import com.example.todoappmvp.ui.add.AddContracts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class ContractsViewModuleFR {
    @Provides
    fun provideAddContractsView(fragment: Fragment): AddContracts.View {
        return fragment as AddContracts.View
    }
}