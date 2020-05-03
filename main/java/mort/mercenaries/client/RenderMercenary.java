package mort.mercenaries.client;

import mort.mercenaries.Reference;
import mort.mercenaries.common.EntityMercenary;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderMercenary extends LivingRenderer<EntityMercenary, PlayerModel<EntityMercenary>> {

	private ResourceLocation texture = new ResourceLocation(Reference.modId,"textures/entity/skin_base.png");

	public RenderMercenary(EntityRendererManager renderManager) {
		super(renderManager, new PlayerModel<>(1F, false), 0.5F);
		this.addLayer(new BipedArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.0F)));
		this.addLayer(new HeldItemLayer<>(this));
	}

	/*@Override
	public void renderName(EntityMercenary entity, double x, double y, double z) {
		RayTraceResult rtr = Minecraft.getMinecraft().objectMouseOver;
		if (rtr.typeOfHit == RayTraceResult.Type.ENTITY && rtr.entityHit == entity){
			String text = entity.getActiveOrderName();
			this.renderLivingLabel(entity, text, x, y, z, 64);
		}

	}*/



    /*@Override
    protected void renderLivingAt(EntityLivingBase par1EntityLiving, double par2, double par4, double par6) {
		if( ((EntityMercenaryOld)par1EntityLiving).sleeping ){
		    GL11.glRotatef(par1EntityLiving.rotationYaw, 0.0F, 1.0F, 0.0F);
		}
		super.renderLivingAt(par1EntityLiving, par2, par4, par6 );
	}*/



	@Override
	public ResourceLocation getEntityTexture(EntityMercenary entity) {
		return texture;
	}

}
