package org.codelibs.elasticsearch.extension.filter;

import java.io.IOException;
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

public interface EngineFilter {

    void doClose(EngineChain chain) throws IOException;

    void doCreate(Create create, EngineChain chain) throws EngineException;

    void doIndex(Index index, EngineChain chain) throws EngineException;

    void doDelete(Delete delete, EngineChain chain) throws EngineException;

    void doDelete(DeleteByQuery delete, EngineChain chain)
            throws EngineException;

    GetResult doGet(Get get, EngineChain chain) throws EngineException;

    void doMaybeMerge(EngineChain chain) throws EngineException;

    void doRefresh(String source, EngineChain chain) throws EngineException;

    CommitId doFlush(boolean force, boolean waitIfOngoing, EngineChain chain)
            throws EngineException, FlushNotAllowedEngineException;

    SnapshotIndexCommit doSnapshotIndex(EngineChain chain)
            throws EngineException;

    void doRecover(RecoveryHandler recoveryHandler, EngineChain chain)
            throws EngineException;

    List<Segment> doSegments(EngineChain chain);

    boolean doPossibleMergeNeeded(EngineChain chain);

    void doForceMerge(boolean flush, int maxNumSegments,
            boolean onlyExpungeDeletes, boolean upgrade,
            boolean upgradeOnlyAncientSegments, EngineChain chain);

    void doFailEngine(String reason, Throwable failure, EngineChain chain);

    void doFlushAndClose(EngineChain chain) throws IOException;

    SyncedFlushResult syncFlush(String syncId, CommitId expectedCommitId,
            EngineChain chain);

    boolean hasUncommittedChanges(EngineChain chain);

    /**
     * The position of the filter in the chain. Execution is done from lowest order to highest.
     */
    int order();

}
