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

public interface EngineFilter {

    void doStart(EngineChain chain) throws EngineException;

    void doCreate(Create create, EngineChain chain) throws EngineException;

    void doIndex(Index index, EngineChain chain) throws EngineException;

    void doDelete(Delete delete, EngineChain chain) throws EngineException;

    void doDelete(DeleteByQuery delete, EngineChain chain)
            throws EngineException;

    GetResult doGet(Get get, EngineChain chain) throws EngineException;

    Searcher doAcquireSearcher(String source, EngineChain chain)
            throws EngineException;

    void doMaybeMerge(EngineChain chain) throws EngineException;

    void doRefresh(Refresh refresh, EngineChain chain) throws EngineException;

    void doFlush(Flush flush, EngineChain chain) throws EngineException,
            FlushNotAllowedEngineException;

    void doOptimize(Optimize optimize, EngineChain chain)
            throws EngineException;

    SnapshotIndexCommit doSnapshotIndex(EngineChain chain) throws EngineException;

    void doRecover(RecoveryHandler recoveryHandler, EngineChain chain)
            throws EngineException;
    /**
     * The position of the filter in the chain. Execution is done from lowest order to highest.
     */
    int order();

}
