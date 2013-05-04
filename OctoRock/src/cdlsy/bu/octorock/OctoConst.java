package cdlsy.bu.octorock;

public interface OctoConst {
	
	public static final float CAMERA_WIDTH = 1920;
	public static final float CAMERA_HEIGHT = 1080;
	public static final float CENTER_X = CAMERA_WIDTH/2;
	public static final float CENTER_Y = CAMERA_HEIGHT/2;
	
	public static final float SPAWNING_X = CENTER_X;
	public static final float SPAWNING_Y = CENTER_Y-50;
	
	public static final float NOTE_SPEED = 7;
	public static final float NOTE_DELAY = NOTE_SPEED/3.0f;
	
	//presser positions
	public static final float P0_X = 1*CENTER_X/8;
	public static final float P0_Y = 5*CAMERA_HEIGHT/8;
	public static final float P1_X = 3*CENTER_X/8;
	public static final float P1_Y = 6*CAMERA_HEIGHT/8;
	public static final float P2_X = 5*CENTER_X/8;
	public static final float P2_Y = 7*CAMERA_HEIGHT/8;
	public static final float P3_X = CAMERA_WIDTH-1*CENTER_X/8;
	public static final float P3_Y = 5*CAMERA_HEIGHT/8;
	public static final float P4_X = CAMERA_WIDTH-3*CENTER_X/8;
	public static final float P4_Y = 6*CAMERA_HEIGHT/8;
	public static final float P5_X = CAMERA_WIDTH-5*CENTER_X/8;
	public static final float P5_Y = 7*CAMERA_HEIGHT/8;
	
	//think these are all in degrees based on looking at
	//package org.andengine.util.adt.transformation which entity uses rotation function of
	public static final float ROTATION_0 = (float) ((180.0f/Math.PI)*Math.atan(Math.abs(P0_X-SPAWNING_X)/Math.abs(P0_Y-SPAWNING_Y)));  //will need to test and change later
	public static final float ROTATION_1 = (float) ((180.0f/Math.PI)*Math.atan(Math.abs(P1_X-SPAWNING_X)/Math.abs(P1_Y-SPAWNING_Y)));
	public static final float ROTATION_2 = (float) ((180.0f/Math.PI)*Math.atan(Math.abs(P2_X-SPAWNING_X)/Math.abs(P2_Y-SPAWNING_Y)));
	public static final float ROTATION_3 = (float) (-(180.0f/Math.PI)*Math.atan(Math.abs(P0_X-SPAWNING_X)/Math.abs(P0_Y-SPAWNING_Y)));
	public static final float ROTATION_4 = (float) (-(180.0f/Math.PI)*Math.atan(Math.abs(P1_X-SPAWNING_X)/Math.abs(P1_Y-SPAWNING_Y)));
	public static final float ROTATION_5 = (float) (-(180.0f/Math.PI)*Math.atan(Math.abs(P2_X-SPAWNING_X)/Math.abs(P2_Y-SPAWNING_Y)));
}