package com.gltests.gdx198;

import android.opengl.GLES20;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class MyGdxGameTest_glReadPixels extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Pixmap pixmap;

	private final int width = 500;
	private final int height = 400;
	private final int capacity = width*height*4;

	private  byte[] mColors;
	private ByteBuffer buffer;
	private ByteBuffer notDirectlyAllocatedByteBuffer;
	private ByteBuffer directlyAllocatedBuffer;
	private ByteBuffer pixmapByteBuffer;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		Gdx.graphics.setContinuousRendering(false);


		//allocating buffers
		mColors = new byte[capacity];
		buffer = ByteBuffer.wrap(mColors);
		notDirectlyAllocatedByteBuffer = ByteBuffer.allocate(capacity);
		directlyAllocatedBuffer = ByteBuffer.allocateDirect(capacity);

		pixmap = new Pixmap( width, height, Pixmap.Format.RGBA8888);
		pixmapByteBuffer = pixmap.getPixels();


		buffer.order(ByteOrder.nativeOrder());
	}

	private void readPixels1() { //this will work
		Gdx.app.log("readPixels1 ##Gdx.gl","start\n\n---");
		Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);
		Gdx.gl.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, directlyAllocatedBuffer);
		Gdx.app.log("readPixels1 to directlyAllocatedBuffer","complete\n\n===");
	}

	private void readPixels2() { //this will work
		Gdx.app.log("readPixels2 ##Gdx.gl","start\n\n---\n\n --- ");
		Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);
		Gdx.gl.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, pixmapByteBuffer);
		Gdx.app.log("readPixels2 to pixmapByteBuffer","complete\n\n===");

	}

	private void readPixels3() { //this will work
		Gdx.app.log("readPixels3 ##GLES20","start\n\n---");
		Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);
		GLES20.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buffer);
		Gdx.app.log("readPixels3 to buffer","complete\n\n===");
	}

	private void readPixels4() { //this will work
		Gdx.app.log("readPixels4 ##GLES20","start\n\n---");
		Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);
		GLES20.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, notDirectlyAllocatedByteBuffer);
		Gdx.app.log("readPixels4 to notDirectlyAllocatedByteBuffer","complete\n\n===");
	}

	private void readPixels5() { //this will fail
		Gdx.app.log("readPixels5 ##Gdx.gl","start\n\n---");
		Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);
		Gdx.gl.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buffer);
		Gdx.app.log("readPixels5 to buffer","complete\n\n===");
	}

	private void readPixels6() { //this will fail
		Gdx.app.log("readPixels6 ##Gdx.gl","start\n\n---");
		Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);
		Gdx.gl.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, notDirectlyAllocatedByteBuffer);
		Gdx.app.log("readPixels6 to notDirectlyAllocatedByteBuffer","complete\n\n===");
	}

	int i = 0;
	@Override
	public void render () {
		Gdx.gl.glClearColor((float) Math.random(), (float) Math.random(), (float) Math.random(), 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(img,0,0);
		batch.end();


		if (i == 0) readPixels1();
		else if (i == 1) readPixels2();
		else if (i == 2) readPixels3();
		else if (i == 3) readPixels4();
		else if (i == 4) readPixels5();
		else if (i == 5) readPixels6();

		i++;

	}


	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

}
