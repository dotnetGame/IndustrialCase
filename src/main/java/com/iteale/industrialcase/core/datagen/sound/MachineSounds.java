package com.iteale.industrialcase.core.datagen.sound;

import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;

public class MachineSounds extends ICSoundProvider {
    /**
     * Creates a new instance of this data provider.
     *
     * @param generator The data generator instance provided by the event you are initializing this provider in.
     * @param modId     The mod ID of the current mod.
     * @param helper    The existing file helper provided by the event you are initializing this provider in.
     */
    protected MachineSounds(DataGenerator generator, String modId, ExistingFileHelper helper) {
        super(generator, modId, helper);
    }

    @Override
    public void registerSounds() {
        this.add(new ResourceLocation(IndustrialCase.MODID, "sounds/generators/generator_loop"),
                SoundDefinition.definition()
                        .subtitle("generator_loop")
                        .replace(true)
                        .with(SoundDefinition.Sound.sound(new ResourceLocation(IndustrialCase.MODID, "sounds/generators/generator_loop"), SoundDefinition.SoundType.SOUND))
        );
    }
}
