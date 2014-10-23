package org.codelibs.elasticsearch.extension.filter;

import java.util.ArrayList;
import java.util.List;

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

public class TestEngineFilter implements EngineFilter {
    public static List<TestEngineFilter> instances = new ArrayList<TestEngineFilter>();

    private boolean doClose;

    private boolean doStart;

    private boolean doCreate;

    private boolean doIndex;

    private boolean doDelete;

    private boolean doDeleteByQuery;

    private boolean doGet;

    private boolean doMaybeMerge;

    private boolean doRefresh;

    private boolean doFlush;

    private boolean doOptimize;

    private boolean doSnapshotIndex;

    private boolean doRecover;

    public TestEngineFilter() {
        instances.add(this);
    }

    public boolean called() {
        return doStart && doIndex && doDelete && doDeleteByQuery && doGet
                && doMaybeMerge && doRefresh && doFlush && doOptimize;
        // && doCreate && doSnapshotIndex && doRecover && doClose;
    }

    @Override
    public void doClose(final EngineChain chain) throws ElasticsearchException {
        doClose = true;
        chain.doClose();
    }

    @Override
    public void doStart(final EngineChain chain) throws EngineException {
        doStart = true;
        chain.doStart();
    }

    @Override
    public void doCreate(final Create create, final EngineChain chain)
            throws EngineException {
        doCreate = true;
        chain.doCreate(create);
    }

    @Override
    public void doIndex(final Index index, final EngineChain chain)
            throws EngineException {
        doIndex = true;
        chain.doIndex(index);
    }

    @Override
    public void doDelete(final Delete delete, final EngineChain chain)
            throws EngineException {
        doDelete = true;
        chain.doDelete(delete);
    }

    @Override
    public void doDelete(final DeleteByQuery delete, final EngineChain chain)
            throws EngineException {
        doDeleteByQuery = true;
        chain.doDelete(delete);
    }

    @Override
    public GetResult doGet(final Get get, final EngineChain chain)
            throws EngineException {
        doGet = true;
        return chain.doGet(get);
    }

    @Override
    public void doMaybeMerge(final EngineChain chain) throws EngineException {
        doMaybeMerge = true;
        chain.doMaybeMerge();
    }

    @Override
    public void doRefresh(final Refresh refresh, final EngineChain chain)
            throws EngineException {
        doRefresh = true;
        chain.doRefresh(refresh);
    }

    @Override
    public void doFlush(final Flush flush, final EngineChain chain)
            throws EngineException, FlushNotAllowedEngineException {
        doFlush = true;
        chain.doFlush(flush);
    }

    @Override
    public void doOptimize(final Optimize optimize, final EngineChain chain)
            throws EngineException {
        doOptimize = true;
        chain.doOptimize(optimize);
    }

    @Override
    public SnapshotIndexCommit doSnapshotIndex(final EngineChain chain)
            throws EngineException {
        doSnapshotIndex = true;
        return chain.doSnapshotIndex();
    }

    @Override
    public void doRecover(final RecoveryHandler recoveryHandler,
            final EngineChain chain) throws EngineException {
        doRecover = true;
        chain.doRecover(recoveryHandler);
    }

    @Override
    public int order() {
        return 10;
    }

    @Override
    public String toString() {
        return "TestEngineFilter [doClose=" + doClose + ", doStart=" + doStart
                + ", doCreate=" + doCreate + ", doIndex=" + doIndex
                + ", doDelete=" + doDelete + ", doDeleteByQuery="
                + doDeleteByQuery + ", doGet=" + doGet + ", doMaybeMerge="
                + doMaybeMerge + ", doRefresh=" + doRefresh + ", doFlush="
                + doFlush + ", doOptimize=" + doOptimize + ", doSnapshotIndex="
                + doSnapshotIndex + ", doRecover=" + doRecover + "]";
    }

}
