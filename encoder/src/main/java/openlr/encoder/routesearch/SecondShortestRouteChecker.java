package openlr.encoder.routesearch;

import openlr.encoder.OpenLREncoderProcessingException;
import openlr.map.GeoCoordinates;
import openlr.map.Line;
import openlr.map.utils.GeometryUtils;
import openlr.map.utils.PQElem;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class SecondShortestRouteChecker {

    private GeoCoordinates source;
    private GeoCoordinates destination;
    private List<? extends Line> location;
    private InternalConfigurations internalConfigurations;


    private SecondShortestRouteChecker(GeoCoordinates source, GeoCoordinates destination, List<? extends Line> location,InternalConfigurations internalConfigurations)
    {
        this.source = source;
        this.destination = destination;
        this.location = location;
        this.internalConfigurations = internalConfigurations;
    }

    private static class InternalConfigurations{
        public BiFunction<Integer,Integer,Boolean> lengthFilter = (Integer length,Integer expectedLength) -> {return false;};
        public boolean relativeToleranceSpecified = false;
    }


    public static SecondShortestRouteChecker on(List<? extends Line> location, Double relativeTolerance) throws OpenLREncoderProcessingException{
        if(location.isEmpty())
        {
           throw new OpenLREncoderProcessingException(OpenLREncoderProcessingException.EncoderProcessingError.NO_ROUTE_FOUND_ERROR);
        }
        GeoCoordinates start = location.get(0).getStartNode().getGeoCoordinates();
        GeoCoordinates end = location.get(location.size()-1).getStartNode().getGeoCoordinates();

        int locationLength = location.stream().mapToInt(Line::getLineLength).sum();

        InternalConfigurations internalConfigurations = new InternalConfigurations();

        if(relativeTolerance != null){
            int maxLengthAllowed = locationLength + (int)(locationLength * relativeTolerance);
            internalConfigurations.lengthFilter = (Integer lengthAlongSecondShortestRoute,Integer lengthAlongLocation) -> {
                if(lengthAlongLocation != null){
                    return (lengthAlongSecondShortestRoute < (lengthAlongLocation + (int)(lengthAlongLocation * relativeTolerance)));
                }else {
                    return (lengthAlongSecondShortestRoute < maxLengthAllowed);
                }
            };

            internalConfigurations.relativeToleranceSpecified = true;
        }

        return new SecondShortestRouteChecker(start, end, location, internalConfigurations);
    }


    public boolean exclude(PQElem elem, int index) {
        if(!internalConfigurations.relativeToleranceSpecified)
        {
            return false;
        }
        Set<Long> closedSet = new HashSet<>();
        closedSet.add(elem.getLine().getID());
        RouteSearchData data = new RouteSearchData();
        PQElem parent = elem.getPrevious();
        if(parent == null || index == location.size() - 1 || index ==0){
           return false;
        }
        data.addToOpen(parent);
        return exploreNetwork(closedSet,data,index);
    }

    private List<Line> getAcceptableSuccessors(PQElem current, Set<Long> closedSet) {
        List<Line> lines = new ArrayList<>();
        current.getLine().getNextLines().forEachRemaining(lines::add);
        return lines.stream()
                .filter(line -> !closedSet.contains(line.getID()))
                .filter(line -> {
                    int newDist = current.getSecondVal() + line.getLineLength();
                    return internalConfigurations.lengthFilter.apply(newDist,null);
                })
                .collect(Collectors.toList());
    }


    private void updateRouteSearchData(RouteSearchData data,List<Line> children,PQElem parent){
           for(Line child : children){

               int distanceCoveredByChild = parent.getSecondVal() + child.getLineLength();
               int heuristics = distanceCoveredByChild + (int) GeometryUtils.distance(child.getEndNode().getGeoCoordinates(),
                       destination);

               if(data.hasLengthValue(child)) {

                   if(data.getLengthValue(child) < distanceCoveredByChild)
                   {
                       PQElem newElem = new PQElem(child, heuristics, distanceCoveredByChild, parent);
                       data.updateInOpen(newElem);
                   }
               } else {
                   data.addToOpen(new PQElem(child,heuristics,distanceCoveredByChild,parent));

               }
           }
    }

    private int lengthOfSecondShortestRoute(final PQElem destination, final int index){
        Long deviationStart = location.get(index-1).getID();
        int secondShortestRouteLength =0;
        PQElem to =destination;
        while (to.getPrevious() != null && to.getPrevious().getLine().getID() != deviationStart){
            secondShortestRouteLength += to.getPrevious().getLine().getLineLength();
            to = to.getPrevious();
        }
        return secondShortestRouteLength;
    }

    private boolean exploreNetwork(Set<Long> closedSet, RouteSearchData routeSearchData,int index){
        List<Long> possibleDestinations = location.stream().map(Line::getID).collect(Collectors.toList()).subList(index+1,location.size());



        while (!routeSearchData.isOpenEmpty())
        {
            PQElem parent = routeSearchData.pollElement();
            if(possibleDestinations.contains(parent.getLine().getID())){
                int destinationIndexOnLocation = location.subList(index,location.size()).indexOf(parent.getLine());
                int subLocationLength = location.subList(index,destinationIndexOnLocation+index).stream().mapToInt(Line::getLineLength).sum();
                int secondShortestRouteLength = lengthOfSecondShortestRoute(parent,index);
                return internalConfigurations.lengthFilter.apply(secondShortestRouteLength,subLocationLength);
            } else {
                List<Line> children = getAcceptableSuccessors(parent, closedSet);
                updateRouteSearchData(routeSearchData, children,parent);
                closedSet.add(parent.getLine().getID());
            }
        }
        return false;
    }
}
