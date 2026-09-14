# 2D Classic Snake Game

A modern 2D Snake arcade game built from scratch in **Java** using Swing and AWT without external game engines. Features modular architecture, event-driven rendering, integrated audio management, and local high score persistence.

## Gameplay Preview

| Start Screen | In-Game Action | Game Over Screen |
| :---: | :---: | :---: |
| ![Start Screen](doc/screenshots/start-game.png) | ![In-Game](doc/screenshots/in-game.png) | ![Game Over](doc/screenshots/game-over.png) |

## Controls & Interface

### Game Controls
| Action | Key / Input |
| :--- | :--- |
| **Move Up** | `W` or `↑` |
| **Move Down** | `S` or `↓` |
| **Move Left** | `A` or `←` |
| **Move Right** | `D` or `→` |
| **Pause / Resume** | `P` |
| **Restart (Game Over)** | `Space` or `Enter` |
| **Exit** | `Esc` |

### Interactive Sidebar HUD
* **Volume Slider / Mute**: Real-time background audio gain adjustment and toggle.
* **Grid Toggle**: Turns playfield tile grid lines on/off.
* **Wall Collision Toggle**: Switches between lethal screen edges and pass-through wraparound.
* **Info**: Displays instructions and credits.

## Core Concepts

* **Object-Oriented Programming**: `SnakeFrame` extends `JFrame`; `SnakePanel` and `SnakeStatus` extend `JPanel`. Separated into `ui` and `model` packages. Implements `ActionListener`.
* **Game Loop**: Managed via `javax.swing.Timer` (`75ms` delay) for periodic coordinate updates, food checks, and canvas repainting.
* **Collision Detection**: Uniform cell grid (`UNIT_SIZE = 50`), head-to-body array coordinate checks, and switchable boundary/wraparound modes.
* **Audio Processing**: Uses `javax.sound.sampled` (`Clip`, `AudioInputStream`) for background loops and effects, with real-time decibel scaling via `FloatControl.Type.MASTER_GAIN`.
* **Data Persistence**: High scores saved/loaded via `Scanner` and `PrintWriter` in `saveFile.txt`.

## Tech Stack

* **Language**: Java (JDK 17+)
* **GUI & Graphics**: `javax.swing.*`, `java.awt.*`
* **Audio API**: `javax.sound.sampled.*`
* **Events**: `java.awt.event.*`
* **Packaging**: `Launch4j` / `jpackage` with embedded JRE
