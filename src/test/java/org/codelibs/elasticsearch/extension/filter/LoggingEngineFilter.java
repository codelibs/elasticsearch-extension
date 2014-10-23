package org.codelibs.elasticsearch.extension.filter;

import org.codelibs.elasticsearch.extension.chain.EngineChain;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.index.deletionpolicy.SnapshotIndexCommit;
import org.elasticsearch.index.engine.Engine.Create;
import org.elasticsearch.index.engine.Engine.Delete;
import org.elasticsearch.index.engine.Engine.DeleteByQuery;
import org.elasticsearch.index.engine.Engine.Flush;
import org.elasticsearch.index.engine.Engine.Get;
import org.elasticsearch.index.engine.Engine.GetResult;
import org.elasticsearch.index.engine.Engine.Index;
import org.elasticsearch.index.engine.Engine.Optimize;
import org.elasticsearch.index.engine.Engine.RecoveryHandler;
import org.elasticsearch.index.engine.Engine.Refresh;
import org.elasticsearch.index.engine.EngineException;
import org.elasticsearch.index.engine.FlushNotAllowedEngineException;

public class LoggingEngineFilter implements EngineFilter {
    ESLogger logger = ESLoggerFactory.getLogger("codelibs.logging.filter");

    @Override
    public void doClose(final EngineChain chain) throws ElasticsearchException {
        logger.info("doClose()");
        chain.doClose();
    }

    @Override
    public void doStart(final EngineChain chain) throws EngineException {
        logger.info("doStart()");
        chain.doStart();
    }

    @Override
    public void doCreate(final Create create, final EngineChain chain)
            throws EngineException {
        logger.info("doCreate({})", create);
        chain.doCreate(create);
    }

    @Override
    public void doIndex(final Index index, final EngineChain chain)
            throws EngineException {
        logger.info("doIndex({})", index);
        chain.doIndex(index);
    }

    @Override
    public void doDelete(final Delete delete, final EngineChain chain)
            throws EngineException {
        logger.info("doDelete({})", delete);
        chain.doDelete(delete);
    }

    @Override
    public void doDelete(final DeleteByQuery delete, final EngineChain chain)
            throws EngineException {
        logger.info("doDelete({})", delete);
        chain.doDelete(delete);
    }

    @Override
    public GetResult doGet(final Get get, final EngineChain chain)
            throws EngineException {
        logger.info("doGet({})", get);
        return chain.doGet(get);
    }

    @Override
    public void doMaybeMerge(final EngineChain chain) throws EngineException {
        logger.info("doMaybeMerge()");
        chain.doMaybeMerge();
    }

    @Override
    public void doRefresh(final Refresh refresh, final EngineChain chain)
            throws EngineException {
        logger.info("doRefresh({})", refresh);
        chain.doRefresh(refresh);
    }

    @Override
    public void doFlush(final Flush flush, final EngineChain chain)
            throws EngineException, FlushNotAllowedEngineException {
        logger.info("doFlush({})", flush);
        chain.doFlush(flush);
    }

    @Override
    public void doOptimize(final Optimize optimize, final EngineChain chain)
            throws EngineException {
        logger.info("doOptimize({})", optimize);
        chain.doOptimize(optimize);
    }

    @Override
    public SnapshotIndexCommit doSnapshotIndex(final EngineChain chain)
            throws EngineException {
        logger.info("doSnapshotIndex()");
        return chain.doSnapshotIndex();
    }

    @Override
    public void doRecover(final RecoveryHandler recoveryHandler,
            final EngineChain chain) throws EngineException {
        logger.info("doRecover({})", recoveryHandler);
        chain.doRecover(recoveryHandler);
    }

    @Override
    public int order() {
        logger.info("doStart()");
        return 0;
    }

}
