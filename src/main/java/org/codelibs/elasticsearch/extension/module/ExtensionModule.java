package org.codelibs.elasticsearch.extension.module;

import java.util.List;

import org.codelibs.elasticsearch.extension.filter.EngineFilter;
import org.codelibs.elasticsearch.extension.filter.EngineFilters;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.inject.multibindings.Multibinder;

public class ExtensionModule extends AbstractModule {
    private final List<Class<? extends EngineFilter>> engineFilters = Lists
            .newArrayList();

    @Override
    protected void configure() {

        final Multibinder<EngineFilter> engineFilterMultibinder = Multibinder
                .newSetBinder(binder(), EngineFilter.class);
        for (final Class<? extends EngineFilter> engineFilter : engineFilters) {
            engineFilterMultibinder.addBinding().to(engineFilter);
        }
        bind(EngineFilters.class).asEagerSingleton();

    }

    public ExtensionModule registerEngineFilter(
            final Class<? extends EngineFilter> engineFilter) {
        engineFilters.add(engineFilter);
        return this;
    }
}