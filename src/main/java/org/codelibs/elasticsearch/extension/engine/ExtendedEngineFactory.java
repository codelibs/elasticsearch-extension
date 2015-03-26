package org.codelibs.elasticsearch.extension.engine;

import org.codelibs.elasticsearch.extension.filter.EngineFilters;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.index.engine.EngineConfig;
import org.elasticsearch.index.engine.EngineFactory;
import org.elasticsearch.index.engine.ShadowEngine;

public class ExtendedEngineFactory implements EngineFactory {

    private EngineFilters engineFilters;

    @Inject
    public ExtendedEngineFactory(final EngineFilters engineFilters) {
        this.engineFilters = engineFilters;
    }

    @Override
    public Engine newReadWriteEngine(final EngineConfig config) {
        return new ExtendedEngine(config, engineFilters);
    }

    @Override
    public Engine newReadOnlyEngine(final EngineConfig config) {
        return new ShadowEngine(config);
    }

}
