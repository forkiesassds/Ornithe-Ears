package me.icanttellyou.ornitheears;

import com.unascribed.ears.api.features.EarsFeatures;
import com.unascribed.ears.common.EarsFeaturesStorage;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.ClientPlayerEntity;
import net.minecraft.client.render.texture.Texture;
import net.minecraft.resource.Identifier;

import java.util.Map;
import java.util.WeakHashMap;

@Environment(EnvType.CLIENT)
public class OrnitheEars implements ClientModInitializer {
    public static final Map<Texture, EarsFeatures> earsSkinFeatures = new WeakHashMap<>();
    //? if <1.9
    public static final com.unascribed.ears.common.util.EarsStorage.Key<Boolean> FORCE_TRANSLUCENT = new com.unascribed.ears.common.util.EarsStorage.Key<>(false);

    @Override
    public void onInitializeClient() {}

    public static EarsFeatures getEarsFeatures(ClientPlayerEntity peer) {
        Identifier skin = peer.getSkinTextureLocation();
        Texture tex = Minecraft.getInstance().getTextureManager().get(skin);
        if (earsSkinFeatures.containsKey(tex)) {
            EarsFeatures feat = earsSkinFeatures.get(tex);
            EarsFeaturesStorage.INSTANCE.put(peer.getGameProfile().getName(), peer.getGameProfile().getId(), feat);
            if (!peer.isInvisible()) {
                return feat;
            }
        }
        return EarsFeatures.DISABLED;
    }
}