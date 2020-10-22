package com.goayo.debtify.viewmodel;

import androidx.lifecycle.ViewModel;

import com.goayo.debtify.database.RealDatabase;
import com.goayo.debtify.mockdatabase.MockDatabase;
import com.goayo.debtify.model.ModelEngine;

/**
 * @author Yenan Wang
 * @date 2020-10-14
 * <p>
 * abstract ViewModel class with a shared instance of ModelEngine for all its subclasses
 */
abstract class ModelEngineViewModel extends ViewModel {

    private static final ModelEngine model = new ModelEngine(new MockDatabase());

    protected final ModelEngine getModel() {
        return model;
    }
}
