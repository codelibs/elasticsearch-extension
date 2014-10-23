package org.codelibs.elasticsearch.extension.chain;

import org.codelibs.elasticsearch.extension.filter.EngineFilter;
import org.elasticsearch.ElasticsearchException;
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
import org.elasticsearch.index.engine.EngineException;
import org.elasticsearch.index.engine.FlushNotAllowedEngineException;

public class EngineChain {

    private Engine engine;

    private EngineFilter[] filters;

    int position = 0;

    public EngineChain(final Engine engine, final EngineFilter[] filters) {
        this.engine = engine;
        this.filters = filters;
    }

    public Engine getEngine() {
        return engine;
    }

    public void doClose() throws ElasticsearchException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            filter.doClose(this);
        } else {
            engine.close();
        }
    }

    public void doStart() throws EngineException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            filter.doStart(this);
        } else {
            engine.start();
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

    public void doRefresh(final Refresh refresh) throws EngineException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            filter.doRefresh(refresh, this);
        } else {
            engine.refresh(refresh);
        }
    }

    public void doFlush(final Flush flush) throws EngineException,
            FlushNotAllowedEngineException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            filter.doFlush(flush, this);
        } else {
            engine.flush(flush);
        }
    }

    public void doOptimize(final Optimize optimize) throws EngineException {
        if (position < filters.length) {
            final EngineFilter filter = filters[position];
            position++;
            filter.doOptimize(optimize, this);
        } else {
            engine.optimize(optimize);
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

}
