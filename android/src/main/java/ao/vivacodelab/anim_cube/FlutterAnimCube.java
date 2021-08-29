package ao.vivacodelab.anim_cube;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.catalinjurjiu.animcubeandroid.AnimCube;
import io.flutter.Log;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import static io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import static io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.platform.PlatformView;

public class FlutterAnimCube implements PlatformView, MethodCallHandler, AnimCube.OnCubeModelUpdatedListener, AnimCube.OnCubeAnimationFinishedListener  {
    private static final String TAG = "AnimCube";
    private final AnimCube animCube;
    private Bundle state = new Bundle();
    private final MethodChannel methodChannel;
    private int[] scheme;

    FlutterAnimCube(Context context, BinaryMessenger messenger, int id) {

        animCube = new AnimCube(context);
        methodChannel = new MethodChannel(messenger, "plugins.vivacodelab.ao/animcube_" + id);
        animCube.setOnCubeModelUpdatedListener(this);
        animCube.setOnAnimationFinishedListener(this);
        animCube.setSingleRotationSpeed(20);
        animCube.setDoubleRotationSpeed(25);
        scheme = context.getResources().getIntArray(R.array.custom_cube_colors);
        animCube.setCubeColors(scheme);
        methodChannel.setMethodCallHandler(this);
    }

    @Override
    public View getView() {
        return animCube;
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        switch (methodCall.method) {
            case "setMoveSequence":
                setMoveSequence(methodCall, result);
                break;
            case "animateMoveSequence":
                animateMoveSequence(result);
                break;
            case "stopAnimation":
                stopAnimation(result);
                break;
            case "animateMove":
                animateMove(result);
                break;
            case "animateMoveReversed":
                animateMoveReversed(result);
                break;
            case "saveState":
                saveState(result);
                break;
            case "restoreState":
                restoreState(result);
                break;
            case "setCubeColors":
                setCubeColors(methodCall, result);
                break;
            case "applyMoveSequenceReversed":
                applyMoveSequenceReversed(result);
                break;
            default:
                result.notImplemented();
        }
    }

    private void setCubeColors(MethodCall methodCall, Result result){
        int[] scheme = (int[]) methodCall.arguments;
        animCube.setCubeColors(scheme);
        result.success(null);
    }

    private void applyMoveSequenceReversed(Result result){
        animCube.applyMoveSequenceReversed();
        result.success(null);
    }

    private void restoreState(Result result){
        animCube.restoreState(state);
        state = animCube.saveState();
        result.success(null);
    }

    private void saveState(Result result){
        state = animCube.saveState();
        result.success(null);
    }

    private void setMoveSequence(MethodCall methodCall, Result result) {
        String text = (String) methodCall.arguments;
        animCube.setMoveSequence(text);
        result.success(null);
    }

    private void animateMoveSequence(Result result) {
        animCube.animateMoveSequence();
        result.success(null);
    }

    private void stopAnimation(Result result) {
        animCube.stopAnimation();
        result.success(null);
    }

    private void animateMove(Result result) {
        animCube.animateMove();
        result.success(null);
    }

    private void animateMoveReversed(Result result) {
        animCube.animateMoveReversed();
        result.success(null);
    }

    @Override
    public void onCubeModelUpdate(int[][] newCubeModel) {
        //Log.d(TAG, "Cube model updated!");
        printCubeModel(newCubeModel);
    }

    void printCubeModel(int[][] cube) {
        //Log.d(TAG, "Cube model:");
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < cube.length; i++) {
            stringBuilder.append("\n");
            stringBuilder.append(i);
            stringBuilder.append(":\n");
            for (int j = 0; j < cube[i].length; j++) {
                stringBuilder.append(" ");
                stringBuilder.append(cube[i][j]);
                if ((j + 1) % 3 == 0) {
                    stringBuilder.append("\n");
                } else {
                    stringBuilder.append(", ");
                }
            }
        }
        //Log.d(TAG, stringBuilder.toString());
    }

    @Override
    public void onAnimationFinished() {
        //Log.d(TAG, "Cube AnimationFinished!");
    }

    @Override
    public void dispose() {
    }
}
