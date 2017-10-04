//
// Created by Kyle Flynn on 10/2/2017.
//
#include <bits/stdc++.h>
#include <iostream>
#include <algorithm>
#include "hakimi_havel.h"

bool hakimi_havel::run(std::vector<int>* sequence) {
    int sum = 0;
    for (auto val : *sequence) {
        std::cout << val << ' ';
        sum += val;
        if (val < 0) {
            std::cout << std::endl;
            return false;
        }
    }

    std::cout << std::endl;

    if (sum == 0) {
        return true;
    }

    int first = sequence->front();
    std::vector<int> newSequence;

    newSequence.assign(sequence->begin()+1, sequence->end());

    if (first > sequence->size()) {
        return false;
    }

    for (int i = 0; i < first; i++) {
        newSequence[i]--;
    }

    std::sort(newSequence.begin(), newSequence.end());
    std::reverse(newSequence.begin(), newSequence.end());
    return run(&newSequence);
}