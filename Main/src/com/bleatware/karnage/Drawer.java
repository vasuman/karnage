package com.bleatware.karnage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:07 PM
 */
public abstract class Drawer {
    public static final float FOV = 67;
    public static final float P_NEAR = 0.1f;
    public static final float P_FAR = 10000f;
    public static final int VERTEX_RES = 1000;

    protected static SpriteBatch spriteDraw;
    protected static BitmapFont font;
    protected static ShapeRenderer shapeDraw;
    protected static GL20 glCtx;
    protected static final Environment environment = new Environment();
    protected static PerspectiveCamera perspectiveCamera;

    protected static ModelBatch modelDraw = new ModelBatch();

    private static ArrayList<FrameBuffer> buffers = new ArrayList<FrameBuffer>();

    public static void init(int width, int height) {
        perspectiveCamera = new PerspectiveCamera(FOV, width, height);
        perspectiveCamera.near = P_NEAR;
        perspectiveCamera.far = P_FAR;
        spriteDraw = new SpriteBatch(VERTEX_RES);
        shapeDraw = new ShapeRenderer(VERTEX_RES);
        glCtx = Gdx.graphics.getGL20();
        modelDraw = new ModelBatch();
        clearBuffers();
        environment.clear();
    }

    public static void setFont(BitmapFont font) {
        Drawer.font = font;
    }

    public static PerspectiveCamera getPerspectiveCamera() {
        return perspectiveCamera;
    }

    public static void setViewport(int x, int y, int width, int height) {
        glCtx.glViewport(x, y, width, height);
    }

    public static Environment getEnvironment() {
        return environment;
    }

    public static void clearScreen() {
        glCtx.glClearColor(0, 0, 0, 0);
        glCtx.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

    public static void setupCamera() {
        Camera camera = perspectiveCamera;
        camera.update();
        shapeDraw.setProjectionMatrix(camera.combined);
        spriteDraw.setProjectionMatrix(camera.combined);
        modelDraw.begin(perspectiveCamera);
    }


    public abstract void draw();

    protected void debugBox(float x, float y, float width, float height) {
        shapeDraw.begin(ShapeRenderer.ShapeType.Filled);
        shapeDraw.box(x - width / 2, y - height / 2, 1, width, height, 1);
        shapeDraw.end();
    }

    protected void debugLine(float x1, float y1, float x2, float y2) {
        shapeDraw.begin(ShapeRenderer.ShapeType.Line);
        shapeDraw.line(x1, y1, x2, y2);
        shapeDraw.end();
    }

    protected void debugCircle(float x, float y, float radius) {
        shapeDraw.begin(ShapeRenderer.ShapeType.Filled);
        shapeDraw.circle(x, y, radius);
        shapeDraw.end();
    }

    protected void debugColor(Color color) {
        shapeDraw.setColor(color);
    }

    public static Model basicCube(float size, ColorAttribute color) {
        ModelBuilder builder = new ModelBuilder();
        return builder.createBox(size, size, size, new Material(color),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    }

    public static Model basicSphere(float radius, ColorAttribute color) {
        radius *= 2;
        ModelBuilder builder = new ModelBuilder();
        return builder.createSphere(radius, radius, radius, 10, 10, new Material(color),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    }

    protected void drawModelAt(Model m, Vector3 position) {
        ModelInstance instance = new ModelInstance(m, position);
        modelDraw.render(instance, environment);

    }


    public static void finishDraw() {
        modelDraw.end();
    }

    protected void drawModelAt(Model m, Vector3 position, Vector3 axis, float rotation) {
        Matrix4 transform = new Matrix4();
        transform.translate(position);
        transform.rotate(axis, rotation);
        ModelInstance instance = new ModelInstance(m, transform);
        modelDraw.render(instance, environment);
    }

    protected void drawModelAt(Model m, Vector3 position, float scale) {
        Matrix4 transform = new Matrix4();
        transform.translate(position);
        transform.scl(scale);
        ModelInstance instance = new ModelInstance(m, transform);
        modelDraw.render(instance, environment);
    }

    protected static void resetProjection(int width, int height) {
        OrthographicCamera camera = new OrthographicCamera(width, height);
        camera.setToOrtho(true, width, height);
        camera.position.set(0, 0, 0);
        camera.update();
        shapeDraw.setProjectionMatrix(camera.combined);
        spriteDraw.setProjectionMatrix(camera.combined);
    }


    public static Texture preDraw(Drawer d, int w, int h) {
        FrameBuffer buffer = new FrameBuffer(Pixmap.Format.RGBA4444, w, h, false);
        resetProjection(w, h);
        buffer.begin();
        clearScreen();
        d.draw();
        buffer.end();
        return buffer.getColorBufferTexture();
    }

    private static void clearBuffers() {
        for (FrameBuffer buffer : buffers) {
            buffer.dispose();
        }
        buffers.clear();
    }

    protected void drawText(String text, float x, float y) {
        BitmapFont.TextBounds bounds = getBounds(text);
        spriteDraw.begin();
        font.draw(spriteDraw, text, x - bounds.width / 2, y + bounds.height / 2);
        spriteDraw.end();
    }

    public static BitmapFont.TextBounds getBounds(String text) {
        return font.getBounds(text);
    }
}