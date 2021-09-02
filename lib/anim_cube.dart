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
    Size sizeOfScreen = MediaQuery.of(context).size;
    if (defaultTargetPlatform == TargetPlatform.android) {
      return Container(
        width: sizeOfScreen.width,
        child: Column(
          children: [
            Container(
              width: sizeOfScreen.width,
              height: sizeOfScreen.width,
              child: AndroidView(
                viewType: 'plugins.vivacodelab.ao/animcube',
                onPlatformViewCreated: _onPlatformViewCreated,
              ),
            ),
            Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Expanded(
                  child: ElevatedButton(
                    onPressed: () {
                      myAnimCubeController.animateMoveReversed();
                    },
                    child: RotatedBox(
                        quarterTurns: 2,
                        child: Icon(
                          Icons.forward,
                        )),
                  ),
                ),
                Expanded(
                  child: ElevatedButton(
                    onPressed: () async{
                      myAnimCubeController.animateMoveSequence();
                    },
                    child: Icon(
                      Icons.play_arrow,
                    ),
                  ),
                ),
                Expanded(
                  child: ElevatedButton(
                    onPressed: () {
                      myAnimCubeController.resetToInitialState();
                      myAnimCubeController.applyMoveSequenceReversed();
                    },
                    child: Icon(
                      Icons.replay,
                    ),
                  ),
                ),
                Expanded(
                  child: ElevatedButton(
                    onPressed: () {
                      myAnimCubeController.stopAnimation();
                    },
                    child: Icon(
                      Icons.stop,
                    ),
                  ),
                ),
                Expanded(
                  child: ElevatedButton(
                    onPressed: () {
                      myAnimCubeController.animateMove();
                    },
                    child: Icon(
                      Icons.forward,
                    ),
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

  Future<void> applyMoveSequence() async {
    return _channel.invokeMethod('applyMoveSequence');
  }

  Future<void> resetToInitialState() async {
    return _channel.invokeMethod('resetToInitialState');
  }

  Future<void> setYellowFace() async {
    return _channel.invokeMethod('setYellowFace');
  }

  Future<void> setWhiteFace() async {
    return _channel.invokeMethod('setWhiteFace');
  }
}