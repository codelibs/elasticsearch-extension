package org.codelibs.elasticsearch.extension;

import java.util.Collection;

import org.codelibs.elasticsearch.extension.module.ExtendedEngineModule;
import org.codelibs.elasticsearch.extension.module.ExtensionModule;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.engine.IndexEngineModule;
import org.elasticsearch.plugins.AbstractPlugin;

public class ExtensionPlugin extends AbstractPlugin {
    private Settings settings;

    public ExtensionPlugin(final Settings settings) {
        this.settings = settings;
    }

    @Override
    public String name() {
        return "ExtensionPlugin";
    }

    @Override
    public String description() {
        return "This plugin provides ExtensionService to customize Elasticsearch features.";
    }

    @Override
    public Collection<Class<? extends Module>> modules() {
        final Collection<Class<? extends Module>> modules = Lists
                .newArrayList();
        modules.add(ExtensionModule.class);
        return modules;
    }

    @Override
    public Settings additionalSettings() {
        if (settings.get(IndexEngineModule.EngineSettings.ENGINE_TYPE) == null) {
            return ImmutableSettings
                    .builder()
                    .put(IndexEngineModule.EngineSettings.ENGINE_TYPE,
                            ExtendedEngineModule.class.getName()).build();
        } else {
            return ImmutableSettings.Builder.EMPTY_SETTINGS;
        }
    }
}
