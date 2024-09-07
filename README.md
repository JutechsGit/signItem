# Sign Item Mod

**Sign Item Mod** is a Minecraft mod for Fabric(Server) that adds a custom `/sign` command allowing players to sign items with a personalized lore message. This mod is designed for Minecraft Fabric 1.21 with Fabric API v102.

## Features

- **/sign Command:** Allows players to add a lore to the item they are holding. The lore will be in the format: `"Signed by PlayerName"`, where `PlayerName` is the name of the player executing the command.

## Installation

1. **Install Minecraft Fabric:**  
   Make sure you have Minecraft Fabric installed. You can download the Fabric installer from [Fabric's official website](https://fabricmc.net/use/).

2. **Install Fabric API:**  
   Download the Fabric API v102 from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-api) and place it in the `mods` folder of your Server installation.

3. **Add Sign Item Mod:**  
   Download the MySignMod `.jar` file from the [releases page](https://github.com/JutechsGit/signItem/releases) and place it in the `mods` folder of your Server installation.

4. **Launch Minecraft:**  
   Start Minecraft with the Fabric loader to use the `/sign` command.

## Usage

- **Command:** `/sign`
  - **Description:** Signs the item currently held in the player's main hand with the lore `"Signed by PlayerName"`.

## Requirements

- **Minecraft Version:** 1.21
- **Fabric API Version:** v102
- **Fabric Loader:** Ensure you have the Fabric Loader installed.

## Development

If you want to contribute to this mod or build it from source:

1. Clone the repository:
   ```bash
   git clone https://github.com/JutechsGit/signItem.git
