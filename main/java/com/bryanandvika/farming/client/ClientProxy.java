// ************************************
// ClientProxy.java -- Client-only code
// ************************************
// This file contains classes that will only be imported and run if this mod is
// running on a client.
//
// Packge and imports
// ==================
package com.bryanandvika.farming.client;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import com.bryanandvika.farming.CommonProxy;
import com.bryanandvika.farming.FarmingMod;

// ClientProxy
// ===========
// This proxy will be instantiantied only when this mod runs on the client side.
// See :ref:`sided-proxies` for more information.
public class ClientProxy extends CommonProxy {       
        @Override
        public void registerRenderers() {
// See more at: http://www.wuppy29.com/minecraft/1-8-tutorial/updating-1-7-to-1-8-part-2-basic-items/#sthash.Ln5a20tw.wFn11Yz1.dpuf
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
