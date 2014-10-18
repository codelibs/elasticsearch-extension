package org.codelibs.elasticsearch.extension.chain;

import org.codelibs.elasticsearch.extension.filter.EngineFilter;
import org.codelibs.elasticsearch.extension.filter.EngineFilters;
import org.elasticsearch.index.deletionpolicy.SnapshotIndexCommit;
import org.elasticsearch.index.engine.Engine;
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

public class EngineChain {

    private static ThreadLocal<Boolean> running = new ThreadLocal<>();

    private Engine engine;

    private EngineFilter[] filters;

    int position = 0;

    protected EngineChain(Engine engine, EngineFilter[] filters) {
        this.engine = engine;
        this.filters = filters;
    }

    public void doStart() throws EngineException {
        if (position < filters.length) {
            EngineFilter filter = filters[position];
            position++;
            filter.doStart(this);
        } else {
            engine.start();
        }
    }

    public void doCreate(Create create) throws EngineException {
        if (position < filters.length) {
            EngineFilter filter = filters[position];
            position++;
            filter.doCreate(create, this);
        } else {
            engine.create(create);
        }
    }

    public void doIndex(Index index) throws EngineException {
        if (position < filters.length) {
            EngineFilter filter = filters[position];
            position++;
            filter.doIndex(index, this);
        } else {
            engine.index(index);
        }
    }

    public void doDelete(Delete delete) throws EngineException {
        if (position < filters.length) {
            EngineFilter filter = filters[position];
            position++;
            filter.doDelete(delete, this);
        } else {
            engine.delete(delete);
        }
    }

    public void doDelete(DeleteByQuery delete) throws EngineException {
        if (position < filters.length) {
            EngineFilter filter = filters[position];
            position++;
            filter.doDelete(delete, this);
        } else {
            engine.delete(delete);
        }
    }

    public GetResult doGet(Get get) throws EngineException {
        if (position < filters.length) {
            EngineFilter filter = filters[position];
            position++;
            return filter.doGet(get, this);
        } else {
            return engine.get(get);
        }
    }

    public Searcher doAcquireSearcher(String source) throws EngineException {
        if (position < filters.length) {
            EngineFilter filter = filters[position];
            position++;
            return filter.doAcquireSearcher(source, this);
        } else {
            return engine.acquireSearcher(source);
        }
    }

    public void doMaybeMerge() throws EngineException {
        if (position < filters.length) {
            EngineFilter filter = filters[position];
            position++;
            filter.doMaybeMerge(this);
        } else {
            engine.maybeMerge();
        }
    }

    public void doRefresh(Refresh refresh) throws EngineException {
        if (position < filters.length) {
            EngineFilter filter = filters[position];
            position++;
            filter.doRefresh(refresh, this);
        } else {
            engine.refresh(refresh);
        }
    }

    public void doFlush(Flush flush) throws EngineException,
            FlushNotAllowedEngineException {
        if (position < filters.length) {
            EngineFilter filter = filters[position];
            position++;
            filter.doFlush(flush, this);
        } else {
            engine.flush(flush);
        }
    }

    public void doOptimize(Optimize optimize) throws EngineException {
        if (position < filters.length) {
            EngineFilter filter = filters[position];
            position++;
            filter.doOptimize(optimize, this);
        } else {
            engine.optimize(optimize);
        }
    }

    public SnapshotIndexCommit doSnapshotIndex() throws EngineException {
        if (position < filters.length) {
            EngineFilter filter = filters[position];
            position++;
            return filter.doSnapshotIndex(this);
        } else {
            return engine.snapshotIndex();
        }
    }

    public void doRecover(RecoveryHandler recoveryHandler)
            throws EngineException {
        if (position < filters.length) {
            EngineFilter filter = filters[position];
            position++;
            filter.doRecover(recoveryHandler, this);
        } else {
            engine.recover(recoveryHandler);
        }
    }

    public static EngineChain createEngineChain(final Engine engine, String key) {
        if (engine.indexSettings().getAsBoolean(key, false)) {
            EngineFilter[] filters = EngineFilters.get().filters();
            return new EngineChain(engine, filters);
        } else {
            return null;
        }
    }

    public static boolean begin() {
        if (running.get() == null) {
            running.set(Boolean.TRUE);
            return true;
        }
        return false;
    }

    public static void end() {
        running.remove();
    }

}
