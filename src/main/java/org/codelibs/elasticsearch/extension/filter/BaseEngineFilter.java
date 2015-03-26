package org.codelibs.elasticsearch.extension.filter;

import java.io.IOException;
import java.util.List;

import org.codelibs.elasticsearch.extension.chain.EngineChain;
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

public abstract class BaseEngineFilter implements EngineFilter {

    @Override
    public void doClose(final EngineChain chain) throws IOException {
        chain.doClose();
    }

    @Override
    public void doCreate(final Create create, final EngineChain chain)
            throws EngineException {
        chain.doCreate(create);
    }

    @Override
    public void doIndex(final Index index, final EngineChain chain)
            throws EngineException {
        chain.doIndex(index);
    }

    @Override
    public void doDelete(final Delete delete, final EngineChain chain)
            throws EngineException {
        chain.doDelete(delete);
    }

    @Override
    public void doDelete(final DeleteByQuery delete, final EngineChain chain)
            throws EngineException {
        chain.doDelete(delete);
    }

    @Override
    public GetResult doGet(final Get get, final EngineChain chain)
            throws EngineException {
        return chain.doGet(get);
    }

    @Override
    public void doMaybeMerge(final EngineChain chain) throws EngineException {
        chain.doMaybeMerge();
    }

    @Override
    public void doRefresh(final String source, final EngineChain chain)
            throws EngineException {
        chain.doRefresh(source);
    }

    @Override
    public void doFlush(final boolean force, final boolean waitIfOngoing,
            final EngineChain chain) throws EngineException,
            FlushNotAllowedEngineException {
        chain.doFlush(force, waitIfOngoing);
    }

    @Override
    public SnapshotIndexCommit doSnapshotIndex(final EngineChain chain)
            throws EngineException {
        return chain.doSnapshotIndex();
    }

    @Override
    public void doRecover(final RecoveryHandler recoveryHandler,
            final EngineChain chain) throws EngineException {
        chain.doRecover(recoveryHandler);
    }

    @Override
    public List<Segment> doSegments(final EngineChain chain) {
        return chain.doSegments();
    }

    @Override
    public boolean doPossibleMergeNeeded(final EngineChain chain) {
        return chain.doPossibleMergeNeeded();
    }

    @Override
    public void doForceMerge(final boolean flush, final int maxNumSegments,
            final boolean onlyExpungeDeletes, final boolean upgrade,
            final EngineChain chain) {
        chain.doForceMerge(flush, maxNumSegments, onlyExpungeDeletes, upgrade);
    }

    @Override
    public void doFailEngine(final String reason, final Throwable failure,
            final EngineChain chain) {
        chain.doFailEngine(reason, failure);
    }

    @Override
    public void doFlushAndClose(final EngineChain chain) throws IOException {
        chain.doFlushAndClose();
    }

    @Override
    public abstract int order();

}
