/**
 * Implementation of an directory gv_iter
 */

#include <cstdlib>
#include <string>
#include "FileSystem.h"

using namespace std;

namespace gvsu {
/*--- constructor ---*/
#ifndef USE_RE
    FileSystem::FileSystem (const string& path)
#else /* USE_RE */
    FileSystem::FileSystem (const string& p)
    : FileSystem(p, regex(".+",regex::extended))
{
    /* the default regular expression ".+" will not filter any filename */
}

FileSystem::FileSystem (const string& path, const regex& filter)
#endif /* USE_RE */
    {
        /* Unix/OSX: absolute path begins with a "/" such as "/path/to/file"
           Windows: absolute path begins with X:/  or X:\\
         */
        if (path[0] == '/' || path.find(":/") != string::npos ||
            path.find(":\\") != string::npos)  // is it an absolute path?
            absPath = path;
        else {
            /* concatenate the path with the current directory */
            absPath = string (getenv ("PWD")) + "/" + path;
        }
#ifdef USE_RE
        name_filter = filter;
#endif /* USE_RE */
    }

/* default iterator constructor */
    FileSystem::gv_iter::gv_iter()
    {
        curr_dir = nullptr;
        entry = nullptr;
    }

/* interator destructor */
    FileSystem::gv_iter::~gv_iter() {
        if (curr_dir != nullptr)
            closedir(curr_dir);
    }

#ifndef USE_RE
    FileSystem::gv_iter::gv_iter(const string& path)
    {
#else /* USE_RE */
        FileSystem::gv_iter::gv_iter(const string& path, const regex& re) :
    filter (re) {
#endif /* USE_RE */
        curr_path = path;
        curr_dir = opendir (path.c_str());
        entry = readdir(curr_dir);
        if (entry->d_type == DT_DIR)
            operator++();
    }

/* iterator comparison ==   and !=   */
    bool FileSystem::gv_iter::operator== (const gv_iter& other) const {
        return this->entry == other.entry;
    }

    bool FileSystem::gv_iter::operator!= (const gv_iter& other) const {
        return this->entry != other.entry;
    }

/* advance to the next regular file that matches the regex filter */
    void FileSystem::gv_iter::operator++ () {
        entry = readdir(curr_dir);
        bool found = false;
        while (!found) {
            if (entry == nullptr) { /* end of current dir? */
                closedir(curr_dir);
                curr_dir = nullptr;
                if (dir_stack.empty()) return;

                /* backup to the previous parent directory */
                curr_dir = dir_stack.top().first;
                curr_path = dir_stack.top().second;
                dir_stack.pop();
                entry = readdir(curr_dir);
            }
            else if (entry->d_type != DT_DIR) {
                /* check if the filename matches the regex filter? */
#ifndef USE_RE
                string check_name{entry->d_name};
                if (check_name.substr(check_name.length() - 3, 3) == "txt")
#else /* USE_RE */
                    if (regex_search(entry->d_name, filter))
#endif /* USE_RE */
                    found = true;
                else
                    entry = readdir(curr_dir);
            }
            else {
                /* handle a directory entry here */
                string check_name {entry->d_name};
                if (check_name == "." || check_name == "..") {
                    /* skip "." and ".." */
                    entry = readdir(curr_dir);
                }
                else {
                    /* save the current directory onto the stack */
                    dir_stack.push(make_pair(curr_dir, curr_path));
                    curr_path = curr_path + "/" + entry->d_name;

                    /* recurse into the lower level directory */
                    curr_dir = opendir(curr_path.c_str());
                    entry = readdir(curr_dir);
                }
            }
        }
        /* if we are here, we either found a regular file or
         * no more directory entries to check */

    }
}