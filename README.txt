This mod is intended to add simple ranching tools to Minecraft 1.12.2


This mod contains the following conceptual changes

        @Item  leatherstrip - component to saddle
        @Item  IRON_BUCKLE - component to saddle
        @Item  BUCKLED_STRAP - component to saddle

        @Recipes for vanilla items
             Saddle
             Iron Horse Armor
             Gold Horse Armor
             Diamond Horse Armor

        @ArmorMaterial  reinforced_leather - a tougher version of leather armor made by adding straps to leather armor. Slightly stronger than leather, with 1 toughness
            - repair with Buckled Straps
        @Armor  Reinforced Leather Boots - +armor, +movement speed
        TODO @Armor Reinforced Leather Cap
        TODO @Armor Reinforced Leather Leggings
        TODO @Armor Reinforced Leather Chestpiece



        @ToolMaterial DONE Quartz_edged - repair with quartz
        @Tool quartz_edged_axe - cuts whole trees down!

        @Tool quartz_edged_pickaxe - +50% ore gains




        TODO @Laquer
            TODO add a concept of laquers - dye equivelents for iron armors; parity function with leather dye
            TODO @Item laquer - dye for metallic items
            TODO @??? Make Iron Armor dye-able

        @Block&Ores
            @Item  QUARTZ_CRYSTAL
            @Block  quartzOre

        @Food
              Flour - Wheat processes into flour
              Dough - Flour + Egg = 2x Dough
              Bread - Cook Dough
              Gnocci - Potato, Flour, Egg, Bowl (better than steak!)
              Sugary Horse Treat - breeds horses


        TODO @Stretch
        TODO Add magic to the world. Based on Thaumcraft. Stretch goals include...
        TODO Every item in the game consists of materia
        TODO @MateriaCommon
        TODO @MateriaRare
        TODO @MateriaSpecial

        TODO @AlchemyFurnace - consume fuel & Items to create materia
        TODO @AlchemyPipe - move materia from source to target

        TODO @AlchemyJarCommon - store materia of a specifc type; pipe interaction
        TODO @AlchemyJarRare - store rare materia; pipe interaction
        TODO @AlchemyJarSpecial - store special materia; pipe interaction

        TODO @AlchemyInfuser - restore item durability via materia consumption; pipe fed
        TODO @AlchemyDuplicator - generates clone of item at cost of materia; pipe fed
        TODO @AlchemyRefiner - generates one tier higher of materia at cost of materia; pipe interaction

        TODO @HungryChest - consumes any item that touches it, large inventory
        TODO @MateriaFurnace - consumes items through input, spits out cooked result

        TODO @MommetEnhancer - consume mommet, material, materia to upgrade a mommet

 INPROGRESS @ItemGolem - ITEM ONLY - a craftable companion that can be given a specific task
        TODO             - Wheat, string, eyes, bones
 INPROGRESS @GolemBase - infuse a MommetInanimate with Common Materia
        TODO @MommetClay - infuse a MommetBase with Clay and Common Materia
 INPROGRESS @GolemFarm - MommetBase - give hoe, seeds
        TODO @MommetGatherer - MommetBase - assign to drop location
        TODO @MommetChopper - MommetBase - give axe
        TODO @MommetRitualist - MommetClay - give ??? - generates materia
        TODO @MommetRitualistRare - Mommet??? - give ??? - generates materiaRare
        TODO @MommetRitualistSpecial - Mommet??? - give ??? - generates materiaSpecial

     **/











_____________________________________________________________
BELOW THIS LINE IS THE GENERAL MINECRAFT FORGE MODDER README
_____________________________________________________________


-------------------------------------------
Source installation information for modders
-------------------------------------------
This code follows the Minecraft Forge installation methodology. It will apply
some small patches to the vanilla MCP source code, giving you and it access 
to some of the data and functions you need to build a successful mod.

Note also that the patches are built against "unrenamed" MCP source code (aka
srgnames) - this means that you will not be able to read them directly against
normal code.

Source pack installation information:

Standalone source installation
==============================

See the Forge Documentation online for more detailed instructions:
http://mcforge.readthedocs.io/en/latest/gettingstarted/

Step 1: Open your command-line and browse to the folder where you extracted the zip file.

Step 2: Once you have a command window up in the folder that the downloaded material was placed, type:

Windows: "gradlew setupDecompWorkspace"
Linux/Mac OS: "./gradlew setupDecompWorkspace"

Step 3: After all that finished, you're left with a choice.
For eclipse, run "gradlew eclipse" (./gradlew eclipse if you are on Mac/Linux)



If you prefer to use IntelliJ, steps are a little different.

1. Open IDEA, and import project.

2. Select your build.gradle file and have it import.

3. Once it's finished you must close IntelliJ and run the following command:

"gradlew genIntellijRuns" (./gradlew genIntellijRuns if you are on Mac/Linux)


Step 4: The final step is to open Eclipse and switch your workspace to /eclipse/ (if you use IDEA, it should automatically start on your project)

If at any point you are missing libraries in your IDE, or you've run into problems you can run "gradlew --refresh-dependencies" to refresh the local cache. "gradlew clean" to reset everything {this does not affect your code} and then start the processs again.

Should it still not work, 
Refer to #ForgeGradle on EsperNet for more information about the gradle environment.

Tip:
If you do not care about seeing Minecraft's source code you can replace "setupDecompWorkspace" with one of the following:
"setupDevWorkspace": Will patch, deobfuscate, and gather required assets to run minecraft, but will not generate human readable source code.
"setupCIWorkspace": Same as Dev but will not download any assets. This is useful in build servers as it is the fastest because it does the least work.

Tip:
When using Decomp workspace, the Minecraft source code is NOT added to your workspace in a editable way. Minecraft is treated like a normal Library. Sources are there for documentation and research purposes and usually can be accessed under the 'referenced libraries' section of your IDE.



Forge source installation
=========================

MinecraftForge ships with this code and installs it as part of the forge
installation process, no further action is required on your part.

LexManos' 

Install Video
=======================

https://www.youtube.com/watch?v=8VEdtQLuLO0&feature=youtu.be

For more details update more often refer to the Forge Forums:
http://www.minecraftforge.net/forum/index.php/topic,14048.0.html
