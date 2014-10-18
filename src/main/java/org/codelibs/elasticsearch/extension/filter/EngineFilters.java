package org.codelibs.elasticsearch.extension.filter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

import org.elasticsearch.common.inject.Inject;

public class EngineFilters {

    private final EngineFilter[] filters;

    private static EngineFilters INSTANCE;

    @Inject
    public EngineFilters(Set<EngineFilter> engineFilters) {
        this.filters = engineFilters.toArray(new EngineFilter[engineFilters
                .size()]);
        Arrays.sort(filters, new Comparator<EngineFilter>() {
            @Override
            public int compare(EngineFilter o1, EngineFilter o2) {
                return Integer.compare(o1.order(), o2.order());
            }
        });

        if (INSTANCE == null) {
            INSTANCE = this;
        }
    }

    /**
     * Returns the action filters that have been injected
     */
    public EngineFilter[] filters() {
        return filters;
    }

    public static EngineFilters get() {
        return INSTANCE;
    }

    // for test
    static void overwrite(Set<EngineFilter> engineFilters) {
        INSTANCE = new EngineFilters(engineFilters);
    }
}
