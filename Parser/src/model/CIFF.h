//
// Created by bence on 2022-11-08.
//

#ifndef BIZTHF_CIFF_H
#define BIZTHF_CIFF_H

#include <string>
#include <utility>
#include <vector>
#include "pixel.h"

struct CIFF {
    size_t height, width;
    std::string caption;
    std::vector<std::string> tags;
    std::vector<pixel> pixels;

    CIFF(size_t height, size_t width,
         std::string caption, std::vector<std::string> tags,
         std::vector<pixel> pixels) :
            height(height), width(width),
            caption(std::move(caption)), tags(std::move(tags)),
            pixels(std::move(pixels)) {}
};

#endif //BIZTHF_CIFF_H
