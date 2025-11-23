import java.util.Random;

public class OptiRoute {
    //Zadanie 1 część KAcpra
    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    static final Point WAREHOUSE = new Point(0, 0);

    public static Point[] generatePackages(int count) {
        if (count < 5 || count > 50) {
            throw new IllegalArgumentException("count must be 5-50");
        }

        Random random = new Random();
        Point[] packages = new Point[count];

        for (int i = 0; i < count; i++) {
            int x = random.nextInt(257);
            int y = random.nextInt(257);
            packages[i] = new Point(x, y);
        }

        return packages;
    }
    //Zadanie 2 część filipa
    public static double calculateDistance(Point a, Point b) {
        double dx = b.x - a.x;
        double dy = b.y - a.y;
        double dist = Math.sqrt(dx * dx + dy * dy);
        return Math.round(dist * 100.0) / 100.0;
    }

    public static Point[] findRoute(Point[] packages) {
        Point[] route = new Point[packages.length + 2];
        route[0] = WAREHOUSE;

        Point[] toDeliver = new Point[packages.length];
        for (int i = 0; i < packages.length; i++) {
            toDeliver[i] = packages[i];
        }

        double totalDistance = 0;
        Point current = WAREHOUSE;
        int routeIndex = 1;

        while (true) {
            Point nearest = null;
            double minDist = Double.MAX_VALUE;
            int nearestIndex = -1;
            boolean empty = true;

            for (int i = 0; i < toDeliver.length; i++) {
                if (toDeliver[i] != null) {
                    empty = false;
                    double dist = calculateDistance(current, toDeliver[i]);
                    if (dist < minDist) {
                        minDist = dist;
                        nearest = toDeliver[i];
                        nearestIndex = i;
                    }
                }
            }

            if (empty) break;

            totalDistance += minDist;
            route[routeIndex] = nearest;
            routeIndex++;
            current = nearest;
            toDeliver[nearestIndex] = null;
        }

        totalDistance += calculateDistance(current, WAREHOUSE);
        route[routeIndex] = WAREHOUSE;

        System.out.println("Total distance: " + totalDistance);
        return route;
    }

    public static void main(String[] args) {
        Point[] packages = generatePackages(10);
        Point[] route = findRoute(packages);

        System.out.println("Generated packages:");
        for (int i = 0; i < packages.length; i++) {
            System.out.println(packages[i]);
        }

        System.out.println("\nRoute:");
        for (int i = 0; i < route.length; i++) {
            if (route[i] != null) System.out.println(route[i]);
        }
    }
}