package org.codelibs.elasticsearch.extension;

import static org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner.newConfigs;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codelibs.elasticsearch.extension.filter.EngineFilter;
import org.codelibs.elasticsearch.extension.filter.EngineFiltersUtil;
import org.codelibs.elasticsearch.extension.filter.LoggingEngineFilter;
import org.codelibs.elasticsearch.extension.filter.TestEngineFilter;
import org.codelibs.elasticsearch.runner.ElasticsearchClusterRunner;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExtensionPluginTest {
    Logger logger = Logger.getLogger(ExtensionPluginTest.class);

    ElasticsearchClusterRunner runner;

    @Before
    public void setUp() throws Exception {
        runner = new ElasticsearchClusterRunner();
        runner.onBuild(new ElasticsearchClusterRunner.Builder() {
            @Override
            public void build(int index, Builder settingsBuilder) {
                settingsBuilder.put("engine.filter.start", true);
                settingsBuilder.put("engine.filter.create", true);
                settingsBuilder.put("engine.filter.index", true);
                settingsBuilder.put("engine.filter.delete", true);
                settingsBuilder.put("engine.filter.delete_by_query", true);
                settingsBuilder.put("engine.filter.get", true);
                settingsBuilder.put("engine.filter.acquire_searcher", true);
                settingsBuilder.put("engine.filter.maybe_merge", true);
                settingsBuilder.put("engine.filter.refresh", true);
                settingsBuilder.put("engine.filter.flush", true);
                settingsBuilder.put("engine.filter.optimize", true);
                settingsBuilder.put("engine.filter.snapshot_index", true);
                settingsBuilder.put("engine.filter.recover", true);
            }
        }).build(newConfigs().numOfNode(1).ramIndexStore());
        runner.ensureGreen();

    }

    @After
    public void tearDown() throws Exception {
        runner.close();
        runner.clean();
    }

    @Test
    public void run_test() throws Exception {
        TestEngineFilter testEngineFilter = new TestEngineFilter();
        Set<EngineFilter> engineFilters = new HashSet<>();
        engineFilters.add(new LoggingEngineFilter());
        engineFilters.add(testEngineFilter);
        EngineFiltersUtil.overwrite(engineFilters);

        assertThat(1, is(runner.getNodeSize()));
        final Client client = runner.client();

        final String index = "sample";
        final String type = "data";
        runner.createIndex(index, ImmutableSettings.builder().build());

        for (int i = 1; i <= 1000; i++) {
            final IndexResponse indexResponse = runner.insert(index, type,
                    String.valueOf(i), "{\"id\":\"" + i + "\",\"msg\":\"test "
                            + i + "\",\"counter\":" + i + "}");
            assertTrue(indexResponse.isCreated());
        }

        client.prepareGet(index, type, "1").execute();
        runner.search(index, type, null, null, 0, 10);
        runner.delete(index, type, "1");
        client.prepareDeleteByQuery(index)
                .setQuery(QueryBuilders.queryString("id:2")).execute();
        runner.flush();
        runner.optimize(true);
        client.admin().indices().prepareRecoveries(index).execute();

        logger.info(testEngineFilter.toString());
        assertTrue(testEngineFilter.called());

    }
}
