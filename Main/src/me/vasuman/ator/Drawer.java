package me.vasuman.ator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:07 PM
 */
public abstract class Drawer {
    public static final float FOV = 76;
    public static final float P_NEAR = 0.1f;
    public static final float P_FAR = 10000f;
    public static final int VERTEX_RES = 1000;

    protected static SpriteBatch spriteDraw;
    protected static ShapeRenderer shapeDraw;
    protected static GL20 glCtx;
    protected static final Environment environment = new Environment();
    protected static final OrthographicCamera screenCamera = new OrthographicCamera();
    protected static PerspectiveCamera perspectiveCamera;

    protected static ModelBatch modelDraw = new ModelBatch();

    public static void init(int width, int height) {
        perspectiveCamera = new PerspectiveCamera(FOV, width, height);
        perspectiveCamera.near = P_NEAR;
        perspectiveCamera.far = P_FAR;
        screenCamera.setToOrtho(true, width, height);
        screenCamera.position.set(width / 2, height / 2, 0);
        spriteDraw = new SpriteBatch(VERTEX_RES);
        shapeDraw = new ShapeRenderer(VERTEX_RES);
        glCtx = Gdx.graphics.getGL20();
        modelDraw = new ModelBatch();
        environment.clear();
        glCtx.glEnable(GL20.GL_DEPTH_TEST);
    }

    public static PerspectiveCamera getPerspectiveCamera() {
        return perspectiveCamera;
    }

    public static Environment getEnvironment() {
        return environment;
    }

    public static void clear() {
        glCtx.glClearColor(0, 0, 0, 0);
        glCtx.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public static void setupCamera(boolean absolute) {
        Camera camera = (absolute) ? screenCamera : perspectiveCamera;
        camera.update();
        shapeDraw.setProjectionMatrix(camera.combined);
        spriteDraw.setProjectionMatrix(camera.combined);
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

    protected Model basicCube(float size, ColorAttribute color) {
        ModelBuilder builder = new ModelBuilder();
        return builder.createBox(size, size, size, new Material(color),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    }

    protected void drawModelAt(Model m, Vector3 position) {
        ModelInstance instance = new ModelInstance(m, position);
        modelDraw.begin(perspectiveCamera);
        modelDraw.render(instance, environment);
        modelDraw.end();
    }


    public static Vector3 unTouch(int x, int y) {
        Vector3 ans = new Vector3(x, y, 0);
        screenCamera.unproject(ans);
        return ans;
    }
}
