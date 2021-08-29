import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

typedef void AnimCubeCreatedCallback(AnimCubeController controller);

class AnimCube extends StatefulWidget {
  const AnimCube({
    Key? key,
    required this.onAnimCubeCreated,
  }) : super(key: key);

  final AnimCubeCreatedCallback onAnimCubeCreated;

  @override
  State<StatefulWidget> createState() => _AnimCubeState();
}

class _AnimCubeState extends State<AnimCube> {

  late AnimCubeController myAnimCubeController;

  @override
  Widget build(BuildContext context) {
    if (defaultTargetPlatform == TargetPlatform.android) {
      return Container(
        width: 350,
        child: Column(
          children: [
            Container(
              height: 350,
              width: 350,
              child: AndroidView(
                viewType: 'plugins.vivacodelab.ao/animcube',
                onPlatformViewCreated: _onPlatformViewCreated,
              ),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                ElevatedButton(
                  onPressed: () {
                    myAnimCubeController.animateMoveReversed();
                  },
                  child: RotatedBox(
                      quarterTurns: 2,
                      child: Icon(
                        Icons.forward,
                        size: 50,
                      )),
                ),
                ElevatedButton(
                  onPressed: () {
                    myAnimCubeController.animateMoveSequence();
                  },
                  child: Icon(
                    Icons.play_arrow,
                    size: 50,
                  ),
                ),
                ElevatedButton(
                  onPressed: () {
                    myAnimCubeController.stopAnimation();
                  },
                  child: Icon(
                    Icons.stop,
                    size: 50,
                  ),
                ),
                ElevatedButton(
                  onPressed: () {
                    myAnimCubeController.animateMove();
                  },
                  child: Icon(
                    Icons.forward,
                    size: 50,
                  ),
                ),
              ],
            ),
          ],
        ),
      );
    }
    return Text(
        '$defaultTargetPlatform is not yet supported by the anim_cube plugin');
  }

  void _onPlatformViewCreated(int id) {
    if (widget.onAnimCubeCreated == null) {
      return;
    }
    myAnimCubeController = new AnimCubeController._(id);
    widget.onAnimCubeCreated(myAnimCubeController);
  }
}

class AnimCubeController {
  AnimCubeController._(int id)
      : _channel = new MethodChannel('plugins.vivacodelab.ao/animcube_$id');

  final MethodChannel _channel;

  Future<void> setMoveSequence(String text) async {
    assert(text != null);
    return _channel.invokeMethod('setMoveSequence', text);
  }

  Future<void> setCubeColors(List list) async {
    assert(list != null);
    return _channel.invokeMethod('setCubeColors', list);
  }

  Future<void> animateMoveSequence() async {
    return _channel.invokeMethod('animateMoveSequence');
  }

  Future<void> applyMoveSequenceReversed() async {
    return _channel.invokeMethod('applyMoveSequenceReversed');
  }

  Future<void> restoreState() async {
    return _channel.invokeMethod('restoreState');
  }

  Future<void> saveState() async {
    return _channel.invokeMethod('saveState');
  }

  Future<void> stopAnimation() async {
    return _channel.invokeMethod('stopAnimation');
  }

  Future<void> animateMove() async {
    return _channel.invokeMethod('animateMove');
  }

  Future<void> animateMoveReversed() async {
    return _channel.invokeMethod('animateMoveReversed');
  }
}