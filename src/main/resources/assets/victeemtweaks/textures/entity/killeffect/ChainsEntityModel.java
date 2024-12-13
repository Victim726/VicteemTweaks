//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package arathain.charter.client.model;

import arathain.charter.common.entity.ChainsEntity;
import java.util.Arrays;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1921;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_5603;
import net.minecraft.class_5605;
import net.minecraft.class_5606;
import net.minecraft.class_5607;
import net.minecraft.class_5609;
import net.minecraft.class_5610;
import net.minecraft.class_583;
import net.minecraft.class_630;

@Environment(EnvType.CLIENT)
public class ChainsEntityModel extends class_583<ChainsEntity> {
	private final class_630 root;
	private final class_630[] chains = new class_630[5];

	public ChainsEntityModel(class_630 root) {
		super(class_1921::method_23580);
		this.root = root.method_32086("root");
		Arrays.setAll(this.chains, (index) -> {
			return root.method_32086("root").method_32086(getChainName(index));
		});
	}

	private static String getChainName(int index) {
		return "chain" + (index + 1);
	}

	public static class_5607 getTexturedModelData() {
		class_5609 data = new class_5609();
		class_5610 rootPart = data.method_32111();
		class_5610 root = rootPart.method_32117("root", class_5606.method_32108(), class_5603.method_32090(0.0F, 12.0F, 0.0F));
		root.method_32117("chain1", class_5606.method_32108().method_32101(0, 0).method_32098(-8.0F, -1.5F, -8.0F, 16.0F, 3.0F, 16.0F, new class_5605(0.0F)), class_5603.method_32090(0.0F, 8.5F, 0.0F));
		root.method_32117("chain2", class_5606.method_32108().method_32101(0, 0).method_32098(-8.0F, -1.5F, -8.0F, 16.0F, 3.0F, 16.0F, new class_5605(0.0F)), class_5603.method_32090(0.0F, 3.5F, 0.0F));
		root.method_32117("chain3", class_5606.method_32108().method_32101(0, 0).method_32098(-8.0F, -1.5F, -8.0F, 16.0F, 3.0F, 16.0F, new class_5605(0.0F)), class_5603.method_32090(0.0F, -1.5F, 0.0F));
		root.method_32117("chain4", class_5606.method_32108().method_32101(0, 0).method_32098(-8.0F, -1.5F, -8.0F, 16.0F, 3.0F, 16.0F, new class_5605(0.0F)), class_5603.method_32090(0.0F, -6.5F, 0.0F));
		root.method_32117("chain5", class_5606.method_32108().method_32101(0, 0).method_32098(-8.0F, -1.5F, -8.0F, 16.0F, 3.0F, 16.0F, new class_5605(0.0F)), class_5603.method_32090(0.0F, -11.5F, 0.0F));
		return class_5607.method_32110(data, 64, 64);
	}

	public void method_2828(class_4587 matrices, class_4588 vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.root.method_22699(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

	public void setAngles(ChainsEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
	}

	public void animateModel(ChainsEntity entity, float limbAngle, float limbDistance, float tickDelta) {
		for(int j = 0; j < this.chains.length; ++j) {
			this.chains[j].field_3675 = j % 2 == 0 ? -(((float)entity.field_6012 + tickDelta) * 0.3F) : ((float)entity.field_6012 + tickDelta) * 0.3F;
		}

	}
}
