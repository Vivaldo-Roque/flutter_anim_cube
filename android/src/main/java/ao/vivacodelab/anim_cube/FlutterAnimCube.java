package ao.vivacodelab.anim_cube;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.catalinjurjiu.animcubeandroid.AnimCube;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

import static io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import static io.flutter.plugin.common.MethodChannel.Result;

public class FlutterAnimCube extends AppCompatActivity implements PlatformView, MethodCallHandler, AnimCube.OnCubeModelUpdatedListener, AnimCube.OnCubeAnimationFinishedListener  {
    public static final String ANIM_CUBE_SAVE_STATE_BUNDLE_ID = "animCube";
    private static final String TAG = "AnimCube";
    private final AnimCube animCube;
    private Bundle state = new Bundle();
    private final MethodChannel methodChannel;
    private int[] scheme;
    private boolean isSolved = false;
    private int[][] whiteFace = {

            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {2, 2, 2, 2, 2, 2, 2, 2, 2},
            {3, 3, 3, 3, 3, 3, 3, 3, 3},
            {4, 4, 4, 4, 4, 4, 4, 4, 4},
            {5, 5, 5, 5, 5, 5, 5, 5, 5},

    };

    private int[][] yellowFace = {

            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 2, 2, 2, 2, 2, 2, 2, 2},
            {3, 3, 3, 3, 3, 3, 3, 3, 3},
            {5, 5, 5, 5, 5, 5, 5, 5, 5},
            {4, 4, 4, 4, 4, 4, 4, 4, 4},

    };

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
            case "applyMoveSequence":
                applyMoveSequence(result);
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
            case "setYellowFace":
                setYellowFace(result);
                break;
            case "setWhiteFace":
                setWhiteFace(result);
                break;
            case "applyMoveSequenceReversed":
                applyMoveSequenceReversed(result);
                break;
            case "resetToInitialState":
                resetToInitialState(result);
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

    private void resetToInitialState(Result result){
        animCube.resetToInitialState();
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

    private void applyMoveSequence(Result result) {
        animCube.applyMoveSequence();
        result.success(null);
    }

    private void animateMoveReversed(Result result) {
        animCube.animateMoveReversed();
        result.success(null);
    }

    private void setYellowFace(Result result) {
        animCube.setCubeModel(yellowFace);
        result.success(null);
    }

    private void setWhiteFace(Result result) {
        animCube.setCubeModel(whiteFace);
        result.success(null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Log.d(TAG, "onSaveInstanceState ");
        outState.putBundle(ANIM_CUBE_SAVE_STATE_BUNDLE_ID, animCube.saveState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Log.d(TAG, "onRestoreInstanceState");
        animCube.restoreState(savedInstanceState.getBundle(ANIM_CUBE_SAVE_STATE_BUNDLE_ID));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.d(TAG, "onDestroy");
        animCube.cleanUpResources();
        //Log.d(TAG, "onDestroy: finish");
    }

    @Override
    public void onCubeModelUpdate(int[][] newCubeModel) {
        //Log.d(TAG, "Cube model updated!");
        //printCubeModel(newCubeModel);
    }

    void printCubeModel(int[][] cube) {
        Log.d(TAG, "Cube model:");
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
        Log.d(TAG, stringBuilder.toString());
    }

    @Override
    public void onAnimationFinished() {
        //Log.d(TAG, "Cube AnimationFinished!");
    }

    @Override
    public void dispose() {
    }
}
