package com.goayo.debtify.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Viewmodel factory for the group view model. Makes sure activities share one single viewmodel.
 *
 * @Author Oscar Sanner, Olof Sjögren and Alex Phu.
 * @date 2020-09-25
 */

public class GroupViewModelFactory implements ViewModelProvider.Factory {

    private static GroupsViewModel viewModel;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(viewModel == null){
            viewModel = new GroupsViewModel();
        }

        return (T)viewModel;
    }
}
