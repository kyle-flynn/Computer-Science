//
//  WordSearch.cpp
//  CS263-HW4
//

#include <iostream>
#include <fstream>
#include <sstream>
#include <algorithm>  // needed for transform()
#include <exception>
#include <regex>
#include "WordSearch.h"

WordSearch::WordSearch() {
    /* default constructor requires no additional code */
}

WordSearch::WordSearch(const string& topdir, const string& ignore_file) {
    load_ignored_words(ignore_file);
    /* filter the directory only for files ending with "txt" */
    gvsu::FileSystem dir (topdir, regex{"txt$"});

    for (auto entry : dir) {
        cout << "Reading words from " << entry.second << endl;
        read_words (entry.first + "/" + entry.second);
    }
}

void WordSearch::load_ignored_words(const string& fname) {
    ifstream ign_file (fname);
    if (!ign_file.is_open()) {
        throw ios_base::failure {"Unable to load ignored.txt"};

    }
    string word;
    while (getline(ign_file, word))
        ignored_words.insert(word);
    ign_file.close();
}

void WordSearch::read_words(const string &file_name)
{
    /* a word is THREE OR MORE alphabetical characters (lowercase) */
    const regex word_re {"[a-z]{3,}"};

    /* Alternate declaration of the above regular expr
     const regex word_re {"[[:alpha:]]{3,}"};
     */
    ifstream txt (file_name); /* file is aumatically open */

    string one_line;

    int line = 1;
    string prev = "";
    while (getline(txt, one_line)) {
        /* change to lowercase */
        transform(one_line.begin(), one_line.end(), one_line.begin(), ::tolower);
        /* iterate over the string using a regular expression */
        auto re_begin = sregex_iterator {one_line.begin(),one_line.end(), word_re};
        auto re_end = sregex_iterator{};
        for (auto word = re_begin; word != re_end; ++word) {
            /* if the word is in the ignored list, don't print it */
            if (ignored_words.find(word->str()) == ignored_words.end()) {
                word_occurrances[word->str()]++;

                if (prev.length() > 0) {
                    // Then we can start learning...
                    words_after_word[prev][word->str()]++;
                }

                prev = word->str();

                words_total++;
            }
        }
        line++;
    }
    txt.close(); /* close the file */
}


unsigned long WordSearch::word_count() const {
//    unsigned long total = 0;
//    for (pair<string, int> pair : word_occurrances) {
//        total += pair.second;
//    }
//    return total;
    return words_total;
}

set<string> WordSearch::words_of_length (int L) const {
    /* TODO complete this function */

    set<string> words;

    for (auto const& pair : word_occurrances) {
        string word = pair.first;
        if (word.length() == L) {
            words.insert(word);
        }
    }

    if (words.empty()) {
        return set<string>();
    } else {
        return words;
    }

}

pair<unsigned int,set<string>> WordSearch::most_frequent_words() const {

    if (word_occurrances.empty() || word_occurrances.size() == 0 || words_total == 0) {
        throw length_error("Data structure contains no data.");
    }

    set<string> words;

    unsigned int times_occurred = 0;
    int max_count = 0;
    
    for (auto const& pair : word_occurrances) {
        int word_count = pair.second;
        if (word_count > max_count) {
            times_occurred = 0;
            words.clear();
            max_count = word_count;
        }
        if (word_count == max_count) {
            times_occurred += word_count;
            words.insert(pair.first);
        }
    }

    return make_pair(times_occurred, words);
}

set<string> WordSearch::least_frequent_words(int count) const {
    set<string> words;

    for (auto const& pair : word_occurrances) {
        string word = pair.first;
        int word_count = pair.second;
        if (word_count <= count) {
            words.insert(word);
        }
    }

    if (!words.empty()) {
        return words;
    } else {
        return set<string>();
    }
}

string WordSearch::most_probable_word_after(const string& word) const {

    // Access is O(1)
    auto const& word_map = words_after_word.at(word);
    string probable_word;
    int max_count = 0;

    for (auto const& pair : word_map) {
        int word_count = pair.second;
        if (word_count > max_count) {
            max_count = word_count;
            probable_word = pair.first;
        }
    }

    return probable_word;
}