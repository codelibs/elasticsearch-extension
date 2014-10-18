package org.codelibs.elasticsearch.extension.filter;

import org.codelibs.elasticsearch.extension.chain.EngineChain;
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
import org.elasticsearch.index.engine.Engine.Searcher;
import org.elasticsearch.index.engine.EngineException;
import org.elasticsearch.index.engine.FlushNotAllowedEngineException;

public abstract class BaseEngineFilter implements EngineFilter {

    @Override
    public void doStart(EngineChain chain) throws EngineException {
        chain.doStart();
    }

    @Override
    public void doCreate(Create create, EngineChain chain)
            throws EngineException {
        chain.doCreate(create);
    }

    @Override
    public void doIndex(Index index, EngineChain chain) throws EngineException {
        chain.doIndex(index);
    }

    @Override
    public void doDelete(Delete delete, EngineChain chain)
            throws EngineException {
        chain.doDelete(delete);
    }

    @Override
    public void doDelete(DeleteByQuery delete, EngineChain chain)
            throws EngineException {
        chain.doDelete(delete);
    }

    @Override
    public GetResult doGet(Get get, EngineChain chain) throws EngineException {
        return chain.doGet(get);
    }

    @Override
    public Searcher doAcquireSearcher(String source, EngineChain chain)
            throws EngineException {
        return chain.doAcquireSearcher(source);
    }

    @Override
    public void doMaybeMerge(EngineChain chain) throws EngineException {
        chain.doMaybeMerge();
    }

    @Override
    public void doRefresh(Refresh refresh, EngineChain chain)
            throws EngineException {
        chain.doRefresh(refresh);
    }

    @Override
    public void doFlush(Flush flush, EngineChain chain) throws EngineException,
            FlushNotAllowedEngineException {
        chain.doFlush(flush);
    }

    @Override
    public void doOptimize(Optimize optimize, EngineChain chain)
            throws EngineException {
        chain.doOptimize(optimize);
    }

    @Override
    public SnapshotIndexCommit doSnapshotIndex(EngineChain chain)
            throws EngineException {
        return chain.doSnapshotIndex();
    }

    @Override
    public void doRecover(RecoveryHandler recoveryHandler, EngineChain chain)
            throws EngineException {
        chain.doRecover(recoveryHandler);
    }

    @Override
    public abstract int order();

}
