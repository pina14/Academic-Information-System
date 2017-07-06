package pt.isel.ls.Phase0;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntsTests {

    @Test
    public void max_returns_greatest(){
        assertEquals(1, Ints.max(1, -2));
        assertEquals(1, Ints.max(-2,1));
        assertEquals(-1, Ints.max(-1,-2));
        assertEquals(-1, Ints.max(-2,-1));
    }

    @Test
    public void indexOfBinary_returns_negative_if_not_found(){
        // Arrange
        int[] v = {1,2,3};

        // Act
        int ix = Ints.indexOfBinary(v,0,3,4);

        // Assert
        assertTrue(ix < 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void indexOfBinary_throws_IllegalArgumentException_if_indexes_are_not_valid(){
        // Arrange
        int[] v = {1,2,3};

        // Act
        int ix = Ints.indexOfBinary(v, 2, 1, 4);

        // Assert
        assertTrue(ix < 0);
    }

    @Test
    public void indexOfBinary_right_bound_parameter_is_exclusive(){
        // Arrange
        int[] v = {2,2,2};

        // Act
        int ix = Ints.indexOfBinary(v, 1, 1, 2);

        // Assert
        assertTrue(ix < 0);
    }

    @Test
    public void indexOfBinary_n_equals_not_existing(){
        // Arrange
        int[] v = {3,3,3,3};

        // Act
        int ix = Ints.indexOfBinary(v, 0, 4, 4);

        // Assert
        assertTrue(ix < 0);
    }

    @Test
    public void indexOfBinary_n_equals(){
        // Arrange
        int[] v = {3,3,3,3};

        // Act
        int ix = Ints.indexOfBinary(v, 0, 4, 3);

        // Assert
        assertTrue(ix >= 0 && ix <= 3);
    }

    @Test
    public void indexOfBinary_one_element(){
        // Arrange
        int[] v = {4};

        // Act
        int ix = Ints.indexOfBinary(v, 0, 1, 4);

        // Assert
        assertTrue(ix == 0);
    }

    @Test
    public void indexOfBinary_one_element_not_existing(){
        // Arrange
        int[] v = {2};

        // Act
        int ix = Ints.indexOfBinary(v, 0, 1, 4);

        // Assert
        assertTrue(ix < 0);
    }

    @Test
    public void indexOfBinary_n_elements(){
        // Arrange
        int[] v = {1,2,3,4,5};

        // Act
        int ix = Ints.indexOfBinary(v, 0, 5, 4);

        // Assert
        assertTrue(ix == 3);
    }

    @Test
    public void indexOfBinary_n_elements_with_half_size(){
        // Arrange
        int[] v = {1,2,3,4,5};

        // Act
        int ix = Ints.indexOfBinary(v, 2, 5, 4);

        // Assert
        assertTrue(ix == 3);
    }

    @Test
    public void indexOfBinary_n_elements_not_existing(){
        // Arrange
        int[] v = {1,2,3,4,5};

        // Act
        int ix = Ints.indexOfBinary(v, 0, 5, 9);

        // Assert
        assertTrue(ix < 0);
    }

    @Test
    public void indexOfBinary_most_right_element_odd(){
        // Arrange
        int[] v = {1,2,3,4,5};

        // Act
        int ix = Ints.indexOfBinary(v, 0, 5, 5);

        // Assert
        assertTrue(ix == 4);
    }

    @Test
    public void indexOfBinary_most_right_element_even(){
        // Arrange
        int[] v = {1,2,3,4};

        // Act
        int ix = Ints.indexOfBinary(v, 0, 4, 4);

        // Assert
        assertTrue(ix == 3);
    }

    @Test
    public void indexOfBinary_most_left_element_odd(){
        // Arrange
        int[] v = {1,2,3,4,5};

        // Act
        int ix = Ints.indexOfBinary(v, 0, 5, 1);

        // Assert
        assertTrue(ix == 0);
    }

    @Test
    public void indexOfBinary_most_left_element_even(){
        // Arrange
        int[] v = {1,2,3,4};

        // Act
        int ix = Ints.indexOfBinary(v, 0, 4, 1);

        // Assert
        assertTrue(ix == 0);
    }
}