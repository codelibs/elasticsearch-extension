package org.codelibs.elasticsearch.extension.module;

import org.codelibs.elasticsearch.extension.engine.ExtendedEngine;
import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.index.engine.Engine;

public class ExtendedEngineModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Engine.class).to(ExtendedEngine.class).asEagerSingleton();
    }

}
