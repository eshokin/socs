package com.eshokin.socs.application.builder;

import com.eshokin.socs.application.builder.modules.api.ClientApiModule;
import com.eshokin.socs.application.builder.modules.context.ContextModule;

import dagger.Component;

@AppScope
@Component(modules = {ContextModule.class, ClientApiModule.class})
public interface AppComponent {
}
