package fr.iamacat.mycoordinatesmods;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import fr.iamacat.mycoordinatesmods.config.CoordinatesConfig;
import fr.iamacat.mycoordinatesmods.eventhandler.CoordinatesEventHandler;
import fr.iamacat.mycoordinatesmods.utils.Reference;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.launch.MixinBootstrap;

@Mod(Reference.MOD_ID)
public class MyCoordinatesMods {

    public static KeyMapping toggleKeyBinding;
    public static KeyMapping toggleKeyBinding2;
    public static boolean showCoordinates = true;
    public MyCoordinatesMods() {
        MixinBootstrap.init();
        // Enregistrer la méthode de configuration commune pour le modloading
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Enregistrer le code de configuration client
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::registerKeyMappings);

        // S'enregistrer pour les événements du serveur et autres événements de jeu
        MinecraftForge.EVENT_BUS.register(this);

        // Enregistrer la configuration
        CoordinatesConfig.loadConfig(CoordinatesConfig.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("mycoordinatesmod.toml"));
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Enregistrer le code de configuration commune
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new CoordinatesEventHandler());
    }

    private void loadComplete(final FMLLoadCompleteEvent event) {
        // Code exécuté à la fin du chargement
    }

    @SubscribeEvent
    public void registerKeyMappings(RegisterKeyMappingsEvent event) {
        // Enregistrer le code client uniquement ici, comme les key mappings
        toggleKeyBinding = new KeyMapping("key.coordinates.toggle", GLFW.GLFW_KEY_T, "key.categories.iamacatcoordinatesmod");
        toggleKeyBinding2 = new KeyMapping("key.coordinates.position", GLFW.GLFW_KEY_Y, "key.categories.iamacatcoordinatesmod");

        // Enregistrer les key mappings avec Forge
        event.register(toggleKeyBinding);
        event.register(toggleKeyBinding2);
    }
}
