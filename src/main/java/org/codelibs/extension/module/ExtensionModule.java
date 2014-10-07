package org.codelibs.extension.module;

import org.codelibs.extension.service.ExtensionService;
import org.elasticsearch.common.inject.AbstractModule;

public class ExtensionModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ExtensionService.class).asEagerSingleton();
    }
}