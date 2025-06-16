package app.ai.world;

import app.ai.Solver;
import app.model.entity.Monster;
import app.model.entity.Player;
import app.model.map.Place;
import app.model.map.World;

import java.util.*;

/**
 * WorldAnalyzer provides utilities to analyze a World instance,
 * including checking connectivity, calculating shortest paths using Dijkstra’s algorithm,
 * and displaying the evolution of shortest distances step by step.
 */
public class WorldAnalyzer {

    private World world;
    public Solver solver;

    private List<Map<Place, Integer>> listeEtapes = new ArrayList<>();

    /**
     * Constructs a WorldAnalyzer for a specific world.
     *
     * @param world The world to analyze.
     */
    public WorldAnalyzer(World world) {
        this.world = world;
    }

    /**
     * Determines whether the world is fully connected.
     * That is, all places are reachable from the place with ID 0.
     *
     * @return true if the world is connected, false otherwise.
     */
    public boolean isConnexe() {
        Queue<Place> file = new LinkedList<>();
        int nbPlaces = world.getPlaces().size();
        ArrayList<Place> visited = new ArrayList<>();
        file.add(world.getPlaceFromId(0));

        while (!file.isEmpty()) {
            Place p = file.poll();
            for (Place voisin : world.getPlaces()) {
                if (!visited.contains(voisin)) {
                    visited.add(voisin);
                    file.add(voisin);
                }
            }
        }
        return visited.size() == nbPlaces;
    }

    /**
     * Checks whether all starting places in the world allow reaching at least one end place,
     * considering whether the given player can defeat all encountered monsters on the way.
     *
     * @param player The player used to check if monsters can be defeated during traversal.
     * @return true if every start place can reach at least one end place without being blocked by undefeatable monsters.
     */
    public boolean isFinishable(Player player) {
        List<Place> places = world.getPlaces();
        List<Place> startPlaces = new ArrayList<>();
        Set<Place> endPlaces = new HashSet<>();

        for (Place p : places) {
            if (p.isStart()) startPlaces.add(p);
            if (p.isEnd()) endPlaces.add(p);
        }

        if (endPlaces.isEmpty()) return false;
        
        for (Place start : startPlaces) {
            Set<Place> visited = new HashSet<>();
            Queue<Place> queue = new LinkedList<>();
            visited.add(start);
            queue.add(start);

            boolean end = false;

            while (!queue.isEmpty()) {
                Place current = queue.poll();

                if (endPlaces.contains(current)) {
                    end = true;
                    break;
                }
                for (Place neighbor : current.getPaths().keySet()) {
                    if (!visited.contains(neighbor)) {                
                        boolean canPass = true;
                        Monster monster = neighbor.getMonster();
                    	if (monster != null && !solver.canBeat(player, monster)) {
                    	    canPass = false;
                    	    break;
                    	}
                        if (canPass) {
                            visited.add(neighbor);
                            queue.add(neighbor);
                        }
                    }
                }
            }
            if (!end) return false;
        }
        return true;
    }


    /**
     * Computes the minimum distance from a starting place to a target place
     * using Dijkstra’s algorithm and stores each step of distance updates.
     *
     * @param start The starting place.
     * @param from  The destination place.
     * @return The shortest distance from start to from, or Integer.MAX_VALUE if unreachable.
     */
    public int lessDistanceToReach(Place start, Place from) {
        listeEtapes = dijkstraWithSteps(start);
        int minDistance = Integer.MAX_VALUE;

        for (Map<Place, Integer> etape : listeEtapes) {
            if (etape.containsKey(from)) {
                int distance = etape.get(from);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        return minDistance;
    }

    /**
     * Executes Dijkstra’s algorithm from a given starting place
     * and returns the list of distance updates at each step.
     *
     * @param start The starting place.
     * @return A list of maps containing the distance values for all places at each step.
     */
    public List<Map<Place, Integer>> dijkstraWithSteps(Place start) {
        List<Place> allPlaces = world.getPlaces();

        Map<Place, Integer> init = new HashMap<>();
        for (Place place : allPlaces) {
            init.put(place, Integer.MAX_VALUE);
        }
        init.put(start, 0);

        Map<Place, Integer> distances = init;
        Map<Place, Place> predecessors = new HashMap<>();

        Set<Place> nonVisites = new HashSet<>(allPlaces);

        while (!nonVisites.isEmpty()) {

            Place actual = trouveMin(distances, nonVisites);
            if (actual == null) {
                break;
            }

            nonVisites.remove(actual);

            Map<Place, Integer> voisins = actual.getPaths();

            for (Place p : voisins.keySet()) {

                if (voisins.containsKey(p)) {
                    int poids = voisins.get(p);
                    int distanceS1 = distances.getOrDefault(actual, Integer.MAX_VALUE);
                    int distanceS2 = distances.getOrDefault(p, Integer.MAX_VALUE);

                    if (distanceS2 > distanceS1 + poids) {
                        distances.put(p, distanceS1 + poids);
                        predecessors.put(p, actual);
                    }
                }
            }

            Map<Place, Integer> m = new HashMap<>(distances);
            listeEtapes.add(m);
        }
        return listeEtapes;
    }

    /**
     * Finds the place with the smallest distance value among the non-visited places.
     *
     * @param map         A map of places to their current distance values.
     * @param nonVisites  A set of places that have not been visited yet.
     * @return The place with the smallest known distance, or null if none found.
     */
    public Place trouveMin(Map<Place, Integer> map, Set<Place> nonVisites) {
        int mini = Integer.MAX_VALUE;
        Place sommet = null;
        for (Place place : nonVisites) {
            int distance = map.getOrDefault(place, Integer.MAX_VALUE);
            if (distance < mini) {
                mini = distance;
                sommet = place;
            }
        }
        return sommet;
    }
    
    /**
     * Finds the minimum total distance (in weighted units) required 
     * to reach any exit (place where isEnd() is true) starting from the given place.
     *
     * This method uses Dijkstra's algorithm to calculate the shortest distances.
     *
     * @param from The starting Place from which the search begins.
     * @return The minimum weighted distance to an exit. Returns Integer.MAX_VALUE if no exit is reachable.
     */
    public int lessDistanceToQuit(Place from) {
        listeEtapes = dijkstraWithSteps(from);
        int minDistance = Integer.MAX_VALUE;

        for (Map<Place, Integer> etape : listeEtapes) {
            if (etape.containsKey(from) && from.isEnd()) {
                int distance = etape.get(from);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }
        return minDistance;
    }

    /**
     * Calculates the smallest number of places (hops) required 
     * to reach an exit (a place where isEnd() is true) from the given starting place.
     *
     * This method uses Breadth-First Search (BFS) to minimize the number of steps.
     *
     * @param from The starting Place for the traversal.
     * @return The minimum number of hops to reach an exit, or -1 if no exit is reachable.
     */
    public int lessPlaceToQuit(Place from) {
        Queue<Place> queue = new LinkedList<>();
        Map<Place, Integer> visited = new HashMap<>();

        queue.add(from);
        visited.put(from, 0);

        while (!queue.isEmpty()) {
            Place current = queue.poll();
            int steps = visited.get(current);

            if (current.isEnd()) {
                return steps;
            }

            for (Place neighbor : current.getPaths().keySet()) {
                if (!visited.containsKey(neighbor)) {
                    visited.put(neighbor, steps + 1);
                    queue.add(neighbor);
                }
            }
        }
        return -1;
    }
}
