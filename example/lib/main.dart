import 'package:anim_cube/anim_cube.dart';
import 'package:flutter/material.dart';

void main() => runApp(MaterialApp(home: AnimCubeExample()));

class AnimCubeExample extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Flutter AnimCube example')),
      body: Center(
        child: TextButton(
          onPressed: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (BuildContext context) => Animation(
                  moves: "R U R'",
                ),
              ),
            );
          },
          child: Text("Press me!"),
        ),
      ),
    );
  }
}

class Animation extends StatefulWidget {
  final String moves;

  const Animation({Key? key, required this.moves}) : super(key: key);

  @override
  _AnimationState createState() => _AnimationState();
}

class _AnimationState extends State<Animation> {
  void _onAnimCubeCreated(AnimCubeController controller) {
    controller.setYellowFace();
    controller.setMoveSequence(widget.moves);
    controller.applyMoveSequenceReversed();
    controller.saveState();
  }

  @override
  Widget build(BuildContext context) {
    Size screenSize = MediaQuery.of(context).size;
    return Container(
      alignment: Alignment.center,
      height: screenSize.height,
      width: screenSize.width,
      child: Scaffold(
        body: Center(
          child: AnimCube(
            onAnimCubeCreated: _onAnimCubeCreated,
          ),
        ),
      ),
    );
  }
}
