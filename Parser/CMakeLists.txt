cmake_minimum_required(VERSION 3.16.3)
project(CAFFparser)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR})
set(CMAKE_CXX_STANDARD 11)

add_executable(CAFFparser src/main.cpp src/model/pixel.h src/parsers/CIFFparser.cpp src/parsers/CIFFparser.h src/model/CIFF.h src/model/CAFF.h src/parsers/CAFFparser.h src/utils/utils.cpp src/utils/utils.h src/parsers/CAFFparser.cpp)
