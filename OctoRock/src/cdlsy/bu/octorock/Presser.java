package cdlsy.bu.octorock;

import org.andengine.entity.primitive.Ellipse;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


public class Presser extends Ellipse implements OctoConst {

	private boolean IsOn;
	private int presserNumber;
	private boolean deleteNote;
	
	public Presser(float pX, float pY, VertexBufferObjectManager pVertexBufferObjectManager, int presserNumber) {
		super(pX, pY, 27, 27, pVertexBufferObjectManager);
		setIsOn(false);
		this.setLineWidth(6);
		this.setColor(1.0f, 0.0f, 1.0f);
		this.presserNumber = presserNumber;
	}

	public boolean getIsOn() {
		return IsOn;
	}
	
	public void setIsOn(boolean IsOn) {
		this.IsOn = IsOn;
		if(this.IsOn) {
			this.setColor(0,1,0);
		}
		else {
			this.setColor(1,0,1);
		}
	}

	public int getPresserNumber() {
		return presserNumber;
	}

	public boolean getDeleteNote() {
		return deleteNote;
	}

	public void setDeleteNote(boolean deleteNote) {
		this.deleteNote = deleteNote;
	}
}