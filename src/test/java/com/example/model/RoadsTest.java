package com.example.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

/**
 * Unit tests for Roads class
 * @author 40452739
 */
public class RoadsTest {

    private Roads roads;

    @BeforeEach
    public void setup() {
        roads = new Roads();
    }

    @Test
    public void testLongestRoadOwner_noRoads() {
        assertEquals(Roads.UNOWNED_ROAD_ID, roads.longestRoadOwner());
    }

    @Test
    public void testLongestRoadOwner_singlePlayer() {
        int save = Roads.minimumLongestRoadLength;
        Roads.minimumLongestRoadLength = 3; // temporarily lower for test
        roads.buildRoad(0, 1, 1);
        roads.buildRoad(1, 2, 1);
        roads.buildRoad(2, 3, 1);

        assertEquals(1, roads.longestRoadOwner());
        Roads.minimumLongestRoadLength = save; // restore original value
    }

    @Test
    public void testLongestRoadOwner_multiplePlayers() {
        int save = Roads.minimumLongestRoadLength;
        Roads.minimumLongestRoadLength = 3; // temporarily lower for test
        // Player 1 roads
        roads.buildRoad(0, 1, 1);
        roads.buildRoad(1, 2, 1);
        roads.buildRoad(2, 3, 1);

        // Player 2 roads
        roads.buildRoad(4, 5, 2);
        roads.buildRoad(5, 6, 2);
        roads.buildRoad(6, 7, 2);
        roads.buildRoad(7, 8, 2);

        assertEquals(2, roads.longestRoadOwner());
        
        Roads.minimumLongestRoadLength = save; // restore original value
    }

    @Test
    public void testLongestRoadOwner_tieBreaker() {
        // Player 1 roads
        int save = Roads.minimumLongestRoadLength;
        Roads.minimumLongestRoadLength = 3; // temporarily lower for test
        roads.buildRoad(0, 1, 1);
        roads.buildRoad(1, 2, 1);
        roads.buildRoad(2, 3, 1);

        // Player 2 roads with higher build IDs
        roads.buildRoad(4, 5, 2);
        roads.buildRoad(5, 6, 2);
        roads.buildRoad(6, 7, 2);

        assertEquals(1, roads.longestRoadOwner());
        Roads.minimumLongestRoadLength = save; // restore original value
    }

    @Test
    public void testLongestRoadExists() {
        assertFalse(roads.longestRoadExists());

        roads.buildRoad(0, 1, 1);
        roads.buildRoad(1, 2, 1);
        roads.buildRoad(2, 3, 1);

        assertTrue(roads.longestRoadExists());
    }

    @Test
    public void testGetLongestRoadLength() {
        assertEquals(0, roads.getLongestRoadLength());

        roads.buildRoad(0, 1, 1);
        roads.buildRoad(1, 2, 1);
        roads.buildRoad(2, 3, 1);

        assertEquals(3, roads.getLongestRoadLength());
    }

    @Test
    public void testAddRoad_invalidVertices() {
        assertThrows(IllegalArgumentException.class, () -> {
            roads.buildRoad(-1, 2, 1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            roads.buildRoad(1, -2, 1);
        });
    }

    @Test
    public void testAddRoad_valid() {
        roads.buildRoad(0, 1, 1);
        roads.buildRoad(1, 2, 1);

        // No exceptions should be thrown
    }

    

    @Test
    public void testRemoveRoad() {
        roads.buildRoad(0, 1, 1);
        roads.buildRoad(1, 2, 1);

        roads.removeRoad(0, 1);

        // Use reflection to access private field
        try {
            Field roadsField = Roads.class.getDeclaredField("roadsList");
            roadsField.setAccessible(true);
            @SuppressWarnings("unchecked")
            java.util.List<Road> roadsList = (java.util.List<Road>) roadsField.get(roads);

            assertEquals(1, roadsList.size());
            Road remainingRoad = roadsList.get(0);
            assertArrayEquals(new int[]{1, 2}, remainingRoad.getVertices());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }   

    @Test
    public void testRemoveRoad_nonExistent() {
        roads.buildRoad(0, 1, 1);

        // Should not throw any exception
        roads.removeRoad(1, 2);

        // Use reflection to access private field
        try {
            Field roadsField = Roads.class.getDeclaredField("roadsList");
            roadsField.setAccessible(true);
            @SuppressWarnings("unchecked")
            java.util.List<Road> roadsList = (java.util.List<Road>) roadsField.get(roads);

            assertEquals(1, roadsList.size());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }


    @Test
    public void testAddRoad_tracksPlayerID() {
        roads.buildRoad(0, 1, 3);

        // Use reflection to access private field
        try {
            Field trackedField = Roads.class.getDeclaredField("trackedPlayerIDS");
            trackedField.setAccessible(true);
            @SuppressWarnings("unchecked")
            java.util.Set<Integer> trackedSet = (java.util.Set<Integer>) trackedField.get(roads);
            assertTrue(trackedSet.contains(3));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    public void testAddRoad_multiplePlayerIDs() {
        roads.buildRoad(0, 1, 2);
        roads.buildRoad(1, 2, 3);
        roads.buildRoad(2, 3, 2);

        // Use reflection to access private field
        try {
            Field trackedField = Roads.class.getDeclaredField("trackedPlayerIDS");
            trackedField.setAccessible(true);
            @SuppressWarnings("unchecked")
            java.util.Set<Integer> trackedSet = (java.util.Set<Integer>) trackedField.get(roads);
            assertTrue(trackedSet.contains(2));
            assertTrue(trackedSet.contains(3));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    public void testAddRoad_duplicatePlayerID() {
        roads.buildRoad(0, 1, 2);
        roads.buildRoad(1, 2, 2);

        // Use reflection to access private field
        try {
            Field trackedField = Roads.class.getDeclaredField("trackedPlayerIDS");
            trackedField.setAccessible(true);
            @SuppressWarnings("unchecked")
            java.util.Set<Integer> trackedSet = (java.util.Set<Integer>) trackedField.get(roads);
            assertEquals(1, trackedSet.size());
            assertTrue(trackedSet.contains(2));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    public void testAddRoad_negativeBuildID() {
        assertFalse(roads.buildRoad(0, 1, Roads.UNOWNED_ROAD_ID));
    }
}
