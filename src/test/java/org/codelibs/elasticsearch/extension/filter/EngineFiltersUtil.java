package org.codelibs.elasticsearch.extension.filter;

import java.util.Set;

import org.codelibs.elasticsearch.extension.filter.EngineFilter;
import org.codelibs.elasticsearch.extension.filter.EngineFilters;

public class EngineFiltersUtil {

    public static void overwrite(Set<EngineFilter> engineFilters) {
        EngineFilters.overwrite(engineFilters);
    }

}
