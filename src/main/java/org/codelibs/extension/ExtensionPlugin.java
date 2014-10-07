package org.codelibs.extension;

import java.util.Collection;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import org.codelibs.extension.module.ExtensionModule;
import org.codelibs.extension.service.ExtensionService;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.component.LifecycleComponent;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.plugins.AbstractPlugin;

public class ExtensionPlugin extends AbstractPlugin {
    public ExtensionPlugin() throws Exception {
        final String transportSearchActionClsName = "org.elasticsearch.action.search.TransportSearchAction";
        final String searchRequestClsName = "org.elasticsearch.action.search.SearchRequest";
        final String actionListenerClsName = "org.elasticsearch.action.ActionListener";
        final String dynamicRankerClsName = "org.codelibs.extension.service.DynamicRanker";

        final ClassPool classPool = ClassPool.getDefault();
        final CtClass cc = classPool.get(transportSearchActionClsName);

        final CtMethod createAndPutContextMethod = cc.getDeclaredMethod(
                "doExecute",
                new CtClass[] { classPool.get(searchRequestClsName),
                        classPool.get(actionListenerClsName) });
        createAndPutContextMethod.insertBefore(//
                actionListenerClsName + " newListener=" + dynamicRankerClsName
                        + ".get().wrapActionListener($1,$2);"//
                        + "if(newListener!=null){$2=newListener;}"//
                );

        final ClassLoader classLoader = this.getClass().getClassLoader();
        cc.toClass(classLoader, this.getClass().getProtectionDomain());

    }

    @Override
    public String name() {
        return "ExtensionPlugin";
    }

    @Override
    public String description() {
        return "This plugin provides ExtensionService to customize Elasticsearch features.";
    }

    @Override
    public Collection<Class<? extends Module>> modules() {
        final Collection<Class<? extends Module>> modules = Lists
                .newArrayList();
        modules.add(ExtensionModule.class);
        return modules;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Collection<Class<? extends LifecycleComponent>> services() {
        final Collection<Class<? extends LifecycleComponent>> services = Lists
                .newArrayList();
        services.add(ExtensionService.class);
        return services;
    }

}
