# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.6

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/bin/cmake.exe

# The command to remove a file.
RM = /usr/bin/cmake.exe -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = "/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1"

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = "/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1/cmake-build-debug"

# Include any dependencies generated for this target.
include CMakeFiles/Project__1.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/Project__1.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/Project__1.dir/flags.make

CMakeFiles/Project__1.dir/reverse.c.o: CMakeFiles/Project__1.dir/flags.make
CMakeFiles/Project__1.dir/reverse.c.o: ../reverse.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1/cmake-build-debug/CMakeFiles" --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/Project__1.dir/reverse.c.o"
	/usr/bin/cc  $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/Project__1.dir/reverse.c.o   -c "/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1/reverse.c"

CMakeFiles/Project__1.dir/reverse.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/Project__1.dir/reverse.c.i"
	/usr/bin/cc  $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E "/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1/reverse.c" > CMakeFiles/Project__1.dir/reverse.c.i

CMakeFiles/Project__1.dir/reverse.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/Project__1.dir/reverse.c.s"
	/usr/bin/cc  $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S "/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1/reverse.c" -o CMakeFiles/Project__1.dir/reverse.c.s

CMakeFiles/Project__1.dir/reverse.c.o.requires:

.PHONY : CMakeFiles/Project__1.dir/reverse.c.o.requires

CMakeFiles/Project__1.dir/reverse.c.o.provides: CMakeFiles/Project__1.dir/reverse.c.o.requires
	$(MAKE) -f CMakeFiles/Project__1.dir/build.make CMakeFiles/Project__1.dir/reverse.c.o.provides.build
.PHONY : CMakeFiles/Project__1.dir/reverse.c.o.provides

CMakeFiles/Project__1.dir/reverse.c.o.provides.build: CMakeFiles/Project__1.dir/reverse.c.o


CMakeFiles/Project__1.dir/file_utils.c.o: CMakeFiles/Project__1.dir/flags.make
CMakeFiles/Project__1.dir/file_utils.c.o: ../file_utils.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1/cmake-build-debug/CMakeFiles" --progress-num=$(CMAKE_PROGRESS_2) "Building C object CMakeFiles/Project__1.dir/file_utils.c.o"
	/usr/bin/cc  $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/Project__1.dir/file_utils.c.o   -c "/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1/file_utils.c"

CMakeFiles/Project__1.dir/file_utils.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/Project__1.dir/file_utils.c.i"
	/usr/bin/cc  $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E "/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1/file_utils.c" > CMakeFiles/Project__1.dir/file_utils.c.i

CMakeFiles/Project__1.dir/file_utils.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/Project__1.dir/file_utils.c.s"
	/usr/bin/cc  $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S "/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1/file_utils.c" -o CMakeFiles/Project__1.dir/file_utils.c.s

CMakeFiles/Project__1.dir/file_utils.c.o.requires:

.PHONY : CMakeFiles/Project__1.dir/file_utils.c.o.requires

CMakeFiles/Project__1.dir/file_utils.c.o.provides: CMakeFiles/Project__1.dir/file_utils.c.o.requires
	$(MAKE) -f CMakeFiles/Project__1.dir/build.make CMakeFiles/Project__1.dir/file_utils.c.o.provides.build
.PHONY : CMakeFiles/Project__1.dir/file_utils.c.o.provides

CMakeFiles/Project__1.dir/file_utils.c.o.provides.build: CMakeFiles/Project__1.dir/file_utils.c.o


# Object files for target Project__1
Project__1_OBJECTS = \
"CMakeFiles/Project__1.dir/reverse.c.o" \
"CMakeFiles/Project__1.dir/file_utils.c.o"

# External object files for target Project__1
Project__1_EXTERNAL_OBJECTS =

Project__1.exe: CMakeFiles/Project__1.dir/reverse.c.o
Project__1.exe: CMakeFiles/Project__1.dir/file_utils.c.o
Project__1.exe: CMakeFiles/Project__1.dir/build.make
Project__1.exe: CMakeFiles/Project__1.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir="/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1/cmake-build-debug/CMakeFiles" --progress-num=$(CMAKE_PROGRESS_3) "Linking C executable Project__1.exe"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/Project__1.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/Project__1.dir/build: Project__1.exe

.PHONY : CMakeFiles/Project__1.dir/build

CMakeFiles/Project__1.dir/requires: CMakeFiles/Project__1.dir/reverse.c.o.requires
CMakeFiles/Project__1.dir/requires: CMakeFiles/Project__1.dir/file_utils.c.o.requires

.PHONY : CMakeFiles/Project__1.dir/requires

CMakeFiles/Project__1.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/Project__1.dir/cmake_clean.cmake
.PHONY : CMakeFiles/Project__1.dir/clean

CMakeFiles/Project__1.dir/depend:
	cd "/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1/cmake-build-debug" && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" "/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1" "/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1" "/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1/cmake-build-debug" "/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1/cmake-build-debug" "/cygdrive/c/Users/Kyle Flynn/Documents/GitHub/Computer-Science/CIS 343/Project #1/cmake-build-debug/CMakeFiles/Project__1.dir/DependInfo.cmake" --color=$(COLOR)
.PHONY : CMakeFiles/Project__1.dir/depend

