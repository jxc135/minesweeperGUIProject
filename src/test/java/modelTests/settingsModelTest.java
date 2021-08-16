package modelTests;

import models.settingsModel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class settingsModelTest {

    private settingsModel sample1,sample2;

    @BeforeEach
    public void init() {
        sample1=new settingsModel(2,1,1);
        sample2=new settingsModel();
    }


//Tests for settingsModel object 'sample1'

    //Testing getXtiles() method
    @Test
    public void sample1test1() {
        int expectedValue = 2;
        int actualValue = sample1.getXtiles();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing getYtiles() method
    @Test
    public void sample1test2() {
        int expectedValue = 1;
        int actualValue = sample1.getYtiles();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing getMineNum() method
    @Test
    public void sample1test3() {
        int expectedValue = 1;
        int actualValue = sample1.getMineNum();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing isValidGameSetup() method
    @Test
    public void sample1test4() {
        assertTrue(settingsModel.isValidGameSetup(sample1.getXtiles(),sample1.getYtiles(),sample1.getMineNum()));
    }

    //Testing getTileColor() method
    @Test
    public void sample1test5() {
        String expectedValue = "-fx-background-color: #e5e5e5";
        String actualValue = sample1.getTileColor();
        assertEquals(expectedValue,actualValue,"Value should be: " + expectedValue );
    }

    //Testing setTileColor() method (valid green color parameter)
    @Test
    public void sample1test6() {
        String expectedValue = "-fx-background-color: #90ee90";
        sample1.setTileColor(settingsModel.greenTiles);
        String actualValue = sample1.getTileColor();
        assertEquals(expectedValue,actualValue,"Value should be: " + expectedValue );
    }

    //Testing setTileColor() method (valid blue color parameter)
    @Test
    public void sample1test7() {
        String expectedValue = "-fx-background-color: #99BBFF";
        sample1.setTileColor(settingsModel.blueTiles);
        String actualValue = sample1.getTileColor();
        assertEquals(expectedValue,actualValue,"Value should be: " + expectedValue );
    }

    //Testing setTileColor() method (invalid parameter)
    @Test
    public void sample1test8() {
        String expectedValue = "-fx-background-color: #e5e5e5";
        sample1.setTileColor("purple");
        String actualValue = sample1.getTileColor();
        assertEquals(expectedValue, actualValue);

    }
    //Testing setUpSettings() method (valid values 5,4,3)
    @Test
    public void sample1test9() {
        int expectedXtiles=5;
        int expectedYtiles=4;
        int expectedMineNum=3;
        sample1.setUpSettings(5, 4,3);
        assertEquals(expectedXtiles,sample1.getXtiles());
        assertEquals(expectedYtiles,sample1.getYtiles());
        assertEquals(expectedMineNum,sample1.getMineNum());
    }

    //Testing setUpSettings() method (invalid values 2,2,5)
    @Test
    public void sample1test10() {
        assertThrows(IllegalArgumentException.class, () -> {
            sample1.setUpSettings(2, 2, 5);
        });
    }

    //Testing setUpSettings() method (invalid values -1,3,3)
    @Test
    public void sample1test11(){
        assertThrows(IllegalArgumentException.class, () -> {
            sample1.setUpSettings(-1, 3, 3);
        });
    }

    //Testing setUpSettings() method (no mines 3,3,0)
    @Test
    public void sample1test12(){
        assertThrows(IllegalArgumentException.class, () -> {
            sample1.setUpSettings(3, 3, 0);
        });
    }

    //Testing setUpSettings() method (mine amount same as board size 4,4,16)
    @Test
    public void sample1test13(){
        int expectedXtiles=4;
        int expectedYtiles=4;
        int expectedMineNum=16;
        sample1.setUpSettings(4,4,16);
        assertEquals(expectedXtiles,sample1.getXtiles());
        assertEquals(expectedYtiles,sample1.getYtiles());
        assertEquals(expectedMineNum,sample1.getMineNum());
    }

    //Testing invalid parameters for constructor
    @Test
    public void test10() {
        assertThrows(IllegalArgumentException.class, () -> {
            new settingsModel(-1,5,5);
        });
    }

//Tests for settingsModel object 'sample2'

    //Testing getXtiles() method
    @Test
    public void sample2test1() {
        int expectedValue = 8;
        int actualValue = sample2.getXtiles();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing getYtiles() method
    @Test
    public void sample2test2() {
        int expectedValue = 8;
        int actualValue = sample2.getYtiles();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing getMineNum() method
    @Test
    public void sample2test3() {
        int expectedValue = 10;
        int actualValue = sample2.getMineNum();
        assertEquals(expectedValue, actualValue, "Value should be: " + expectedValue);
    }

    //Testing isValidGameSetup() method
    @Test
    public void sample2test4() {
        assertTrue(settingsModel.isValidGameSetup(sample2.getXtiles(),sample2.getYtiles(),sample2.getMineNum()));
    }

    //Testing getTileColor() method
    @Test
    public void sample2test5() {
        String expectedValue = "-fx-background-color: #e5e5e5";
        String actualValue = sample2.getTileColor();
        assertEquals(expectedValue,actualValue,"Value should be: " + expectedValue );
    }

    //Testing setTileColor() method (valid green color parameter)
    @Test
    public void sample2test6() {
        String expectedValue = "-fx-background-color: #90ee90";
        sample2.setTileColor(settingsModel.greenTiles);
        String actualValue = sample2.getTileColor();
        assertEquals(expectedValue,actualValue,"Value should be: " + expectedValue );
    }

    //Testing setTileColor() method (invalid parameter. Expect default Grey tile color)
    @Test
    public void sample2test7() {
        String expectedValue = "-fx-background-color: #e5e5e5";
        sample2.setTileColor("invalid_string");
        String actualValue = sample2.getTileColor();
        assertEquals(expectedValue,actualValue,"Value should be: " + expectedValue );
    }

}
