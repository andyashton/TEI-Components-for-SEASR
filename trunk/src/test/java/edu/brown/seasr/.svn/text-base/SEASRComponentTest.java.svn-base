package edu.brown.seasr;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.meandre.core.ComponentContext;
import org.meandre.core.ExecutableComponent;
import org.seasr.meandre.components.abstracts.AbstractExecutableComponent;

public abstract class SEASRComponentTest {

    protected SEASRTestUtils utils;
    protected Map<String, Object> inputs;
    protected Map<String, String> properties;
    private Mockery context;
    protected File baseDir;
    private Runnable reconfigurator;
    protected ExecutableComponent  c;
    
    public SEASRComponentTest() {
        reset();
    }
    
    protected abstract ExecutableComponent getComponent();

    protected void test(Expectations expectations) throws Exception {
        c = getComponent();
        utils.initializeComponent(c, properties);
        utils.prepareInputs(inputs);
        final ComponentContext cc = utils.getComponentContext();        
        context.checking(expectations);
        if (reconfigurator != null) {
            reconfigurator.run();
        }
        c.execute(cc);
        context.assertIsSatisfied();
    }

    /*
    This method needs to be called if you're manipulating the component's properties more than once in a given test.
     */
    protected void reset() {
        utils = new SEASRTestUtils();
        inputs = new HashMap<String, Object>();
        properties = new HashMap<String, String>();
        context = utils.getMockery();
        baseDir = utils.getResourcesDir();
    }

    protected void setComponentReconfigurator(Runnable reconfigurator) {
        this.reconfigurator = reconfigurator;
    }
}
