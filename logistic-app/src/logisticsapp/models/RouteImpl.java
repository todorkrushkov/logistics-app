    package logisticsapp.models;

    import logisticsapp.models.contracts.Route;
    import java.time.LocalDate;
    import java.util.*;

    import static java.lang.String.format;

    public class RouteImpl implements Route {

        int id;
        // Route stops (Locations) are location ids
        List<Integer> routeStops;
        // location ID -> date (every next location added has date + 1)
        Map<Integer, LocalDate> scheduleMap = new LinkedHashMap<>();

        public RouteImpl(int id, List<Integer> routeStops, Map<Integer, LocalDate> scheduleMap) {
            setId(id);
            setRouteStops(routeStops);
            setScheduleMap(scheduleMap);
        }

        @Override
        public int getId() {
            return id;
        }

        private void setId(int id) {
            this.id = id;
        }

        @Override
        public List<Integer> getRouteStops() {
            return routeStops;
        }

        private void setRouteStops(List<Integer> routeStops) {
            this.routeStops = routeStops;
        }

        @Override
        public Map<Integer, LocalDate> getScheduleMap() {
            return scheduleMap;
        }

        private void setScheduleMap(Map<Integer, LocalDate> scheduleMap) {
            this.scheduleMap = scheduleMap;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            RouteImpl route = (RouteImpl) o;
            return id == route.id &&
                    Objects.equals(routeStops, route.routeStops) &&
                    Objects.equals(scheduleMap, route.scheduleMap);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, routeStops, scheduleMap);
        }

        @Override
        public String printInfo() {
            return format("-> Route %d%n", getId());
        }
    }
