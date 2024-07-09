# Message-2Harkinian

![message 2harkinian logo](https://i.postimg.cc/FRNsY9BY/message.png)

# Introduction

Message 2Harkinian is an interpreter and converter for decompiled dialogues from the game Zelda Majora's Mask for Nintendo 64 into the compiled dialogue file for 2ship2harkinian. The program was designed for simple and straightforward use, where the user just needs to select the "message_data.h" file, define the desired parameters, and then start the conversion process.

Currently, the configs option is disabled, which means that in the current version, the program is operating only in default mode. This default mode generates a message_data file for 2s2h that matches the same hash as long as an unmodified message_data.h file is used.

# Setup

This program doesn't require installation, but it does need JDK 17 or higher to run. The reason for this is that Message 2Harkinian utilizes hexadecimal manipulation features introduced in Java 17, which aren't available in Java 8, the version typically used for end-user program execution.

The latest JDK can be obtained on the OpenJDK website. You can find it [Here](https://jdk.java.net/).

Extract the JDK to a suitable location, typically a convenient place like `C:\Program Files\Java` on Windows. After extraction, navigate to the 'bin' directory of the extracted folder. Open a command prompt in that location and use the following command:

```
java -jar <directory\m2h-betaV0.1.jar>
```

Replace <directory\m2h-betaV0.1.jar> with the actual path to your m2h-betaV0.1.jar file.

# Interface

When you run the program, the following interface will be displayed:

![message-2harnikian.png](https://i.postimg.cc/wvY85hFq/message-2harnikian.png)

Click on "Select file," choose your message_data.h file, and click "Start." Wait for the process to complete. This will generate a file named message_data_static, which is the compiled dialogue file for 2s2h. Note that the file will be generated in the location where the program was called. For example, if you called it from the JDK's bin directory, it will be in that directory.

After that you'll need to add the message_data_static file to a .otr/.o2r or mm.o2r, following this hierarchy:

```
|───text
    └───message_data_static
          └─────<generated_message_data_static_here>

```

You can use 7zip to open .o2r files and an MPQ editor to open .otr files.

# License and Final Considerations

This program is distributed under the MIT license. Suggestions and bug reports are welcome; however, updates and support will be provided as time permits and without guarantees.
