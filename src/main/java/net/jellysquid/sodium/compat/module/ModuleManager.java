package net.jellysquid.sodium.compat.module;

import net.jellysquid.sodium.compat.module.render.*;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    // O Singleton (só existe um gerente no jogo)
    public static final ModuleManager INSTANCE = new ModuleManager();

    // A lista onde guardamos os hacks
    private final List<Module> modules = new ArrayList<>();

    private ModuleManager() {
        // --- AQUI A GENTE REGISTRA OS HACKS ---
        add(new OverlayManager());
        add(new GammaOverride());
        add(new FogDisabler());
        add(new EntityProcessor());
        add(new OcclusionService());
    }

    private void add(Module module) {
        modules.add(module);
    }

    public List<Module> getModules() {
        return modules;
    }

    // O método vital: Permite que o Comando ache o módulo pelo nome da classe
    // Ex: ModuleManager.get(NoPumpkin.class)
    public static <T extends Module> T get(Class<T> clazz) {
        for (Module m : INSTANCE.modules) {
            if (m.getClass() == clazz) return (T) m;
        }
        return null;
    }
}