package org.codelibs.elasticsearch.extension.engine;

import java.util.List;

import org.codelibs.elasticsearch.extension.chain.EngineChain;
import org.codelibs.elasticsearch.extension.filter.EngineFilter;
import org.codelibs.elasticsearch.extension.filter.EngineFilters;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.analysis.AnalysisService;
import org.elasticsearch.index.codec.CodecService;
import org.elasticsearch.index.deletionpolicy.SnapshotDeletionPolicy;
import org.elasticsearch.index.deletionpolicy.SnapshotIndexCommit;
import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.index.engine.EngineException;
import org.elasticsearch.index.engine.FlushNotAllowedEngineException;
import org.elasticsearch.index.engine.Segment;
import org.elasticsearch.index.engine.SegmentsStats;
import org.elasticsearch.index.engine.internal.InternalEngine;
import org.elasticsearch.index.indexing.ShardIndexingService;
import org.elasticsearch.index.merge.policy.MergePolicyProvider;
import org.elasticsearch.index.merge.scheduler.MergeSchedulerProvider;
import org.elasticsearch.index.settings.IndexSettingsService;
import org.elasticsearch.index.shard.ShardId;
import org.elasticsearch.index.similarity.SimilarityService;
import org.elasticsearch.index.store.Store;
import org.elasticsearch.index.translog.Translog;
import org.elasticsearch.indices.warmer.IndicesWarmer;
import org.elasticsearch.threadpool.ThreadPool;

public class ExtendedEngine implements Engine {

    private EngineFilter[] filters;

    private InternalEngine internalEngine;

    @Inject
    public ExtendedEngine(final ShardId shardId, final Settings indexSettings,
            final ThreadPool threadPool,
            final IndexSettingsService indexSettingsService,
            final ShardIndexingService indexingService,
            final IndicesWarmer warmer, final Store store,
            final SnapshotDeletionPolicy deletionPolicy,
            final Translog translog,
            final MergePolicyProvider mergePolicyProvider,
            final MergeSchedulerProvider mergeScheduler,
            final AnalysisService analysisService,
            final SimilarityService similarityService,
            final CodecService codecService, final EngineFilters engineFilters)
            throws EngineException {
        internalEngine = new InternalEngine(shardId, indexSettings, threadPool,
                indexSettingsService, indexingService, warmer, store,
                deletionPolicy, translog, mergePolicyProvider, mergeScheduler,
                analysisService, similarityService, codecService);
        filters = engineFilters.filters();
    }

    @Override
    public void close() throws ElasticsearchException {
        new EngineChain(internalEngine, filters).doClose();
    }

    @Override
    public void start() throws EngineException {
        new EngineChain(internalEngine, filters).doStart();
    }

    @Override
    public void create(final Create create) throws EngineException {
        new EngineChain(internalEngine, filters).doCreate(create);
    }

    @Override
    public void index(final Index index) throws EngineException {
        new EngineChain(internalEngine, filters).doIndex(index);
    }

    @Override
    public void delete(final Delete delete) throws EngineException {
        new EngineChain(internalEngine, filters).doDelete(delete);
    }

    @Override
    public void delete(final DeleteByQuery delete) throws EngineException {
        new EngineChain(internalEngine, filters).doDelete(delete);
    }

    @Override
    public GetResult get(final Get get) throws EngineException {
        return new EngineChain(internalEngine, filters).doGet(get);
    }

    @Override
    public void maybeMerge() throws EngineException {
        new EngineChain(internalEngine, filters).doMaybeMerge();
    }

    @Override
    public void refresh(final Refresh refresh) throws EngineException {
        new EngineChain(internalEngine, filters).doRefresh(refresh);
    }

    @Override
    public void flush(final Flush flush) throws EngineException,
            FlushNotAllowedEngineException {
        new EngineChain(internalEngine, filters).doFlush(flush);
    }

    @Override
    public void optimize(final Optimize optimize) throws EngineException {
        new EngineChain(internalEngine, filters).doOptimize(optimize);
    }

    @Override
    public SnapshotIndexCommit snapshotIndex() throws EngineException {
        return new EngineChain(internalEngine, filters).doSnapshotIndex();
    }

    @Override
    public void recover(final RecoveryHandler recoveryHandler)
            throws EngineException {
        new EngineChain(internalEngine, filters).doRecover(recoveryHandler);
    }

    @Override
    public ShardId shardId() {
        return internalEngine.shardId();
    }

    @Override
    public Settings indexSettings() {
        return internalEngine.indexSettings();
    }

    @Override
    public TimeValue defaultRefreshInterval() {
        return internalEngine.defaultRefreshInterval();
    }

    @Override
    public void enableGcDeletes(boolean enableGcDeletes) {
        internalEngine.enableGcDeletes(enableGcDeletes);
    }

    @Override
    public void updateIndexingBufferSize(ByteSizeValue indexingBufferSize) {
        internalEngine.updateIndexingBufferSize(indexingBufferSize);
    }

    @Override
    public void addFailedEngineListener(FailedEngineListener listener) {
        internalEngine.addFailedEngineListener(listener);
    }

    @Override
    public Searcher acquireSearcher(String source) throws EngineException {
        return internalEngine.acquireSearcher(source);
    }

    @Override
    public SegmentsStats segmentsStats() {
        return internalEngine.segmentsStats();
    }

    @Override
    public List<Segment> segments() {
        return internalEngine.segments();
    }

    @Override
    public boolean refreshNeeded() {
        return internalEngine.refreshNeeded();
    }

    @Override
    public boolean possibleMergeNeeded() {
        return internalEngine.possibleMergeNeeded();
    }

    @Override
    public void failEngine(String reason, Throwable failure) {
        internalEngine.failEngine(reason, failure);
    }

}
