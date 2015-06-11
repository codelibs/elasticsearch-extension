package org.codelibs.elasticsearch.extension.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codelibs.elasticsearch.extension.chain.EngineChain;
import org.elasticsearch.index.deletionpolicy.SnapshotIndexCommit;
import org.elasticsearch.index.engine.Engine.CommitId;
import org.elasticsearch.index.engine.Engine.Create;
import org.elasticsearch.index.engine.Engine.Delete;
import org.elasticsearch.index.engine.Engine.DeleteByQuery;
import org.elasticsearch.index.engine.Engine.Get;
import org.elasticsearch.index.engine.Engine.GetResult;
import org.elasticsearch.index.engine.Engine.Index;
import org.elasticsearch.index.engine.Engine.RecoveryHandler;
import org.elasticsearch.index.engine.Engine.SyncedFlushResult;
import org.elasticsearch.index.engine.EngineException;
import org.elasticsearch.index.engine.FlushNotAllowedEngineException;
import org.elasticsearch.index.engine.Segment;

public class TestEngineFilter implements EngineFilter {
    public static List<TestEngineFilter> instances = new ArrayList<TestEngineFilter>();

    private boolean doClose;

    private boolean doCreate;

    private boolean doIndex;

    private boolean doDelete;

    private boolean doDeleteByQuery;

    private boolean doGet;

    private boolean doMaybeMerge;

    private boolean doRefresh;

    private boolean doFlush;

    private boolean doSnapshotIndex;

    private boolean doRecover;

    private boolean doSegments;

    private boolean doPossibleMergeNeeded;

    private boolean doForceMerge;

    private boolean doFailEngine;

    private boolean doFlushAndClose;

    private boolean doSyncFlush;

    private boolean doHasUncommittedChanges;

    public TestEngineFilter() {
        instances.add(this);
    }

    public boolean called() {
        return doIndex && doDelete && doDeleteByQuery && doGet && doMaybeMerge
                && doRefresh && doFlush && doPossibleMergeNeeded
                && doForceMerge;
        // && doCreate && doSnapshotIndex && doRecover && doClose 
        // && doSegments && doFailEngine && doFlushAndClose
        // && doSyncFlush && doHasUncommittedChanges
    }

    @Override
    public void doClose(final EngineChain chain) throws IOException {
        doClose = true;
        chain.doClose();
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
    public void doRefresh(final String source, final EngineChain chain)
            throws EngineException {
        doRefresh = true;
        chain.doRefresh(source);
    }

    @Override
    public CommitId doFlush(final boolean force, final boolean waitIfOngoing,
            final EngineChain chain)
                    throws EngineException, FlushNotAllowedEngineException {
        doFlush = true;
        return chain.doFlush(force, waitIfOngoing);
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
    public List<Segment> doSegments(final EngineChain chain) {
        doSegments = true;
        return chain.doSegments();
    }

    @Override
    public boolean doPossibleMergeNeeded(final EngineChain chain) {
        doPossibleMergeNeeded = true;
        return chain.doPossibleMergeNeeded();
    }

    @Override
    public void doForceMerge(final boolean flush, final int maxNumSegments,
            final boolean onlyExpungeDeletes, final boolean upgrade,
            final boolean upgradeOnlyAncientSegments, final EngineChain chain) {
        doForceMerge = true;
        chain.doForceMerge(flush, maxNumSegments, onlyExpungeDeletes, upgrade,
                upgradeOnlyAncientSegments);
    }

    @Override
    public void doFailEngine(final String reason, final Throwable failure,
            final EngineChain chain) {
        doFailEngine = true;
        chain.doFailEngine(reason, failure);
    }

    @Override
    public void doFlushAndClose(final EngineChain chain) throws IOException {
        doFlushAndClose = true;
        chain.doFlushAndClose();
    }

    @Override
    public SyncedFlushResult syncFlush(String syncId, CommitId expectedCommitId,
            EngineChain chain) {
        doSyncFlush = true;
        return chain.syncFlush(syncId, expectedCommitId);
    }

    @Override
    public boolean hasUncommittedChanges(EngineChain chain) {
        doHasUncommittedChanges = true;
        return chain.hasUncommittedChanges();
    }

    @Override
    public int order() {
        return 10;
    }

    @Override
    public String toString() {
        return "TestEngineFilter [doClose=" + doClose + ", doCreate=" + doCreate
                + ", doIndex=" + doIndex + ", doDelete=" + doDelete
                + ", doDeleteByQuery=" + doDeleteByQuery + ", doGet=" + doGet
                + ", doMaybeMerge=" + doMaybeMerge + ", doRefresh=" + doRefresh
                + ", doFlush=" + doFlush + ", doSnapshotIndex="
                + doSnapshotIndex + ", doRecover=" + doRecover + ", doSegments="
                + doSegments + ", doPossibleMergeNeeded="
                + doPossibleMergeNeeded + ", doForceMerge=" + doForceMerge
                + ", doFailEngine=" + doFailEngine + ", doFlushAndClose="
                + doFlushAndClose + ", doSyncFlush=" + doSyncFlush
                + ", doHasUncommittedChanges=" + doHasUncommittedChanges + "]";
    }

}
