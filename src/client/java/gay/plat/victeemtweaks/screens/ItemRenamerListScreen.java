package gay.plat.victeemtweaks.screens;

import gay.plat.victeemtweaks.VicteemTweaks;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemRenamerListScreen extends Screen {
    public static final String TITLE = "options.item_renamer_list";
    public static final Identifier LIST_TEXTURE = VicteemTweaks.identiferOf("textures/gui/screen/item_renamer_list.png");
    protected int x;
    protected int y;

    public ItemRenamerListScreen() {
        super(Text.translatable(TITLE));
    }

    @Override
    protected void init() {
        this.x = (this.width-256)/2;
        this.y = (this.height-128)/2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawTexture(LIST_TEXTURE,this.x,this.y,0,0,256,128);
    }
}
