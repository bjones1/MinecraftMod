// ****************
// HelperClass.java
// ****************
// These routines make it easier to create a mod by performing common
// initialization functions. Everything is static, so it can be statically
// imported.
//
// Package and imports
// ===================
package com.bryanandvika.farming;

import java.util.Random;

import com.bryanandvika.farming.BuilderClass.GenericBuilder;

import java.util.LinkedList;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLStateEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.world.World;
//
// Concepts
// ========
// Some essential concepts upon which the classes in this file depend are:
//
// Name
// ----
// A ``name`` is used for several purposes: to define an
// ``unlocalizedName``, which Minecraft uses to look up a locale-
// specific name and to find a model file (see below for details); and to
// provide a mod-unique name when when `registering the item <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraftforge/fml/common/registry/GameRegistry.html#registerItem(net.minecraft.item.Item,%20java.lang.String)>`_
// with Forge.
//
// Naming and Internationalization
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
// Since Minecraft is sold all over the world, there must be a way to display
// strings in it in multiple languages. To do this, Minecraft uses an internal,
// unlocalized name, then relies on language-specific files to map this name to
// its translation various language and regions. Forge uses the same approach.
// All names for a given language and region are stored in a language file named
// ``resources/assets/``\ |modid|\ ``/lang/xx_YY.lang``, where xx = `the two-letter
// name of a language <http://en.wikipedia.org/wiki/ISO_639-1>`_ in lowercase
// letters and YY = `the two-letter country code
// <http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2>`_ in capital letters.
// In this file, a line of the form ``object.unlocalizedName=Name players see``
// assigns a name, where ``object`` is the specific Minecraft object (e.g.
// ``item``, ``block``, etc.) and ``unlocalizedName`` is the mod-assigned
// unlocalized name. For example, given an unlocalized name of ``color_box`` for
// a new item, the line ``item.color_box=Color Box`` in the file ``en_US.lang``
// and the line ``item.color_box=Colour Box`` in the file ``en_GB.lang`` would
// give then item the name "Color Box" in the US and the name "Colour Box" in
// Great Britain. For examples in this mod, see
// :download:`../../../../resources/assets/farming/lang/en_US.lang`.
//
// .. |modid| replace:: :ref:`modid <modid>`
//
// Naming and textures
// ^^^^^^^^^^^^^^^^^^^
// An ``unlocalizedName`` also gives the name of a `.json
// <http://www.json.org/>`_ model file located in
// ``resources/assets/``\ |modid|\ ``/models/<object_type>`` which must be present
// for every object in a mod, where ``<object_type>`` is ``item``, ``block``, etc.
// This file is used to specify the name of the texture to be used when
// rendering an object, among other things. See
// :download:`../../../../resources/assets/farming/models/item/gem_shard.json`
// for more information.
//
// TODO: More details on this.
//
// BuilderClass
// ===========
// Provides a set of classes coupled with a means to construct them at the 
// appropriate times (at construction, or during preInit, init, or postInit).
public class BuilderClass {
	
	// The Minecraft Block class has a protected constructor. Make it public.
	public static class BasicBlock extends Block {
		public BasicBlock(Material material) {
			super(material);
		}
	}

    // Copied from http://bedrockminer.jimdo.com/modding-tutorials/basic-modding/custom-armor/
    public static class ItemArmorInit extends ItemArmor {
    	public String textureName;
    	// type: which piece of armor: 0 = helmet, 1 = chestplate, 2 = leggings, 3 = boots;
    	ItemArmorInit(FMLStateEvent event, String name, String textureName, ArmorMaterial armor, int type) {
    		super(armor, 0 /* Don't know what this does */, type);
    		this.textureName = textureName;
//    		initItem(this, event, name, CreativeTabs.tabCombat, 1);
    	}

    	@Override
    	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    	{
    	    return FarmingMod.MODID + ":textures/armor/" + this.textureName + "_" + (this.armorType == 2 ? "2" : "1") + ".png";
    	}
    }

    public static class ItemSwordInit extends ItemSword {
    	ItemSwordInit(FMLStateEvent event, ToolMaterial toolMaterial, String name) {
    		super(toolMaterial);
//    		initItem(this, event, name, CreativeTabs.tabCombat, 1);
    	}
    }

    public static class CreativeTabsInit extends CreativeTabs {
    	Item iconItem;
    	CreativeTabsInit(String name, Item iconItem) {
    		super(name);
    		this.iconItem = iconItem;
    	}

        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return iconItem;
        }
    }

    public static void registerEntity(String name, Object modInstance, Class entityClass)
    {
	    int entityID = EntityRegistry.findGlobalUniqueEntityId();
	    long seed = name.hashCode();
	    Random rand = new Random(seed);
	    int primaryColor = rand.nextInt() * 16777215;
	    int secondaryColor = rand.nextInt() * 16777215;

	    EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
	    EntityRegistry.registerModEntity(entityClass, name, entityID, modInstance, 64, 1, true);
	    EntityList.entityEggs.put(Integer.valueOf(entityID), 
	    		new EntityList.EntityEggInfo(entityID, primaryColor, secondaryColor));
    }



// Helper functions
// ================
    // Perform common initialization on a Minecraft Item.
    public static void preInitItem(Item item, String name, CreativeTabs tab) {
        // The GUI name is therefore in assets.genericmod.lang/en_xx.lang, item.GemShardItem.name=yyy.
        item.setUnlocalizedName(name);
        // The texture is in assets.genericmod.textures.items/gem_shard.png.
        item.setCreativeTab(tab);
        // Register this item.
        // The second parameter is an unique registry identifier (not the displayed name)
        // Please don't use item1.getUnlocalizedName(), or you will make Lex sad
        GameRegistry.registerItem(item, name);
    }
    
    public static void initItem(Item item, String name, FMLInitializationEvent event) {
        // Tell only the client where to find the texture for this item.
        if (event.getSide() == Side.CLIENT) {
            // Tell Minecraft where to find the texture for a given item. To do
            // so, use `ItemModelMesher <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraft/client/renderer/ItemModelMesher.html>`_\ .
            // `register <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraft/client/renderer/ItemModelMesher.html#register(net.minecraft.item.Item,%20int,%20net.minecraft.client.resources.model.ModelResourceLocation)>`_ .
            // This method expects a `ModelResourceLocation <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraft/client/resources/model/ModelResourceLocation.html>`_.
            // This takes two strings; one, I presume, is a path to the resource
            // location. I don't know why the second one is "inventory".
            RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
            renderItem.getItemModelMesher().register(item, 0, 
            		new ModelResourceLocation(FarmingMod.MODID + ":" + name, "inventory"));
        }
    }
    
// Builders
// ========
    // A list of builders, on which preInit, Init, and postInit will be invoked.
    protected LinkedList<GenericBuilder<Object>> genericBuilderList = 
    		new LinkedList<GenericBuilder<Object>>();
    
    public void preInit(FMLPreInitializationEvent event) {
    	for (GenericBuilder<Object> gb : genericBuilderList) {
    		gb.preInit(event);
    	}
    }
    
	public void init(FMLInitializationEvent event) {
    	for (GenericBuilder<Object> gb : genericBuilderList) {
    		gb.init(event);
    	}
	}
	
	public void postInit(FMLPostInitializationEvent event) {
    	for (GenericBuilder<Object> gb : genericBuilderList) {
    		gb.postInit(event);
    	}
	}
	
    // The generic builder only holds a name and defines empty preInit, init, and postInit methods.
    protected class GenericBuilder<O> {
    	// The object which this class builds.
    	O o;
    	// .. _name::
    	//
   	    // The name of this item.
    	String name;
    	
    	GenericBuilder(
    	  // See name_.
          String name) {
    		
    		this.name = name;
    		genericBuilderList.add((GenericBuilder<Object>) this);
    	}
    	
    	public void preInit(FMLPreInitializationEvent event) {
    	}
    	
    	public void init(FMLInitializationEvent event) {
    	}
    	
    	public void postInit(FMLPostInitializationEvent event) {
    	}
    }
    
    
    // Build an item.
    public class ItemBuilder extends GenericBuilder<Item> {
    	// .. _tab:
    	//
		// The `creative-mode tab <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraft/creativetab/CreativeTabs.html>`_
	  	// in which to place this item.
        CreativeTabs tab;
    	
		ItemBuilder(
		 // See name_.
		 String name,
		 // See tab_.
	  	 CreativeTabs tab) {
			
			super(name);
    		o = new Item();
			this.tab = tab;
		}

    	public void preInit(FMLPreInitializationEvent event) {
    		preInitItem(o, name, tab);
    	}
    	
    	public void init(FMLInitializationEvent event) {
            initItem(o, name, event);
    	}	
    }

    
    // Create and define a new `food
    // <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraft/item/ItemFood.html>`_.
    // Refer to `hunger mechanics <http://minecraft.gamepedia.com/Hunger#Mechanics>`_
    // for more information on the meaning of food and saturation below.
    public class ItemFoodBuilder extends ItemBuilder {

	    ItemFoodBuilder(
		  // See name_.
		  String name,
		  // See tab_.
		  CreativeTabs tab,
		  // The amount of food points added when this item is eaten,
		  // each each point = 1/2 heart.
		  int amount,
		  // The food saturation level ratio: food saturation increases by
		  // food*saturation when this food is eaten.
		  float saturation,
		  // True if this food is wolf meat.
		  boolean isWolfMeat) {
	    		
			super(name, tab);
			o = new ItemFood(amount, saturation, isWolfMeat);
	    }
	    
	    // See above for parameter definitions.
	    ItemFoodBuilder(String name, CreativeTabs tab, int amount, 
	    		boolean isWolfMeat) {
			super(name, tab);
			o = new ItemFood(amount, isWolfMeat);
	    }
    }

    
    // A class to simplify adding a block to a mod.
    public class BlockBuilder extends GenericBuilder<Block> {
    	// See tab_.
    	CreativeTabs tab;
    	
        // Create and define a new `block <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraft/block/Block.html>`_.
    	BlockBuilder(
    	  // See name_.
    	  String name,
    	  // See tab_.
    	  CreativeTabs tab,
    	  // The material <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraft/block/material/Material.html>`_
    	  // which composes this block.
    	  Material material) {
    		super(name);
    		o = new BasicBlock(material);
    		this.tab = tab;
    	}
    	
    	public void preInit(FMLPreInitializationEvent event) {
    		o.setUnlocalizedName(name);
    		o.setCreativeTab(tab);
    		// Parameterize!
    		o.setLightLevel(1.0f);
    		GameRegistry.registerBlock(o, name);
    	}
    	
    	public void init(FMLInitializationEvent event) {
    		initItem(Item.getItemFromBlock(o), name, event);
    	}
    }
}
