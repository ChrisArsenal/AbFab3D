/*****************************************************************************
 *                        Shapeways, Inc Copyright (c) 2011
 *                               Java Source
 *
 * This source is licensed under the GNU LGPL v2.1
 * Please read http://www.gnu.org/copyleft/lgpl.html for more information
 *
 * This software comes with the standard NO WARRANTY disclaimer for any
 * purpose. Use it at your own risk. If there's a problem you get to fix it.
 *
 ****************************************************************************/

package abfab3d.grid;

// External Imports
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Internal Imports

/**
 * Tests the functionality of a RegionCounter
 * 
 * NOTE: Filled voxels cannot be at the edge of the grid.
 *
 * @author Tony Wong
 * @version
 */
public class TestRegionCounter extends BaseTestAttributeGrid {

    /**
     * Creates a test suite consisting of all the methods that start with "test".
     */
    public static Test suite() {
        return new TestSuite(TestRegionCounter.class);
    }

    /**
     * Test countComponents. Connected components are 6-connected (face adjacent).
     */
    public void testCountComponentsMaterial() {
        // single voxel
    	AttributeGrid grid = new ArrayAttributeGridByte(10, 10, 10, 0.001, 0.001);
        grid.setData(2, 2, 2, (byte)1, 3);
        int count = RegionCounter.countComponents(grid, 3, 10, false);
        assertEquals("Region count is not 1", 1, count);
        
        // two side adjacent voxels (share a face)
        grid.setData(3, 2, 2, (byte)1, 3);
        count = RegionCounter.countComponents(grid, 3, 10, false);
        assertEquals("Region count is not 1", 1, count);
        
        // two diagonal adjacent voxels, same plane (share an edge)
        grid.setData(3, 2, 2, (byte)0, 0);
        grid.setData(3, 3, 2, (byte)1, 3);
        count = RegionCounter.countComponents(grid, 3, 10, false);
        assertEquals("Region count is not 2", 2, count);
        
        // two diagonal adjacent voxels, different plane (share a corner)
        grid.setData(3, 3, 2, (byte)0, 0);
        grid.setData(3, 3, 3, (byte)1, 3);
        count = RegionCounter.countComponents(grid, 3, 10, false);
        assertEquals("Region count is not 2", 2, count);
    }
    
    /**
     * Test setGrid.
     */
    public void testCountComponentsMulti() {
        // two voxels, not connected
    	AttributeGrid grid = new ArrayAttributeGridByte(10, 10, 10, 0.001, 0.001);
        grid.setData(2, 2, 2, (byte)1, 3);
        grid.setData(4, 2, 2, (byte)1, 3);
        int count = RegionCounter.countComponents(grid, 3, 10, false);
        assertEquals("Region count is not 2", 2, count);
        
        grid.setData(5, 2, 2, (byte)1, 3);
        count = RegionCounter.countComponents(grid, 3, 10, false);
        assertEquals("Region count is not 2", 2, count);
    }
    
    /**
     * Test setGrid.
     */
    public void testCountComponentsDifferentMaterial() {
        // two adjacent voxels of different material
    	AttributeGrid grid = new ArrayAttributeGridByte(10, 10, 10, 0.001, 0.001);
        grid.setData(2, 2, 2, (byte)1, 3);
        grid.setData(3, 2, 2, (byte)1, 4);
        
        int count = RegionCounter.countComponents(grid, 3, 10, false);
        assertEquals("Region count is not 1", 1, count);
        
        count = RegionCounter.countComponents(grid, 4, 10, false);
        assertEquals("Region count is not 1", 1, count);
        
        grid.setData(4, 2, 2, (byte)1, 3);
        
        count = RegionCounter.countComponents(grid, 3, 10, false);
        assertEquals("Region count is not 2", 2, count);
        
        count = RegionCounter.countComponents(grid, 4, 10, false);
        assertEquals("Region count is not 1", 1, count);
    }

    /**
     * Test countComponents. Connected components are 6-connected (face adjacent).
     */
    public void testCountComponentsState() {
        // single voxel
    	AttributeGrid grid = new ArrayAttributeGridByte(10, 10, 10, 0.001, 0.001);
        grid.setData(2, 2, 2, (byte)1, 3);
        int count = RegionCounter.countComponents((Grid) grid, (byte) 1, 10, false);
        assertEquals("Region count is not 1", 1, count);
        
        // two side adjacent voxels (share a face)
        grid.setData(3, 2, 2, (byte)1, 3);
        count = RegionCounter.countComponents((Grid) grid, (byte) 1, 10, false);
        assertEquals("Region count is not 1", 1, count);
        
        // two diagonal adjacent voxels, same plane (share an edge)
        grid.setData(3, 2, 2, (byte)0, 0);
        grid.setData(3, 3, 2, (byte)1, 3);
        count = RegionCounter.countComponents((Grid) grid, (byte) 1, 10, false);
        assertEquals("Region count is not 2", 2, count);
        
        // two diagonal adjacent voxels, different plane (share a corner)
        grid.setData(3, 3, 2, (byte)0, 0);
        grid.setData(3, 3, 3, (byte)1, 3);
        count = RegionCounter.countComponents((Grid) grid, (byte) 1, 10, false);
        assertEquals("Region count is not 2", 2, count);
    }
    
    /**
     * Test countComponents. Connected components are 6-connected (face adjacent).
     */
    public void testCountComponentsMultiState() {
        // two voxels, not connected
    	AttributeGrid grid = new ArrayAttributeGridByte(10, 10, 10, 0.001, 0.001);
        grid.setData(2, 2, 2, (byte)1, 3);
        grid.setData(4, 2, 2, (byte)1, 3);
        int count = RegionCounter.countComponents((Grid) grid, (byte) 1, 10, false);
        assertEquals("Region count is not 2", 2, count);
        
        grid.setData(5, 2, 2, (byte)1, 3);
        count = RegionCounter.countComponents((Grid) grid, (byte) 1, 10, false);
        assertEquals("Region count is not 2", 2, count);
    }
    
    /**
     * Test countComponents. Connected components are 6-connected (face adjacent).
     */
    public void testCountComponentsDifferentState() {
        // two adjacent voxels of different material
    	AttributeGrid grid = new ArrayAttributeGridByte(10, 10, 10, 0.001, 0.001);
        grid.setData(2, 2, 2, (byte)1, 3);
        grid.setData(3, 2, 2, (byte)2, 4);
        
        int count = RegionCounter.countComponents((Grid) grid, (byte) 1, 10, false);
        assertEquals("Region count is not 1", 1, count);
        
        count = RegionCounter.countComponents((Grid) grid, (byte) 2, 10, false);
        assertEquals("Region count is not 1", 1, count);
        
        grid.setData(4, 2, 2, (byte)1, 3);
        
        count = RegionCounter.countComponents((Grid) grid, (byte) 1, 10, false);
        assertEquals("Region count is not 2", 2, count);
        
        count = RegionCounter.countComponents((Grid) grid, (byte) 2, 10, false);
        assertEquals("Region count is not 1", 1, count);
    }
    
    /**
     * Test countComponents. Connected components are 6-connected (face adjacent).
     */
    public void testgetComponentBoundsByVolumeMaterial() {
        // single voxel
    	AttributeGrid grid = new ArrayAttributeGridByte(10, 10, 10, 0.001, 0.001);
        grid.setData(2, 2, 2, (byte)1, 3);

        List<int[]> regionsBoundsList = RegionCounter.getComponentBoundsByVolume(grid, 3, 10, 1, false);
        assertEquals("Bounds count is not 1", 1, regionsBoundsList.size());
        
        int[] bounds = regionsBoundsList.get(0);
        assertTrue(bounds[0] == 2 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 2 && bounds[4] == 2 && bounds[5] == 2);
        
        // two side adjacent voxels (share a face)
        grid.setData(3, 2, 2, (byte)1, 3);
        
        regionsBoundsList = RegionCounter.getComponentBoundsByVolume(grid, 3, 10, 1, false);
        assertEquals("Bounds count is not 1", 1, regionsBoundsList.size());
        
        bounds = regionsBoundsList.get(0);
        assertTrue(bounds[0] == 2 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 3 && bounds[4] == 2 && bounds[5] == 2);
        
        // two diagonal adjacent voxels, same plane (share an edge)
        grid.setData(3, 2, 2, (byte)0, 0);
        grid.setData(3, 3, 2, (byte)1, 3);

        regionsBoundsList = RegionCounter.getComponentBoundsByVolume(grid, 3, 10, 1, false);
        assertEquals("Bounds count is not 2", 2, regionsBoundsList.size());
        
        // two diagonal adjacent voxels, different plane (share a corner)
        grid.setData(3, 3, 2, (byte)0, 0);
        grid.setData(3, 3, 3, (byte)1, 3);

        regionsBoundsList = RegionCounter.getComponentBoundsByVolume(grid, 3, 10, 1, false);
        assertEquals("Bounds count is not 2", 2, regionsBoundsList.size());
    }
    
    /**
     * Test getComponentBoundsByVolume.
     */
    public void testgetComponentBoundsByVolumeMaterial2() {
        // two adjacent voxels of different material
    	AttributeGrid grid = new ArrayAttributeGridByte(10, 10, 10, 0.001, 0.001);
        grid.setData(2, 2, 2, (byte)1, 3);
        grid.setData(3, 2, 2, (byte)1, 3);
        grid.setData(2, 3, 2, (byte)1, 3);
        grid.setData(2, 2, 3, (byte)1, 3);
        
        List<int[]> regionsBoundsList = RegionCounter.getComponentBoundsByVolume(grid, 3, 10, 1, false);
        assertEquals("Bounds count is not 1", 1, regionsBoundsList.size());
        
        int[] bounds = regionsBoundsList.get(0);
        assertTrue(bounds[0] == 2 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 3 && bounds[4] == 3 && bounds[5] == 3);

        grid.setData(5, 2, 2, (byte)1, 3);
        grid.setData(6, 2, 2, (byte)1, 3);
        grid.setData(5, 5, 2, (byte)1, 3);
        
        regionsBoundsList = RegionCounter.getComponentBoundsByVolume(grid, 3, 10, 1, false);
        assertEquals("Bounds count is not 3", 3, regionsBoundsList.size());
        
        bounds = regionsBoundsList.get(0);
        assertTrue(bounds[0] == 2 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 3 && bounds[4] == 3 && bounds[5] == 3);

        bounds = regionsBoundsList.get(1);
        assertTrue(bounds[0] == 5 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 6 && bounds[4] == 2 && bounds[5] == 2);
        
        bounds = regionsBoundsList.get(2);
        assertTrue(bounds[0] == 5 && bounds[1] == 5 && bounds[2] == 2 && bounds[3] == 5 && bounds[4] == 5 && bounds[5] == 2);
        
        // maxCount = 2
        regionsBoundsList = RegionCounter.getComponentBoundsByVolume(grid, 3, 2, 1, false);
        assertEquals("Bounds count is not 2", 2, regionsBoundsList.size());
        
        bounds = regionsBoundsList.get(0);
        assertTrue(bounds[0] == 2 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 3 && bounds[4] == 3 && bounds[5] == 3);

        bounds = regionsBoundsList.get(1);
        assertTrue(bounds[0] == 5 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 6 && bounds[4] == 2 && bounds[5] == 2);
        
        // minSize = 2
        regionsBoundsList = RegionCounter.getComponentBoundsByVolume(grid, 3, 10, 2, false);
        assertEquals("Bounds count is not 2", 2, regionsBoundsList.size());
        
        bounds = regionsBoundsList.get(0);
        assertTrue(bounds[0] == 2 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 3 && bounds[4] == 3 && bounds[5] == 3);

        bounds = regionsBoundsList.get(1);
        assertTrue(bounds[0] == 5 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 6 && bounds[4] == 2 && bounds[5] == 2);
    }
    
    /**
     * Test countComponents. Connected components are 6-connected (face adjacent).
     */
    public void testgetComponentBoundsByVolumeState() {
        // single voxel
    	AttributeGrid grid = new ArrayAttributeGridByte(10, 10, 10, 0.001, 0.001);
        grid.setData(2, 2, 2, (byte)1, 3);

        List<int[]> regionsBoundsList = RegionCounter.getComponentBoundsByVolume((Grid) grid, (byte) 1, 10, 1, false);
        assertEquals("Bounds count is not 1", 1, regionsBoundsList.size());
        
        int[] bounds = regionsBoundsList.get(0);
        assertTrue(bounds[0] == 2 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 2 && bounds[4] == 2 && bounds[5] == 2);
        
        // two side adjacent voxels (share a face)
        grid.setData(3, 2, 2, (byte)1, 3);
        
        regionsBoundsList = RegionCounter.getComponentBoundsByVolume((Grid) grid, (byte) 1, 10, 1, false);
        assertEquals("Bounds count is not 1", 1, regionsBoundsList.size());
        
        bounds = regionsBoundsList.get(0);
        assertTrue(bounds[0] == 2 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 3 && bounds[4] == 2 && bounds[5] == 2);
        
        // two diagonal adjacent voxels, same plane (share an edge)
        grid.setData(3, 2, 2, (byte)0, 0);
        grid.setData(3, 3, 2, (byte)1, 3);

        regionsBoundsList = RegionCounter.getComponentBoundsByVolume((Grid) grid, (byte) 1, 10, 1, false);
        assertEquals("Bounds count is not 2", 2, regionsBoundsList.size());
        
        // two diagonal adjacent voxels, different plane (share a corner)
        grid.setData(3, 3, 2, (byte)0, 0);
        grid.setData(3, 3, 3, (byte)1, 3);

        regionsBoundsList = RegionCounter.getComponentBoundsByVolume((Grid) grid, (byte) 1, 10, 1, false);
        assertEquals("Bounds count is not 2", 2, regionsBoundsList.size());
    }
    
    /**
     * Test getComponentBoundsByVolume.
     */
    public void testgetComponentBoundsByVolumeState2() {
        // two adjacent voxels of different material
    	AttributeGrid grid = new ArrayAttributeGridByte(10, 10, 10, 0.001, 0.001);
        grid.setData(2, 2, 2, (byte)1, 3);
        grid.setData(3, 2, 2, (byte)1, 3);
        grid.setData(2, 3, 2, (byte)1, 3);
        grid.setData(2, 2, 3, (byte)1, 3);
        
        List<int[]> regionsBoundsList = RegionCounter.getComponentBoundsByVolume((Grid) grid, (byte) 1, 10, 1, false);
        assertEquals("Bounds count is not 1", 1, regionsBoundsList.size());
        
        int[] bounds = regionsBoundsList.get(0);
        assertTrue(bounds[0] == 2 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 3 && bounds[4] == 3 && bounds[5] == 3);

        grid.setData(5, 2, 2, (byte)1, 3);
        grid.setData(6, 2, 2, (byte)1, 3);
        grid.setData(5, 5, 2, (byte)1, 3);
        
        regionsBoundsList = RegionCounter.getComponentBoundsByVolume((Grid) grid, (byte) 1, 10, 1, false);
        assertEquals("Bounds count is not 3", 3, regionsBoundsList.size());
        
        bounds = regionsBoundsList.get(0);
        assertTrue(bounds[0] == 2 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 3 && bounds[4] == 3 && bounds[5] == 3);

        bounds = regionsBoundsList.get(1);
        assertTrue(bounds[0] == 5 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 6 && bounds[4] == 2 && bounds[5] == 2);
        
        bounds = regionsBoundsList.get(2);
        assertTrue(bounds[0] == 5 && bounds[1] == 5 && bounds[2] == 2 && bounds[3] == 5 && bounds[4] == 5 && bounds[5] == 2);
        
        // maxCount = 2
        regionsBoundsList = RegionCounter.getComponentBoundsByVolume((Grid) grid, (byte) 1, 2, 1, false);
        assertEquals("Bounds count is not 2", 2, regionsBoundsList.size());
        
        bounds = regionsBoundsList.get(0);
        assertTrue(bounds[0] == 2 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 3 && bounds[4] == 3 && bounds[5] == 3);

        bounds = regionsBoundsList.get(1);
        assertTrue(bounds[0] == 5 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 6 && bounds[4] == 2 && bounds[5] == 2);
        
        // minSize = 2
        regionsBoundsList = RegionCounter.getComponentBoundsByVolume((Grid) grid, (byte) 1, 10, 2, false);
        assertEquals("Bounds count is not 2", 2, regionsBoundsList.size());
        
        bounds = regionsBoundsList.get(0);
        assertTrue(bounds[0] == 2 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 3 && bounds[4] == 3 && bounds[5] == 3);

        bounds = regionsBoundsList.get(1);
        assertTrue(bounds[0] == 5 && bounds[1] == 2 && bounds[2] == 2 && bounds[3] == 6 && bounds[4] == 2 && bounds[5] == 2);
    }
}
