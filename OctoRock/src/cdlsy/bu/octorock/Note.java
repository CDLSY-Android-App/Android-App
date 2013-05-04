package cdlsy.bu.octorock;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Note extends Sprite implements OctoConst {
	private float xDir;
	private float yDir;
	private int noteType;
	private Presser myPresser;
	
	public Note(ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, Presser press) {
		super(SPAWNING_X-pTextureRegion.getWidth()/2, SPAWNING_Y-pTextureRegion.getHeight()/2, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager);
		this.myPresser = press;
		this.noteType = this.myPresser.getPresserNumber();
		setSpriteDirection(noteType);
	}
	
	public float getXdir(){
		return this.xDir;
	}

	public float getYdir(){
		return this.yDir;
	}
	
	public int getNoteType() {
		return noteType;
	}
	
	public Presser getMyPresser() {
		return this.myPresser;
	}
	
	public boolean isOnPresser() {
		float xDist = Math.abs(this.getX() - myPresser.getX());
		float yDist = Math.abs(this.getY() - myPresser.getY());
		return (xDist < 64) && (yDist < 64);
	}
	
	public void setSpriteDirection(int noteType) { 
		switch(noteType) {
			case 0:
				this.setRotation(ROTATION_0);
				this.xDir = P0_X-this.getWidth()/2-2*Math.abs(SPAWNING_X-P0_X);
				this.yDir = P0_Y-this.getHeight()/2+2*Math.abs(SPAWNING_Y-P0_Y);
				break;
			case 1:
				this.setRotation(ROTATION_1);
				this.xDir = P1_X-this.getWidth()/2-2*Math.abs(SPAWNING_X-P1_X);
				this.yDir = P1_Y-this.getHeight()/2+2*Math.abs(SPAWNING_Y-P1_Y);
				break;
			case 2:
				this.setRotation(ROTATION_2);
				this.xDir = P2_X-this.getWidth()/2-2*Math.abs(SPAWNING_X-P2_X);
				this.yDir = P2_Y-this.getHeight()/2+2*Math.abs(SPAWNING_Y-P2_Y);
				break;
			case 3:
				this.setRotation(ROTATION_3);
				this.xDir = P3_X-this.getWidth()/2+2*Math.abs(SPAWNING_X-P3_X);
				this.yDir = P3_Y-this.getHeight()/2+2*Math.abs(SPAWNING_Y-P3_Y);
				break;
			case 4:
				this.setRotation(ROTATION_4);
				this.xDir = P4_X-this.getWidth()/2+2*Math.abs(SPAWNING_X-P4_X);
				this.yDir = P4_Y-this.getHeight()/2+2*Math.abs(SPAWNING_Y-P4_Y);
				break;
			case 5:
				this.setRotation(ROTATION_5);
				this.xDir = P5_X-this.getWidth()/2+2*Math.abs(SPAWNING_X-P5_X);
				this.yDir = P5_Y-this.getHeight()/2+2*Math.abs(SPAWNING_Y-P5_Y);
				break;
			default:
				this.setRotation(0);
				break;
		}
	}
}