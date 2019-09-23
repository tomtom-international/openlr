package openlr.decoder.worker;

import openlr.LocationReferencePoint;
import openlr.LocationType;
import openlr.OpenLRProcessingException;
import openlr.decoder.data.CandidateLine;
import openlr.decoder.data.CandidateLinePair;
import openlr.decoder.data.CandidateLinesResultSet;
import openlr.decoder.properties.OpenLRDecoderProperties;
import openlr.map.Line;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class DecoderUtilsTest {

    private static final Mockery context = new Mockery();

    @Test
    public void testCandidatePairOrder() {
        final Line l24 = context.mock(Line.class, "l24");
        context.checking(new Expectations() {
            {
                allowing(l24).getID();
                will(returnValue(24l));
            }
        });
        final Line l21 = context.mock(Line.class, "l21");
        context.checking(new Expectations() {
            {
                allowing(l21).getID();
                will(returnValue(21l));
            }
        });
        final Line l33 = context.mock(Line.class, "l33");
        context.checking(new Expectations() {
            {
                allowing(l33).getID();
                will(returnValue(33l));
            }
        });
        final LocationReferencePoint lrp1 = context
                .mock(LocationReferencePoint.class, "lrp1");
        context.checking(new Expectations() {
            {
                allowing(lrp1).getSequenceNumber();
                will(returnValue(1));
            }
        });
        List<CandidateLine> candidates1 = new ArrayList<CandidateLine>();
        candidates1.add(new CandidateLine(l24, 1197));
        candidates1.add(new CandidateLine(l21, 1110));
        candidates1.add(new CandidateLine(l33, 855));
        CandidateLinesResultSet clrs = new CandidateLinesResultSet();
        clrs.putCandidateLines(lrp1, candidates1);

        final LocationReferencePoint lrp2 = context
                .mock(LocationReferencePoint.class, "lrp2");
        context.checking(new Expectations() {
            {
                allowing(lrp2).getSequenceNumber();
                will(returnValue(2));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(lrp2).isLastLRP();
                will(returnValue(false));
            }
        });
        List<CandidateLine> candidates2 = new ArrayList<CandidateLine>();
        candidates2.add(new CandidateLine(l24, 1164));
        candidates2.add(new CandidateLine(l21, 1149));
        candidates2.add(new CandidateLine(l33, 821));
        clrs.putCandidateLines(lrp2, candidates2);

        final LocationReferencePoint lrp3 = context
                .mock(LocationReferencePoint.class, "lrp3");
        context.checking(new Expectations() {
            {
                allowing(lrp3).getSequenceNumber();
                will(returnValue(3));
            }
        });
        context.checking(new Expectations() {
            {
                allowing(lrp3).isLastLRP();
                will(returnValue(true));
            }
        });
        List<CandidateLine> candidates3 = new ArrayList<CandidateLine>();
        candidates3.add(new CandidateLine(l24, 1194));
        candidates3.add(new CandidateLine(l33, 821));
        clrs.putCandidateLines(lrp3, candidates3);

        try {
            List<CandidateLinePair> order = DecoderUtils
                    .resolveCandidatesOrder(lrp1, lrp2, clrs, null,
                            new OpenLRDecoderProperties(null),
                            LocationType.LINE_LOCATION);
            //System.out.println(order);
        } catch (OpenLRProcessingException e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception");
        }

    }

}
