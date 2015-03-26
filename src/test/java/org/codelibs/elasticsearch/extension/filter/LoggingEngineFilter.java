package org.codelibs.elasticsearch.extension.filter;

import java.io.IOException;
import java.util.List;

import org.codelibs.elasticsearch.extension.chain.EngineChain;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.index.deletionpolicy.SnapshotIndexCommit;
import org.elasticsearch.index.engine.Engine.Create;
import org.elasticsearch.index.engine.Engine.Delete;
import org.elasticsearch.index.engine.Engine.DeleteByQuery;
import org.elasticsearch.index.engine.Engine.Get;
import org.elasticsearch.index.engine.Engine.GetResult;
import org.elasticsearch.index.engine.Engine.Index;
import org.elasticsearch.index.engine.Engine.RecoveryHandler;
import org.elasticsearch.index.engine.EngineException;
import org.elasticsearch.index.engine.FlushNotAllowedEngineException;
import org.elasticsearch.index.engine.Segment;

public class LoggingEngineFilter implements EngineFilter {
    ESLogger logger = ESLoggerFactory.getLogger("codelibs.logging.filter");

    @Override
    public void doClose(final EngineChain chain) throws IOException {
        logger.info("doClose()");
        chain.doClose();
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
    public void doRefresh(final String source, final EngineChain chain)
            throws EngineException {
        logger.info("doRefresh({})", source);
        chain.doRefresh(source);
    }

    @Override
    public void doFlush(final boolean force, final boolean waitIfOngoing,
            final EngineChain chain) throws EngineException,
            FlushNotAllowedEngineException {
        logger.info("doFlush({}, {})", force, waitIfOngoing);
        chain.doFlush(force, waitIfOngoing);
    }

    @Override
    public List<Segment> doSegments(final EngineChain chain) {
        logger.info("doSegments()");
        return chain.doSegments();
    }

    @Override
    public boolean doPossibleMergeNeeded(final EngineChain chain) {
        logger.info("doPossibleMergeNeeded()");
        return chain.doPossibleMergeNeeded();
    }

    @Override
    public void doForceMerge(final boolean flush, final int maxNumSegments,
            final boolean onlyExpungeDeletes, final boolean upgrade,
            final EngineChain chain) {
        logger.info("doForceMerge({}, {}, {}, {})", flush, maxNumSegments,
                onlyExpungeDeletes, upgrade);
        chain.doForceMerge(flush, maxNumSegments, onlyExpungeDeletes, upgrade);
    }

    @Override
    public void doFailEngine(final String reason, final Throwable failure,
            final EngineChain chain) {
        logger.info("doFailEngine({}, {})", reason, failure);
        chain.doFailEngine(reason, failure);
    }

    @Override
    public void doFlushAndClose(final EngineChain chain) throws IOException {
        logger.info("doFlushAndClose()");
        chain.doFlushAndClose();
    }

    @Override
    public int order() {
        logger.info("doStart()");
        return 0;
    }

}
