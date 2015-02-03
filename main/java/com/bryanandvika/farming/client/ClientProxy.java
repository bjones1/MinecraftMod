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

        public void registerItemModelResourceLocation(Item item) {
            // Tell Minecraft where to find the texture for a given item. To do
            // so, use `ItemModelMesher <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraft/client/renderer/ItemModelMesher.html>`_\ .
            // `register <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraft/client/renderer/ItemModelMesher.html#register(net.minecraft.item.Item,%20int,%20net.minecraft.client.resources.model.ModelResourceLocation)>`_ .
            // This method expects a `ModelResourceLocation <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraft/client/resources/model/ModelResourceLocation.html>`_.
            // This takes two strings; one, I presume, is a path to the resource
            // location. I don't know why the second one is "inventory".
            RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
            System.out.println("Loading texture " + item.getUnlocalizedName());
            renderItem.getItemModelMesher().register(item, 0, 
            		new ModelResourceLocation(FarmingMod.MODID + ":" + "gem_shard", //item.getUnlocalizedName(), 
            				"inventory"));
        }
       
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
