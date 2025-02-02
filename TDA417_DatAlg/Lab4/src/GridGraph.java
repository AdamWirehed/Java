
import java.lang.Math;

import java.util.List;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Random;

import java.util.stream.Collectors;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;


public class GridGraph implements DirectedGraph<GridGraph.Coord> {

    private char[][] grid;
    private int width;
    private int height;

    // Characters from Moving AI Lab:
    //   . - passable terrain
    //   G - passable terrain
    //   @ - out of bounds
    //   O - out of bounds
    //   T - trees (unpassable)
    //   S - swamp (passable from regular terrain)
    //   W - water (traversable, but not passable from terrain)
    // Characters from http://www.delorie.com/game-room/mazes/genmaze.cgi
    //   | - +  walls
    //   space  passable
    // Note: "-" must come last in allowedChars, because it's a regular expression

    private static String allowedChars = ".G@OTSW +|-"; 
    private static String passableChars = ".G ";


    public static class Coord {
        public final int x, y;
        public Coord(int x, int y) {
            this.x = x; this.y = y;
        }
        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof Coord)) return false;
            Coord other = (Coord) o;
            return this.x == other.x && this.y == other.y;
        }
        public int hashCode() {
            return (this.x << 8) ^ this.y;
        }
        public String toString() {
            return x + ":" + y;
        }
    }


    public GridGraph(String file) throws IOException {
        grid = Files.lines(Paths.get(file))
            .filter(line -> line.matches("^[" + allowedChars + "]+$"))
            .map(line -> line.toCharArray())
            .toArray(char[][]::new);
        height = grid.length;
        width = grid[0].length;
        for (char[] row : grid)
            if (row.length != width)
                throw new IllegalArgumentException("Malformatted grid, row widths don't match.");
    }


    public int width() {
        return width;
    }


    public int height() {
        return height;
    }


    public List<DirectedEdge<Coord>> outgoingEdges(Coord p) {
        List<DirectedEdge<Coord>> outgoing = new LinkedList<>();
        for (var dx = -1; dx <= +1; dx++) 
            for (var dy = -1; dy <= +1; dy++) 
                if (!(dx == 0 && dy == 0)) 
                    if (passable(p.x+dx, p.y+dy)) {
                        Coord q = new Coord(p.x+dx, p.y+dy);
                        double w = Math.sqrt(dx*dx + dy*dy);
                        outgoing.add(new DirectedEdge<>(p, q, w));
                    }
        return outgoing;
    }


    public boolean passable(int x, int y) {
        return x >= 0 && y >= 0 && x < width-1 && y < height-1 && passableChars.indexOf(grid[y][x]) >= 0;
    }


    public double guessCost(Coord start, Coord end) {
        double distx = end.x - start.x;
        double disty = end.y - start.y;
        double dist = Math.sqrt(Math.pow(distx,2) + Math.pow(disty,2));
        return dist;
    }


    public String showGrid() {
        return showGrid(new LinkedList<>());
    }


    public String showGrid(List<Coord> path) {
        StringBuilder s = new StringBuilder();
        s.append("Bitmap graph of dimesions " + width + " x " + height + " pixels\n");
        int ctr = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) 
                s.append(path.contains(new Coord(x, y)) ? "*" : grid[y][x]);
            s.append("\n");
        }
        return s.toString();
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(showGrid());
        s.append("\nExample random states and edges:\n");
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int x, y;
            do {
                x = random.nextInt(width-2) + 1;
                y = random.nextInt(height-2) + 1;
            } while (!passable(x, y));
            Coord p = new Coord(x, y);
            List<DirectedEdge<Coord>> edges = outgoingEdges(p);
            s.append(p.x + ":" + p.y + " --> " + edges.stream()
                     .map(e -> e.to().x + ":"+ e.to().y + "[" + e.weight() + "]")
                     .collect(Collectors.joining(", ")) + "\n");
        }
        return s.toString();
    }


    /**
     * Unit tests the class
     * @param args  the command-line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println(new GridGraph(args[0]));
        } catch (Exception e) {
            // If there is an error, print it and a little command-line help
            e.printStackTrace();
            System.err.println();
            System.err.println("Usage: java GridGraph gridfile");
            System.exit(1);
        }
    }

}
