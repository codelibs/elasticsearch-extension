package org.codelibs.elasticsearch.extension.filter;

import org.codelibs.elasticsearch.extension.chain.EngineChain;
import org.elasticsearch.ElasticsearchException;
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

public abstract class BaseEngineFilter implements EngineFilter {

    @Override
    public void doClose(final EngineChain chain) throws ElasticsearchException {
        chain.doClose();
    }

    @Override
    public void doStart(final EngineChain chain) throws EngineException {
        chain.doStart();
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
    public void doRefresh(final Refresh refresh, final EngineChain chain)
            throws EngineException {
        chain.doRefresh(refresh);
    }

    @Override
    public void doFlush(final Flush flush, final EngineChain chain)
            throws EngineException, FlushNotAllowedEngineException {
        chain.doFlush(flush);
    }

    @Override
    public void doOptimize(final Optimize optimize, final EngineChain chain)
            throws EngineException {
        chain.doOptimize(optimize);
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
    public abstract int order();

}
