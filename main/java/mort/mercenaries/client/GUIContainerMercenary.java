/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mort.mercenaries.client;

import mort.mercenaries.Reference;
import mort.mercenaries.common.EntityMercenary;
import mort.mercenaries.inventory.ContainerMercenary;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Martin
 */
public class GUIContainerMercenary extends GuiContainer{

	GuiButton profButton;
	GuiButton setHomeButton;
	
	public GUIContainerMercenary(EntityMercenary merc, EntityPlayer plr ) {
		super( new ContainerMercenary( merc, plr ) );
		this.xSize = 176;
		this.ySize = 222;
		this.allowUserInput = true;
	}

	@Override
	public void initGui() {
		super.initGui();
		int minX = (this.width - this.xSize) / 2;
		int minY = (this.height - this.ySize) / 2;
		profButton = new GuiButton( 0, minX + 44, minY + 108, 16, 16, "" );
		setHomeButton = new GuiButton( 1, minY + 150, minY+150, 48, 16, "Set Home"  );
		this.buttonList.add(profButton);
		this.buttonList.add(setHomeButton);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture( Reference.GUI_MERC_INV );
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect( var5, var6, 0, 0, this.xSize, this.ySize );
		//this.drawTexturedModalRect(var5, var6 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		EntityMercenary merc = ((ContainerMercenary)this.inventorySlots).merc;
		if( merc.mercname != null )
			this.fontRenderer.drawString(merc.mercname, 8, 8, 4210752);
		/*MercenaryProfession prof = merc.prof;
		if( prof == null ){
			prof = ProfessionManager.instance.noProf;
		}

		mc.getTextureManager().bindTexture( prof.getResourceLocation() );
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawTexturedModalRect( 44, 108, 0, 0, 16, 16);

		this.fontRendererObj.drawString(prof.getName(), 108, 108, 4210752);
		if( this.isPointInRegion(44, 108, 16, 16, par1, par2) ) //func_146978_c - isPointInRegion
			this.drawCreativeTabHoveringText( prof.getName() + "\n" + prof.getDescription(), par1 - (this.width - this.xSize) / 2, par2 - (this.height - this.ySize) / 2);
			*/
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if( par1GuiButton == profButton )
			Minecraft.getMinecraft().displayGuiScreen( new GuiScreenProfessions( this ) );
		else if( par1GuiButton == setHomeButton ){
			
		}
			
	}








}
