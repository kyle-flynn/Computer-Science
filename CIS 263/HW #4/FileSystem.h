#ifndef DULIMARTA_FILESYSTEM_H
#define DULIMARTA_FILESYSTEM_H

#include <string>
#include <sys/stat.h>
#include <dirent.h>
#include <stack>
#define USE_RE
#ifdef USE_RE
#include <regex>
#endif /* USE_RE */

using namespace std;
namespace gvsu {
    class FileSystem {
    private:
        std::string absPath;   // the absolute path to the file/directory
#ifdef USE_RE
        regex name_filter;
#endif /* USE_RE */
    public:
        class gv_iter;   // forward declaration

        FileSystem (const std::string&);
#ifdef USE_RE
        FileSystem (const std::string&, const std::regex&);
#endif /* USE_RE */

        gv_iter begin() const {
#ifndef USE_RE
            return gv_iter(absPath);
#else /* USE_RE */
            return gv_iter(absPath, name_filter);
#endif /* USE_RE */
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
#ifdef USE_RE
            regex filter;
#endif /* USE_RE */
        public:
            gv_iter();
            ~gv_iter();
#ifndef USE_RE
            gv_iter(const string& path);
#else /* USE_RE */
            gv_iter(const string& path, const regex&);
#endif /* USE_RE */

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