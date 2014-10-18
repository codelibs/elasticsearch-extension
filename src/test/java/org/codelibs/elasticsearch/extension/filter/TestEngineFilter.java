package org.codelibs.elasticsearch.extension.filter;

import org.codelibs.elasticsearch.extension.chain.EngineChain;
import org.codelibs.elasticsearch.extension.filter.EngineFilter;
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

public class TestEngineFilter implements EngineFilter {

    private boolean doStart;

    private boolean doCreate;

    private boolean doIndex;

    private boolean doDelete;

    private boolean doDeleteByQuery;

    private boolean doGet;

    private boolean doAcquireSearcher;

    private boolean doMaybeMerge;

    private boolean doRefresh;

    private boolean doFlush;

    private boolean doOptimize;

    private boolean doSnapshotIndex;

    private boolean doRecover;

    public boolean called() {
        return doStart && doIndex && doDelete && doDeleteByQuery && doGet
                && doAcquireSearcher && doMaybeMerge && doRefresh && doFlush
                && doOptimize;
        // && doCreate && doSnapshotIndex && doRecover;
    }

    @Override
    public void doStart(EngineChain chain) throws EngineException {
        doStart = true;
        chain.doStart();
    }

    @Override
    public void doCreate(Create create, EngineChain chain)
            throws EngineException {
        doCreate = true;
        chain.doCreate(create);
    }

    @Override
    public void doIndex(Index index, EngineChain chain) throws EngineException {
        doIndex = true;
        chain.doIndex(index);
    }

    @Override
    public void doDelete(Delete delete, EngineChain chain)
            throws EngineException {
        doDelete = true;
        chain.doDelete(delete);
    }

    @Override
    public void doDelete(DeleteByQuery delete, EngineChain chain)
            throws EngineException {
        doDeleteByQuery = true;
        chain.doDelete(delete);
    }

    @Override
    public GetResult doGet(Get get, EngineChain chain) throws EngineException {
        doGet = true;
        return chain.doGet(get);
    }

    @Override
    public Searcher doAcquireSearcher(String source, EngineChain chain)
            throws EngineException {
        doAcquireSearcher = true;
        return chain.doAcquireSearcher(source);
    }

    @Override
    public void doMaybeMerge(EngineChain chain) throws EngineException {
        doMaybeMerge = true;
        chain.doMaybeMerge();
    }

    @Override
    public void doRefresh(Refresh refresh, EngineChain chain)
            throws EngineException {
        doRefresh = true;
        chain.doRefresh(refresh);
    }

    @Override
    public void doFlush(Flush flush, EngineChain chain) throws EngineException,
            FlushNotAllowedEngineException {
        doFlush = true;
        chain.doFlush(flush);
    }

    @Override
    public void doOptimize(Optimize optimize, EngineChain chain)
            throws EngineException {
        doOptimize = true;
        chain.doOptimize(optimize);
    }

    @Override
    public SnapshotIndexCommit doSnapshotIndex(EngineChain chain)
            throws EngineException {
        doSnapshotIndex = true;
        return chain.doSnapshotIndex();
    }

    @Override
    public void doRecover(RecoveryHandler recoveryHandler, EngineChain chain)
            throws EngineException {
        doRecover = true;
        chain.doRecover(recoveryHandler);
    }

    @Override
    public int order() {
        return 10;
    }

    @Override
    public String toString() {
        return "TestEngineFilter [doStart=" + doStart + ", doCreate="
                + doCreate + ", doIndex=" + doIndex + ", doDelete=" + doDelete
                + ", doDeleteByQuery=" + doDeleteByQuery + ", doGet=" + doGet
                + ", doAcquireSearcher=" + doAcquireSearcher
                + ", doMaybeMerge=" + doMaybeMerge + ", doRefresh=" + doRefresh
                + ", doFlush=" + doFlush + ", doOptimize=" + doOptimize
                + ", doSnapshotIndex=" + doSnapshotIndex + ", doRecover="
                + doRecover + "]";
    }

}
