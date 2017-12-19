#include <iostream>
#include <cstdlib>
#include <sstream>
#include <cstring>
#define _WIN32_WINNT 0x0500
#include<windows.h>

/*int main(int argc, char *argv[])
{

std::stringstream stream;
stream << "\"Clicker.jar\"";
system(stream.str().c_str());
exit(0);
return 0;
}


*/


INT WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance,
    PSTR lpCmdLine, INT nCmdShow)
{
    WinExec("java -jar Clicker.jar", 1);
 //   CloseWindow(GetConsoleWindow());
    PostMessage(GetConsoleWindow(), WM_QUIT, 0, 0);
    return 0;
}
