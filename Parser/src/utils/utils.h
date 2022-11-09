//
// Created by bence on 2022-11-08.
//

#ifndef BIZTHF_UTILS_H
#define BIZTHF_UTILS_H

#include <fstream>

int readInt(std::ifstream &filestream);

int readInt(std::ifstream &filestream, int size);

std::vector<int> readNBytesAsInts(std::ifstream &filestream, int n);

std::vector<std::string> split(const std::string &s, char delim);

std::string readNCharacters(std::ifstream &filestream, int length);

#endif //BIZTHF_UTILS_H
