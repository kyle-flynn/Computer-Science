#ifndef DULIMARTA_FILESYSTEM_H
#define DULIMARTA_FILESYSTEM_H

#include <string>
#ifdef _WIN32
#include <windows.h>
#endif
#include <dirent.h>
#include <stack>
#include <regex>

using namespace std;
namespace gvsu {
    class FileSystem {
    private:
        std::string absPath;   // the absolute path to the file/directory
        regex name_filter;
    public:
        class gv_iter;   // forward declaration

        FileSystem (const std::string&);
        FileSystem (const std::string&, const std::regex);

        gv_iter begin() const {
            return gv_iter(absPath, name_filter);
        }

        gv_iter end() const {
            return gv_iter();  // a nullptr entry
        }

        /* the iterator inner class */
        class gv_iter {
        private:
            string curr_path;
            DIR *curr_dir;
            stack<pair<DIR*,string>> dir_stack;
            dirent *entry;
            regex filter;
        public:
            gv_iter();
            ~gv_iter();
            gv_iter(const string& path, const regex&);

            void operator++(); // prefix increment
            bool operator== (const gv_iter&) const;
            bool operator!= (const gv_iter&) const;

            void operator++(int) { // postfix increment
                operator++();
            }

            /*
             the dereference oper returns a pair.

             The first field is the leading path name
             The second field is the file name
             */
            pair<string,string> operator*() const {
                return make_pair(curr_path, entry->d_name);
            }
        };

    };
}
#endif