//
// Created by bence on 2022-11-08.
//

#include <iostream>
#include "CIFFparser.h"
#include "../utils/utils.h"

#define CIFF_LENGTH 4
#define METADATA_OFFSET (4 * 8 + CIFF_LENGTH)

std::vector<pixel> parsePixels(std::vector<int> allPixels) {
    std::vector<pixel> result;
    result.reserve(allPixels.size() / 3);
    for (int i = 0; i < allPixels.size(); i += 3) {
        result.emplace_back(allPixels[i], allPixels[i + 1], allPixels[i + 2]);
    }
    return result;
}

CIFF parsers::parseCIFF(std::ifstream &filestream) {
    if (readNCharacters(filestream, CIFF_LENGTH) != "CIFF") {
        throw std::invalid_argument("Wrong file format!");
    }

    int header_size = readInt(filestream);
    int content_size = readInt(filestream);
    size_t width = readInt(filestream);
    size_t height = readInt(filestream);

    std::string captionAndTags = readNCharacters(filestream, header_size - METADATA_OFFSET);
    size_t endOfCaption = captionAndTags.find_first_of('\n');
    std::vector<std::string> tags = split(captionAndTags.substr(endOfCaption + 1, captionAndTags.length()), '\0');

    std::vector<int> allContent = readNBytesAsInts(filestream, content_size);
    std::vector<pixel> pixels = parsePixels(allContent);

    return {height, width, captionAndTags.substr(0, endOfCaption), tags, pixels};
}




