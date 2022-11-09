//
// Created by bence on 2022-11-08.
//

#include <vector>
#include <sstream>
#include "utils.h"

#define DEFAULT_SIZE 8

int readInt(std::ifstream &filestream, int size) {
    char a = 0;
    char result[2];
    switch (size) {
        case 1:
            filestream.read(&a, 1);
            return a;
        case 2:
            filestream.read(result, 2);
            return result[0] + (result[1] << 8);
        default:
            break;
    }
    throw std::invalid_argument("This shouldn't happen");
}

int readInt(std::ifstream &filestream) {
    char result[8];
    filestream.read(result, 8);
    return *((int *) result); // if int is not 8 bit this will fail
}

std::string readNCharacters(std::ifstream &filestream, int length) {
    std::string result;
    result.reserve(length);
    for (int i = 0; i < length; i++) {
        char c = readInt(filestream, 1);
        result.push_back(c);
    }
    return result;
}

std::vector<int> readNBytesAsInts(std::ifstream &filestream, int n) {
    std::vector<int> result;
    result.reserve(n);

    for (int i = 0; i < n; ++i) {
        result.push_back(readInt(filestream, 1));
    }

    return result;
}

std::vector<std::string> split(const std::string &s, char delim) {
    std::vector<std::string> result;
    std::stringstream ss(s);
    std::string item;

    while (getline(ss, item, delim)) {
        result.push_back(item);
    }

    return result;
}
