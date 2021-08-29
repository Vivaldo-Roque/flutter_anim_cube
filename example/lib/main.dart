import 'package:anim_cube/anim_cube.dart';
import 'package:flutter/material.dart';

void main() => runApp(MaterialApp(home: AnimCubeExample()));

class AnimCubeExample extends StatelessWidget {
  void _onAnimCubeCreated(AnimCubeController controller) {
    controller.setMoveSequence("R U R'");
    controller.applyMoveSequenceReversed();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Flutter AnimCube example')),
      body: Center(
        child: AnimCube(
          onAnimCubeCreated: _onAnimCubeCreated,
        ),
      ),
    );
  }
}
