package com.goayo.debtify.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Viewmodel factory for the ContactsViewModel. Makes sure activities share one single viewModel.
 *
 * @Author Gabriel Brattgård.
 * @date 2020-09-28
 */

public class ContactsViewModelFactory implements ViewModelProvider.Factory {

    private static ContactsViewModel viewModel;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(viewModel == null){
            viewModel = new ContactsViewModel();
        }

        return (T)viewModel;
    }
}