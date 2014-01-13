package com.bleatware.karnage.desktop;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

import java.io.IOException;

/**
 * Ator
 * User: vasuman
 * Date: 1/2/14
 * Time: 6:41 PM
 */
public class BuildHook {

    private static final String MODEL_CONV_SCRIPT = "/home/vasuman/Code/bin/libgdx_model_conv.sh";
    private static final String IMG_SRC = "/home/vasuman/Documents/Karnage/images";
    private static final String MODEL_SRC = "/home/vasuman/Documents/Karnage/models";
    private static final String ASSET_DIR = "/mnt/LMedia/Development/Ator/Android/assets";

    public static void main(String[] args) {
        TexturePacker2.process(IMG_SRC, ASSET_DIR, "game");
        try {
            Process process = Runtime.getRuntime().exec(new String[]{MODEL_CONV_SCRIPT, MODEL_SRC, ASSET_DIR});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
