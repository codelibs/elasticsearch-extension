package org.codelibs.elasticsearch.extension.filter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

import org.elasticsearch.common.inject.Inject;

public class EngineFilters {

    private final EngineFilter[] filters;

    @Inject
    public EngineFilters(final Set<EngineFilter> engineFilters) {
        filters = engineFilters.toArray(new EngineFilter[engineFilters.size()]);
        Arrays.sort(filters, new Comparator<EngineFilter>() {
            @Override
            public int compare(final EngineFilter o1, final EngineFilter o2) {
                return Integer.compare(o1.order(), o2.order());
            }
        });

    }

    /**
     * Returns the action filters that have been injected
     */
    public EngineFilter[] filters() {
        return filters;
    }

}
