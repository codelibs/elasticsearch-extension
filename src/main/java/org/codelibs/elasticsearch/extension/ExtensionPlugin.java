package org.codelibs.elasticsearch.extension;

import java.util.Collection;

import org.codelibs.elasticsearch.extension.engine.ExtendedEngineFactory;
import org.codelibs.elasticsearch.extension.module.ExtensionModule;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.shard.IndexShardModule;
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
        if (settings.get(IndexShardModule.ENGINE_FACTORY) == null) {
            return ImmutableSettings
                    .builder()
                    .put(IndexShardModule.ENGINE_FACTORY,
                            ExtendedEngineFactory.class.getName()).build();
        } else {
            return ImmutableSettings.Builder.EMPTY_SETTINGS;
        }
    }
}
