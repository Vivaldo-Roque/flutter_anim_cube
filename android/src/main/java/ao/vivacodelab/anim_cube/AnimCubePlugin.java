package ao.vivacodelab.anim_cube;
import io.flutter.embedding.engine.plugins.FlutterPlugin;

/** AnimCubePlugin */
public class AnimCubePlugin implements FlutterPlugin {

  @Override
  public void onAttachedToEngine(FlutterPluginBinding binding) {
    binding.getPlatformViewRegistry().registerViewFactory(
            "plugins.vivacodelab.ao/animcube", new AnimCubeFactory(binding.getBinaryMessenger()));
  }

  @Override
  public void onDetachedFromEngine(FlutterPluginBinding binding) {

  }
}
