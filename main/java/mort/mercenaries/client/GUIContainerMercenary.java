/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mort.mercenaries.client;

import mort.mercenaries.Reference;
import mort.mercenaries.common.EntityMercenary;
import mort.mercenaries.inventory.ContainerMercenary;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

public class GUIContainerMercenary extends ContainerScreen<ContainerMercenary> {

	Button profButton;
	Button setHomeButton;
	
	public GUIContainerMercenary(ContainerMercenary screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void init() {
		super.init();
		int minX = (this.width - this.xSize) / 2;
		int minY = (this.height - this.ySize) / 2;
		profButton = new Button( 0, minX + 44, minY + 108, 16, "", (b) -> {} );
		setHomeButton = new Button( 1, minY + 150, minY+150, 48, "Set Home", (b) -> {}  );
		addButton(profButton);
		addButton(setHomeButton);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture( Reference.GUI_MERC_INV );
		int var5 = (this.width - this.xSize) / 2;
		int var6 = (this.height - this.ySize) / 2;
		this.blit( var5, var6, 0, 0, this.xSize, this.ySize );
		//this.drawTexturedModalRect(var5, var6 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		EntityMercenary merc = container.merc;
		if( merc.mercname != null )
			this.font.drawString(merc.mercname, 8, 8, 4210752);
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

	
	   /*
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if( par1GuiButton == profButton )
			Minecraft.getMinecraft().displayGuiScreen( new GuiScreenProfessions( this ) );
		else if( par1GuiButton == setHomeButton ){
			
		}
			
	}      */








}
