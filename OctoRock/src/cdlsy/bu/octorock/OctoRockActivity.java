package cdlsy.bu.octorock;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;
import org.xmlpull.v1.XmlPullParserException;

import android.graphics.Typeface;


public class OctoRockActivity extends SimpleBaseGameActivity implements OctoConst {
	
	
	protected Camera camera;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TextureRegion mNoteTextureRegion;
	private TextureRegion mheadTextureRegion;
	
	protected Scene mainScene;
	private SongParser songParser;
	private ArrayList<Note> noteList = new ArrayList<Note>();
	private ArrayList<Presser> presserList = new ArrayList<Presser>();
	private ElapsedTime elapsedTime;
	private Music myMusic;
	private int score = 0;
	private Font font;
	private Text text;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		return engineOptions;
	}


	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Sprites/");
		
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this.mNoteTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "inkinkink.png", 0, 0);
		this.mheadTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "octopus.png", 64, 0);
		this.mBitmapTextureAtlas.load();
		
		MusicFactory.setAssetBasePath("mfx/");
		try {
			this.myMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "LateEvening.mp3");
			this.myMusic.setLooping(false);
		} catch (final IOException e) {
			Debug.e(e);
		}
		
		this.font = FontFactory.create(this.getFontManager(), this.getTextureManager(), 2048, 2048, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 128);
		this.font.load();
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mainScene = new Scene();
		
		mainScene.setBackground(new Background(0,0,1));
		
		try {
			songParser = new SongParser(getAssets().open("assets/Songs/LateEvening.xml"));
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Sprite head = new Sprite(CENTER_X-mheadTextureRegion.getWidth()/2, CENTER_Y-mheadTextureRegion.getHeight()/2-200, mheadTextureRegion, this.getVertexBufferObjectManager());
		head.setZIndex(10);
		this.mainScene.attachChild(head);
		
		//set up the pressers in their respective positions
		Presser presser0 = new Presser(P0_X,P0_Y,this.getVertexBufferObjectManager(),0);
		presserList.add(presser0);
		Presser presser1 = new Presser(P1_X,P1_Y,this.getVertexBufferObjectManager(),1);
		presserList.add(presser1);
		Presser presser2 = new Presser(P2_X,P2_Y,this.getVertexBufferObjectManager(),2);
		presserList.add(presser2);
		Presser presser3 = new Presser(P3_X,P3_Y,this.getVertexBufferObjectManager(),3);
		presserList.add(presser3);
		Presser presser4 = new Presser(P4_X,P4_Y,this.getVertexBufferObjectManager(),4);
		presserList.add(presser4);
		Presser presser5 = new Presser(P5_X,P5_Y,this.getVertexBufferObjectManager(),5);
		presserList.add(presser5);
		
		this.mainScene.attachChild(presser0);
		this.mainScene.attachChild(presser1);
		this.mainScene.attachChild(presser2);
		this.mainScene.attachChild(presser3);
		this.mainScene.attachChild(presser4);
		this.mainScene.attachChild(presser5);
		
		text = new Text(100f, 40f, this.font, "00000000", mEngine.getVertexBufferObjectManager());
		this.mainScene.attachChild(text);
		
		myMusic.play();
		try {
			createNoteTimeHandler();
		} catch (XmlPullParserException e) {
			Debug.e(e);
		} catch (IOException e) {
			Debug.e(e);
		}
		
		this.mainScene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() { }

			@Override
			public void onUpdate(final float pSecondsElapsed) {
				
				boolean[] presserIsOn = new boolean[6];
				
				if(!myMusic.isPlaying()) {
					finish();
				}
				
				//check if any notes need to be destroyed
				for(int i = 0; i < noteList.size(); i++) {
					Note tempNote = noteList.get(i);
					
					
					//if out of bounds remove note
					if (tempNote.getX() < 0-tempNote.getWidth() || tempNote.getX() > CAMERA_WIDTH || tempNote.getY() > CAMERA_HEIGHT) {
						removeNote(tempNote);
					} else if(tempNote.isOnPresser()) {
						tempNote.getMyPresser().setIsOn(true);
						presserIsOn[tempNote.getMyPresser().getPresserNumber()] = true;
						if(tempNote.getMyPresser().getDeleteNote()) {
							tempNote.getMyPresser().setDeleteNote(false);
							removeNote(tempNote);
							score += 10;
						}
					}
				}
				
				for(int i = 0; i < 6; i++) {
					if(!presserIsOn[i])
						presserList.get(i).setIsOn(false);
				}
				text.setText(Integer.toString(score));
			}
			
		});
		
		this.mainScene.setOnSceneTouchListener(new IOnSceneTouchListener() {

			@Override
			public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
				for(int i = 0; i < 6; i++) {
					
					if(pSceneTouchEvent.isActionDown()) {
					
						Presser currentPresser = presserList.get(i);
						float distX = Math.abs(pSceneTouchEvent.getX() - currentPresser.getX() );
						float distY = Math.abs(pSceneTouchEvent.getY() - currentPresser.getY() );
						if(distX < 64 && distY < 64) {
							if(currentPresser.getIsOn()) {
								currentPresser.setDeleteNote(true);
							}
						}
					}
				}
				
				return false;
			}
		});
		return this.mainScene;
	}
	
	//Spawner Functions
	public InputStream getPath() throws IOException {
		return getAssets().open("Songs/LateEvening.xml");
	}
	
	public void initializeSongParser() throws XmlPullParserException, IOException {
		songParser = new SongParser(getPath());
	}

	public void initializeElapsedTime() {
		elapsedTime = new ElapsedTime();
	}

private void createNoteTimeHandler() throws XmlPullParserException, IOException
	{		
		final float PERIOD = 0.01f;
		initializeSongParser();
		songParser.getNextTime();
		initializeElapsedTime();
		
		TimerHandler noteTimerHandler = new TimerHandler(PERIOD, true,
		        new ITimerCallback() {
		     
		            @Override
		            public void onTimePassed(TimerHandler pTimerHandler) {
		            	try {
		            		long currentNoteTime = songParser.getCurrentTime();
		            		long currentTime = elapsedTime.get();
							if(currentNoteTime >= (currentTime + (NOTE_DELAY - PERIOD) * 1000) && currentNoteTime <= (currentTime + (NOTE_DELAY + PERIOD) * 1000)) {
								int[] Notes = songParser.getNextNotes();
								for(int i = 0; i < 6; i++) {
									if(Notes[i] == 1) {
										addNote(presserList.get(i));
										mainScene.sortChildren();
									}
								}
								
								songParser.getNextTime(); // get ready for next call
								
							} else if(currentNoteTime < currentTime - (PERIOD - NOTE_DELAY) * 1000) {
								System.out.println("Note at " + currentTime + " skipped");
								songParser.getNextTime();
							} else { /* Just Wait */}
						} catch (XmlPullParserException e) {
							Debug.e(e);
						} catch (IOException e) {
							Debug.e(e);
						}
		            	
		            	
		               
		            }
		        });
		        mEngine.registerUpdateHandler(noteTimerHandler);
		
	}

	
	//NOTE SPAWNING FUNCTIONS HERE

	public void addNote(Presser press) {
		final Note note = new Note(mNoteTextureRegion, this.getVertexBufferObjectManager(), press);
		note.registerEntityModifier(new MoveModifier(NOTE_SPEED, note.getX(), note.getXdir(), note.getY(), note.getYdir()));
		noteList.add(note);
		this.mainScene.attachChild(note);
		
	}
	
	public void removeNote(Note note) {
		noteList.remove(note);
		this.mainScene.detachChild(note);
	}
	
	public ArrayList<Presser> getPressorList() {
		return presserList;
	}
	
	public void onPause() {
		super.onPause();
		if(myMusic != null)
			myMusic.pause();
	}
	
	public void onResume() {
		super.onResume();
		if(myMusic != null && !myMusic.isPlaying()) {
			myMusic.resume();
		}
	}
}