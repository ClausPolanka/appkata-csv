package test.unit.appkata;

import org.junit.Test;

import java.util.HashMap;

import static appkata.MaximumDistanceCalculator.calcMaxDistancesForColumnSeparator;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static test.unit.appkata.StringArrayBeautifier.asArray;
import static test.unit.appkata.StringArrayBeautifier.emptyStringArray;

public class MaximumDistanceCalculatorTest {
    private static final Integer FIRST_COLUMN = 0;
    private static final Integer SECOND_COLUMN = 1;

    @Test
    public void givenNoRowsNoDistancesAreCalculated() {
        HashMap<Integer,Integer> distances = calcMaxDistancesForColumnSeparator(emptyStringArray());
        assertThat("Number of distances", distances.size(), is(0));
    }

    @Test
    public void givenOnlyTheHeaderNoDistancesAreCalculated() {
        HashMap<Integer,Integer> distances = calcMaxDistancesForColumnSeparator(asArray("Header"));
        assertThat("Number of distances", distances.size(), is(0));
    }

    @Test
    public void givenOneRowWithOneColumn() {
        HashMap<Integer,Integer> distances = calcMaxDistancesForColumnSeparator(asArray("Header", "x"));
        assertThat("1st column max distance", distances.get(FIRST_COLUMN), is(equalTo("x".length())));
        assertThat("Number of distances", distances.size(), is(1));
    }

    @Test
    public void givenTwoRowsContainingOnlyOneColumnOnlyOneMaximumDistanceIsCalculated() {
        String[] rows = asArray("Header", "x", "xx");
        HashMap<Integer,Integer> distances = calcMaxDistancesForColumnSeparator(rows);
        assertThat("1st column max distance", distances.get(FIRST_COLUMN), is(equalTo("xx".length())));
        assertThat("Number of distances", distances.size(), is(1));
    }

    @Test
    public void givenOneRowAndTwoColumnsMaxDistanceForEachColumnAreCalculated() {
        HashMap<Integer,Integer> distances = calcMaxDistancesForColumnSeparator(asArray("Header", "x;xx"));
        assertThat("1st column max distance", distances.get(FIRST_COLUMN), is(equalTo("x".length())));
        assertThat("2nd column max distance", distances.get(SECOND_COLUMN), is(equalTo("xx".length())));
        assertThat("Number of distances", distances.size(), is(2));
    }

    @Test
    public void givenTwoRowsAndTwoColumnsMaximumDistanceForEachColumnIsCalculated() {
        String[] rows = asArray("Header",
                                "x;zz",
                                "xx;z");

        HashMap<Integer,Integer> distances = calcMaxDistancesForColumnSeparator(rows);

        assertThat("1st column max distance", distances.get(FIRST_COLUMN), is(equalTo("xx".length())));
        assertThat("2nd column max distance", distances.get(SECOND_COLUMN), is(equalTo("zz".length())));
        assertThat("Number of distances", distances.size(), is(2));
    }
}
