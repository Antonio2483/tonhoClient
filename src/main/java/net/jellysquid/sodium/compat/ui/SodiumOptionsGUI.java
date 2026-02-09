package net.jellysquid.sodium.compat.ui;

import net.jellysquid.sodium.compat.module.Module;
import net.jellysquid.sodium.compat.module.ModuleManager;
import net.jellysquid.sodium.compat.module.render.EntityProcessor;
import net.jellysquid.sodium.compat.module.render.OcclusionService;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

import java.util.List;

public class SodiumOptionsGUI extends Screen {

    public SodiumOptionsGUI() {
        super(Text.literal("Tonho Client Menu"));
    }

    @Override
    protected void init() {
        super.init();

        int buttonWidth = 150;
        int buttonHeight = 20;
        int padding = 4;
        int startY = 40;

        List<Module> allModules = ModuleManager.INSTANCE.getModules();

        for (int i = 0; i < allModules.size(); i++) {
            Module mod = allModules.get(i);
            int yPos = startY + (i * (buttonHeight + padding));

            // --- LÓGICA DO SLIDER (SeeInvis) ---
            if (mod instanceof EntityProcessor) {
                // Pega o valor atual
                double valorAtual = EntityProcessor.OPACITY;

                // Texto inicial
                int porcentagemInicial = (int) (valorAtual * 100);
                Text textoInicial = Text.literal("SeeInvis: " + porcentagemInicial + "%");

                // SliderWidget no lugar de AbstractSliderButton
                SliderWidget slider = new SliderWidget(
                        this.width / 2 - buttonWidth / 2, yPos,
                        buttonWidth, buttonHeight,
                        textoInicial,
                        valorAtual
                ) {
                    @Override
                    protected void updateMessage() {
                        // Atualiza o texto visualmente
                        int p = (int) (this.value * 100);
                        this.setMessage(Text.literal("Opacidade: " + p + "%"));
                    }

                    @Override
                    protected void applyValue() {
                        // Atualiza a variável estática do mod
                        EntityProcessor.OPACITY = (float) this.value;

                        // Auto-Enable: Se arrastar pra cima de 1% e estiver desligado, liga.
                        if (this.value > 0.01 && !mod.isEnabled()) {
                            mod.toggle();
                        }
                    }
                };

                this.addDrawableChild(slider); // Yarn usa addDrawableChild

            } else {

                if (mod instanceof OcclusionService) {
                    int subButtonWidth = buttonWidth / 3 - 2;

                    // Botão Principal (Toggle)
                    this.addDrawableChild(ButtonWidget.builder(getModuleText(mod), (button) -> {
                        mod.toggle();
                        button.setMessage(getModuleText(mod));
                    }).dimensions(this.width / 2 - buttonWidth / 2, yPos, buttonWidth, buttonHeight).build());
                }
                else{
                // --- LÓGICA DO BOTÃO PADRÃO ---
                this.addDrawableChild(ButtonWidget.builder(getModuleText(mod), (button) -> {
                            mod.toggle();
                            button.setMessage(getModuleText(mod));
                        })
                        .dimensions(this.width / 2 - buttonWidth / 2, yPos, buttonWidth, buttonHeight) // .bounds virou .dimensions
                        .build());
                    }
            }
        }
    }

    // Método genérico para texto do módulo
    private Text getModuleText(Module mod) {
        if (mod instanceof OcclusionService) {
            return Text.literal(mod.getName() + ": " + OcclusionService.currentMode.nome);
        }

        boolean ligado = mod.isEnabled();
        String cor = ligado ? "§a" : "§c";
        String estado = ligado ? "LIGADO" : "DESLIGADO";
        return Text.literal(mod.getName() + ": " + cor + estado);
    }

    // Método de renderização (Fundo e Título)
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // No Yarn, 'render' recebe um DrawContext, não GuiGraphics
        super.render(context, mouseX, mouseY, delta); // Desenha o fundo padrão (dirt screen)

        // Desenha o Título centralizado
        // context.drawCenteredTextWithShadow(Renderer de Texto, Texto, X, Y, Cor);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
    }
}