package com.video.process.compose.filter;

import android.opengl.GLES20;

import com.video.process.utils.GLESUtils;

/**
 * Created by sudamasayuki on 2018/01/06.
 */

public class GlBulgeDistortionComposeFilter extends GlComposeFilter {

    private static final String FRAGMENT_SHADER =
            "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;" +

                    "varying highp vec2 vTextureCoord;" +
                    "uniform samplerExternalOES sTexture;" +

                    "uniform highp vec2 center;" +
                    "uniform highp float radius;" +
                    "uniform highp float scale;" +

                    "void main() {" +
                    "highp vec2 textureCoordinateToUse = vTextureCoord;" +
                    "highp float dist = distance(center, vTextureCoord);" +
                    "textureCoordinateToUse -= center;" +
                    "if (dist < radius) {" +
                    "highp float percent = 1.0 - ((radius - dist) / radius) * scale;" +
                    "percent = percent * percent;" +
                    "textureCoordinateToUse = textureCoordinateToUse * percent;" +
                    "}" +
                    "textureCoordinateToUse += center;" +

                    "gl_FragColor = texture2D(sTexture, textureCoordinateToUse);" +
                    "}";

    private float centerX = 0.5f;
    private float centerY = 0.5f;
    private float radius = 0.25f;
    private float scale = 0.5f;

    public GlBulgeDistortionComposeFilter() {
        super(GLESUtils.DEFAULT_VERTEX_SHADER, FRAGMENT_SHADER);
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(final float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(final float centerY) {
        this.centerY = centerY;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(final float radius) {
        this.radius = radius;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(final float scale) {
        this.scale = scale;
    }

    //////////////////////////////////////////////////////////////////////////

    @Override
    public void onDraw() {
        GLES20.glUniform2f(getHandle("center"), centerX, centerY);
        GLES20.glUniform1f(getHandle("radius"), radius);
        GLES20.glUniform1f(getHandle("scale"), scale);
    }
}

