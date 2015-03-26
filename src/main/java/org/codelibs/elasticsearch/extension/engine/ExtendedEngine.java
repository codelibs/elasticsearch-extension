package org.codelibs.elasticsearch.extension.engine;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.lucene.search.SearcherManager;
import org.codelibs.elasticsearch.extension.chain.EngineChain;
import org.codelibs.elasticsearch.extension.filter.EngineFilter;
import org.codelibs.elasticsearch.extension.filter.EngineFilters;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.index.deletionpolicy.SnapshotIndexCommit;
import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.index.engine.EngineConfig;
import org.elasticsearch.index.engine.EngineException;
import org.elasticsearch.index.engine.InternalEngine;
import org.elasticsearch.index.engine.Segment;

public class ExtendedEngine extends Engine {

    private final EngineFilter[] filters;

    private final Engine engine;

    private Method getSearcherManagerMethod;

    private Method closeNoLockMethod;

    public ExtendedEngine(final EngineConfig engineConfig,
            final EngineFilters engineFilters) throws EngineException {
        super(engineConfig);
        engine = new InternalEngine(engineConfig);
        filters = engineFilters.filters();

        Class<? extends Engine> clazz = engine.getClass();
        try {
            getSearcherManagerMethod = clazz
                    .getDeclaredMethod("getSearcherManager");
            getSearcherManagerMethod.setAccessible(true);
            closeNoLockMethod = clazz.getDeclaredMethod("closeNoLock",
                    new Class<?>[] { String.class });
            closeNoLockMethod.setAccessible(true);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new EngineException(shardId, "Cannot load methods from "
                    + clazz.getName(), e);
        }
    }

    @Override
    public int hashCode() {
        return engine.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return engine.equals(obj);
    }

    @Override
    public void create(final Create create) throws EngineException {
        new EngineChain(engine, engineConfig, filters).doCreate(create);
    }

    @Override
    public void index(final Index index) throws EngineException {
        new EngineChain(engine, engineConfig, filters).doIndex(index);
    }

    @Override
    public void delete(final Delete delete) throws EngineException {
        new EngineChain(engine, engineConfig, filters).doDelete(delete);
    }

    @Override
    public void delete(final DeleteByQuery delete) throws EngineException {
        new EngineChain(engine, engineConfig, filters).doDelete(delete);
    }

    @Override
    public GetResult get(final Get get) throws EngineException {
        return new EngineChain(engine, engineConfig, filters).doGet(get);
    }

    @Override
    public String toString() {
        return engine.toString();
    }

    @Override
    public List<Segment> segments() {
        return new EngineChain(engine, engineConfig, filters).doSegments();
    }

    @Override
    public boolean possibleMergeNeeded() {
        return new EngineChain(engine, engineConfig, filters)
                .doPossibleMergeNeeded();
    }

    @Override
    public void maybeMerge() throws EngineException {
        new EngineChain(engine, engineConfig, filters).doMaybeMerge();
    }

    @Override
    public void refresh(final String source) throws EngineException {
        new EngineChain(engine, engineConfig, filters).doRefresh(source);
    }

    @Override
    public void flush(final boolean force, final boolean waitIfOngoing)
            throws EngineException {
        new EngineChain(engine, engineConfig, filters).doFlush(force,
                waitIfOngoing);
    }

    @Override
    public void flush() throws EngineException {
        flush(false, false);
    }

    @Override
    public void forceMerge(final boolean flush) {
        forceMerge(flush, 1, false, false);
    }

    @Override
    public void forceMerge(final boolean flush, final int maxNumSegments,
            final boolean onlyExpungeDeletes, final boolean upgrade)
            throws EngineException {
        new EngineChain(engine, engineConfig, filters).doForceMerge(flush,
                maxNumSegments, onlyExpungeDeletes, upgrade);
    }

    @Override
    public SnapshotIndexCommit snapshotIndex() throws EngineException {
        return new EngineChain(engine, engineConfig, filters).doSnapshotIndex();
    }

    @Override
    public void recover(final RecoveryHandler recoveryHandler)
            throws EngineException {
        new EngineChain(engine, engineConfig, filters)
                .doRecover(recoveryHandler);
    }

    @Override
    public void failEngine(final String reason, final Throwable failure) {
        new EngineChain(engine, engineConfig, filters).doFailEngine(reason,
                failure);
    }

    @Override
    public void flushAndClose() throws IOException {
        new EngineChain(engine, engineConfig, filters).doFlushAndClose();
    }

    @Override
    public void close() throws IOException {
        new EngineChain(engine, engineConfig, filters).doClose();
    }

    @Override
    protected SearcherManager getSearcherManager() {
        try {
            return (SearcherManager) getSearcherManagerMethod.invoke(engine);
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new ExtendedEngineException(
                    "Cannot invoke getSearcherManager() of " + engine, e);
        }
    }

    @Override
    protected void closeNoLock(final String reason)
            throws ElasticsearchException {
        try {
            closeNoLockMethod.invoke(engine, new Object[] { reason });
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new ExtendedEngineException("Cannot invoke closeNoLock() of "
                    + engine, e);
        }
    }

}
