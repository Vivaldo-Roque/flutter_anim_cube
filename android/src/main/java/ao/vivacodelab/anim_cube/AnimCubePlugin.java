package ao.vivacodelab.anim_cube;
import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;

/** AnimCubePlugin */
public class AnimCubePlugin implements FlutterPlugin {

  @Override
  public void onAttachedToEngine(@NonNull @NotNull FlutterPluginBinding binding) {
    binding.getPlatformViewRegistry().registerViewFactory(
            "plugins.vivacodelab.ao/animcube", new AnimCubeFactory(binding.getBinaryMessenger()));
  }

  @Override
  public void onDetachedFromEngine(@NonNull @NotNull FlutterPluginBinding binding) {

  }
}
