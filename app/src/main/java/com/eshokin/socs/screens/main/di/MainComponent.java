package com.eshokin.socs.screens.main.di;

import com.eshokin.socs.application.di.AppComponent;
import com.eshokin.socs.screens.main.MainActivity;
import com.eshokin.socs.screens.main.MainPresenter;

import dagger.Component;
import dagger.Subcomponent;

@MainScope
@Component(dependencies = AppComponent.class, modules = {MainModule.class})
public interface MainComponent {

    void inject(MainPresenter presenter);
}
