/*
 * Copyright (c) 2019, V12 Technology Ltd.
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Server Side Public License, version 1,
 * as published by MongoDB, Inc.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Server Side Public License for more details.
 *
 * You should have received a copy of the Server Side Public License
 * along with this program.  If not, see
 * <http://www.mongodb.com/licensing/server-side-public-license>.
 */
package com.fluxtion.compiler.generation;

import com.fluxtion.compiler.generation.compiler.SepFactoryConfigBean;
import com.fluxtion.compiler.generation.model.SimpleEventProcessorModel;
import com.fluxtion.compiler.generation.model.TopologicallySortedDependencyGraph;
import com.fluxtion.compiler.generation.targets.InMemoryEventProcessor;
import com.fluxtion.compiler.generation.targets.SepJavaSourceModelHugeFilter;
import com.fluxtion.runtim.annotations.EventHandler;
import com.fluxtion.compiler.builder.generation.GenerationContext;
import com.fluxtion.compiler.builder.node.DeclarativeNodeConiguration;
import com.fluxtion.compiler.builder.node.NodeFactory;
import com.fluxtion.compiler.builder.node.SEPConfig;
import com.fluxtion.compiler.generation.exporter.PngGenerator;
import com.fluxtion.compiler.generation.graphbuilder.NodeFactoryLocator;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import net.openhft.compiler.CachedCompiler;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.fluxtion.compiler.generation.Templates.*;

/**
 * @author Greg Higgins
 */
public class Generator {

    private SEPConfig config;
    private static final Logger LOG = LoggerFactory.getLogger(Generator.class);
    private SimpleEventProcessorModel simpleEventProcessorModel;

    public InMemoryEventProcessor inMemoryProcessor(SEPConfig config) throws Exception {
        config.buildConfig();

        //Loading factories
        LOG.debug("locateFactories");
        SepFactoryConfigBean loadedConfig = new SepFactoryConfigBean();
        Set<Class<? extends NodeFactory<?>>> class2Factory = NodeFactoryLocator.nodeFactorySet();
        loadedConfig.setConfig(new HashMap<>());
        DeclarativeNodeConiguration cfgActual = loadedConfig.asDeclarativeNodeConiguration();
        cfgActual.factoryClassSet.addAll(class2Factory);
        config.declarativeConfig = cfgActual;
        config.declarativeConfig.factoryClassSet.addAll(class2Factory);
        //Loading factories

        this.config = config;
        if( GenerationContext.SINGLETON==null){
            GenerationContext.setupStaticContext("", "", null, null);
        }
        TopologicallySortedDependencyGraph graph = new TopologicallySortedDependencyGraph(
                config.nodeList,
                config.publicNodes,
                config.declarativeConfig,
                GenerationContext.SINGLETON,
                config.auditorMap,
                config
        );
        simpleEventProcessorModel = new SimpleEventProcessorModel(graph, config.filterMap, GenerationContext.SINGLETON.getProxyClassMap());
        simpleEventProcessorModel.generateMetaModel(config.supportDirtyFiltering);
        if(config.generateDescription || GenerationContext.SINGLETON.getPackageName().isEmpty()){
            exportGraphMl(graph);
        }
        return new InMemoryEventProcessor(simpleEventProcessorModel);
    }

    public void templateSep(SEPConfig config) throws Exception {
        ExecutorService execSvc = Executors.newCachedThreadPool();
        execSvc.submit(Generator::warmupCompiler);
        config.buildConfig();
        this.config = config;
        LOG.debug("init velocity");
        initVelocity();
        LOG.debug("start graph calc");
        GenerationContext context = GenerationContext.SINGLETON;
        //generate model
        TopologicallySortedDependencyGraph graph = new TopologicallySortedDependencyGraph(
                config.nodeList,
                config.publicNodes,
                config.declarativeConfig,
                context,
                config.auditorMap,
                config
        );
//        graph.registrationListenerMap = config.auditorMap;
        LOG.debug("start model gen");
        simpleEventProcessorModel = new SimpleEventProcessorModel(graph, config.filterMap, context.getProxyClassMap());
        simpleEventProcessorModel.generateMetaModel(config.supportDirtyFiltering);
        //TODO add conditionality for different target languages
        //buildJava output
        execSvc.submit(() -> {
            LOG.debug("start exporting graphML/images");
            exportGraphMl(graph);
            LOG.debug("completed exporting graphML/images");
            LOG.debug("finished generating SEP");
        });
        LOG.debug("start template output");
        templateJavaOutput();
        LOG.debug("completed template output");
        execSvc.shutdown();
    }

    public SimpleEventProcessorModel getSimpleEventProcessorModel() {
        return simpleEventProcessorModel;
    }

    public static void warmupCompiler() {
        LOG.debug("running compiler warmup");
        try {
            CachedCompiler c = new CachedCompiler(null, null);
            c.loadFromJava("com.fluxtion.compiler.WarmupSample",
                    "package com.fluxtion.compiler;\n"
                            + "\n"
                            + "public class WarmupSample {\n"
                            + "\n"
                            + "    public String test;\n"
                            + "\n"
                            + "    public String getTest() {\n"
                            + "        return test;\n"
                            + "    }\n"
                            + "    \n"
                            + "}");
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("problem running warmup compile", ex);
            } else {
                LOG.warn("problem running warmup compile");
            }
        } finally {
            LOG.debug("completed compiler warmup");
        }
    }

    private static void initVelocity() {
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(GenerationContext.SINGLETON.getClassLoader());
        Velocity.init();
        Thread.currentThread().setContextClassLoader(originalClassLoader);
    }

    private File templateJavaOutput() throws Exception {
        SepJavaSourceModelHugeFilter srcModel = new SepJavaSourceModelHugeFilter(
                simpleEventProcessorModel,
                config.inlineEventHandling,
                config.assignPrivateMembers
        );
        srcModel.additionalInterfacesToImplement(config.interfacesToImplement());
        LOG.debug("building source model");
        srcModel.buildSourceModel();
        //set up defaults
        if (config.templateFile == null) {
            config.templateFile = JAVA_TEMPLATE;
        }

        LOG.debug("templating output source - start");
        String templateFile = config.templateFile;
        Template template;//= Velocity.getTemplate(config.templateFile);

        try {
            template = Velocity.getTemplate(templateFile);
        } catch (Exception e) {
            System.out.println("failed to load template, setting threadcontext class loader");
            ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(GenerationContext.SINGLETON.getClassLoader());
                template = Velocity.getTemplate(templateFile);
            } finally {
                Thread.currentThread().setContextClassLoader(originalClassLoader);
            }
        }

        Context ctx = new VelocityContext();
        addVersionInformation(ctx);
        ctx.put("MODEL", srcModel);
        ctx.put("MODEL_EXTENSION", config.templateContextExtension);
        ctx.put("package", GenerationContext.SINGLETON.getPackageName());
        ctx.put("className", GenerationContext.SINGLETON.getSepClassName());
        ctx.put("logenabled", config.isGenerateLogging());
        File outFile = new File(GenerationContext.SINGLETON.getPackageDirectory(), GenerationContext.SINGLETON.getSepClassName() + ".java");
        FileWriter templateWriter = new FileWriter(outFile);
        template.merge(ctx, templateWriter);
        templateWriter.flush();
        LOG.debug("templating output source - finish");
        //add some formatting
        templateWriter.close();
        return outFile;
    }

    private void addVersionInformation(Context ctx) {
        ctx.put("generator_version_information", this.getClass().getPackage().getImplementationVersion());
        ctx.put("api_version_information", EventHandler.class.getPackage().getImplementationVersion());
        ctx.put("build_time", LocalDateTime.now());
    }

    public static void formatSource(File outFile) {
        try {
            LOG.debug("Reading source:'{}'", outFile.getCanonicalPath());
            CharSource source = Files.asCharSource(outFile, Charset.defaultCharset());
            CharSink output = Files.asCharSink(outFile, Charset.defaultCharset());
            LOG.debug("formatting source - start");
            new Formatter().formatSource(source, output);
            LOG.debug("formatting source - finish");
        } catch (FormatterException | IOException ex) {
            LOG.error("problem formatting source file", ex);
        }
    }

    private void exportGraphMl(TopologicallySortedDependencyGraph graph) {
        if (config.generateDescription) {
            try {
                LOG.debug("generating event images and graphml");
                File graphMl = new File(GenerationContext.SINGLETON.getResourcesOutputDirectory(), GenerationContext.SINGLETON.getSepClassName() + ".graphml");
                File pngFile = new File(GenerationContext.SINGLETON.getResourcesOutputDirectory(), GenerationContext.SINGLETON.getSepClassName() + ".png");
                if (graphMl.getParentFile() != null) {
                    graphMl.getParentFile().mkdirs();
                }
                FileWriter graphMlWriter = new FileWriter(graphMl);
                graph.exportAsGraphMl(graphMlWriter, true);
                PngGenerator.generatePNG(graphMl, pngFile);
            } catch (IOException | TransformerConfigurationException | SAXException iOException) {
                LOG.error("error writing png and graphml:", iOException);
            }
        }
    }

}