package com.bryanandvika.farming.client;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraftforge.client.MinecraftForgeClient;

import com.bryanandvika.farming.CommonProxy;
import com.bryanandvika.farming.FarmingMod;

public class ClientProxy extends CommonProxy {
       
        @Override
        public void registerRenderers() {
//            RenderingRegistry.registerEntityRenderingHandler(FarmingMod.EntityFlameCreeper.class, 
//            	new FarmingMod.RenderFlameCreeper());
//            RenderingRegistry.registerEntityRenderingHandler(FarmingMod.EntityLlama.class, 
//                    new FarmingMod.RenderLlama(new FarmingMod.ModelLlama(), new ModelSheep2(), 0.5F));
//            RenderingRegistry.registerEntityRenderingHandler(FarmingMod.EntityEntsKrope.class, 
//                    new FarmingMod.RenderEntsKrope(new FarmingMod.ModelEntsKrope(), 0.5F));
//            RenderingRegistry.registerEntityRenderingHandler(FarmingMod.EntityCoyote.class, 
//                    new FarmingMod.RenderCoyote(new ModelWolf(), new ModelWolf(), 0.5F));
        }
       
}