package org.codelibs.elasticsearch.extension.chain;

import java.io.IOException;
import java.util.List;

import org.codelibs.elasticsearch.extension.filter.EngineFilter;
import org.elasticsearch.index.deletionpolicy.SnapshotIndexCommit;
import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.index.engine.Engine.CommitId;
import org.elasticsearch.index.engine.Engine.Create;
import org.elasticsearch.index.engine.Engine.Delete;
import org.elasticsearch.index.engine.Engine.DeleteByQuery;
import org.elasticsearch.index.engine.Engine.Get;
import org.elasticsearch.index.engine.Engine.GetResult;
import org.elasticsearch.index.engine.Engine.Index;
import org.elasticsearch.index.engine.Engine.RecoveryHandler;
import org.elasticsearch.index.engine.Engine.SyncedFlushResult;
import org.elasticsearch.index.engine.EngineConfig;
import org.elasticsearch.index.engine.EngineException;
import org.elasticsearch.index.engine.FlushNotAllowedEngineException;
import org.elasticsearch.index.engine.Segment;

public class EngineChain {

    private final Engine engine;

    private final EngineConfig engineConfig;

    private final EngineFilter[] filters;

    int position = 0;

    public EngineChain(final Engine engine, final EngineConfig engineConfig,
            final EngineFilter[] filters) {
        this.engine = engine;
        this.engineConfig = engineConfig;
        this.filters = filters;
    }

    public Engine getEngine() {
        return engine;
    }

    public EngineConfig getEngineConfig() {
        return engineConfig;
    }

    public void doClose() throws IOException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            filter.doClose(this);
        } else {
            engine.close();
        }
    }

    public void doCreate(final Create create) throws EngineException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            filter.doCreate(create, this);
        } else {
            engine.create(create);
        }
    }

    public void doIndex(final Index index) throws EngineException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            filter.doIndex(index, this);
        } else {
            engine.index(index);
        }
    }

    public void doDelete(final Delete delete) throws EngineException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            filter.doDelete(delete, this);
        } else {
            engine.delete(delete);
        }
    }

    public void doDelete(final DeleteByQuery delete) throws EngineException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            filter.doDelete(delete, this);
        } else {
            engine.delete(delete);
        }
    }

    public GetResult doGet(final Get get) throws EngineException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            return filter.doGet(get, this);
        } else {
            return engine.get(get);
        }
    }

    public void doMaybeMerge() throws EngineException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            filter.doMaybeMerge(this);
        } else {
            engine.maybeMerge();
        }
    }

    public void doRefresh(final String source) throws EngineException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            filter.doRefresh(source, this);
        } else {
            engine.refresh(source);
        }
    }

    public CommitId doFlush(final boolean force, final boolean waitIfOngoing)
            throws EngineException, FlushNotAllowedEngineException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            return filter.doFlush(force, waitIfOngoing, this);
        } else {
            return engine.flush(force, waitIfOngoing);
        }
    }

    public SnapshotIndexCommit doSnapshotIndex() throws EngineException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            return filter.doSnapshotIndex(this);
        } else {
            return engine.snapshotIndex();
        }
    }

    public void doRecover(final RecoveryHandler recoveryHandler)
            throws EngineException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            filter.doRecover(recoveryHandler, this);
        } else {
            engine.recover(recoveryHandler);
        }
    }

    public List<Segment> doSegments() {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            return filter.doSegments(this);
        } else {
            return engine.segments();
        }
    }

    public boolean doPossibleMergeNeeded() {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            return filter.doPossibleMergeNeeded(this);
        } else {
            return engine.possibleMergeNeeded();
        }
    }

    public void doForceMerge(final boolean flush, final int maxNumSegments,
            final boolean onlyExpungeDeletes, final boolean upgrade,
            final boolean upgradeOnlyAncientSegments) {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            filter.doForceMerge(flush, maxNumSegments, onlyExpungeDeletes,
                    upgrade, upgradeOnlyAncientSegments, this);
        } else {
            engine.forceMerge(flush, maxNumSegments, onlyExpungeDeletes,
                    upgrade, upgradeOnlyAncientSegments);
        }
    }

    public void doFailEngine(final String reason, final Throwable failure) {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            filter.doFailEngine(reason, failure, this);
        } else {
            engine.failEngine(reason, failure);
        }
    }

    public void doFlushAndClose() throws IOException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            filter.doFlushAndClose(this);
        } else {
            engine.flushAndClose();
        }
    }

    public SyncedFlushResult syncFlush(String syncId,
            CommitId expectedCommitId) {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            return filter.syncFlush(syncId, expectedCommitId,this);
        } else {
            return engine.syncFlush(syncId, expectedCommitId);
        }
    }

    public boolean hasUncommittedChanges() {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            return filter.hasUncommittedChanges(this);
        } else {
            return engine.hasUncommittedChanges();
        }
    }

}
