//  Created by Hans Dulimarta on 10/15/14.
//  Copyright (c) 2014 Hans Dulimarta. All rights reserved.
//


#include <iostream>
#define CATCH_CONFIG_MAIN
#include "catch.hpp"
#include "WordSearch.h"

/*
 * TODO: replace the path names with your own setting. Make sure
 *       the path includes no trailing '/'
 *
 * Windows Users: include your drive letter:
 *   TOP_DIR = "C:/Users/YourName/blah/blah/.....";
 *
 */
const string TOP_DIR = "Z:/Coding/Repositories/Computer-Science/CIS 263/HW #4";

/* TODO: comment out only one of the following two directive lines
 * to select which dataset to use for testing your program.
 * When PARTIAL_DATASET is defined, you will be using a smaller dataset (10 text files)
 * When PARTIAL_DATASET is undefined, you will be using all the files in the dataset (150+ text files)
 */
//#undef PARTIAL_DATASET
#define PARTIAL_DATASET

#ifdef PARTIAL_DATASET
WordSearch gb_lib{TOP_DIR + "/ebooks/partial", TOP_DIR + "/ignored.txt"};
#else
WordSearch gb_lib{TOP_DIR + "/ebooks/all", TOP_DIR + "/ignored.txt"};
#endif
WordSearch empty;

#ifndef PARTIAL_DATASET
TEST_CASE("number of words") {
    CHECK (gb_lib.word_count() == 0x071f591L);
}

TEST_CASE("most frequent word on empty doc") {
    CHECK_THROWS_AS(empty.most_frequent_words(), length_error);
}

TEST_CASE("words by length") {
    int vals[] = {0,0,0, 2123, 6605, 0x3155, 0x446b, 0x4bce, 0x456f,
        0x38f9, 0x299f, 6779, 4014, 2134, 1040, 489, 187, 92, 37, 14};

    for (int len = 3; len < 20; len++) {
        auto myset = gb_lib.words_of_length(len);
        CHECK (vals[len] == myset.size());
    }
}

TEST_CASE("most frequent word") {
    auto result = gb_lib.most_frequent_words();
    CHECK (result.first == 0x186a6);
    CHECK (result.second.size() == 1);
    CHECK (result.second.count("was") != 0);
}

TEST_CASE("files with most frequent word(s)") {
    auto result = gb_lib.files_with_most_frequent_words();
    CHECK (result.size() == 151);
    int count = 0;
    for (string f : result) {
        auto pos = f.find_last_of("/");
        if (f.substr(pos) == "/4warn10.txt") {
            count ++;
            break;
        }
    }
    CHECK (count == 1);
}

TEST_CASE("Least frequent words") {
    auto result = gb_lib.least_frequent_words(3);
    CHECK(result.size() == 0xf42b);
    CHECK(result.find("abdomens") != result.end());
    CHECK(result.find("abstractly") != result.end());
    CHECK(result.find("adventurously") != result.end());
    CHECK(result.find("amphitheaters") != result.end());
    CHECK(result.find("bandwagon") != result.end());
    CHECK(result.find("barleystubb") != result.end());
    CHECK(result.find("belabouring") != result.end());
    CHECK(result.find("bombazine") != result.end());
    CHECK(result.find("caliphate") != result.end());
    CHECK(result.find("chancellorship") != result.end());
    CHECK(result.find("determinant") != result.end());
    CHECK(result.find("emphatical") != result.end());
    CHECK(result.find("featherbacks") != result.end());
    CHECK(result.find("gaucheries") != result.end());
    CHECK(result.find("halberstadt") != result.end());
    CHECK(result.find("immobilize") != result.end());
    CHECK(result.find("indiscrimination") != result.end());
    CHECK(result.find("japanization") != result.end());
    CHECK(result.find("kreuzungen") != result.end());
    CHECK(result.find("leaderless") != result.end());
    CHECK(result.find("maidenhoods") != result.end());
    CHECK(result.find("naysaying") != result.end());
    CHECK(result.find("omniscients") != result.end());
    CHECK(result.find("parallelograms") != result.end());
    CHECK(result.find("quibbled") != result.end());
    CHECK(result.find("rationalizing") != result.end());
    CHECK(result.find("schizophrenic") != result.end());
    CHECK(result.find("theosophy") != result.end());
    CHECK(result.find("unintermittent") != result.end());
    CHECK(result.find("valedictory") != result.end());
    CHECK(result.find("wastepaper") != result.end());
    CHECK(result.find("xenophobic") != result.end());
    CHECK(result.find("yosemite") != result.end());
    CHECK(result.find("zeitlinger") != result.end());
}

TEST_CASE("Files with Least frequent words") {
    auto result = gb_lib.files_with_least_frequent_words(3);
    CHECK(result.size() == 154);
}

TEST_CASE("Next word") {
    CHECK (gb_lib.most_probable_word_after("account") == "for");
    CHECK (gb_lib.most_probable_word_after("after") == "all");
    CHECK (gb_lib.most_probable_word_after("all") == "his");
    CHECK (gb_lib.most_probable_word_after("alternatively") == "give");
    CHECK (gb_lib.most_probable_word_after("art") == "thou");
    CHECK (gb_lib.most_probable_word_after("better") == "than");
    CHECK (gb_lib.most_probable_word_after("covered") == "with");
    CHECK (gb_lib.most_probable_word_after("does") == "not");
    CHECK (gb_lib.most_probable_word_after("its") == "own");
    CHECK (gb_lib.most_probable_word_after("own") == "disk");
    CHECK (gb_lib.most_probable_word_after("disk") == "keeping");
    CHECK (gb_lib.most_probable_word_after("keeping") == "electronic");
    CHECK (gb_lib.most_probable_word_after("electronic") == "texts");
}

#else


TEST_CASE("number of words") {
CHECK (gb_lib.word_count() == 402040);
}


TEST_CASE("words by length") {
int vals[] = {0,0,0, 688, 1922, 3279, 4383, 4583, 4102, 3195, 2158, 1278, 718, 342, 148, 69, 14, 5, 2, 1};

for (int len = 3; len < 20; len++) {
auto myset = gb_lib.words_of_length(len);
CHECK (vals[len] == myset.size());
}
}

TEST_CASE("most frequent word on empty doc") {
CHECK_THROWS_AS(empty.most_frequent_words(), length_error);
}

TEST_CASE("most frequent word") {
auto result = gb_lib.most_frequent_words();
CHECK (result.first == 6069);
CHECK (result.second.size() == 1);
CHECK (result.second.count("you") != 0);
}

TEST_CASE("Least frequent words") {
auto result = gb_lib.least_frequent_words(10);
CHECK(result.size() == 22167);
CHECK(result.find("woven") != result.end());
CHECK(result.find("wonne") != result.end());
}

TEST_CASE("Next word") {
CHECK (gb_lib.most_probable_word_after("are") == "not");
CHECK (gb_lib.most_probable_word_after("would") == "have");
CHECK (gb_lib.most_probable_word_after("might") == "have");
CHECK (gb_lib.most_probable_word_after("have") == "been");
CHECK (gb_lib.most_probable_word_after("could") == "not");
CHECK (gb_lib.most_probable_word_after("had") == "been");
}

#endif