//
// Created by bence on 2022-11-09.
//

#include "../model/CAFF.h"
#include "CAFFparser.h"
#include <iostream>
#include "CIFFparser.h"
#include "../utils/utils.h"

#define CAFF_LENGTH 4


CAFF parsers::parseCAFF(std::ifstream &filestream) {
    if (!filestream.good()) {
        throw std::invalid_argument("File is unavailable!");
    }

    int id = readInt(filestream, 1);
    if (id != 1) {
        throw std::invalid_argument("Wrong file format, no header on caff file!");
    }

    readInt(filestream); // totally useless info being skipped...

    if (readNCharacters(filestream, CAFF_LENGTH) != "CAFF") {
        throw std::invalid_argument("Wrong file format!");
    }

    readInt(filestream); // totally useless info being skipped...
    int framesCount = readInt(filestream);

    int id2 = readInt(filestream, 1);
    if (id2 != 2) {
        throw std::invalid_argument("Wrong file format, no credits on caff file!");
    }
    readInt(filestream); // totally useless info being skipped...

    int year = readInt(filestream, 2);
    int month = readInt(filestream, 1);
    int day = readInt(filestream, 1);
    int hour = readInt(filestream, 1);
    int minute = readInt(filestream, 1);

    int creatorLength = readInt(filestream);

    std::string creator = readNCharacters(filestream, creatorLength);

    std::vector<std::pair<int, CIFF>> content;

    int id3 = readInt(filestream, 1);
    while (!filestream.eof()) {
        if (id3 != 3) {
            throw std::invalid_argument("Wrong file format, non content in content section in caff file!");
        }
        readInt(filestream); // totally useless info being skipped...
        int duration = readInt(filestream);
        CIFF ciff = parseCIFF(filestream);
        content.emplace_back(duration, ciff);
        id3 = readInt(filestream, 1);
    }

    if (content.size() != framesCount) {
        throw std::invalid_argument("Loaded frames count are inconsistent with metadata!");
    }

    filestream.close();
    return {SimpleDate(year, month, day, hour, minute), creator, content};
}
