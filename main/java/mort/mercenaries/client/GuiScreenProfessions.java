package mort.mercenaries.client;

import mort.mercenaries.inventory.ContainerMercenary;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
/*
public class GuiScreenProfessions extends ContainerScreen<ContainerMercenary> {

	GUIContainerMercenary previous;
	Button back;

	//public static RenderItem renderItem = new RenderItem();

	private static final int xSize = 176;
	private static final int ySize = 222;
	boolean ending = false;

	static final int colorEnabled = 0x999966;
	static final int colorDisabled = 0x474719;

	public GuiScreenProfessions(ContainerMercenary screenContainer, PlayerInventory inv, ITextComponent titleIn, GUIContainerMercenary previous) {
		super(screenContainer, inv, titleIn);
		this.previous = previous;
	}

	public GuiScreenProfessions(ContainerMercenary screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}
/*
	@Override
	public void initGui() {
		super.initGui();
		int var5 = (this.width - xSize) / 2;
		int var6 = (this.height - ySize) / 2;
		back = new GuiButton( 0, var5 + 10, var6 + 192, 20, 20, "<" );
		this.buttonList.add(back);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		this.drawDefaultBackground();
		//super.drawScreen(mouseX, mouseY, par3);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture( Reference.GUI_PROFLIST );
		int xOffset = (this.width - xSize) / 2;
		int yOffset = (this.height - ySize) / 2;
		this.drawTexturedModalRect( xOffset, yOffset, 0, 0, xSize, ySize );
		//buttons
		super.drawScreen(mouseX, mouseY, par3);
		//other stuff
		int y = yOffset;
		/*for( MercenaryProfession prof : ProfessionManager.instance.professions ){
			drawProfessionLine( xOffset+10, y, prof);
			y += 32;
			if( mouseX >= xOffset+10 && mouseX <= xOffset+26 && mouseY >= y && mouseY<y+16 )
				this.drawCreativeTabHoveringText( prof.getName() + "\n" + prof.getDescription(), mouseX - (this.width - this.xSize) / 2, mouseY - (this.height - this.ySize) / 2);
			
		}*/
	//}
/*
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if( par1GuiButton == back )
			FMLClientHandler.instance().showGuiScreen( previous );
	}
*/
	/*protected void drawProfessionLine( int x, int y, MercenaryProfession prof ){
		boolean enabled = previous==null || prof.isAvaible( ((ContainerMercenary)previous.inventorySlots).merc );
		this.mc.getTextureManager().bindTexture( Reference.GUI_PROFLIST );

		mc.getTextureManager().bindTexture( prof.getResourceLocation() ); //bind texture location
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.drawTexturedModalRect( x, y + 8, prof.icn, 16, 16);
		this.fontRendererObj.drawStringWithShadow( prof.getName(), x + 20, y + 16, enabled?colorEnabled:colorDisabled );
		
	}*/

/*
}
*/