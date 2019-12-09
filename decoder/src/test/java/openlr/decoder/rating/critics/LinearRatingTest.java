package openlr.decoder.rating.critics;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LinearRatingTest {

    private RatingFunction ratingFunction;

    @BeforeTest
    public void initialize() {
        ratingFunction = new LinearRating(45, 100);
    }

    @DataProvider(name = "scoreMapping")
    public static Object[][] testData() {
        return new Object[][]{{0, 100}, {45, 0}, {-45, 0}, {20, 55}, {40, 11}};
    }

    @Test(dataProvider = "scoreMapping")
    public void testLinearRating(int value, int expectedRating) {
        Assert.assertEquals(ratingFunction.rate(value), expectedRating);
    }
}
