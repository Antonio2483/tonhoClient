package me.antonio.tonhoclient.module;

import me.antonio.tonhoclient.module.render.Fullbright;
import me.antonio.tonhoclient.module.render.NoFog;
import me.antonio.tonhoclient.module.render.NoPumpkin;
import me.antonio.tonhoclient.module.render.SeeInvis;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    // O Singleton (só existe um gerente no jogo)
    public static final ModuleManager INSTANCE = new ModuleManager();

    // A lista onde guardamos os hacks
    private final List<Module> modules = new ArrayList<>();

    private ModuleManager() {
        // --- AQUI A GENTE REGISTRA OS HACKS ---
        add(new NoPumpkin());
        add(new Fullbright());
        add(new NoFog());
        add(new SeeInvis());
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