//? if <1.0.0-beta.9 {
/*package me.icanttellyou.ornitheears;

import me.icanttellyou.ornitheears.mixin.ModelPartAccessor;
import net.minecraft.client.render.model.ModelPart;
import net.minecraft.client.render.model.Quad;
import net.minecraft.client.render.model.Vertex;

public class SizeableTexModelPart extends ModelPart {
	public float textureWidth;
	public float textureHeight;

	public SizeableTexModelPart(/^? if >=1.0.0-beta.8 {^/net.minecraft.client.render.model.Model model, /^?}^/ int u, int v) {
		super(/^? if >=1.0.0-beta.8 {^/ model, /^?}^/ u, v);
		textureWidth = 64.0F;
		textureHeight = 32.0F;
	}

	public SizeableTexModelPart setTextureSize(int width, int height) {
		textureWidth = (float)width;
		textureHeight = (float)height;
		return this;
	}

	@Override
	public void addBox(float x, float y, float z, int sizeX, int sizeY, int sizeZ, float increase) {
		ModelPartAccessor access = (ModelPartAccessor) this;

		int textureU = access.getTextureU();
		int textureV = access.getTextureV();

		Vertex[] verts = new Vertex[8];
		Quad[] quads = new Quad[6];
		float maxX = x + (float)sizeX;
		float maxY = y + (float)sizeY;
		float maxZ = z + (float)sizeZ;
		x -= increase;
		y -= increase;
		z -= increase;
		maxX += increase;
		maxY += increase;
		maxZ += increase;
		if (flipped) {
			float var11 = maxX;
			maxX = x;
			x = var11;
		}

		Vertex v000 = new Vertex(x, y, z, 0.0F, 0.0F);
		Vertex v100 = new Vertex(maxX, y, z, 0.0F, 8.0F);
		Vertex v110 = new Vertex(maxX, maxY, z, 8.0F, 8.0F);
		Vertex v010 = new Vertex(x, maxY, z, 8.0F, 0.0F);
		Vertex v001 = new Vertex(x, y, maxZ, 0.0F, 0.0F);
		Vertex v101 = new Vertex(maxX, y, maxZ, 0.0F, 8.0F);
		Vertex v111 = new Vertex(maxX, maxY, maxZ, 8.0F, 8.0F);
		Vertex v011 = new Vertex(x, maxY, maxZ, 8.0F, 0.0F);
		verts[0] = v000;
		verts[1] = v100;
		verts[2] = v110;
		verts[3] = v010;
		verts[4] = v001;
		verts[5] = v101;
		verts[6] = v111;
		verts[7] = v011;
		quads[0] = makeQuad(
			new Vertex[]{v101, v100, v110, v111},
			textureU + sizeZ + sizeX,
			textureV + sizeZ,
			textureU + sizeZ + sizeX + sizeZ,
			textureV + sizeZ + sizeY
		);
		quads[1] = makeQuad(
			new Vertex[]{v000, v001, v011, v010}, textureU, textureV + sizeZ, textureU + sizeZ, textureV + sizeZ + sizeY
		);
		quads[2] = makeQuad(
			new Vertex[]{v101, v001, v000, v100}, textureU + sizeZ, textureV, textureU + sizeZ + sizeX, textureV + sizeZ
		);
		quads[3] = makeQuad(
			new Vertex[]{v110, v010, v011, v111},
			textureU + sizeZ + sizeX,
			textureV + sizeZ,
			textureU + sizeZ + sizeX + sizeX,
			textureV
		);
		quads[4] = makeQuad(
			new Vertex[]{v100, v000, v010, v110},
			textureU + sizeZ,
			textureV + sizeZ,
			textureU + sizeZ + sizeX,
			textureV + sizeZ + sizeY
		);
		quads[5] = makeQuad(
			new Vertex[]{v001, v101, v111, v011},
			textureU + sizeZ + sizeX + sizeZ,
			textureV + sizeZ,
			textureU + sizeZ + sizeX + sizeZ + sizeX,
			textureV + sizeZ + sizeY
		);
		if (flipped) {
			for (Quad quad : quads) {
				quad.flip();
			}
		}

		access.setVertices(verts);
		access.setQuads(quads);
	}

	private Quad makeQuad(Vertex[] verts, int maxX, int maxY, int minX, int minY) {
		Quad quad = new Quad(verts);
		quad.vertices[0] = quad.vertices[0].withTextureCoords((float)minX / textureWidth, (float)maxY / textureHeight);
		quad.vertices[1] = quad.vertices[1].withTextureCoords((float)maxX / textureWidth, (float)maxY / textureHeight);
		quad.vertices[2] = quad.vertices[2].withTextureCoords((float)maxX / textureWidth, (float)minY / textureHeight);
		quad.vertices[3] = quad.vertices[3].withTextureCoords((float)minX / textureWidth, (float)minY / textureHeight);
		return quad;
	}
}
*///?}