package org.codelibs.elasticsearch.extension;

import org.codelibs.elasticsearch.extension.filter.LoggingEngineFilter;
import org.codelibs.elasticsearch.extension.filter.TestEngineFilter;
import org.codelibs.elasticsearch.extension.module.ExtensionModule;
import org.elasticsearch.plugins.AbstractPlugin;

public class TestPlugin extends AbstractPlugin {

    @Override
    public String name() {
        return "TestPlugin";
    }

    @Override
    public String description() {
        return "This is a test plugin.";
    }

    public void onModule(final ExtensionModule module) {
        module.registerEngineFilter(LoggingEngineFilter.class);
        module.registerEngineFilter(TestEngineFilter.class);
    }
}
