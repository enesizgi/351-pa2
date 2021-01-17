import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class CengTreeParser
{
    public static ArrayList<CengVideo> parseVideosFromFile(String filename)
    {
        ArrayList<CengVideo> videoList = new ArrayList<CengVideo>();

        // You need to parse the input file in order to use GUI tables.
        // TODO: Parse the input file, and convert them into CengVideos

        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split("\\|");
                CengVideo item = new CengVideo(Integer.parseInt(data[0]),data[1],data[2],data[3]);
                videoList.add(item);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return videoList;
    }

    public static void startParsingCommandLine() throws IOException
    {
        // TODO: Start listening and parsing command line -System.in-.
        // There are 4 commands:
        // 1) quit : End the app, gracefully. Print nothing, call nothing, just break off your command line loop.
        // 2) add : Parse and create the video, and call CengVideoRunner.addVideo(newlyCreatedVideo).
        // 3) search : Parse the key, and call CengVideoRunner.searchVideo(parsedKey).
        // 4) print : Print the whole tree, call CengVideoRunner.printTree().

        // Commands (quit, add, search, print) are case-insensitive.
        Scanner myReader = new Scanner(System.in);
        while (true) {
            String input = myReader.nextLine();
            if (input.equals("quit")) {
                //System.exit(0);
                break;
            }
            if (input.equals("print")) {
                CengVideoRunner.printTree();
            }
            else {
                String[] items = input.split("\\|");
                if (items[0].equals("search")) {
                    CengVideoRunner.searchVideo(Integer.parseInt(items[1]));
                }
                else if (items[0].equals("add")) {
                    CengVideo video = new CengVideo(Integer.parseInt(items[1]),items[2],items[3],items[4]);
                    CengVideoRunner.addVideo(video);
                }
            }


        }
    }
}
