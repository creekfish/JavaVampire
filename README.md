# JavaVampire
Java version of the MSDOS Vampire text adventure game

The original game "Vampire's Castle" was written in BASIC for MSDOS, and I first played it in the early 1980's on a Commodore Vic20 computer. A thread discussing this obscure little game can still be found here: https://www.dosgames.com/forum/viewtopic.php?t=11302. The original BASIC code can be found here: http://www.dosgames.com/vampire.zip

As a project when I was first learning the Java language (on the job in a big hurry!) I created a version of the game in Java, much refactored and cleaner than the orinal BASIC program, tho sadly lacking its nostalgic charm.

OK, enough history.  So how can you play this fantastic little text adventure you ask?

1. Download/clone it from github: git@github.com:creekfish/JavaVampire.git
2. Change to the the JavaVampire/src directory
3. Make sure you have some version of java installed (e.g. install Java SE JDK: https://www.oracle.com/java/technologies/javase-downloads.html)
4. > javac game/control/Console.java
5. > jar cfm Vampire.jar Manifest.txt game
6. > java -jar Vampire.jar vampire

Optinally after step 4 you can just run from the compiled files with no jar: > java -cp . game.control.Console vampire

Hopefully you can avoid becoming the vampire's lunch... Enjoy!
