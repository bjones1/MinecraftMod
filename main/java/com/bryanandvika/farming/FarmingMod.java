// ***************************************************
// FarmingMod.java -- Define the Minecraft Farming Mod
// ***************************************************
// This file defines the core of the Farming Mod, and contains the annotations so that the Minecraft Forge can load and install this into Minecraft.
//
//
// Packge and imports
// ==================
package com.bryanandvika.farming;

import java.util.Random;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
// Provide convenient access to all the `public static <http://en.wikipedia.org/wiki/Static_import>`_ members in these classes.
//
// In particular, I use this to help organize the code: I've placed generic portions of the mod into the ``HelperClass`` below, such as a routine to initialize a new item or block. Specific instances, such as *Black Foozo cookie*, are kept in this file.
import static com.bryanandvika.farming.HelperClass.*;
import static java.lang.System.out;
//
// Core Mod
// ========
// .. _modid:
//
// All Minecraft Forge mods must have a
// `Mod <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraftforge/fml/common/Mod.html>`_  `annotation <http://docs.oracle.com/javase/tutorial/java/annotations/>`_. This tells Forge to load this mod and pass it various events (init, etc.). The ``modid`` parameter
// must a unique name; all resources (texture, etc.) must then be placed in
// a directory named ``resources/assets/<modid>``.
@Mod(modid=FarmingMod.MODID, name="Farming mod", version="0.0.1")
public class FarmingMod {
    // Since the ``MODID`` is uesd in several places, define it once here.
	public static final String MODID = "farming";

    // The `instance <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraftforge/fml/common/Mod.Instance.html>`_ of your mod that Forge uses. As I understand it, I can either manually construct this (via ``public static FarmingMod instance = new FarmingMod()``) or request Forge to construct it for me by passing the ``value=MODID`` parameter.
    @Instance(value=MODID)
    public static FarmingMod instance;

// .. _sided-proxies:
//
// Sided proxies
// -------------
    // Minecraft uses a client-server architecture: visualization of the player
    // and the world are handled by the client, while the server takes care of
    // everything else: storing and updating the state of the world.
    // To allow the "Open to LAN" option, the client is actually a
    // client plus a small server; people who host Minecraft run an industrial-
    // strength server, with no client. Therefore, all Minecraft Forge mods
    // are split into three pieces:
    //
    // - Code that runs both on the client and on the server: everything in
    //   this file and loaded by this file.
    // - Code that runs only on the client (the ``ClientProxy``). For example,
    //   any imports from ``net.minecraft.client`` should only be used in the
    //   ``ClientProxy``. Otherwise, this code when running on the server will
    //   crash.
    // - Code that runs only on the server (the ``ServerProxy``). Since the
    //   client always includes a server, doing this probably won't cause
    //   crashes.
    //
    // The page at
    // http://greyminecraftcoder.blogspot.com/2013/11/how-forge-starts-up-your-code.html
    // provides a nice diagram of this.
    //
    // The parameters of `SidedProxy <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraftforge/fml/common/SidedProxy.html>`_
    // gives the package name of these proxies.
    @SidedProxy(clientSide="com.bryanandvika.farming.client.ClientProxy",
    		serverSide="com.bryanandvika.farming.CommonProxy")
    public static CommonProxy proxy;

// Instances for elements created by this mod
// ------------------------------------------
// It seems clearer to me to both define and construct objects in one place wherever possible. The traditional alternative would be to define an object here, then construct it in a preInit or Init method. The core Minecraft code seemt adopt my approach: most of the blocks and items are final, meaning they are construct and initialized at the same time.
    public static ItemInit acidSlimeItem; 
    public static ItemInit gemShardItem;
    public static ItemInit gemsItem;
    public static ItemInit sandWormsItem;

    public static ItemFoodInit blackFoozooCookieItem;
    public static ItemFoodInit peeledCactusFruitItem;
    public static ItemFoodInit smallPlumItem;
    public static ItemFoodInit orangePineappleItem;
    public static ItemFoodInit cheeseItem;
    public static ItemFoodInit dressedCheeseItem;
    public static ItemFoodInit rawTBoneSteakItem;
    public static ItemFoodInit smallPufferFishItem;


    // Copied from http://www.minecraftforge.net/wiki/Custom_Creative_Tabs
    public static CreativeTabsInit tabSauces;

    public static ItemFoodInit cactusSauceItem;

    // See http://bedrockminer.jimdo.com/modding-tutorials/basic-modding/custom-armor/.
    public static ArmorMaterial crabArmorMaterial;
    public static ItemArmorInit crabCapItem;
    public static ItemArmorInit crabChestpateItem;
    public static ItemArmorInit crabLeggingsItem;
    public static ItemArmorInit crabBootsItem;

    // Copied from http://bedrockminer.jimdo.com/modding-tutorials/basic-modding/custom-tools-swords/
    public static ToolMaterial crabToolMaterial;
    public static ItemSwordInit crabClawWand;

    public static BlockInit sandWormCanBlock;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        crabArmorMaterial = EnumHelper.addArmorMaterial("crab_armor", "crab_armor",
        		5, new int[]{1, 2, 1, 1}, 12);
//        crabCapItem = new ItemArmorInit(event, "crab_cap", "tutorial", crabArmorMaterial,
//        		0); // Which piece of armor: 0 = helmet, 1 = chestplate, 2 = leggings, 3 = boots
//        crabChestpateItem = new ItemArmorInit(event, "crab_chestplate", "tutorial", crabArmorMaterial,
//        		1);
//        crabLeggingsItem = new ItemArmorInit(event, "crab_leggings", "tutorial", crabArmorMaterial,
//        		2);
//        crabBootsItem = new ItemArmorInit(event, "crab_boots", "tutorial", crabArmorMaterial,
//        		3);


        crabToolMaterial = EnumHelper.addToolMaterial("crabToolMaterial",
        		0, 48, 2.0f, 1.0f, 5);
//        crabClawWand = new ItemSwordInit(event, crabToolMaterial, "crab_claw_wand");

        sandWormCanBlock = new BlockInit(event, "sand_worm_can",
        		CreativeTabs.tabDecorations, Material.iron);

        registerEntity("flame_creeper", instance, EntityFlameCreeper.class);
        registerEntity("eye", instance, EntityEye.class);
        registerEntity("llama", instance, EntityLlama.class);
        registerEntity("flame_orb", instance, EntityFlameOrb.class);
        registerEntity("ents_krope", instance, EntityEntsKrope.class);
        registerEntity("dirt_blaster", instance, EntityDirtBlaster.class);
        registerEntity("coyote", instance, EntityCoyote.class);

    }

    // This `EventHandler <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraftforge/fml/common/Mod.EventHandler.html>`_ annotation_ combined with the single `FMLInitializationEvent <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraftforge/fml/common/event/FMLInitializationEvent.html>`_ parameter causes Forge to call this function during Forge mod initialization.
    @EventHandler
    public void init(FMLInitializationEvent event) {
//        tabSauces = new CreativeTabsInit("tab_sauces", smallPlumItem);
        
    	acidSlimeItem = new ItemInit(event, "acid_slime", CreativeTabs.tabMisc);
    	gemShardItem = new ItemInit(event, "gem_shard", CreativeTabs.tabMaterials);
    	gemsItem = new ItemInit(event, "gems", CreativeTabs.tabMisc);  
        sandWormsItem = new ItemInit(event, "sand_worms", CreativeTabs.tabMaterials);
        
        blackFoozooCookieItem = new ItemFoodInit(event, "black_foozoo_cookie", CreativeTabs.tabFood,
        		3, 0.3f, false);
        peeledCactusFruitItem = new ItemFoodInit(event, "peeled_cactus_fruit", CreativeTabs.tabFood,
       		    4, 0.3f, false);
        smallPlumItem = new ItemFoodInit(event, "small_plum", CreativeTabs.tabFood,
        		1, 0.3f, false);
        orangePineappleItem = new ItemFoodInit(event, "orange_pineapple", CreativeTabs.tabFood,
         		2, 5.0f, false);
        cheeseItem = new ItemFoodInit(event, "cheese", CreativeTabs.tabFood,
        		6, 0.5f, false);
        dressedCheeseItem = new ItemFoodInit(event, "dressed_cheese", CreativeTabs.tabFood,
        		7, 0.6f, false);
        rawTBoneSteakItem = new ItemFoodInit(event, "raw_t_bone_steak", CreativeTabs.tabFood,
        		2, .8f, true);
        smallPufferFishItem = new ItemFoodInit(event, "small_puffer_fish", CreativeTabs.tabFood,
        		2, .15f, false);
        cactusSauceItem = new ItemFoodInit(event, "cactus_sauce", CreativeTabs.tabFood, 1, 0.3f, false);

        proxy.registerRenderers();

// Recipes
// -------
        // Acid slime

        GameRegistry.addShapelessRecipe(new ItemStack(acidSlimeItem), Items.slime_ball,
     		   Items.gold_nugget);
        GameRegistry.addSmelting(acidSlimeItem, new ItemStack(Items.nether_wart), 0.2f);
        MinecraftForge.EVENT_BUS.register(this);

        // Black Foozoo Cookie
        GameRegistry.addRecipe(new ItemStack(blackFoozooCookieItem),
        		" o ", "ouo", " o ", 'u', Items.cookie, 'o', Items.apple);

        // Gem Shard
        GameRegistry.addRecipe(new ItemStack(Items.diamond),
        		" xx", "xxx", "xxx", 'x', gemShardItem);
        GameRegistry.addShapelessRecipe(new ItemStack(gemShardItem, 16), Items.emerald);

        // Sand Worm Can
        GameRegistry.addRecipe(new ItemStack(sandWormCanBlock), "www", "sss", "sss",
        		'w', Items.iron_ingot, 's', FarmingMod.sandWormsItem);
    }

    // This EventHandler_ combined with the `FMLPostInitializationEvent <file:///C:/Users/bjones/Documents/forge-1.8-11.14.0.1290-1.8-javadoc/net/minecraftforge/fml/common/event/FMLPostInitializationEvent.html>`_ as a method parameter causes Forge to call this in the post Mod initialization phase.
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // Stub Method
    }

    @SubscribeEvent
    public void onEntityDrop(LivingDropsEvent event) {
    	if (event.entityLiving instanceof EntityZombie) {
    		event.entityLiving.dropItem(FarmingMod.acidSlimeItem, 1);
  	  	}
    }

    // Entities
    // ========
    // Taken from http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571558-1-7-2-forge-add-new-block-item-entity-ai-creative.
    // See http://www.minecraftforge.net/wiki/Mob_Tut_%281.6.2%29 for more details.
    public static class EntityFlameCreeper extends EntityCreeper {
    	public EntityFlameCreeper(World world) {
    		super(world);
    	}

    	@Override
    	protected void applyEntityAttributes()
    	{
    		super.applyEntityAttributes();
	    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
	    	this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(45.0D);
	    	this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
	    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
	    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
    	}

    	// Never gets called -- creepers attack using an explosion, so perhaps this is the
    	// wrong way?
    	@Override
    	public boolean attackEntityAsMob(Entity entity) {
	    	System.out.println("attack");
    		boolean canDamage = super.attackEntityAsMob(entity);
    		if (canDamage) {
    			entity.setFire(5);
    		}
    		return canDamage;
    	}
    }

    @SideOnly(Side.CLIENT)
    public static class RenderFlameCreeper extends RenderCreeper
    {
        public RenderFlameCreeper(RenderManager p_i46186_1_) {
			super(p_i46186_1_);
			// TODO Auto-generated constructor stub
		}

		private static final ResourceLocation Your_Texture =
        	new ResourceLocation(MODID + ":textures/entity/flame_creeper.png");

        @Override
        protected ResourceLocation getEntityTexture(Entity par1Entity)
        {
            return Your_Texture;
        }
    }

    public static class EntityEye extends EntitySlime {
    	public EntityEye(World world) {
    		super(world);
    	}
    }

    public static class EntityLlama extends EntitySheep {

		public EntityLlama(World world) {
			super(world);
		}

    	@Override
        protected void applyEntityAttributes()
        {
            super.applyEntityAttributes();
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0D);
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
        }
    }

    @SideOnly(Side.CLIENT)
    public static class RenderLlama extends RenderSheep
    {
        public RenderLlama(RenderManager p_i1266_1_, ModelBase p_i1266_2_,
				float p_i1266_3_) {
			super(p_i1266_1_, p_i1266_2_, p_i1266_3_);
			// TODO Auto-generated constructor stub
		}

		private static final ResourceLocation Your_Texture =
        	new ResourceLocation(MODID + ":textures/entity/sheep_skin.png");

        @Override
        protected ResourceLocation getEntityTexture(Entity par1Entity)
        {
            return Your_Texture;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class ModelLlama extends ModelQuadruped {
        private float field_78152_i;
        private static final String __OBFID = "CL_10000852";
        private static int height = 12;

        public ModelLlama()
        {
            super(12 + ModelLlama.height, 0.0F);
            this.head = new ModelRenderer(this, 0, 0);
            this.head.addBox(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
            this.head.setRotationPoint(0.0F, 6.0F - ModelLlama.height, -8.0F);
            this.body = new ModelRenderer(this, 28, 8);
            this.body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 1.75F);
            this.body.setRotationPoint(0.0F, 5.0F - ModelLlama.height, 2.0F);
            float f = 0.5F;
            this.leg1 = new ModelRenderer(this, 0, 16);
            this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6 + ModelLlama.height, 4, f);
            this.leg1.setRotationPoint(-3.0F, 12.0F - ModelLlama.height, 7.0F);
            this.leg2 = new ModelRenderer(this, 0, 16);
            this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6 + ModelLlama.height, 4, f);
            this.leg2.setRotationPoint(3.0F, 12.0F - ModelLlama.height, 7.0F);
            this.leg3 = new ModelRenderer(this, 0, 16);
            this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6 + ModelLlama.height, 4, f);
            this.leg3.setRotationPoint(-3.0F, 12.0F - ModelLlama.height, -5.0F);
            this.leg4 = new ModelRenderer(this, 0, 16);
            this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6 + ModelLlama.height, 4, f);
            this.leg4.setRotationPoint(3.0F, 12.0F - ModelLlama.height, -5.0F);
        }

        /**
         * Used for easily adding entity-dependent animations. The second and third float params here are the same second
         * and third as in the setRotationAngles method.
         */
        public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_)
        {
            super.setLivingAnimations(p_78086_1_, p_78086_2_, p_78086_3_, p_78086_4_);
            //this.head.rotationPointY = 6.0F + ((EntitySheep)p_78086_1_).func_70894_j(p_78086_4_) * 9.0F;
            //this.field_78152_i = ((EntitySheep)p_78086_1_).func_70890_k(p_78086_4_);
        }

        /**
         * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
         * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
         * "far" arms and legs can swing at most.
         */
        public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
        {
            super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
            this.head.rotateAngleX = this.field_78152_i;
        }
    }

    // Coyote
    // ======
    // This is the same as a wolf, except for its skin (texture). So, only define a
    // renderer class, then register it.
    @SideOnly(Side.CLIENT)
    public static class RenderCoyote extends RenderWolf
    {
        public RenderCoyote(RenderManager p_i1266_1_, ModelBase p_i1266_2_,
				float p_i1266_3_) {
			super(p_i1266_1_, p_i1266_2_, p_i1266_3_);
			// TODO Auto-generated constructor stub
		}

		private static final ResourceLocation Your_Texture =
        	new ResourceLocation(MODID + ":textures/entity/sheep_skin.png");

        @Override
        protected ResourceLocation getEntityTexture(Entity par1Entity)
        {
            return Your_Texture;
        }
    }

    public static class EntityCoyote extends EntityWolf {

		public EntityCoyote(World p_i1696_1_) {
			super(p_i1696_1_);
			// TODO Auto-generated constructor stub
		}
    }


    
    // Flame Orb
    // =========
    public static class EntityFlameOrb extends EntityBlaze {
    	public EntityFlameOrb(World world) {
    		super(world);
    	}

    	@Override
    	protected void applyEntityAttributes()
    	{
    		super.applyEntityAttributes();
	    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
	    	this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
	    	this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(16.0D);
	    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.00D);
	    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.8D);
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public static class RenderFlameOrb extends RenderLiving
    {
        public RenderFlameOrb(RenderManager p_i46186_1_) {
            super(p_i46186_1_, new ModelFlameOrb(), 0.5F);
			// TODO Auto-generated constructor stub
		}

		private static final ResourceLocation Your_Texture =
        	new ResourceLocation(MODID + ":textures/entity/flame_creeper.png");

        @Override
        protected ResourceLocation getEntityTexture(Entity par1Entity)
        {
            return Your_Texture;
        }
    }

    public static class ModelFlameOrb extends ModelBase
    {
      //fields
        ModelRenderer Orb;
        ModelRenderer Shield1;
        ModelRenderer Shield2;
      
      public ModelFlameOrb()
      {
        textureWidth = 64;
        textureHeight = 32;
        
          Orb = new ModelRenderer(this, 0, 0);
          Orb.addBox(0F, 0F, 0F, 7, 7, 7);
          Orb.setRotationPoint(0F, 0F, 0F);
          Orb.setTextureSize(64, 32);
          Orb.mirror = true;
          setRotation(Orb, 0F, 0F, 0F);
          Shield1 = new ModelRenderer(this, 0, 0);
          Shield1.addBox(-3F, -3F, -4F, 14, 0, 14);
          Shield1.setRotationPoint(0F, 0F, 0F);
          Shield1.setTextureSize(64, 32);
          Shield1.mirror = true;
          setRotation(Shield1, 0F, 0F, 0F);
          Shield2 = new ModelRenderer(this, 0, 0);
          Shield2.addBox(-3F, -4F, -11F, 14, 0, 14);
          Shield2.setRotationPoint(0F, 0F, 0F);
          Shield2.setTextureSize(64, 32);
          Shield2.mirror = true;
          setRotation(Shield2, 1.594067F, 0F, 0F);
      }
      
      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
      {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Orb.render(f5);
        Shield1.render(f5);
        Shield2.render(f5);
      }
      
      private void setRotation(ModelRenderer model, float x, float y, float z)
      {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
      }
      
      public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
      {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
      }

    }

    
    // Ents Krope
    // ==========
    // To create a new mob, I must:
    //
    // - Define a new entity, where health/movement speed/etc. is specified.
    // - Define a new renderer, where the texture is loaded.
    // - Define a new model, which gives the 3D geometry and motion of the mob.
    // - Register all three for use in game, then register the entity as well.
    public static class EntityEntsKrope extends EntityMob {
    	public EntityEntsKrope(World world) {
    		super(world);
    	}

    	@Override
    	protected void applyEntityAttributes()
    	{
    		super.applyEntityAttributes();
	    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
	    	this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(45.0D);
	    	this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(6.0D);
	    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(4.00D);
	    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.8D);
    	}
    }

    @SideOnly(Side.CLIENT)
    public static class RenderEntsKrope extends RenderLiving
    {
        public RenderEntsKrope(RenderManager p_i46186_1_) {
            super(p_i46186_1_, new ModelEntsKrope(), 0.5F);
			// TODO Auto-generated constructor stub
		}

		private static final ResourceLocation Your_Texture =
        	new ResourceLocation(MODID + ":textures/entity/sheep_skin.png");

        @Override
        protected ResourceLocation getEntityTexture(Entity par1Entity)
        {
            return Your_Texture;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class ModelEntsKrope extends ModelBase
    {
      //fields
        ModelRenderer Head;
        ModelRenderer Side__left;
        ModelRenderer Side__rite;
        ModelRenderer Shape1;
        ModelRenderer Shape2;
        ModelRenderer Shape3;
        ModelRenderer Shape4;
        ModelRenderer Shape5;

      public ModelEntsKrope()
      {
        textureWidth = 64;
        textureHeight = 32;

          Head = new ModelRenderer(this, 0, 0);
          Head.addBox(0F, 0F, 0F, 8, 8, 8);
          Head.setRotationPoint(0F, 1F, 0F);
          Head.setTextureSize(64, 32);
          Head.mirror = true;
          setRotation(Head, 0F, 0F, 0F);
          Side__left = new ModelRenderer(this, 0, 0);
          Side__left.addBox(0F, 0F, 0F, 8, 8, 8);
          Side__left.setRotationPoint(-8F, 8F, 0F);
          Side__left.setTextureSize(64, 32);
          Side__left.mirror = true;
          setRotation(Side__left, 0F, 0F, 0F);
          Side__rite = new ModelRenderer(this, 0, 0);
          Side__rite.addBox(8F, 8F, 0F, 8, 8, 8);
          Side__rite.setRotationPoint(0F, 0F, 0F);
          Side__rite.setTextureSize(64, 32);
          Side__rite.mirror = true;
          setRotation(Side__rite, 0F, 0F, 0F);
          Shape1 = new ModelRenderer(this, 0, 0);
          Shape1.addBox(0F, 16F, 0F, 8, 8, 8);
          Shape1.setRotationPoint(0F, 0F, 0F);
          Shape1.setTextureSize(64, 32);
          Shape1.mirror = true;
          setRotation(Shape1, 0F, 0F, 0F);
          Shape2 = new ModelRenderer(this, 0, 0);
          Shape2.addBox(0F, 0F, 0F, 8, 8, 8);
          Shape2.setRotationPoint(0F, -5F, 0F);
          Shape2.setTextureSize(64, 32);
          Shape2.mirror = true;
          setRotation(Shape2, 0F, 0F, 0F);
          Shape3 = new ModelRenderer(this, 0, 0);
          Shape3.addBox(0F, 0F, 0F, 8, 8, 8);
          Shape3.setRotationPoint(-8F, -13F, 0F);
          Shape3.setTextureSize(64, 32);
          Shape3.mirror = true;
          setRotation(Shape3, 0F, 0F, 0F);
          Shape4 = new ModelRenderer(this, 0, 0);
          Shape4.addBox(0F, 0F, 0F, 8, 8, 8);
          Shape4.setRotationPoint(8F, -13F, 0F);
          Shape4.setTextureSize(64, 32);
          Shape4.mirror = true;
          setRotation(Shape4, 0F, 0F, 0F);
          Shape5 = new ModelRenderer(this, 0, 0);
          Shape5.addBox(0F, 0F, 0F, 8, 1, 1);
          Shape5.setRotationPoint(0F, -14F, 3F);
          Shape5.setTextureSize(64, 32);
          Shape5.mirror = true;
          setRotation(Shape5, 0F, 0F, 0F);
      }

      public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
      {
        super.render(entity, f, f1, f2, f3, f4, f5);
//        setRotationAngles(f, f1, f2, f3, f4, f5);
        Head.render(f5);
        Side__left.render(f5);
        Side__rite.render(f5);
        Shape1.render(f5);
        Shape2.render(f5);
        Shape3.render(f5);
        Shape4.render(f5);
        Shape5.render(f5);
      }

      private void setRotation(ModelRenderer model, float x, float y, float z)
      {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
      }

//      public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
//      {
//        super.setRotationAngles(f, f1, f2, f3, f4, f5);
//      }

    }

    // Dirt Blaster
    // ============
    // To create a new mob, I must:
    //
    // - Define a new entity, where health/movement speed/etc. is specified.
    // - Define a new renderer, where the texture is loaded.
    // - Define a new model, which gives the 3D geometry and motion of the mob.
    // - Register all three for use in game, then register the entity as well.
    public static class EntityDirtBlaster extends EntityMob {
    	public EntityDirtBlaster(World world) {
    		super(world);
    	}

    	@Override
    	protected void applyEntityAttributes()
    	{
    		super.applyEntityAttributes();
	    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
	    	this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(45.0D);
	    	this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(6.0D);
	    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(4.00D);
	    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.8D);
    	}
    }

    @SideOnly(Side.CLIENT)
    public static class RenderDirtBlaster extends RenderLiving
    {
        public RenderDirtBlaster(RenderManager p_i46186_1_) {
            super(p_i46186_1_, new ModelDirtBlaster(), 0.5F);
			// TODO Auto-generated constructor stub
		}

		private static final ResourceLocation Your_Texture =
        	new ResourceLocation(MODID + ":textures/entity/sheep_skin.png");

        @Override
        protected ResourceLocation getEntityTexture(Entity par1Entity)
        {
            return Your_Texture;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class ModelDirtBlaster extends ModelBase
    {
      //fields
        ModelRenderer Body;
        ModelRenderer Spout;
        ModelRenderer Eye;
        ModelRenderer Eye_Lide;
        ModelRenderer Eye2;
        ModelRenderer Eye_Lide2;


      public ModelDirtBlaster()
      {
        textureWidth = 64;
        textureHeight = 32;

        Body = new ModelRenderer(this, 0, 0);
        Body.addBox(0F, 0F, 0F, 8, 8, 8);
        Body.setRotationPoint(1F, 2F, 3F);
        Body.setTextureSize(64, 32);
        Body.mirror = true;
        setRotation(Body, 0F, 0F, 0F);
        Spout = new ModelRenderer(this, 0, 0);
        Spout.addBox(0F, 0F, 0F, 4, 3, 3);
        Spout.setRotationPoint(-3F, 4F, 6F);
        Spout.setTextureSize(64, 32);
        Spout.mirror = true;
        setRotation(Spout, 0F, 0F, 0F);
        Eye = new ModelRenderer(this, 0, 0);
        Eye.addBox(0F, 0F, 4F, 1, 1, 1);
        Eye.setRotationPoint(-1F, 3F, 0F);
        Eye.setTextureSize(64, 32);
        Eye.mirror = true;
        setRotation(Eye, 0F, 0F, 0F);
        Eye_Lide = new ModelRenderer(this, 0, 0);
        Eye_Lide.addBox(0F, 0F, 0F, 1, 2, 2);
        Eye_Lide.setRotationPoint(0F, 3F, 4F);
        Eye_Lide.setTextureSize(64, 32);
        Eye_Lide.mirror = true;
        setRotation(Eye_Lide, 0F, 0F, 0F);
        Eye2 = new ModelRenderer(this, 0, 0);
        Eye2.addBox(0F, 0F, 0F, 1, 1, 1);
        Eye2.setRotationPoint(-1F, 3F, 10F);
        Eye2.setTextureSize(64, 32);
        Eye2.mirror = true;
        setRotation(Eye2, 0F, 0F, 0F);
        Eye_Lide2 = new ModelRenderer(this, 0, 0);
        Eye_Lide2.addBox(0F, 0F, 0F, 1, 2, 2);
        Eye_Lide2.setRotationPoint(0F, 3F, 9F);
        Eye_Lide2.setTextureSize(64, 32);
        Eye_Lide2.mirror = true;
        setRotation(Eye_Lide2, 0F, 0F, 0F);
    }
    
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
      super.render(entity, f, f1, f2, f3, f4, f5);
//      setRotationAngles(f, f1, f2, f3, f4, f5);
      Body.render(f5);
      Spout.render(f5);
      Eye.render(f5);
      Eye_Lide.render(f5);
      Eye2.render(f5);
      Eye_Lide2.render(f5);
    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
    }
    
//    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
//    {
//      super.setRotationAngles(f, f1, f2, f3, f4, f5);
//    }

    }
}


// gamemode survival
// gamemode creative
