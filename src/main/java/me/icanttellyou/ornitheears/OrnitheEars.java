package me.icanttellyou.ornitheears;

import com.unascribed.ears.api.features.EarsFeatures;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Map;
import java.util.WeakHashMap;

@Environment(EnvType.CLIENT)
public class OrnitheEars implements ClientModInitializer {
    public static final Map</*? if >=1.6 {*/ net.minecraft.client.render.texture.Texture /*?} else {*/ /*String *//*?}*/, EarsFeatures> earsSkinFeatures = new WeakHashMap<>();
    //? if <1.9
    public static final com.unascribed.ears.common.util.EarsStorage.Key<Boolean> FORCE_TRANSLUCENT = new com.unascribed.ears.common.util.EarsStorage.Key<>(false);

    @Override
    public void onInitializeClient() {}

    //? if >=1.9 {
    /*public static EarsFeatures getEarsFeatures(net.minecraft.client.entity.living.player.ClientPlayerEntity peer) {
        net.minecraft.resource.Identifier skin = peer.getSkinTextureLocation();
        net.minecraft.client.render.texture.Texture tex = net.minecraft.client.Minecraft.getInstance().getTextureManager().get(skin);
        if (earsSkinFeatures.containsKey(tex)) {
            EarsFeatures feat = earsSkinFeatures.get(tex);
            com.unascribed.ears.common.EarsFeaturesStorage.INSTANCE.put(
                    /^? if >=1.7 {^/ peer.getGameProfile().getName(), peer.getGameProfile().getId() /^?} else {^/ /^peer.getName(), null ^//^?}^/, feat
            );
            if (!peer.isInvisible()) {
                return feat;
            }
        }
        return EarsFeatures.DISABLED;
    }
    *///?}
}