# Wurst MC 1.7

:boom: Wurst Client for Minecraft 1.7.2 - 1.7.10

## About

This repository contains the source code of Wurst 1.4 pre-release 1 and compiled versions of all Wurst releases, pre-releases, public beta releases and private beta releases for Minecraft 1.7.x, except for private Beta version 1.0, which had never been released.

The source code is only available for Wurst 1.4 pre-release 1 because we didn't use version control when we made the Minecraft 1.7 version of Wurst.

## Installation

### For Users

If you just want to use Wurst, follow these steps:

1. [Download Wurst.](https://www.wurstclient.net/download/minecraft-1-7-x/)

2. [Install Wurst.](https://www.wurstclient.net/how-to-install/).

### For Developers (Windows only)

If you want to edit Wurst, follow these steps:

1. Fork and clone this repository.

2. Import it to Eclipse (Import... > Existing Projects into Workspace).

3. Decompile Minecraft 1.7.2 using MCP 9.03, create a folder named `mc` (next to the `src` folder) and put the decompiled Minecraft source code in there.

4. Go to the [`patch` folder](/patch) and run `initialize.bat` (requires git to be installed and added to the path so that the script can use it).

5. Create a `lib` folder (also next to `src`), then add Minecraft 1.7.2's libraries and Slick2D to it.

All errors should have disappeared by now and you should be able to launch Wurst from the Eclipse project. If not, something is wrong.
