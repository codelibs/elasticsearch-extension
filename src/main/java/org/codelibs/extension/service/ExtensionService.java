package org.codelibs.extension.service;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.common.component.AbstractLifecycleComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;

public class ExtensionService extends
        AbstractLifecycleComponent<ExtensionService> {

    @Inject
    public ExtensionService(final Settings settings) {
        super(settings);
 
        // TODO Your code..
    }

    @Override
    protected void doStart() throws ElasticsearchException {
        logger.info("Starting ExtensionService");

        // TODO Your code..
    }

    @Override
    protected void doStop() throws ElasticsearchException {
        logger.info("Stopping ExtensionService");

        // TODO Your code..
    }

    @Override
    protected void doClose() throws ElasticsearchException {
        logger.info("Closing ExtensionService");

        // TODO Your code..
    }

}
