package com.iteale.industrialcase.core.init;


import com.google.common.base.Charsets;
import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.Platform;
import com.iteale.industrialcase.core.PlatformClient;
import com.iteale.industrialcase.core.network.NetworkManager;
import com.iteale.industrialcase.core.network.NetworkManagerClient;
import com.iteale.industrialcase.core.util.LogCategory;
import com.iteale.industrialcase.core.util.ReflectionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.locale.Language;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class Localization
{
    private static final String defaultLang = "en_us";
    private static final String ic2LangKey = "ic2.";

    public static void preInit(File modSourceFile) {
        DistExecutor.safeRunWhenOn(Dist.CLIENT,
            (DistExecutor.SafeSupplier<DistExecutor.SafeRunnable>) () -> new DistExecutor.SafeRunnable() {
                @Override
                public void run() {
                    Map<String, String> map = getLanguageMapMap();
                    loadServerLangFile(modSourceFile, map);
                }
            });

        DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER,
            (DistExecutor.SafeSupplier<DistExecutor.SafeRunnable>) () -> new DistExecutor.SafeRunnable() {
                @Override
                public void run() {
                    registerResourceReloadHook();
                }
            });
    }

    private static void loadServerLangFile(File modSourceFile, Map<String, String> out) {
        String path = "/assets/ic2/" + getLangPath("en_us");

        InputStream is = Localization.class.getResourceAsStream(path);
        try {
            loadLocalization(is, out);
            IndustrialCase.log.trace(LogCategory.Resource, "Successfully loaded server localization.");
        } catch (IOException e) {
            IndustrialCase.log.warn(LogCategory.Resource, "Failed to load server localization.");
            e.printStackTrace();
        }
    }

    private static String getLangPath(String language) {
        return "lang_ic2/" + language + ".properties";
    }

    @OnlyIn(Dist.CLIENT)
    private static void registerResourceReloadHook() {
        ResourceManager resManager = Minecraft.getInstance().getResourceManager();

        if (resManager instanceof ReloadableResourceManager) {
            ((ReloadableResourceManager)resManager).registerReloadListener(new ResourceManagerReloadListener()
            {
                public void onResourceManagerReload(ResourceManager manager) {
                    Map<String, String> tmpMap = new HashMap<>();
                    Map<String, String> lmMap = Localization.getLanguageMapMap();
                    Map<String, String> localeMap = Localization.getLocaleMap();

                    Set<String> languages = new LinkedHashSet<>();
                    languages.add("en_us");
                    languages.add((Minecraft.getInstance()).getLanguageManager().getSelected().getName());

                    for (String lang : languages) {
                        try {
                            for (Resource res : manager.getResources(new ResourceLocation(IndustrialCase.MODID, Localization.getLangPath(lang)))) {
                                try {
                                    tmpMap.clear();
                                    Localization.loadLocalization(res.getInputStream(), tmpMap);
                                    lmMap.putAll(tmpMap);
                                    localeMap.putAll(tmpMap);

                                    IndustrialCase.log.debug(LogCategory.Resource, "Loaded translation keys from %s.", res.getLocation());
                                } finally {
                                    try {
                                        res.close();
                                    } catch (IOException iOException) {}
                                }
                            }
                        } catch (FileNotFoundException e) {
                            IndustrialCase.log.debug(LogCategory.Resource, "No translation file for language %s.", new Object[] { lang });
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }
    }

    private static void loadLocalization(InputStream inputStream, Map<String, String> out) throws IOException {
        Properties properties = new Properties();

        properties.load(new InputStreamReader(inputStream, Charsets.UTF_8));

        for (Map.Entry<Object, Object> entries : properties.entrySet()) {
            Object key = entries.getKey();
            Object value = entries.getValue();

            if (key instanceof String && value instanceof String) {
                String newKey = (String)key;

                if (!newKey.startsWith("achievement.") &&
                        !newKey.startsWith("itemGroup.") &&
                        !newKey.startsWith("death.")) {
                    newKey = "ic2." + newKey;
                }

                out.put(newKey, (String)value);
            }
        }
    }

    protected static Map<String, String> getLanguageMapMap() {
        for (Method method : Language.class.getDeclaredMethods()) {
            if (method.getReturnType() == Language.class) {
                method.setAccessible(true);

                Field mapField = ReflectionUtil.getField(Language.class, Map.class);

                try {
                    return (Map<String, String>)mapField.get(method.invoke(null));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return null;
    }

    protected static Map<String, String> getLocaleMap() {
        Field localeField = ReflectionUtil.getField(I18n.class, Locale.class);
        Field mapField = ReflectionUtil.getField(Locale.class, Map.class);

        try {
            return (Map<String, String>)mapField.get(localeField.get(null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static String translate(String key) {
        return I18n.get(key);
    }


    public static String translate(String key, Object... args) {
        return I18n.get(key, args);
    }
}

