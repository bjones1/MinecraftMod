package com.bryanandvika.farming;

import java.util.Random;

import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

public class HelperClass {
    // Helper classes
    // ==============
    public static class ItemInit extends Item {
    	ItemInit(String name, CreativeTabs tab) {
    		super();
    		initItem(this, name, tab);
    	}
    }
    
    public static class ItemFoodInit extends ItemFood {
    	ItemFoodInit(String name, CreativeTabs tab, int food, float saturation, 
    			boolean isWolfMeat) {
    		super(food, saturation, isWolfMeat);
    		initItem(this, name, tab);
    	}    	
    }
    
    public static class BlockInit extends Block {
    	BlockInit(String name, CreativeTabs tab, Material material) {
    		super(Material.iron);
    		this.setUnlocalizedName(name);
    		this.setCreativeTab(tab);
    		GameRegistry.registerBlock(this, name);
    	}
    }
    
    // Copied from http://bedrockminer.jimdo.com/modding-tutorials/basic-modding/custom-armor/
    public static class ItemArmorInit extends ItemArmor {
    	public String textureName;
    	// type: which piece of armor: 0 = helmet, 1 = chestplate, 2 = leggings, 3 = boots;
    	ItemArmorInit(String name, String textureName, ArmorMaterial armor, int type) {
    		super(armor, 0 /* Don't know what this does */, type);
    		this.textureName = textureName;
    		initItem(this, name, CreativeTabs.tabCombat, 1);
    	}
 
    	@Override
    	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    	{
    	    return FarmingMod.MODID + ":textures/armor/" + this.textureName + "_" + (this.armorType == 2 ? "2" : "1") + ".png";
    	}
    }
    
    public static class ItemSwordInit extends ItemSword {
    	ItemSwordInit(ToolMaterial toolMaterial, String name) {
    		super(toolMaterial);
    		initItem(this, name, CreativeTabs.tabCombat, 1);
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
	    EntityList.entityEggs.put(Integer.valueOf(entityID), new EntityList.EntityEggInfo(entityID, primaryColor, secondaryColor));
    }


    
    // Helper functions
    // ================
    // Perform common initialization on a Minecraft Item.
    public static void initItem(Item item, String name, CreativeTabs tab) {
    	initItem(item, name, tab, 64);
    }

    public static void initItem(Item item, String name, CreativeTabs tab, int maxStackSize) {
        // The GUI name is therefore in assets.genericmod.lang/en_xx.lang, item.GemShardItem.name=yyy.
        item.setUnlocalizedName(name);
        // The texture is in assets.genericmod.textures.items/gem_shard.png.
        item.setMaxStackSize(maxStackSize);
        item.setCreativeTab(tab);
        // Register this item.
        // The second parameter is an unique registry identifier (not the displayed name)
        // Please don't use item1.getUnlocalizedName(), or you will make Lex sad
        GameRegistry.registerItem(item, name);    	
    }    
}
