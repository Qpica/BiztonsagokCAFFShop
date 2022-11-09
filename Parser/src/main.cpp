#include <iostream>
#include <fstream>
#include "parsers/CAFFparser.h"

int main() {
    std::ifstream file1("test_files/1.caff", std::ios::binary);
    std::cout << "Parsing first file..." << std::endl;
    CAFF caff1 = parsers::parseCAFF(file1);
    std::cout << "Parsed " << caff1.content.size() << " frames" << std::endl;
    std::cout << "Parsed " << caff1.creator << " as the creator" << std::endl;
    std::cout << "Parsed " << caff1.creationDate.year << "." << caff1.creationDate.month << "."
              << caff1.creationDate.day << " " << caff1.creationDate.hour << ":" << caff1.creationDate.minute
              << " as the creation time" << std::endl << std::endl;

    std::cout << "Parsing second file..." << std::endl;
    std::ifstream file2("test_files/2.caff", std::ios::binary);
    CAFF caff2 = parsers::parseCAFF(file2);
    std::cout << "Parsed " << caff2.content.size() << " frames" << std::endl;
    std::cout << "Parsed " << caff2.creator << " as the creator" << std::endl;
    std::cout << "Parsed " << caff2.creationDate.year << "." << caff2.creationDate.month << "."
              << caff2.creationDate.day << " " << caff2.creationDate.hour << ":" << caff2.creationDate.minute
              << " as the creation time" << std::endl << std::endl;

    std::cout << "Parsing third file..." << std::endl;
    std::ifstream file3("test_files/3.caff", std::ios::binary);
    CAFF caff3 = parsers::parseCAFF(file3);
    std::cout << "Parsed " << caff3.content.size() << " frames" << std::endl;
    std::cout << "Parsed " << caff3.creator << " as the creator" << std::endl;
    std::cout << "Parsed " << caff3.creationDate.year << "." << caff3.creationDate.month << "."
              << caff3.creationDate.day << " " << caff3.creationDate.hour << ":" << caff3.creationDate.minute
              << " as the creation time" << std::endl << std::endl;
    return 0;
}
