package edu.brown.seasr;

import org.junit.Before;
import org.junit.Test;
import org.meandre.core.ComponentContext;

import java.util.*;

import static org.junit.Assert.assertFalse;

/**
 * User: mdellabitta
 * Date: 2011-02-23
 * Time: 9:11 AM
 */
public class QueueBlockTest {

    //ugly hack, but not going to decouple the block and the component with an interface at the moment

    class TestComponent extends AbstractXMLStreamComponent {

        QueueBlock qb;

        public void setData(List<String> data) {
            getQueue().addAll(data);
        }

        public void setQb(QueueBlock qb) {
            this.qb = qb;
        }

        @Override
        protected boolean isSidechainReady() {
            return true;
        }

        @Override
        protected void processSidechainInputs(ComponentContext cc) throws Exception {
            //do nothing
        }

        @Override
        protected void processQueued(ComponentContext cc, Queue<String> queue) {
            foreachDoc(qb);
        }
    }

    TestComponent t;
    SEASRTestUtils utils;
    @Before
    public void setup() {
        t = new TestComponent();
        utils = new SEASRTestUtils();
    }

    @Test
    public void test() throws Exception {

        final List<String> received = new ArrayList<String>();

        QueueBlock qb = new QueueBlock() {

            @Override
            public void process(String xml) throws Exception {
                received.add(xml);
            }
        };

        List<String> testData = Arrays.asList("hi", "mom", "eat", "at", "joes");

        utils.initializeComponent(t);
        t.setQb(qb);
        t.setData(testData);
        final Map<String, Object> fakeInputs = new HashMap<String, Object>() {{
            put("xml", "yes");
        }};
        utils.prepareInputs(fakeInputs);
        
        t.execute(utils.getComponentContext());
        assertFalse(Collections.disjoint(testData, received));
    }
}
