package org.codelibs.elasticsearch.extension;

import java.util.Collection;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.codelibs.elasticsearch.extension.module.ExtensionModule;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.plugins.AbstractPlugin;

public class ExtensionPlugin extends AbstractPlugin {

    public ExtensionPlugin() throws Exception {
        setupEngineChain();
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

    private void setupEngineChain() throws NotFoundException,
            CannotCompileException {
        final String engineClsName = "org.elasticsearch.index.engine.Engine";
        final String internalEngineClsName = "org.elasticsearch.index.engine.internal.InternalEngine";
        final String engineChainClsName = "org.codelibs.elasticsearch.extension.chain.EngineChain";

        final ClassPool classPool = ClassPool.getDefault();
        final CtClass cc = classPool.get(internalEngineClsName);

        final CtMethod startMethod = cc.getDeclaredMethod("start",
                new CtClass[] {});
        startMethod.insertBefore(//
                "if(" + engineChainClsName
                        + ".begin()){"//
                        + engineChainClsName + " chain=" + engineChainClsName
                        + ".createEngineChain($0,\"engine.filter.start\");"//
                        + "if(chain!=null){chain.doStart();return;}}"//
                );
        startMethod.insertAfter(engineChainClsName + ".end();", true);

        final CtMethod createMethod = cc.getDeclaredMethod("create",
                new CtClass[] { classPool.get(engineClsName + "$Create") });
        createMethod.insertBefore(//
                "if(" + engineChainClsName
                        + ".begin()){"//
                        + engineChainClsName + " chain=" + engineChainClsName
                        + ".createEngineChain($0,\"engine.filter.create\");"//
                        + "if(chain!=null){chain.doCreate($1);return;}}"//
                );
        createMethod.insertAfter(engineChainClsName + ".end();", true);

        final CtMethod indexMethod = cc.getDeclaredMethod("index",
                new CtClass[] { classPool.get(engineClsName + "$Index") });
        indexMethod.insertBefore(//
                "if(" + engineChainClsName
                        + ".begin()){"//
                        + engineChainClsName + " chain=" + engineChainClsName
                        + ".createEngineChain($0,\"engine.filter.index\");"//
                        + "if(chain!=null){chain.doIndex($1);return;}}"//
                );
        indexMethod.insertAfter(engineChainClsName + ".end();", true);

        final CtMethod deleteMethod = cc.getDeclaredMethod("delete",
                new CtClass[] { classPool.get(engineClsName + "$Delete") });
        deleteMethod.insertBefore(//
                "if(" + engineChainClsName
                        + ".begin()){"//
                        + engineChainClsName + " chain=" + engineChainClsName
                        + ".createEngineChain($0,\"engine.filter.delete\");"//
                        + "if(chain!=null){chain.doDelete($1);return;}}"//
                );
        deleteMethod.insertAfter(engineChainClsName + ".end();", true);

        final CtMethod deleteByQueryMethod = cc
                .getDeclaredMethod(
                        "delete",
                        new CtClass[] { classPool.get(engineClsName
                                + "$DeleteByQuery") });
        deleteByQueryMethod.insertBefore(//
                "if("
                        + engineChainClsName
                        + ".begin()){"//
                        + engineChainClsName
                        + " chain="
                        + engineChainClsName
                        + ".createEngineChain($0,\"engine.filter.delete_by_query\");"//
                        + "if(chain!=null){chain.doDelete($1);return;}}"//
                );
        deleteByQueryMethod.insertAfter(engineChainClsName + ".end();", true);

        final CtMethod getMethod = cc.getDeclaredMethod("get",
                new CtClass[] { classPool.get(engineClsName + "$Get") });
        getMethod.insertBefore(//
                "if(" + engineChainClsName
                        + ".begin()){"//
                        + engineChainClsName + " chain=" + engineChainClsName
                        + ".createEngineChain($0,\"engine.filter.get\");"//
                        + "if(chain!=null){return chain.doGet($1);}}"//
                );
        getMethod.insertAfter(engineChainClsName + ".end();", true);

        final CtMethod acuireSearcherMethod = cc.getDeclaredMethod(
                "acquireSearcher",
                new CtClass[] { classPool.get("java.lang.String") });
        acuireSearcherMethod.insertBefore(//
                "if("
                        + engineChainClsName
                        + ".begin()){"//
                        + engineChainClsName
                        + " chain="
                        + engineChainClsName
                        + ".createEngineChain($0,\"engine.filter.acquire_searcher\");"//
                        + "if(chain!=null){return chain.doAcquireSearcher($1);}}"//
                );
        acuireSearcherMethod.insertAfter(engineChainClsName + ".end();", true);

        final CtMethod maybeMergeMethod = cc.getDeclaredMethod("maybeMerge",
                new CtClass[] {});
        maybeMergeMethod.insertBefore(//
                "if("
                        + engineChainClsName
                        + ".begin()){"//
                        + engineChainClsName
                        + " chain="
                        + engineChainClsName
                        + ".createEngineChain($0,\"engine.filter.maybe_merge\");"//
                        + "if(chain!=null){chain.doMaybeMerge();return;}}"//
                );
        maybeMergeMethod.insertAfter(engineChainClsName + ".end();", true);

        final CtMethod refreshMethod = cc.getDeclaredMethod("refresh",
                new CtClass[] { classPool.get(engineClsName + "$Refresh") });
        refreshMethod.insertBefore(//
                "if(" + engineChainClsName
                        + ".begin()){"//
                        + engineChainClsName + " chain=" + engineChainClsName
                        + ".createEngineChain($0,\"engine.filter.refresh\");"//
                        + "if(chain!=null){chain.doRefresh($1);return;}}"//
                );
        refreshMethod.insertAfter(engineChainClsName + ".end();", true);

        final CtMethod flushMethod = cc.getDeclaredMethod("flush",
                new CtClass[] { classPool.get(engineClsName + "$Flush") });
        flushMethod.insertBefore(//
                "if(" + engineChainClsName
                        + ".begin()){"//
                        + engineChainClsName + " chain=" + engineChainClsName
                        + ".createEngineChain($0,\"engine.filter.flush\");"//
                        + "if(chain!=null){chain.doFlush($1);return;}}"//
                );
        flushMethod.insertAfter(engineChainClsName + ".end();", true);

        final CtMethod optimizeMethod = cc.getDeclaredMethod("optimize",
                new CtClass[] { classPool.get(engineClsName + "$Optimize") });
        optimizeMethod.insertBefore(//
                "if(" + engineChainClsName
                        + ".begin()){"//
                        + engineChainClsName + " chain=" + engineChainClsName
                        + ".createEngineChain($0,\"engine.filter.optimize\");"//
                        + "if(chain!=null){chain.doOptimize($1);return;}}"//
                );
        optimizeMethod.insertAfter(engineChainClsName + ".end();", true);

        final CtMethod snapshotIndexMethod = cc.getDeclaredMethod(
                "snapshotIndex", new CtClass[] {});
        snapshotIndexMethod.insertBefore(//
                "if("
                        + engineChainClsName
                        + ".begin()){"//
                        + engineChainClsName
                        + " chain="
                        + engineChainClsName
                        + ".createEngineChain($0,\"engine.filter.snapshot_index\");"//
                        + "if(chain!=null){return chain.doSnapshotIndex();}}"//
                );
        snapshotIndexMethod.insertAfter(engineChainClsName + ".end();", true);

        final CtMethod recoverMethod = cc.getDeclaredMethod(
                "recover",
                new CtClass[] { classPool.get(engineClsName
                        + "$RecoveryHandler") });
        recoverMethod.insertBefore(//
                "if(" + engineChainClsName
                        + ".begin()){"//
                        + engineChainClsName + " chain=" + engineChainClsName
                        + ".createEngineChain($0,\"engine.filter.recover\");"//
                        + "if(chain!=null){chain.doRecover($1);return;}}"//
                );
        recoverMethod.insertAfter(engineChainClsName + ".end();", true);

        final ClassLoader classLoader = this.getClass().getClassLoader();
        cc.toClass(classLoader, this.getClass().getProtectionDomain());
    }

}
