package mort.mercenaries.client;

import mort.mercenaries.EntityMercenary;
import mort.mercenaries.Reference;
import mort.mercenaries.ai.MercenaryAI;
import mort.mercenaries.ai.OrderWork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author Martin
 */
@SideOnly( Side.CLIENT )
public class RenderMercenary extends RenderLivingBase<EntityMercenary>{

	private ResourceLocation texture = new ResourceLocation(Reference.modId,"textures/entity/skinBase.png");
	
    public RenderMercenary(RenderManager rmgr){
		super( rmgr, new ModelBiped(), 0.5f );
		this.addLayer(new LayerHeldItem(this));
    }

	@Override
	public void renderName(EntityMercenary entity, double x, double y, double z) {
		RayTraceResult rtr = Minecraft.getMinecraft().objectMouseOver;
		if(rtr.typeOfHit == RayTraceResult.Type.ENTITY)
			if(rtr.entityHit == entity) {
				int orderId = (((EntityMercenary)entity).flags>>1)&3;
				int workTime = (((EntityMercenary)entity).flags>>3)&3;
				String text = net.minecraft.util.text.translation.I18n.translateToLocal( "order."+MercenaryAI.orderTypes[orderId].getSimpleName() );
				if( MercenaryAI.orderTypes[orderId] == OrderWork.class )
					text += " (" + net.minecraft.util.text.translation.I18n.translateToLocal( "work.time."+workTime ) + ")";
				this.renderLivingLabel(entity, text, x, y, z, 64);
			}

	}

    /*@Override
    protected void renderLivingAt(EntityLivingBase par1EntityLiving, double par2, double par4, double par6) {
		if( ((EntityMercenaryOld)par1EntityLiving).sleeping ){
		    GL11.glRotatef(par1EntityLiving.rotationYaw, 0.0F, 1.0F, 0.0F);
		}
		super.renderLivingAt(par1EntityLiving, par2, par4, par6 );
	}*/

	@Override
	protected ResourceLocation getEntityTexture(EntityMercenary entity) {
		return texture;
	}

}
