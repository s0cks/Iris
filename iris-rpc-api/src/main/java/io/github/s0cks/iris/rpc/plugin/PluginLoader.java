package io.github.s0cks.iris.rpc.plugin;

import com.google.common.eventbus.EventBus;
import com.google.gson.Gson;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.StreamSupport;

@Singleton
public final class PluginLoader{
    public static final String PLUGIN_DIRECTORY = "Plugins.Dir";

    private static URL[] getPluginJars(Path dir){
        try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir)){
            return StreamSupport.stream(dirStream.spliterator(), false)
                    .filter((file) -> file.endsWith(".jar"))
                    .map((file) -> {
                        try {
                            return file.toUri().toURL();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toArray(URL[]::new);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private final Logger logger = LoggerFactory.getLogger(PluginLoader.class);
    private final URLClassLoader classLoader;
    private final Path dir;
    private final Gson gson;
    private final Injector injector;
    private final EventBus eventBus;


    @Inject
    private PluginLoader(@Named(PLUGIN_DIRECTORY) Path pluginDir, Gson gson, Injector injector, EventBus eventBus){
        this.dir = pluginDir;
        this.classLoader = new URLClassLoader(getPluginJars(pluginDir), ClassLoader.getSystemClassLoader());
        this.gson = gson;
        this.injector = injector;
        this.eventBus = eventBus;
    }

    public void load(){
        try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(this.dir)){
            StreamSupport.stream(dirStream.spliterator(), false)
                    .filter((file)->file.endsWith(".jar"))
                    .map((file)->{
                        try{
                            return new JarFile(file.toFile());
                        } catch(Exception e){
                            throw new RuntimeException(e);
                        }
                    })
                    .forEach((jar)->{
                        JarEntry manifest = jar.stream()
                                .filter((e)->e.getName().equals("plugin.json"))
                                .findFirst()
                                .orElseThrow(()->new IllegalStateException("Cannot find plugin manifest"));
                        try(InputStream manifestInput = jar.getInputStream(manifest)){
                            Plugin plugin = this.gson.fromJson(new InputStreamReader(manifestInput), Plugin.class);
                            this.logger.info("Loading plugin {}", plugin.getName());

                            Class<?> cls = this.classLoader.loadClass(plugin.getPluginClass());
                            plugin.setInstance(this.injector.getInstance(cls));
                            this.eventBus.register(plugin.getInstance());
                        } catch(Exception e){
                            throw new RuntimeException(e);
                        }
                    });
        } catch(Exception e){
            this.logger.error("Error loading plugins {}", e);
        }
    }
}
