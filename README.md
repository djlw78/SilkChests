SilkChests
------
Center is a plugin that lets players silk a single or double chest and all of its contents into an item that can be placed with all its contents intact. A chest is silked if the correct tool is used with the silktouch enchantment.

Compiling
---
The project is written in Java 8 and uses [Gradle](http://gradle.org/) for the build process.  

To build run: `./gradlew build`

The built jar file will be located in the `./build/libs` directory.

Dependencies are automatically handled by Gradle, however the project depends on versioned builds of CraftBukkit being located in the local Maven repository.  

In order to build these, you must first run BuildTools for all the specific versions of CraftBukkit since these jars are not distributable.  The code is pretty much dependent on NMS to provide the functionality for reading and writing NBTTagCompound data for the chest.

Contributing
---
Contributions are accepted, especially through pull requests on GitHub.
Submissions must be licensed under the GNU General Public Licence v3.

