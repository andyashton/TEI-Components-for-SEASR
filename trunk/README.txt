

                               T E I S E A S R


0. REQUIREMENTS

* A Java Development Kit, version 6 or later (http://java.oracle.com)

* Apache Ant, version 1.7 or later (http://ant.apache.org/)

* A working Meandre infrastructure. (http://seasr.org). These components were
developed against version 1.4.8, but also tested with 1.4.10.

1. INTRODUCTION

These components were developed as part of a National Endowment for the
Humanities Digital Humanities Start-Up Grant
(http://www.neh.gov/grants/guidelines/digitalhumanitiesstartup.html). The goal
of this project is to explore how the Meandre framework can enable processing
and mining of Text Encoding Initiative-encoded texts (http://www.tei-c.org/).
The components include some for doing generic XML processing as well.

2. BUILDING

* Download and unpack the source on the host your Meandre instance is running
on. THIS IS IMPORTANT -- it won't work if you specify a remote host.

* Bring up a terminal or command prompt window and change directory to the
project directory. You should see a file named "build.xml" in this directory.
This is Ant's buildfile.

* Edit the build properties in build.xml just under the line that says
"Default settings for the 'upload-components' task" to match the information
for your Meandre instance.

* Assuming your Ant and JDK installs are working correctly and are on your
PATH, run the following command to build the source:

 ant compile

* If you're interested in running the tests that come with the codebase, or
enjoy scrolling text, run the following command:

 ant run-tests

* To install the compiled components, run the following command:

 ant upload-components

* You should now be able to launch the Meandre Workbench in your browser and
see the teiseasr components available for your use!
