package cdlsy.bu.octorock;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import android.content.Intent;
import android.graphics.Typeface;
/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga
 *
 * @author Nicolas Gramlich
 * @since 01:30:15 - 02.04.2010
 */

/**
 *  Code modified by Spring 2013 EC327 Group CDLSY
 *  for Project OctoRock
 *
 */
public class MenuExample extends SimpleBaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;

	protected Camera mCamera;

	protected Scene mMainScene;

	private BitmapTextureAtlas mBitmapTextureAtlas;
	private ITextureRegion mPlayTextureRegion;
	
	private MenuExample thisActivity = this;
	private Font font;

	@Override
	public EngineOptions onCreateEngineOptions() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
	}

	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),800, 480, TextureOptions.BILINEAR);
		this.mPlayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "play_button.png", 250,90);
		this.mBitmapTextureAtlas.load();

		this.font = FontFactory.create(this.getFontManager(), this.getTextureManager(), 2048, 2048, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 28, true, Color.WHITE_ABGR_PACKED_INT);
		this.font.load();
	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		this.mMainScene = new Scene();
		this.mMainScene.setBackground(new Background(Color.BLUE));
		
		
		final Sprite play = new Sprite(250, 110, this.mPlayTextureRegion, this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				startActivity(new Intent(thisActivity, OctoRockActivity.class));
				return true;
			};
		};
		
		this.mMainScene.registerTouchArea(play);
		this.mMainScene.attachChild(play);
		
		TextOptions txtoptions = new TextOptions(AutoWrap.WORDS, 600, HorizontalAlign.CENTER, Text.LEADING_DEFAULT);

		CharSequence text = "Tap the note-presser whenever a note created by OctoRock passes over it. When a note is matched successfully the note-presser will emit a green light, and you will gain 10 points. There is no penalty for missing a note. Song used with permission by FLEETA. ROCK ON!";
		Text t = new Text(100, 480/2, font, text, txtoptions, this.getVertexBufferObjectManager());
		this.mMainScene.attachChild(t);
		
		
		return this.mMainScene;
	}
}