# TAR utility package in Java

### - How to archive:

First you need to import `archiver.TAR` _facade_ as following:

```java
import archiver.TAR;
```
then you must define the files to be archived, in this example we will archive three files as shown in this snippist:
```java
File[] filesToArchive = new File[3];

filesToArchive[0] = new File("/someText.txt");
filesToArchive[1] = new File("/pic.jpg");
filesToArchive[2] = new File("/anotherTAR.tar");
```
> Note: you must import `java.io.File` class first.

after that you need extra variables to hold the directory where the archive file will be placed, and another holding the archive file name:

``` Java
File destDirectory = new File("./bar");

String archiveName = "foo";
```


we are going to place archive file in the `bar` folder which exists in the working path,`foo` would be the name of it, so the relative path of the generated file would be `./bar/foo.tar`.

> Note: if the directory does not exist the package will handle the creation for you.

> Note: archive file name may contain spaces e.g: `foo baz` and the result will be `foo-baz.tar`.

now we can archive the target files as following:

``` Java
TAR utility = new TAR();
    
utility.archive(filesToArchive, destDirectory, archiveName);
```



### - How to extract files from an archive
To extract the content of any TAR file we need variables to hold some information for us as shown in the snippist below:
``` java
File archiveToProcess = new File("archive path");

File destDirectory = new File("directory path where the content will be extracted to");
```
> Note: again you must import `java.io.file` first.

then you can simply call:

``` java
utility.unarchive(archiveToProcess, destDirectory);
```
### - Events:

Two events get fires during the archive process `onProcess` & `onDone`,  you can handle them using ***Lambda***  expression :

##### - onProcess
To get the processing progress you can use `onProcess` method as following:
``` java
TAR utility = new TAR();

utility.onProcess((String fileUnderProcess, float percentageOfComplete) -> {
	System.out.println("Processing file: " + fileUnderProcess);
	
	System.out.println("Progress: " + percentageOfComplete + "%");
});

utility.archive(...);
```
> Note: this method get called when starting archive new file from the selected ones.

##### - onDone
When the process finished, the _utility_ fire the second event which is `onDone` telling wether the process has succeeded or not:
``` java
TAR utility = new TAR();

utility.onDone((State processFinishState) -> {
	String result = processFinishState == State.Success ?
					      "Process has succeeded" : 
					      "Process has failed";

	System.out.println(result);
});

utility.archive(...);
```
> Note: you must import `archiver.enumerations.State` enum.

### - Exceptions which may be throwen:
- `java.io.FileNotFoundException`
- `java.io.file.NotDirectoryException`
- `archiver.exceptions.BadArchiveFormatException`